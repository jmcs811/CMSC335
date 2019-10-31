package com.jcaseydev;

import java.util.Scanner;

public class Thing implements Comparable {

  private int index;
  private String name;
  private int parent;

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
    System.out.println("done with thing");
  }

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

  @Override
  public int compareTo(Object o) {
    return 0;
  }

  @Override
  public String toString() {
    return name + " " + index;
  }
}
