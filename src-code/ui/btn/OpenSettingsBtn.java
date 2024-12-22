package ui.btn;

import javax.swing.JButton;

import ui.listener.OpenSettingsBtnListener;

public class OpenSettingsBtn extends JButton {
  // final
  // fields
  private String t = "settings";

  // constructors
  public OpenSettingsBtn() {
    this.config();
  }

  // getters & setters
  // methods
  public void config() {
    this.addActionListener(new OpenSettingsBtnListener());
    this.setText(t);
  }
  // static methods
}
