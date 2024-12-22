import component.server.Server;
import ui.apachelike.ApacheLikeFrame;
import util.AppConsole;

public class App {
  // final
  // fields
  // constructors
  // getters & setters
  // methods
  // static methods
  public static void main(String[] args) {
    AppConsole.setAppConsole(System.console());
    Server.bootstrap();
    new ApacheLikeFrame();
  }
}