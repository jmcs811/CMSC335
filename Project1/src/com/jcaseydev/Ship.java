package com.jcaseydev;

/////////////////
// File: Ship.java
// Date: 1 Nov 2019
// Author: Justin Casey
// Purpose: Class defining the Ship
// object.
//

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Ship extends Thing {

  private SeaPort port;
  private Dock dock;
  private boolean isBusy = false;
  private double draft;
  private double length;
  private double weight;
  private double width;
  private ArrayList<Job> jobs;
  private ArrayList<Person> workers;

  // constructor
  Ship(Scanner scanner, HashMap<Integer, SeaPort> portMap, HashMap<Integer, Dock> dockMap) {
    super(scanner);
    if (scanner.hasNextDouble()) {
      weight = scanner.nextDouble();
    }

    if (scanner.hasNextDouble()) {
      length = scanner.nextDouble();
    }

    if (scanner.hasNextDouble()) {
      width = scanner.nextDouble();
    }

    if (scanner.hasNextDouble()) {
      draft = scanner.nextDouble();
    }

    jobs = new ArrayList<>();

    dock = dockMap.get(this.getParent());
    if (dock == null) {
      port = portMap.get(this.getParent());
    } else {
      port = portMap.get(dock.getParent());
    }
  }

  double getDraft() {
    return draft;
  }


  double getLength() {
    return length;
  }


  double getWeight() {
    return weight;
  }


  double getWidth() {
    return width;
  }


  ArrayList<Job> getJobs() {
    return jobs;
  }

  SeaPort getPort() {
    return port;
  }

  public Dock getDock() {
    return dock;
  }

  public void setDock(Dock dock) {
    this.dock = dock;
  }

  boolean isBusy() {
    return isBusy;
  }

  void setBusy(boolean busy) {
    isBusy = busy;
  }

  ArrayList<Person> getWorkers() {
    return workers;
  }

  void setWorkers(ArrayList<Person> workers) {
    this.workers = workers;
  }

  // toString
  @Override
  public String toString() {
    return super.toString() +
        " " + this.getWeight() +
        " " + this.getLength() +
        " " + this.getWidth() +
        " " + this.getDraft();
  }
}
