package com.jcaseydev;

/////////////////
// File: Job.java
// Date: 1 Nov 2019
// Author: Justin Casey
// Purpose: Class defining the Job
// object.

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

class Job extends Thing implements Runnable {

  private final SeaPort port;
  private Ship ship;
  private JTable jobsTable;
  private DefaultTableModel jobsTableModel;

  private ArrayList<String> requirements = new ArrayList<>();
  private JobStatus status = JobStatus.SUSPENDED;
  private boolean isRunning = true;
  private boolean isCanceled = false;
  private boolean isFinished = false;
  private double duration;
  private Thread thread;

  // GUI Variables
  private SeaPortProgram program;

  // Create JPanels
  private JPanel statusPanel;
  private JPanel progressPanel;
  private JPanel suspendPanel;
  private JPanel cancelPanel;
  private JLabel statusLabel;

  private JProgressBar progressBar;

  Job(Scanner sc, SeaPortProgram program, HashMap<Integer, Ship> shipsHashMap) {

    super(sc);
    if (sc.hasNextDouble()) {
      duration = sc.nextDouble();
    }
    while (sc.hasNext()) {
      requirements.add(sc.next());
    }

    this.program = program;
    jobsTable = program.getJobsTable();
    jobsTableModel = (DefaultTableModel) jobsTable.getModel();

    ship = shipsHashMap.get(this.getParent());
    port = ship.getPort();

    thread = new Thread(this, ship.getName() + "-" + this.getName());

    buildGUI();
  }

  private void buildGUI() {

    // Create JPanels
    statusPanel = new JPanel(new BorderLayout());
    progressPanel = new JPanel(new BorderLayout());
    suspendPanel = new JPanel(new BorderLayout());
    cancelPanel = new JPanel(new BorderLayout());

    // Set Panel Borders
    statusPanel.setBorder(null);
    progressPanel.setBorder(null);
    suspendPanel.setBorder(null);
    cancelPanel.setBorder(null);

    // Create Components
    statusLabel = new JLabel();
    progressBar = new JProgressBar(0, 100000);
    JButton suspendBtn = new JButton("Pause");
    JButton cancelBtn = new JButton("Cancel");

    // Set Component Borders
    statusLabel.setBorder(null);
    progressBar.setBorder(null);
    suspendBtn.setBorder(null);
    cancelBtn.setBorder(null);

    // statusLabel Settings
    statusLabel.setForeground(Color.BLACK);
    statusLabel.setHorizontalAlignment(JLabel.CENTER);
    setStatus(status);

    // progressBar Settings
    progressBar.setStringPainted(true);

    // Add Components
    statusPanel.add(statusLabel, BorderLayout.CENTER);
    progressPanel.add(progressBar, BorderLayout.CENTER);
    suspendPanel.add(suspendBtn, BorderLayout.CENTER);
    cancelPanel.add(cancelBtn, BorderLayout.CENTER);

    // Action Listeners
    suspendBtn.addActionListener(e -> toggleIsRunning());
    cancelBtn.addActionListener(e -> cancelJob());
  }

  private void toggleIsRunning() {
    isRunning = !isRunning;
  }

  private void cancelJob() {
    isCanceled = true;
    isRunning = false;
  }

  private void setStatus(JobStatus status) {
    this.status = status;
    switch (status) {
      case RUNNING:
        statusPanel.setBackground(Color.GREEN);
        break;
      case SUSPENDED:
        statusPanel.setBackground(Color.YELLOW);
        break;
      case WAITING:
        statusPanel.setBackground(Color.ORANGE);
        break;
      case DONE:
        statusPanel.setBackground(Color.RED);
        program.updateLog(JobMessage.FINISHED, this.getName(), ship.getName());
        isFinished = true;
        break;
      case CANCELED:
        statusPanel.setBackground(Color.RED);
        program.updateLog(JobMessage.CANCELED, this.getName(), ship.getName());
        isFinished = true;
        break;
    }
    statusLabel.setText(status.toString());
  }

  JPanel getStatusPanel() {
    return statusPanel;
  }

  JPanel getProgressPanel() {
    return progressPanel;
  }

  JPanel getSuspendPanel() {
    return suspendPanel;
  }

  JPanel getCancelPanel() {
    return cancelPanel;
  }

  Thread getThread() {
    return thread;
  }

  @Override
  public void run() {
    long currentTime = System.currentTimeMillis();
    long startTime = currentTime;
    long stopTime = (long) (startTime + duration * 1000);

    synchronized (port) {
      canRun();
      while (isWaiting()) {
        setStatus(JobStatus.WAITING);
        try {
          port.wait();
        } catch (InterruptedException ignored) {
        }
      }
      program.updateResourceDisplay();
      ship.setBusy(true);
    }

    program.updateLog(JobMessage.STARTED, this.getName(), ship.getName());

    while (startTime < stopTime && !isCanceled) {
      try {
        Thread.sleep(100);
      } catch (InterruptedException ignored) {
      }

      if (isRunning) {
        setStatus(JobStatus.RUNNING);
        startTime += 100;
        progressBar.setValue((int) (((startTime - currentTime) / duration) * 100));
      } else {
        setStatus(JobStatus.SUSPENDED);
      }
      jobsTable.tableChanged(new TableModelEvent(jobsTableModel));
    }

    if (isCanceled && status != JobStatus.DONE) {
      setStatus(JobStatus.CANCELED);
    } else {
      setStatus(JobStatus.DONE);
    }

    isFinished = true;
    jobsTable.tableChanged(new TableModelEvent(jobsTableModel));

    synchronized (port) {
      deallocateWorkers();
      program.updateResourceDisplay();
      ship.setBusy(false);
      for (Job jobs : ship.getJobs()) {
        if (!(jobs.isFinished)) {
          port.notifyAll();
          return;
        }
      }

      Dock dock = ship.getDock();

      if (dock != null) {
        program.updateLog(JobMessage.DEPARTED, dock.getName(), ship.getName());
        dock.setShip(null);

        if (!port.getQue().isEmpty()) {
          Ship newShip = port.getQue().remove(0);
          dock.setShip(newShip);
          newShip.setDock(dock);
          program.updateLog(JobMessage.ARRIVED, dock.getName(), newShip.getName());
        }
      } else {
        program.updateLog(JobMessage.DEPARTED, ship.getPort().getName(), ship.getName());
      }

      port.notifyAll();
    }
  }


  private void canRun() {
    boolean canRun = hasRequiredWorkers(port.getPersons());
    if (!canRun) {
      cancelJob();
    }
  }

  private synchronized boolean isWaiting() {
    if (!isRunning) {
      return false;
    }

    if (ship.isBusy() || ship.getDock() == null) {
      return true;
    } else {
      if (!requirements.isEmpty()) {
        // if Job has requirements, Job should NOT wait if workers CAN BE allocated
        boolean canAllocate = hasRequiredWorkers(port.getResourcePool());
        if (canAllocate) {
          allocateWorkers();
          notifyAll();
        }
        return !canAllocate;
      } else {
        return false;
      }
    }
  }

  private boolean hasRequiredWorkers(ArrayList<Person> workers) {
    ArrayList<Integer> indexes = new ArrayList<>();
    for (String requirement : requirements) {
      boolean workerFound = false;
      for (Person person : workers) {
        if (person.getSkill().equals(requirement) && !indexes.contains(person.getIndex())) {
          workerFound = true;
          indexes.add(person.getIndex());
          break;
        }
      }
      if (!workerFound) {
        program.updateLog(JobMessage.RESOURCES_REQUIRED, requirement, ship.getName());
        return false;
      }
    }
    return true;
  }

  private void allocateWorkers() {
    ArrayList<Person> workers = new ArrayList<>();
    for (String requirement : requirements) {
      for (Person worker : port.getResourcePool()) {
        if (worker.getSkill().equals(requirement) && worker.getStatus() == WorkerStatus.AVAILABLE) {
          worker.setLoc("Ship: " + ship.getName());
          worker.setStatus(WorkerStatus.WORKING);
          workers.add(worker);
          break;
        }
      }
    }
    ship.setWorkers(workers);
    port.getResourcePool().removeAll(workers);
    program.updateResourceDisplay();
  }

  private void deallocateWorkers() {
    ArrayList<Person> workers = new ArrayList<>();
    for (Person worker : ship.getWorkers()) {
      worker.setLoc("Port: " + port.getName());
      worker.setStatus(WorkerStatus.AVAILABLE);
      port.getResourcePool().add(worker);
      workers.add(worker);
    }
    ship.getWorkers().removeAll(workers);
    program.updateResourceDisplay();
  }

  @Override
  public String toString() {
    return super.toString();
  }
}
