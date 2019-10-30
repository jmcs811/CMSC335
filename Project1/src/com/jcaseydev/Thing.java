package com.jcaseydev;

public class Thing implements Comparable {
  private int index;
  private String name;
  private int parent;

  @Override
  public int compareTo(Object o) {
    return 0;
  }
}
