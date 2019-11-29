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
//  private PortTime arrivalTime;
//  private PortTime dockTime;
  private double draft;
  private double length;
  private double weight;
  private double width;
  private ArrayList<Job> jobs;

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

  // getters and setters
//  public PortTime getArrivalTime() {
//    return arrivalTime;
//  }

//  public void setArrivalTime(PortTime arrivalTime) {
//    this.arrivalTime = arrivalTime;
//  }

//  public PortTime getDockTime() {
//    return dockTime;
//  }

//  public void setDockTime(PortTime dockTime) {
//    this.dockTime = dockTime;
//  }

  double getDraft() {
    return draft;
  }

//  public void setDraft(double draft) {
//    this.draft = draft;
//  }

  double getLength() {
    return length;
  }

//  public void setLength(double length) {
//    this.length = length;
//  }

  double getWeight() {
    return weight;
  }

//  public void setWeight(double weight) {
//    this.weight = weight;
//  }

  double getWidth() {
    return width;
  }

//  public void setWidth(double width) {
//    this.width = width;
//  }

  ArrayList<Job> getJobs() {
    return jobs;
  }

//  public void setJobs(ArrayList<Job> jobs) {
//    this.jobs = jobs;
//  }

  SeaPort getPort() {
    return port;
  }

//  public void setPort(SeaPort port) {
//    this.port = port;
//  }

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
