package ui.settings;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;

import ui.btn.UpdateSettingsBtn;

public class SettingsFrame extends JFrame {
  // final
  // fields
  private String appTitle = "ApacheLike Settings";
  private Dimension frameDimension = new Dimension(700, 500);
  private int dCO = DISPOSE_ON_CLOSE;
  private boolean isR = false;
  private boolean isV = true;

  private static SettingsTable settingsTable;

  // constructors
  public SettingsFrame() {
    this.config();
  }

  // getters & setters
  public static SettingsTable getSettingsTable() {
    return settingsTable;
  }

  // methods
  public void config() {
    this.addWindowListener(new ClosingSettingsWindowAdapter());

    this.setTitle(this.appTitle);

    this.setSize(this.frameDimension);

    this.setLayout(new FlowLayout(FlowLayout.LEADING));
    settingsTable = new SettingsTable(SettingsTableModel.STM);
    this.add(settingsTable);

    this.add(new UpdateSettingsBtn());

    this.setDefaultCloseOperation(this.dCO);
    this.setResizable(this.isR);
    this.setVisible(this.isV);
  }
  // static methods
}
