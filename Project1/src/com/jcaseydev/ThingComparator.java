package com.jcaseydev;

import java.util.Comparator;

public class ThingComparator implements Comparator<Thing> {

  private String target;

  ThingComparator(String target) {
    this.target = target;
  }

  String getTarget() {
    return target;
  }

  void setTarget(String target) {
    this.target = target;
  }


  @Override
  public int compare(Thing o1, Thing o2) {
    switch (target) {
      case "index":
        return Integer.compare(o1.getIndex(), o2.getIndex());
      case "name":
        return o1.getName().compareTo(o2.getName());
    }
    return 0;
  }
}
