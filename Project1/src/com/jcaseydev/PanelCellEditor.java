package com.jcaseydev;

import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class PanelCellEditor extends AbstractCellEditor implements TableCellEditor {

  private Component editor;

  @Override
  public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,
      int row, int column) {
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
