package com.jcaseydev;

import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class PanelCellRenderer extends JPanel implements TableCellRenderer {

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
      boolean hasFocus, int row, int column) {
    if (table.getModel().getColumnClass(column) == JPanel.class) {
      return (JPanel) value;
    }
    return (Component) value;
  }
}
