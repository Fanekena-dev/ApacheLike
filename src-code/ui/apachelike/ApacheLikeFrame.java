package ui.apachelike;

import javax.swing.JFrame;

import ui.btn.OpenSettingsBtn;
import ui.btn.StartServerBtn;
import ui.btn.StopServerBtn;
import util.Time;

import java.awt.Dimension;
import java.awt.FlowLayout;

public class ApacheLikeFrame extends JFrame {
  // final
  // fields
  private String appTitle = "ApacheLike";
  private Dimension frameDimension = new Dimension(300, 150);
  private int dCO = EXIT_ON_CLOSE;
  private boolean isR = false;
  private boolean isV = true;

  private static boolean settingsOpened = false;

  // constructors
  public ApacheLikeFrame() {
    this.config();
  }

  // getters & setters
  public static boolean isSettingsOpened() {
    return settingsOpened;
  }

  public static void setSettingsOpened(boolean settingsOpened) {
    ApacheLikeFrame.settingsOpened = settingsOpened;
  }

  // methods
  public void config() {
    System.out.println(Time.forConsole() + " Starting ApacheLike app");

    this.setTitle(this.appTitle);

    this.setSize(this.frameDimension);

    this.setLayout(new FlowLayout(FlowLayout.LEADING));
    this.add(new StartServerBtn());
    this.add(new StopServerBtn());
    this.add(new OpenSettingsBtn());

    this.setDefaultCloseOperation(this.dCO);
    this.setResizable(this.isR);
    this.setVisible(this.isV);

    System.out.println(Time.forConsole() + " ApacheLike started");
  }
  // static methods
}
