package com.jcaseydev;

import java.awt.BorderLayout;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SeaPortProgram extends JFrame {

  private World world;
  private Scanner scanner;
  private JTextArea worldTextArea;

  private SeaPortProgram() {
    setTitle("SeaPort");
    setSize(500, 500);
    setVisible(true);
    setLayout(new BorderLayout());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // make panels
    JPanel main = new JPanel();
    JPanel file = new JPanel();
    JPanel search = new JPanel();
    JPanel world = new JPanel();

    file.setBorder(new TitledBorder("File"));
    search.setBorder(new TitledBorder("Search"));
    world.setBorder(new TitledBorder("World"));

    JButton readButton = new JButton("Read");
    JButton showButton = new JButton("Show");
    JButton searchButton = new JButton("Search");

    JTextField searchField = new JTextField(10);

    JComboBox<String> searchComboBox = new JComboBox<>();
    searchComboBox.addItem("Index");
    searchComboBox.addItem("Name");
    searchComboBox.addItem("SKill");
    searchComboBox.addItem("Type");

    worldTextArea = new JTextArea(20, 61);
    worldTextArea.setFont(new java.awt.Font("Monospaced", Font.PLAIN, 12));
    JScrollPane worldScrollPane = new JScrollPane(worldTextArea);

    file.add(readButton);
    file.add(showButton);
    file.add(searchField);
    file.add(searchComboBox);
    file.add(searchButton);

    world.add(worldScrollPane, BorderLayout.CENTER);

    main.add(file, BorderLayout.NORTH);
    main.add(search, BorderLayout.NORTH);
    main.add(world, BorderLayout.CENTER);

    add(main);

    validate();

    // button actions
    readButton.addActionListener(e -> read());
    showButton.addActionListener(e -> showObjects());
    searchButton.addActionListener(e -> search((String) Objects
        .requireNonNull(searchComboBox.getSelectedItem()), searchField.getText()));
  }

  private void read() {
    JFileChooser jFileChooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt", "text");
    jFileChooser.setFileFilter(filter);
    jFileChooser.showOpenDialog(this);

    try {
      scanner = new Scanner(jFileChooser.getSelectedFile());
    } catch (FileNotFoundException | NullPointerException e) {
      e.printStackTrace();
    }

    world = new World(scanner);

    while (scanner.hasNextLine()) {
      String line = scanner.nextLine().trim();
      world.createObjects(line);
    }
  }

  private void search(String type, String query) {
    ArrayList<Thing> results = new ArrayList<>();

    switch (type) {
      case "Index":
        results = world.indexSearch(results, Integer.parseInt(query));
        break;
      case "Type":
        results = world.typeSearch(results, query);
        break;
      case "Skill":
        results = world.skillSearch(results, query);
        break;
      case "Name":
        results = world.nameSearch(results, query);
        break;
    }

    worldTextArea.append(textAreaToString(results));
  }

  private String textAreaToString(ArrayList<Thing> results) {
    StringBuilder out = new StringBuilder("\nSearch Results: ");
    if (results.isEmpty()) {
      out.append("\n NO RESULTS");
    } else {
      for (Thing thing : results) {
        out.append("\n ").append(thing);
      }
    }
    return out.toString();
  }

  private void showObjects() {
    worldTextArea.append(world.toString());
  }

  public static void main(String[] args) {
    SeaPortProgram program = new SeaPortProgram();
  }
}
