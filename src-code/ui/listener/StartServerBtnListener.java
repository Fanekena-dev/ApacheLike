package ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import component.server.Server;
import ui.apachelike.ApacheLikeFrame;
import util.AppConsole;
import util.Time;

public class StartServerBtnListener implements ActionListener {
  @Override
  public void actionPerformed(ActionEvent e) {
    if (!ApacheLikeFrame.isSettingsOpened()) {
      Server.bootstrap();
      if (Server.getApacheLike() != null) {
        Server.getApacheLike()._start();
        Thread serverThread = new Thread(Server.getApacheLike());
        serverThread.start();
      } else {
        AppConsole.getAppConsole().printf(Time.forConsole() + " cannot start server because of configuration error\n");
      }
    } else {
      AppConsole.getAppConsole().printf(Time.forConsole() + " you need to close the settings window first\n");
    }
  }
}