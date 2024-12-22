package ui.settings;

import java.awt.Font;

import javax.swing.JTable;

public class SettingsTable extends JTable {
  // final
  // fields
  // constructors
  public SettingsTable(SettingsTableModel settingsTableModel) {
    super(settingsTableModel);
    this.config();
  }

  // getters & setters
  // methods
  public void config() {
    this.setRowHeight(32);
    this.setFont(new Font("Arial", Font.PLAIN, 16));
    this.getColumnModel().getColumn(0).setPreferredWidth(200);
    this.getColumnModel().getColumn(1).setPreferredWidth(500);
  }
  // static methods
}
