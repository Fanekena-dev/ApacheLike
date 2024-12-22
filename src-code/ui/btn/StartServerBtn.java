package ui.btn;

import javax.swing.JButton;

import ui.listener.StartServerBtnListener;

public class StartServerBtn extends JButton {
  // final
  // fields
  private String t = "start";

  // constructors
  public StartServerBtn() {
    this.config();
  }

  // getters & setters
  // methods
  public void config() {
    this.addActionListener(new StartServerBtnListener());
    this.setText(t);
  }
  // static methods
}
