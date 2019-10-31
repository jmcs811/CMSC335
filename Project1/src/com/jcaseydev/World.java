package com.jcaseydev;

import java.util.ArrayList;
import java.util.Scanner;

public class World extends Thing {

  private ArrayList<SeaPort> ports;
  private PortTime time;

  World(Scanner scanner) {
    super(scanner);
    ports = new ArrayList<>();
  }

  void createObjects(String line) {
    Scanner scanner = new Scanner(line);

    if (!scanner.hasNext() || line.startsWith("//")) {
      return;
    }

    switch (scanner.next()) {
      case "pship":
//        PassengerShip passengerShip = new PassengerShip(scanner);
//        Dock passengerDock = getDockByIndex(passengerShip.getParent());
//        SeaPort passengerPort = getSeaPortByIndex(passengerShip.getParent());
//        SeaPort passengerDockPort = null;
//        if (passengerDock != null) {
//          passengerDockPort = getSeaPortByIndex(passengerDock.getParent());
//        }
//        addShip(passengerPort, passengerDockPort, passengerDock, passengerShip);
        addShip(new PassengerShip(scanner));
        break;

//      case "cship":
//        CargoShip cargoShip = new CargoShip(scanner);
//        Dock cargoDock = getDockByIndex(cargoShip.getParent());
//        SeaPort cargoPort = getSeaPortByIndex(cargoShip.getParent());
//        SeaPort cargoDockPort = null;
//        if (cargoDock != null) {
//          cargoDockPort = getSeaPortByIndex(cargoDock.getParent());
//        }
//        addShip(cargoPort, cargoDockPort, cargoDock, cargoShip);
//        break;

      case "port":
        SeaPort port = new SeaPort(scanner);
        addPort(port);
        break;

      case "person":
        Person person = new Person(scanner);
        SeaPort personPort = getSeaPortByIndex(person.getParent());
        if (personPort != null) {
          addPerson(personPort, person);
        }
        break;

      case "dock":
        Dock dock = new Dock(scanner);
        SeaPort dockPort = getSeaPortByIndex(dock.getIndex());
        if (dockPort != null) {
          addDock(dockPort, dock);
        }
        break;
    }
  }

  private void addPerson(SeaPort personPort, Person person) {
    personPort.getPersons().add(person);
  }

  private void addPort(SeaPort port) {
    ports.add(port);
  }

  private void addDock(SeaPort port, Dock dock) {
    port.getDocks().add(dock);
  }

  private void addShip(Ship ship) {
    Dock md = getDockByIndex(ship.getParent());
    if (md == null) {
      getSeaPortByIndex(ship.getParent()).getShips().add(ship);
      getSeaPortByIndex(ship.getParent()).getQue().add(ship);
      return;
    }

    md.setShip(ship);
    getSeaPortByIndex(md.getParent()).getShips().add(ship);
  }
//  private void addShip(SeaPort port, SeaPort dPort, Dock dock, Ship ship) {
//    if (dock == null) {
//      port.getShips().add (ship);
//      port.getQue().add (ship);
//      return;
//    }
//    dock.setShip(ship);
//    dPort.getShips().add (ship);
//  }

  private SeaPort getSeaPortByIndex(int index) {
    for (SeaPort port : ports) {
      if (port.getIndex() == index) {
        return port;
      }
    }
    return null;
  }

  private Dock getDockByIndex(int index) {
    for (SeaPort port : ports) {
      for (Dock dock : port.getDocks()) {
        if (dock.getIndex() == index) {
          return dock;
        }
      }
    }
    return null;
  }

  private Person getPersonByIndex(int index) {
    for (SeaPort port : ports) {
      for (Person person : port.getPersons()) {
        if (person.getIndex() == index) {
          return person;
        }
      }
    }
    return null;
  }

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

  @Override
  public String toString() {
    StringBuilder output = new StringBuilder("\n>>> World: ");

    for (SeaPort port : ports) {
      output.append("\n").append(port);
    }

    return output.toString();
  }
}
