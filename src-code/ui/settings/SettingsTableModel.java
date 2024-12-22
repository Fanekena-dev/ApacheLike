package ui.settings;

import javax.swing.table.AbstractTableModel;

import component.config.Config;
import component.config.ConfigManager;
import component.config.Param;

public class SettingsTableModel extends AbstractTableModel {
  // final
  public static final SettingsTableModel STM = new SettingsTableModel();
  // fields
  private Object[][] data;
  private String[] columnNames;

  // constructors
  // getters & setters
  // methods
  @Override
  public int getRowCount() {
    return data.length;
  }

  @Override
  public int getColumnCount() {
    return columnNames.length;
  }

  @Override
  public String getColumnName(int column) {
    return columnNames[column];
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    return data[rowIndex][columnIndex];
  }

  @Override
  public boolean isCellEditable(int row, int column) {
    if (column == 0) {
      return false;
    }
    return true;
  }

  @Override
  public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    if (columnIndex != 0) {
      data[rowIndex][columnIndex] = (String) aValue;
    }
  }

  // static methods
  public static void loadSettings() {
    STM.columnNames = new String[] { "Params", "Values" };
    STM.data = new Object[Config.getParams().size()][2];
    int i = 0;
    for (Param param : Config.getParams()) {
      STM.data[i][0] = param.getConfKey();
      String value = ConfigManager.getProperties().getProperty(param.getConfKey());
      if (value != null) {
        STM.data[i][1] = value;
      } else {
        STM.data[i][1] = "null";
      }
      i++;
    }
  }
}