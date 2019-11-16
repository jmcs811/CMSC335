package com.jcaseydev;
/////////////////
// File: ShipComparator.java
// Date: 13 Nov 2019
// Author: Justin Casey
// Purpose: Comparator class for Ships
//

import java.util.Comparator;

public class ShipComparator implements Comparator<Ship> {

  private String target;
  private ThingComparator comparator;

  ShipComparator(String target) {
    this.target = target;
    comparator = new ThingComparator(target);
  }

  String getTarget() {
    return target;
  }

  void setTarget(String target) {
    this.target = target;
  }


  @Override
  public int compare(Ship o1, Ship o2) {
    switch (target) {
      case "weight":
        return Double.compare(o1.getWeight(), o2.getWeight());
      case "length":
        return Double.compare(o1.getLength(), o2.getLength());
      case "width":
        return Double.compare(o1.getWidth(), o2.getWidth());
      case "draft":
        return Double.compare(o1.getDraft(), o2.getDraft());
    }
    return comparator.compare(o1, o2);
  }
}
