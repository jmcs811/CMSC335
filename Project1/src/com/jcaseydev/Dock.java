package com.jcaseydev;

import java.util.Scanner;

public class Dock extends Thing {

  private Ship ship;

  public Dock(Scanner scanner) {
    super(scanner);
  }

  public Ship getShip() {
    return ship;
  }

  public void setShip(Ship ship) {
    this.ship = ship;
  }

  @Override
  public String toString() {
    return " " + "Dock: " + super.toString() + "\n" + " ship: " + ship;
  }
}
