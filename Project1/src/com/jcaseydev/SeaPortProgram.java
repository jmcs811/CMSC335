package com.jcaseydev;

/////////////////
// File: SeaPortProgram.java
// Date: 1 Nov 2019
// Author: Justin Casey
// Purpose: Class that defines
// the GUI for the program.
//

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SeaPortProgram extends JFrame {

  private World world;
  private JPanel mainPanel = new JPanel();

  private JComboBox<String> searchComboBox = new JComboBox<>();
  private JComboBox<String> sortComboBox = new JComboBox<>();
  private JComboBox<String> sortTypeComboBox = new JComboBox<>();

  private JTextField searchField = new JTextField();

  private JTextArea worldTextArea = new JTextArea();
  private JTextArea searchTextArea = new JTextArea();

  // constructor
  private SeaPortProgram() {
    final String[] searchItems = {"Index", "Skill", "Type", "Name"};
    final String[] sortItems = {"Ports", "People", "Ships", "Queue", "Jobs", "Docks"};

    final String[] seaPortSort = {"name"};
    final String[] docksSort = {"name"};
    final String[] allShipSort = {"name"};
    final String[] queuedShipSort = {"name", "weight", "length", "width", "draft"};
    final String[] peopleSort = {"name"};
    final String[] jobSort = {"name"};

    final String[][] sortTargets = {
        seaPortSort,
        docksSort,
        allShipSort,
        queuedShipSort,
        peopleSort,
        jobSort
    };

    final Dimension comboBoxDimen = new Dimension(125, 30);
    final Dimension textFileDimen = new Dimension(175, 30);
    final Dimension buttonDimen = new Dimension(100, 30);
    final Dimension scrollPaneDimen = new Dimension(535, 405);

    setTitle("SeaPort");
    setSize(1150, 580);
    setVisible(true);
    setLayout(new BorderLayout());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // make panels
    JPanel optionsPanel = new JPanel();
    JPanel filePanel = new JPanel();
    JPanel searchPanel = new JPanel();
    JPanel sortPanel = new JPanel();
    JPanel viewPanel = new JPanel();
    JPanel logPanel = new JPanel();
    JPanel resultPanel = new JPanel();

    // create buttons
    JButton readButton = new JButton("Read");
    JButton showButton = new JButton("Show");
    JButton searchButton = new JButton("Search");
    JButton sortButton = new JButton("Sort");
    JButton clearButton = new JButton("Clear");

    readButton.setPreferredSize(buttonDimen);
    showButton.setPreferredSize(buttonDimen);
    clearButton.setPreferredSize(buttonDimen);
    searchButton.setPreferredSize(buttonDimen);
    sortButton.setPreferredSize(buttonDimen);
    searchField.setPreferredSize(textFileDimen);

    JScrollPane worldScroll = new JScrollPane(worldTextArea);
    JScrollPane sortScroll = new JScrollPane(searchTextArea);

    filePanel.setBorder(new TitledBorder("File"));
    searchPanel.setBorder(new TitledBorder("Search"));
    sortPanel.setBorder(new TitledBorder("Sort"));
    logPanel.setBorder(new TitledBorder("Log"));
    resultPanel.setBorder(new TitledBorder("Results"));

    // setting up combo boxes
    searchComboBox.setEditable(false);
    searchComboBox.setPreferredSize(comboBoxDimen);
    for (String item : searchItems) {
      searchComboBox.addItem(item);
    }
    searchComboBox.setSelectedIndex(0);

    sortComboBox.setEditable(false);
    sortComboBox.setPreferredSize(comboBoxDimen);
    for (String item : sortItems) {
      sortComboBox.addItem(item);
    }
    sortComboBox.setSelectedIndex(0);

    sortTypeComboBox.setEditable(false);
    sortTypeComboBox.setPreferredSize(comboBoxDimen);
    for (String item : sortTargets[0]) {
      sortTypeComboBox.addItem(item);
    }

    worldTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
    worldTextArea.setEditable(false);

    searchTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
    searchTextArea.setEditable(false);

    worldScroll.setPreferredSize(scrollPaneDimen);
    sortScroll.setPreferredSize(scrollPaneDimen);

    filePanel.add(readButton);
    filePanel.add(showButton);
    filePanel.add(clearButton);

    searchPanel.add(searchField);
    searchPanel.add(searchComboBox);
    searchPanel.add(searchButton);

    sortPanel.add(sortComboBox);
    sortPanel.add(sortTypeComboBox);
    sortPanel.add(sortButton);

    logPanel.add(worldScroll);

    resultPanel.add(sortScroll);

    optionsPanel.add(filePanel, BorderLayout.WEST);
    optionsPanel.add(searchPanel, BorderLayout.CENTER);
    optionsPanel.add(sortPanel, BorderLayout.EAST);

    viewPanel.add(logPanel, BorderLayout.WEST);
    viewPanel.add(resultPanel, BorderLayout.EAST);

    mainPanel.add(optionsPanel, BorderLayout.NORTH);
    mainPanel.add(viewPanel, BorderLayout.SOUTH);

    add(mainPanel);

    validate();

    // button actions
    readButton.addActionListener(e -> read());
    showButton.addActionListener(e -> showObjects());
    searchButton.addActionListener(e -> search());
    sortButton.addActionListener(e -> sort());
    clearButton.addActionListener(e -> {
      worldTextArea.setText("");
      searchTextArea.setText("");
    });
    sortComboBox.addActionListener(e -> {
      sortTypeComboBox.removeAllItems();
      for (String item : sortTargets[sortComboBox.getSelectedIndex()]) {
        sortTypeComboBox.addItem(item);
      }
      validate();
    });
  }

  // method to show file chooser and read the file
  private void read() {
    JFileChooser jFileChooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt", "text");
    jFileChooser.setFileFilter(filter);
    jFileChooser.showOpenDialog(this);

    Scanner scanner;
    try {
      scanner = new Scanner(jFileChooser.getSelectedFile());
    } catch (FileNotFoundException | NullPointerException e) {
      errorDialog("No file selected");
      return;
    }

    world = new World(scanner);
    world.createObjects(scanner);
  }

  // search method
  private void search() {
    final String resultType = "Search";
    ArrayList<Thing> results = new ArrayList<>();
    String type = String.valueOf(searchComboBox.getSelectedItem());
    String query = searchField.getText();

    if (world == null) {
      errorDialog("No world loaded");
      return;
    }

    if (type == null || query.equals("")) {
      errorDialog("Please fill out all fields");
      return;
    }

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

    worldTextArea.append(textAreaToString(results, query));
    searchTextArea.append(textAreaToString(results, resultType));
  }

  private void sort() {
    final String resultType = "Sort";
    String sortItem = String.valueOf(sortComboBox.getSelectedItem());
    String type = String.valueOf(sortTypeComboBox.getSelectedItem());

    if (world == null) {
      errorDialog("No world loaded");
      return;
    }

    switch (sortItem) {
      case "Ports":
        world.getPorts().sort(new ThingComparator(type));
        break;
      case "Docks":
        for (SeaPort port : world.getPorts()) {
          port.getDocks().sort(new ThingComparator(type));
        }
        break;
      case "Ships":
        for (SeaPort port : world.getPorts()) {
          port.getShips().sort(new ShipComparator(type));
        }
        break;
      case "Queue":
        for (SeaPort port : world.getPorts()) {
          port.getQue().sort(new ShipComparator(type));
        }
        break;
      case "People":
        for (SeaPort port : world.getPorts()) {
          port.getPersons().sort(new ThingComparator(type));
        }
        break;
      case "Jobs":
        // TODO: implement
        break;
    }

    ArrayList<Thing> results = new ArrayList<>(world.getPorts());
    searchTextArea.append(textAreaToString(results, resultType));
  }

  private void errorDialog(String message) {
    JOptionPane.showMessageDialog(mainPanel,
        message,
        "Error",
        JOptionPane.ERROR_MESSAGE);
  }

  // convert ArrayList to a string to show in the text area
  private String textAreaToString(ArrayList<Thing> results, String query) {
    StringBuilder out = new StringBuilder("\nSearch Results for " + query + ": ");
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
    if (world == null) {
      errorDialog("No world loaded");
    } else {
      worldTextArea.append(world.toString());
    }
    validate();
  }

  // main method for the program. Creates a SeaPortProgram instance.
  public static void main(String[] args) {
    SeaPortProgram program = new SeaPortProgram();
  }
}
