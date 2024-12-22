package ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import component.config.ConfigManager;
import ui.settings.SettingsFrame;
import util.AppConsole;
import util.Time;

public class UpdateSettingsBtnListener implements ActionListener {

  @Override
  public void actionPerformed(ActionEvent e) {
    ConfigManager.changePropertiesFromGUI(SettingsFrame.getSettingsTable());
    AppConsole.getAppConsole()
        .printf(Time.forConsole() + " settings updated\n");
  }

}
