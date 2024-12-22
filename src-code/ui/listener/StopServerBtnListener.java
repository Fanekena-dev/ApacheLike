package ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import component.server.Server;

public class StopServerBtnListener implements ActionListener{
    @Override
  public void actionPerformed(ActionEvent e) {
    if(Server.getApacheLike() != null){
      Server.getApacheLike()._stop();
    }
  }
}
