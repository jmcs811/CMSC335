package com.jcaseydev;

import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class ResourcesTable extends DefaultTableModel implements TableModel {

  private static final String[] COL_NAMES = new String[]{
      "Port",
      "Name",
      "Skill",
      "Status",
      "Current Location"
  };

  ResourcesTable() {
    super(COL_NAMES, 0);
  }

  public Class<?> getColumnClass(int col) {
    if (col == 3) {
      return JPanel.class;
    }
    return Object.class;
  }

  @Override
  public boolean isCellEditable(int row, int column) {
    return false;
  }
}
