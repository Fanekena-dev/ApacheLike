package ui.btn;

import javax.swing.JButton;

import ui.listener.StopServerBtnListener;

public class StopServerBtn extends JButton{
    // final
  // fields
  private String t = "stop";

  // constructors
  public StopServerBtn() {
    this.config();
  }

  // getters & setters
  // methods
  public void config() {
    this.addActionListener(new StopServerBtnListener());
    this.setText(t);
  }
  // static methods
}
