package com.jcaseydev;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class World extends Thing {

  private SeaPortProgram program;
  private ArrayList<SeaPort> ports;
  private PortTime time;

  World(Scanner sc, SeaPortProgram program) {
    super(sc);
    this.program = program;
    ports = new ArrayList<>();
  }

  void createObjects(Scanner file) {

    HashMap<Integer, SeaPort> portsHashMap = new HashMap<>();
    HashMap<Integer, Dock> docksHashMap = new HashMap<>();
    HashMap<Integer, Ship> shipsHashMap = new HashMap<>();
    HashMap<Integer, Person> personsHashMap = new HashMap<>();
    HashMap<Integer, Job> jobsHashMap = new HashMap<>();

    while (file.hasNextLine()) {

      String line = file.nextLine().trim();
      Scanner sc = new Scanner(line);

      if (sc.hasNext() && !line.startsWith("//")) {
        switch (sc.next()) {
          case "port":
            SeaPort seaPort = new SeaPort(sc);
            addPort(portsHashMap, seaPort);
            break;
          case "dock":
            Dock dock = new Dock(sc);
            addDock(portsHashMap, docksHashMap, dock);
            break;
          case "pship":
            PassengerShip pShip = new PassengerShip(sc, portsHashMap, docksHashMap);
            addShip(portsHashMap, docksHashMap, shipsHashMap, pShip);
            break;
          case "cship":
            CargoShip cShip = new CargoShip(sc, portsHashMap, docksHashMap);
            addShip(portsHashMap, docksHashMap, shipsHashMap, cShip);
            break;
          case "person":
            Person person = new Person(sc);
            String location = "Port: " + portsHashMap.get(person.getParent()).getName();
            person.setLoc(location);
            addPerson(portsHashMap, personsHashMap, person);
            break;
          case "job":
            Job job = new Job(sc, program, shipsHashMap);
            addJob(shipsHashMap, jobsHashMap, job);
            break;
        }
      }
    }
  }

  private void addPort(HashMap<Integer, SeaPort> portsHashMap, SeaPort port) {
    this.getPorts().add(port);
    portsHashMap.put(port.getIndex(), port);
  }

  private void addDock(HashMap<Integer, SeaPort> portsHashMap, HashMap<Integer, Dock> docksHashMap,
      Dock dock) {
    SeaPort port = portsHashMap.get(dock.getParent());
    port.getDocks().add(dock);
    docksHashMap.put(dock.getIndex(), dock);
  }

  private void addShip(HashMap<Integer, SeaPort> portsHashMap, HashMap<Integer, Dock> docksHashMap,
      HashMap<Integer, Ship> shipsHashMap, Ship ship) {
    Dock dock = docksHashMap.get(ship.getParent());
    SeaPort port;

    if (dock == null) {
      port = portsHashMap.get(ship.getParent());
      port.getQue().add(ship);
    } else {
      port = portsHashMap.get(dock.getParent());
      dock.setShip(ship);
    }
    port.getShips().add(ship);
    shipsHashMap.put(ship.getIndex(), ship);
  }

  private void addPerson(HashMap<Integer, SeaPort> portsHashMap,
      HashMap<Integer, Person> personsHashMap, Person person) {
    SeaPort port = portsHashMap.get(person.getParent());
    port.getPersons().add(person);
    port.getResourcePool().add(person);
    personsHashMap.put(person.getIndex(), person);
  }

  private void addJob(HashMap<Integer, Ship> shipsHashMap, HashMap<Integer, Job> jobsHashMap,
      Job job) {
    Ship ship = shipsHashMap.get(job.getParent());
    ship.getJobs().add(job);
    jobsHashMap.put(job.getIndex(), job);
  }

  ArrayList<SeaPort> getPorts() {
    return ports;
  }


  void setPorts(ArrayList<SeaPort> ports) {
    this.ports = ports;
  }


  PortTime getTime() {
    return time;
  }


  void setTime(PortTime time) {
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
        for (Job job : ship.getJobs()) {
          if (job.getIndex() == index) {
            results.add(job);
          }
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

  ArrayList<Thing> nameSearch(ArrayList<Thing> results, String name) {
    for (SeaPort port : ports) {
      if (port.getName().equalsIgnoreCase(name)) {
        results.add(port);
      }
      for (Dock dock : port.getDocks()) {
        if (dock.getName().equalsIgnoreCase(name)) {
          results.add(dock);
        }
      }
      for (Ship ship : port.getShips()) {
        if (ship.getName().equalsIgnoreCase(name)) {
          results.add(ship);
        }
        for (Job job : ship.getJobs()) {
          if (job.getName().equalsIgnoreCase(name)) {
            results.add(job);
          }
        }
      }
      for (Person person : port.getPersons()) {
        if (person.getName().equalsIgnoreCase(name)) {
          results.add(person);
        }
      }
    }
    return results;
  }

  ArrayList<Thing> skillSearch(ArrayList<Thing> results, String skill) {
    for (SeaPort port : ports) {
      for (Person person : port.getPersons()) {
        if (person.getSkill().equalsIgnoreCase(skill)) {
          results.add(person);
        }
      }
    }
    return results;
  }

  ArrayList<Thing> typeSearch(ArrayList<Thing> results, String type) {
    switch (type.toLowerCase()) {
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
      case "job":
        for (SeaPort port : ports) {
          for (Ship ship : port.getShips()) {
            results.addAll(ship.getJobs());
          }
        }
        break;
    }
    return results;
  }

  @Override
  public String toString() {
    StringBuilder out = new StringBuilder("\n\n>>>>> World: ");

    for (SeaPort port : ports) {
      out.append("\n").append(port);
    }

    return out.toString();
  }
}