package com.jcaseydev;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class World extends Thing {

  private ArrayList<SeaPort> ports;
  private PortTime time;

  // constructor
  World(Scanner scanner) {
    super(scanner);
    ports = new ArrayList<>();
  }

  // method to read the line and create the
  // specified object
  void createObjects(Scanner file) {
    HashMap<Integer, SeaPort> ports = new HashMap<>();
    HashMap<Integer, Dock> docks = new HashMap<>();
    HashMap<Integer, Ship> ships = new HashMap<>();
    HashMap<Integer, Person> persons = new HashMap<>();

    while (file.hasNextLine()) {
      String line = file.nextLine().trim();
      Scanner sc = new Scanner(line);

      if (sc.hasNext() && !line.startsWith("//")) {
        switch (sc.next()) {
          case "port":
            SeaPort seaPort = new SeaPort(sc);
            addPort(seaPort);
            ports.put(seaPort.getIndex(), seaPort);
            break;
          case "dock":
            Dock dock = new Dock(sc);
            addDock(ports, dock);
            docks.put(dock.getIndex(), dock);
            break;
          case "pship":
            PassengerShip pShip = new PassengerShip(sc);
            addShip(ports, docks, pShip);
            ships.put(pShip.getIndex(), pShip);
            break;
          case "cship":
            CargoShip cShip = new CargoShip(sc);
            addShip(ports, docks, cShip);
            ships.put(cShip.getIndex(), cShip);
            break;
          case "person":
            Person person = new Person(sc);
            addPerson(ports, person);
            persons.put(person.getIndex(), person);
            break;
        }
      }
    }
  }

  // methods to add the specified object to the
  // ArrayList of that object.
  private void addPerson(HashMap<Integer, SeaPort> ports, Person person) {
    SeaPort port = ports.get(person.getParent());
    port.getPersons().add(person);
  }

  private void addPort(SeaPort port) {
    this.getPorts().add(port);
  }

  private void addDock(HashMap<Integer, SeaPort> ports, Dock dock) {
    SeaPort port = ports.get(dock.getParent());
    port.getDocks().add(dock);
  }

  private void addShip(HashMap<Integer, SeaPort> ports, HashMap<Integer, Dock> docks, Ship ship) {
    Dock dock = docks.get(ship.getParent());
    SeaPort port;

    if (dock == null) {
      port = ports.get(ship.getParent());
      port.getQue().add(ship);
    } else {
      port = ports.get(dock.getParent());
      dock.setShip(ship);
    }
    port.getShips().add(ship);
  }

  // getters and setters
  public ArrayList<SeaPort> getPorts() {
    return ports;
  }

  public void setPorts(ArrayList<SeaPort> ports) {
    this.ports = ports;
  }

  public PortTime getTime() {
    return time;
  }

  public void setTime(PortTime time) {
    this.time = time;
  }

  // methods for searching
  ArrayList<Thing> indexSearch(ArrayList<Thing> results, int index) {
    for (SeaPort port : ports) {
      if (port.getIndex() == index) {
        results.add(port);
      }

      for (Dock dock : port.getDocks()) {
        if (dock.getIndex() == index) {
          results.add(dock);
        }
      }

      for (Ship ship : port.getShips()) {
        if (ship.getIndex() == index) {
          results.add(ship);
        }
      }

      for (Person person : port.getPersons()) {
        if (person.getIndex() == index) {
          results.add(person);
        }
      }
    }
    return results;
  }

  ArrayList<Thing> typeSearch(ArrayList<Thing> results, String query) {
    switch (query.toLowerCase()) {
      case "port":
        results.addAll(ports);
        break;
      case "dock":
        for (SeaPort port : ports) {
          results.addAll(port.getDocks());
        }
        break;
      case "ship":
        for (SeaPort port : ports) {
          results.addAll(port.getShips());
        }
        break;
      case "person":
        for (SeaPort port : ports) {
          results.addAll(port.getPersons());
        }
        break;
    }
    return results;
  }

  ArrayList<Thing> skillSearch(ArrayList<Thing> results, String query) {
    for (SeaPort port : ports) {
      for (Person person : port.getPersons()) {
        if (person.getSkill().equalsIgnoreCase(query)) {
          results.add(person);
        }
      }
    }
    return results;
  }

  ArrayList<Thing> nameSearch(ArrayList<Thing> results, String query) {
    for (SeaPort port : ports) {
      if (port.getName().equalsIgnoreCase(query)) {
        results.add(port);
      }
      for (Dock dock : port.getDocks()) {
        if (dock.getName().equalsIgnoreCase(query)) {
          results.add(dock);
        }
      }
      for (Ship ship : port.getShips()) {
        if (ship.getName().equalsIgnoreCase(query)) {
          results.add(ship);
        }
      }
      for (Person person : port.getPersons()) {
        if (person.getName().equalsIgnoreCase(query)) {
          results.add(person);
        }
      }
    }
    return results;
  }

  // toString
  @Override
  public String toString() {
    StringBuilder output = new StringBuilder("\n>>> World: ");

    for (SeaPort port : ports) {
      output.append("\n").append(port);
    }

    return output.toString();
  }
}
