package com.jcaseydev;

/////////////////
// File: PassengerShip.java
// Date: 1 Nov 2019
// Author: Justin Casey
// Purpose: Class defining the passenger
// ship object.
//

import java.util.Scanner;

public class PassengerShip extends Ship {

  private int numberOfOccupiedRooms;
  private int numberOfPassengers;
  private int numberOfRooms;

  // constructor
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

  // getters and setters
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

  // to string
  @Override
  public String toString() {
    return "Passenger Ship: " + super.toString();
  }
}
