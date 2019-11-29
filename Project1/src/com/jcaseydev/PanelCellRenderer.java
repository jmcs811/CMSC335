package com.jcaseydev;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class PanelCellRenderer extends JPanel implements TableCellRenderer {

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    if (table.getModel().getColumnClass(column) == JPanel.class) {
      return (JPanel) value;
    }
    return (Component) value;
  }
}
