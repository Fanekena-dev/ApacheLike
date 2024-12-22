package component.config;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Vector;

import component.error.config.ConfigException;
import component.error.config.IllegalConfigValueException;
import component.error.config.MissingKeyException;
import component.error.config.NullConfigValueException;
import util.AppConsole;
import util.Time;

public class Config {
  // final
  // fields
  private static Vector<Param> params;

  // constructors
  // getters & setters
  public static Vector<Param> getParams() {
    return params;
  }

  // methods
  // static methods
  static {
    params = new Vector<>();

    // port du serveur
    params.add(new Param("server_port", "server_port", Integer.class));

    // chemin vers htdocs du serveur
    params.add(new Param("htdocs_path", "htdocs_path", Path.class));

    // php
    params.add(new Param("php_enable", "php_enable", Boolean.class));
    params.add(new Param("php_executable_path", "php_executable_path", Path.class));
  }

  public static void changeParams() {
    Properties properties = ConfigManager.getProperties();
    for (Param param : params) {
      String cKey = param.getConfKey();
      String property = properties.getProperty(cKey);
      if (param.getType().equals(Integer.class)) {
        param.setValue(Integer.parseInt(property));
      }
      if (param.getType().equals(Path.class)) {
        param.setValue(Paths.get(property));
      }
      if (param.getType().equals(Boolean.class)) {
        param.setValue(Boolean.parseBoolean(property));
      }
    }
  }

  public static void checkParams() throws ConfigException {
    Properties properties = ConfigManager.getProperties();
    for (Param param : params) {
      String cKey = param.getConfKey();
      if (properties.containsKey(cKey)) {
        String property = properties.getProperty(cKey);
        if (!cKey.equals("php_executable_path")) {
          if (property != null) {
            if (param.getType().equals(Integer.class)) {
              if (!property.matches("^[0-9]+$")) {
                throw new IllegalConfigValueException(cKey + " cannot have this value -> " + property, cKey, property);
              }
            }
            if (param.getType().equals(Path.class)) {
              if (!Files.exists(Paths.get(property))) {
                throw new IllegalConfigValueException("The path of this key " + cKey + " doesn't exist " + property,
                    cKey,
                    property);
              }
            }
            if (param.getType().equals(Boolean.class)) {
              if (!(property.equals("true") || property.equals("false"))) {
                throw new IllegalConfigValueException(cKey + " doesn't have a boolean value " + property, cKey,
                    property);
              }
            }
          } else {
            throw new NullConfigValueException("Null config value at " + cKey, cKey);
          }
        } else {
          if (isPHPEnabled()) {
            if (property != null) {
              if (param.getType().equals(Integer.class)) {
                if (!property.matches("^[0-9]+$")) {
                  throw new IllegalConfigValueException(cKey + " cannot have this value -> " + property, cKey,
                      property);
                }
              }
              if (param.getType().equals(Path.class)) {
                if (!Files.exists(Paths.get(property))) {
                  throw new IllegalConfigValueException("The path of this key " + cKey + " doesn't exist " + property,
                      cKey,
                      property);
                }
              }
              if (param.getType().equals(Boolean.class)) {
                if (!(property.equals("true") || property.equals("false"))) {
                  throw new IllegalConfigValueException(cKey + " doesn't have a boolean value " + property, cKey,
                      property);
                }
              }
            } else {
              throw new NullConfigValueException("Null config value at " + cKey, cKey);
            }
          }
        }
      } else {
        throw new MissingKeyException(cKey + " is missing in the configuration file", cKey);
      }
    }
  }

  public static boolean isPHPEnabled() {
    Properties properties = ConfigManager.getProperties();
    String php_enable = properties.getProperty("php_enable");
    if (php_enable != null) {
      if (php_enable == "true") {
        return true;
      }
    }
    return false;
  }

  public static void updateParams() throws ConfigException {
    try {
      checkParams();
      changeParams();
    } catch (ConfigException e) {
      AppConsole.getAppConsole().printf(
          Time.forConsole() + " we couldn't load any parameters because an error occured in the configuration file"
              + "\n");
      AppConsole.getAppConsole().printf(Time.forConsole() + " " + e.getMessage() + "\n");
      throw e;
    }
  }

  public static Object get(String name) {
    for (Param param : params) {
      if (param.getName().equals(name)) {
        return param.getValue();
      }
    }
    return null; // TODO : tokony tsy amerina null mihitsy ity fonction ity fa diso name izay
                 // matoa mahazo null
  }
}
