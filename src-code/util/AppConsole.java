package util;

import java.io.Console;

public class AppConsole {
  // final
  // fields
  private static Console appConsole;
  // constructors
  // getters & setters
  // methods
  public static void setAppConsole(Console appConsole) {
    AppConsole.appConsole = appConsole;
  }
  public static Console getAppConsole() {
    return appConsole;
  }
  // static methods
}
