package com.jcaseydev;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.JTable;

public class World extends Thing {

  private ArrayList<SeaPort> ports;
  private PortTime time;
  private SeaPortProgram program;
  private JTable jobTable;

  // constructor
  World(Scanner scanner, SeaPortProgram program, JTable jobTable) {
    super(scanner);
    this.program = program;
    ports = new ArrayList<>();
    this.jobTable = jobTable;
  }

  // method to read the line and create the
  // specified object
  void createObjects(Scanner file) {
    HashMap<Integer, SeaPort> ports = new HashMap<>();
    HashMap<Integer, Dock> docks = new HashMap<>();
    HashMap<Integer, Ship> ships = new HashMap<>();
    HashMap<Integer, Person> persons = new HashMap<>();
    HashMap<Integer, Job> jobs = new HashMap<>();

    while (file.hasNextLine()) {
      String line = file.nextLine().trim();
      Scanner sc = new Scanner(line);

      if (sc.hasNext() && !line.startsWith("//")) {
        switch (sc.next()) {
          case "port":
            SeaPort seaPort = new SeaPort(sc);
            addPort(ports, seaPort);
            break;
          case "dock":
            Dock dock = new Dock(sc);
            addDock(ports, docks, dock);
            break;
          case "pship":
            PassengerShip pShip = new PassengerShip(sc, ports, docks);
            addShip(ports, docks, ships, pShip);
            break;
          case "cship":
            CargoShip cShip = new CargoShip(sc, ports, docks);
            addShip(ports, docks, ships, cShip);
            break;
          case "person":
            Person person = new Person(sc);
            addPerson(ports, persons, person);
            break;
          case "job":
            Job job = new Job(sc, program, ships, jobTable);
            addJob(ships, jobs, job);
        }
      }
    }
  }

  // methods to add the specified object to the
  // ArrayList of that object.
  private void addJob(HashMap<Integer, Ship> ships, HashMap<Integer, Job> jobs, Job job) {
    Ship ship = ships.get(job.getParent());
    ship.getJobs().add(job);
    jobs.put(job.getIndex(), job);
  }

  private void addPerson(HashMap<Integer, SeaPort> ports, HashMap<Integer, Person> persons,
      Person person) {
    SeaPort port = ports.get(person.getParent());
    port.getPersons().add(person);
    persons.put(person.getIndex(), person);
  }

  private void addPort(HashMap<Integer, SeaPort> ports, SeaPort port) {
    this.getPorts().add(port);
    ports.put(port.getIndex(), port);
  }

  private void addDock(HashMap<Integer, SeaPort> ports, HashMap<Integer, Dock> docks, Dock dock) {
    SeaPort port = ports.get(dock.getParent());
    port.getDocks().add(dock);
    docks.put(dock.getIndex(), dock);
  }

  private void addShip(HashMap<Integer, SeaPort> ports, HashMap<Integer,
      Dock> docks, HashMap<Integer, Ship> ships, Ship ship) {
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
    ships.put(ship.getIndex(), ship);
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
