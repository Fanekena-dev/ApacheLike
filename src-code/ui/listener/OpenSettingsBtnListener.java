package ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import component.server.Server;
import ui.apachelike.ApacheLikeFrame;
import ui.settings.SettingsFrame;
import util.AppConsole;
import util.Time;

public class OpenSettingsBtnListener implements ActionListener {
  @Override
  public void actionPerformed(ActionEvent e) {
    if (Server.getApacheLike() != null) {
      if (!Server.getApacheLike().getAlive()) {
        if (!ApacheLikeFrame.isSettingsOpened()) {
          new SettingsFrame();
          ApacheLikeFrame.setSettingsOpened(true);
        }
      } else {
        AppConsole.getAppConsole().printf(Time.forConsole() + " you need to stop the server first before opening settings\n");
      }
    } else {
      new SettingsFrame();
      ApacheLikeFrame.setSettingsOpened(true);
    }
  }
}
