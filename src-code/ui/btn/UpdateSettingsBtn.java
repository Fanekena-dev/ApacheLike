package ui.btn;

import javax.swing.JButton;

import ui.listener.UpdateSettingsBtnListener;

public class UpdateSettingsBtn extends JButton{
    // final
  // fields
  private String t = "update settings";

  // constructors
  public UpdateSettingsBtn() {
    this.config();
  }

  // getters & setters
  // methods
  public void config() {
    this.addActionListener(new UpdateSettingsBtnListener());
    this.setText(t);
  }
  // static methods
}
