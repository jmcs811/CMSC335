package com.jcaseydev;

/////////////////
// File: Person.java
// Date: 1 Nov 2019
// Author: Justin Casey
// Purpose: Class defining the person
// object.
//

import java.util.Scanner;

public class Person extends Thing {

  private String skill;

  // constructor
  Person(Scanner scanner) {
    super(scanner);
    if (scanner.hasNext()) {
      skill = scanner.next();
    }
  }

  // getters and setters
  String getSkill() {
    return skill;
  }

  public void setSkill(String skill) {
    this.skill = skill;
  }

  // toString
  @Override
  public String toString() {
    return "Person: " + super.toString() + " " + skill;
  }
}
