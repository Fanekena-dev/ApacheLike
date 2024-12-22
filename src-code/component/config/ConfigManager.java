package component.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import component.error.ExceptionManager;
import ui.settings.SettingsTable;
import util.AppConsole;
import util.Time;

public class ConfigManager {
  // final
  private static String ABSOLUTE_PATH_TO_CONF = "/opt/ApacheLike/conf/apache_like.properties";
  // fields
  private static Properties properties;

  // constructors
  // getters & setters
  // methods
  // static methods
  public static void changePropertiesFromGUI(SettingsTable settingsTable) {
    try {
      for (int row = 0; row < settingsTable.getRowCount(); row++) {
        properties.setProperty((String) settingsTable.getValueAt(row, 0), (String) settingsTable.getValueAt(row, 1));
      }
      FileOutputStream fileOutputStream = new FileOutputStream(ABSOLUTE_PATH_TO_CONF);
      properties.store(fileOutputStream, null);
      fileOutputStream.close();
    } catch (FileNotFoundException e) {
      AppConsole.getAppConsole().printf(Time.forConsole() + " " + ExceptionManager.formatException(e));
    } catch (IOException e) {
      AppConsole.getAppConsole().printf(Time.forConsole() + " " + ExceptionManager.formatException(e));
    }
  }

  public static void loadPropertiesFromFile() {
    try {
      FileInputStream fileInputStream = new FileInputStream(ABSOLUTE_PATH_TO_CONF);
      properties.load(fileInputStream);
      fileInputStream.close();
    } catch (FileNotFoundException e) {
      AppConsole.getAppConsole().printf(Time.forConsole() + " " + ExceptionManager.formatException(e));
    } catch (IOException e) {
      AppConsole.getAppConsole().printf(Time.forConsole() + " " + ExceptionManager.formatException(e));
    }
  }

  static {
    properties = new Properties();
  }

  public static Properties getProperties() {
    return properties;
  }
}