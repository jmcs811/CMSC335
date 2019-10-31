package com.jcaseydev;

import java.util.Scanner;

public class Person extends Thing {

  private String skill;

  Person(Scanner scanner) {
    super(scanner);
    if (scanner.hasNext()) {
      skill = scanner.next();
    }
  }

  String getSkill() {
    return skill;
  }

  public void setSkill(String skill) {
    this.skill = skill;
  }

  @Override
  public String toString() {
    return "Person: " + super.toString() + " " + skill;
  }
}
