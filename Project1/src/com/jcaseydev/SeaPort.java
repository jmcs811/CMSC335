package com.jcaseydev;

/////////////////
// File: SeaPort.java
// Date: 1 Nov 2019
// Author: Justin Casey
// Purpose: Class defining the SeaPort
// object.
//

import java.util.ArrayList;
import java.util.Scanner;

public class SeaPort extends Thing {

  private ArrayList<Dock> docks;
  private ArrayList<Ship> que;
  private ArrayList<Ship> ships;
  private ArrayList<Person> persons;

  // constructor
  SeaPort(Scanner scanner) {
    super(scanner);
    docks = new ArrayList<>();
    que = new ArrayList<>();
    ships = new ArrayList<>();
    persons = new ArrayList<>();
  }

  // getters and setters
  ArrayList<Dock> getDocks() {
    return docks;
  }

  public void setDocks(ArrayList<Dock> docks) {
    this.docks = docks;
  }

  ArrayList<Ship> getQue() {
    return que;
  }

  public void setQue(ArrayList<Ship> que) {
    this.que = que;
  }

  ArrayList<Ship> getShips() {
    return ships;
  }

  public void setShips(ArrayList<Ship> ships) {
    this.ships = ships;
  }

  ArrayList<Person> getPersons() {
    return persons;
  }

  public void setPersons(ArrayList<Person> persons) {
    this.persons = persons;
  }

  // to string method
  @Override
  public String toString() {
    StringBuilder output = new StringBuilder("\nSeaPort: " + super.toString());

    for (Dock dock : docks) {
      output.append("\n").append(dock);
    }

    output.append("\n\n ---- List of ships in que");

    for (Ship ship : que) {
      output.append("\n > ").append(ship);
    }

    output.append("\n\n ---- List of ships");

    for (Ship ship : ships) {
      output.append("\n > ").append(ship);
    }

    output.append("\n\n ---- List of people");

    for (Person person : persons) {
      output.append("\n > ").append(person);
    }

    return output.toString();
  }
}
