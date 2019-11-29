package com.jcaseydev;

/////////////////
// File: CargoShip.java
// Date: 1 Nov 2019
// Author: Justin Casey
// Purpose: Class defining the Cargo
// ship object.
//

import java.util.HashMap;
import java.util.Scanner;

public class CargoShip extends Ship {

  private double cargoValue;
  private double cargoVolume;
  private double cargoWeight;

  // constructor
  CargoShip(Scanner scanner, HashMap<Integer, SeaPort> portMap, HashMap<Integer, Dock> dockMap) {
    super(scanner, portMap, dockMap);
    if (scanner.hasNextDouble()) {
      cargoWeight = scanner.nextDouble();
    }

    if (scanner.hasNextDouble()) {
      cargoVolume = scanner.nextDouble();
    }

    if (scanner.hasNextDouble()) {
      cargoValue = scanner.nextDouble();
    }
  }

  // getter and setter methods
  public double getCargoValue() {
    return cargoValue;
  }

  public void setCargoValue(double cargoValue) {
    this.cargoValue = cargoValue;
  }

  public double getCargoVolume() {
    return cargoVolume;
  }

  public void setCargoVolume(double cargoVolume) {
    this.cargoVolume = cargoVolume;
  }

  public double getCargoWeight() {
    return cargoWeight;
  }

  public void setCargoWeight(double cargoWeight) {
    this.cargoWeight = cargoWeight;
  }

  // toString method
  @Override
  public String toString() {
    return "Cargo Ship: " + super.toString();
  }
}
