package com.jcaseydev;

/////////////////
// File: Dock.java
// Date: 1 Nov 2019
// Author: Justin Casey
// Purpose: Class defining the Dock
// object.
//

import java.util.Scanner;

public class Dock extends Thing {

  private Ship ship;

  // constructor
  Dock(Scanner scanner) {
    super(scanner);
  }

  // getter and setter
  public Ship getShip() {
    return ship;
  }

  void setShip(Ship ship) {
    this.ship = ship;
  }

  // toString
  @Override
  public String toString() {
    return "Dock: " + super.toString() + "\n" + " ship: " + ship;
  }
}
