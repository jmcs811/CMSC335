package com.jcaseydev;

import java.util.Scanner;

public class PassengerShip extends Ship {

  private int numberOfOccupiedRooms;
  private int numberOfPassengers;
  private int numberOfRooms;

  PassengerShip(Scanner scanner) {
    super(scanner);
    if (scanner.hasNextInt()) {
      numberOfPassengers = scanner.nextInt();
    }

    if (scanner.hasNextInt()) {
      numberOfRooms = scanner.nextInt();
    }

    if (scanner.hasNextInt()) {
      numberOfOccupiedRooms = scanner.nextInt();
    }
    System.out.println("done with p ship");
  }

  public int getNumberOfOccupiedRooms() {
    return numberOfOccupiedRooms;
  }

  public void setNumberOfOccupiedRooms(int numberOfOccupiedRooms) {
    this.numberOfOccupiedRooms = numberOfOccupiedRooms;
  }

  public int getNumberOfPassengers() {
    return numberOfPassengers;
  }

  public void setNumberOfPassengers(int numberOfPassengers) {
    this.numberOfPassengers = numberOfPassengers;
  }

  public int getNumberOfRooms() {
    return numberOfRooms;
  }

  public void setNumberOfRooms(int numberOfRooms) {
    this.numberOfRooms = numberOfRooms;
  }

  @Override
  public String toString() {
    return "Passenger Ship: " + super.toString();
  }
}
