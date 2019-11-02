package com.jcaseydev;

/////////////////
// File: Ship.java
// Date: 1 Nov 2019
// Author: Justin Casey
// Purpose: Class defining the Ship
// object.
//

import java.util.ArrayList;
import java.util.Scanner;

public class Ship extends Thing {

  private PortTime arrivalTime;
  private PortTime dockTime;
  private double draft;
  private double length;
  private double weight;
  private double width;
  private ArrayList<Job> jobs;

  // constructor
  Ship(Scanner scanner) {
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
    System.out.println("done with ship");
  }

  // getters and setters
  public PortTime getArrivalTime() {
    return arrivalTime;
  }

  public void setArrivalTime(PortTime arrivalTime) {
    this.arrivalTime = arrivalTime;
  }

  public PortTime getDockTime() {
    return dockTime;
  }

  public void setDockTime(PortTime dockTime) {
    this.dockTime = dockTime;
  }

  public double getDraft() {
    return draft;
  }

  public void setDraft(double draft) {
    this.draft = draft;
  }

  public double getLength() {
    return length;
  }

  public void setLength(double length) {
    this.length = length;
  }

  public double getWeight() {
    return weight;
  }

  public void setWeight(double weight) {
    this.weight = weight;
  }

  public double getWidth() {
    return width;
  }

  public void setWidth(double width) {
    this.width = width;
  }

  public ArrayList<Job> getJobs() {
    return jobs;
  }

  public void setJobs(ArrayList<Job> jobs) {
    this.jobs = jobs;
  }

  // toString
  @Override
  public String toString() {
    return super.toString();
  }
}
