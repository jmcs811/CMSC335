package com.jcaseydev;

/////////////////
// File: Thing.java
// Date: 1 Nov 2019
// Author: Justin Casey
// Purpose: Class defining the thing
// object. This is the base class for
// almost all of the classes in this
// program
//

import java.util.Scanner;

public class Thing implements Comparable {

  private int index;
  private String name;
  private int parent;

  // constructor
  Thing(Scanner scanner) {
    if (scanner.hasNext()) {
      name = scanner.next();
    }

    if (scanner.hasNextInt()) {
      index = scanner.nextInt();
    }

    if (scanner.hasNextInt()) {
      parent = scanner.nextInt();
    }
  }

  // getters and setters
  int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  int getParent() {
    return parent;
  }

  public void setParent(int parent) {
    this.parent = parent;
  }

  // compareTo method. Will be used later
  @Override
  public int compareTo(Object o) {
    return 0;
  }

  // toString
  @Override
  public String toString() {
    return name + " " + index;
  }
}
