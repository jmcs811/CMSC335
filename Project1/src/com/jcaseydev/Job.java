package com.jcaseydev;

/////////////////
// File: Job.java
// Date: 1 Nov 2019
// Author: Justin Casey
// Purpose: Class defining the Job
// object.
//

import java.util.ArrayList;
import java.util.Scanner;

public class Job extends Thing {

  private double duration;
  private ArrayList<String> requirements;

  // constructor
  public Job(Scanner scanner) {
    super(scanner);
  }

  // getters and setters
  public double getDuration() {
    return duration;
  }

  public void setDuration(double duration) {
    this.duration = duration;
  }

  public ArrayList<String> getRequirements() {
    return requirements;
  }

  public void setRequirements(ArrayList<String> requirements) {
    this.requirements = requirements;
  }

  @Override
  public String toString() {
    return super.toString();
  }
}
