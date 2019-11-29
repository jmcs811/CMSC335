package com.jcaseydev;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

public class PanelCellEditor extends AbstractCellEditor implements TableCellEditor {

  private Component editor;

  @Override
  public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
    editor = (JPanel) value;
    if (table.getModel().getColumnClass(column) == JPanel.class) {
      editor = (JPanel) value;
    }
    editor = (Component) value;
    return editor;
  }

  @Override
  public Object getCellEditorValue() {
    return editor;
  }
}
