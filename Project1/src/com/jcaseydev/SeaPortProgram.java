package com.jcaseydev;

import java.awt.BorderLayout;
import java.awt.Font;
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
    readButton.addActionListener( e -> read());
  }

  private void read() {
    JFileChooser jFileChooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt", "text");
    jFileChooser.setFileFilter(filter);
    jFileChooser.showOpenDialog(this);
  }

  public static void main(String[] args) {
    SeaPortProgram program = new SeaPortProgram();
  }
}
