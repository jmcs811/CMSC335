package com.jcaseydev;

/////////////////
// File: Person.java
// Date: 1 Nov 2019
// Author: Justin Casey
// Purpose: Class defining the person
// object.
//

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Scanner;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Person extends Thing {

  private String skill;
  private String loc;
  private WorkerStatus status;

  // panels
  private JPanel statusPanel;
  private JLabel statusLabel;

  // constructor
  Person(Scanner scanner) {
    super(scanner);
    if (scanner.hasNext()) {
      skill = scanner.next();
    }
    initGUI();
  }

  private void initGUI() {
    statusPanel = new JPanel(new BorderLayout());

    statusPanel.setBorder(null);
    statusLabel = new JLabel();
    statusLabel.setBorder(null);

    statusLabel.setForeground(Color.BLACK);
    statusLabel.setHorizontalAlignment(JLabel.CENTER);
    setStatus(WorkerStatus.AVAILABLE);

    statusPanel.add(statusLabel, BorderLayout.CENTER);
  }

  // getters and setters
  String getSkill() {
    return skill;
  }

  public void setSkill(String skill) {
    this.skill = skill;
  }

  public String getLoc() {
    return loc;
  }

  public void setLoc(String loc) {
    this.loc = loc;
  }

  public WorkerStatus getStatus() {
    return status;
  }

  public void setStatus(WorkerStatus status) {
    this.status = status;
  }

  JPanel getStatusPanel() {
    return statusPanel;
  }

  // toString
  @Override
  public String toString() {
    return "Person: " + super.toString() + " " + skill;
  }
}
