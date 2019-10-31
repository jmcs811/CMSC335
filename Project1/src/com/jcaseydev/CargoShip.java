package com.jcaseydev;

import java.util.Scanner;

public class CargoShip extends Ship {
  private double cargoValue;
  private double cargoVolume;
  private double cargoWeight;

  CargoShip(Scanner scanner) {
    super(scanner);
    if (scanner.hasNextDouble())
      cargoWeight = scanner.nextDouble();

    if (scanner.hasNextDouble())
      cargoVolume = scanner.nextDouble();

    if (scanner.hasNextDouble())
      cargoValue = scanner.nextDouble();

    System.out.println("done with c ship");
  }

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

  @Override
  public String toString() {
    return "Cargo Ship: " + super.toString();
  }
}
