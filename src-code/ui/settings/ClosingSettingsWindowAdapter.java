package ui.settings;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import ui.apachelike.ApacheLikeFrame;

public class ClosingSettingsWindowAdapter extends WindowAdapter{
  @Override
  public void windowClosing(WindowEvent e){
    ApacheLikeFrame.setSettingsOpened(false);
  }
}