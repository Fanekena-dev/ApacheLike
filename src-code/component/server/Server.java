package component.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import component.client.ClientHandler;
import component.config.Config;
import component.config.ConfigManager;
import component.error.ExceptionManager;
import component.error.config.ConfigException;
import ui.settings.SettingsTableModel;
import util.AppConsole;
import util.Time;

public class Server implements Runnable {
  // final
  // fields
  private Integer port; // port du serveur
  private ServerSocket serverSocket; // socket du serveur
  private Boolean alive = false; // etat du serveur ON/OFF
  private static Server apacheLike;

  // constructors
  public Server() {
  }

  public Server(int port) {
    this.port = port;
  }

  // getters & setters
  public Integer getPort() {
    return port;
  }

  public void setPort(Integer port) {
    this.port = port;
  }

  public ServerSocket getServerSocket() {
    return serverSocket;
  }

  public void setServerSocket(ServerSocket serverSocket) {
    this.serverSocket = serverSocket;
  }

  public Boolean getAlive() {
    return alive;
  }

  public void setAlive(Boolean alive) {
    this.alive = alive;
  }

  public static Server getApacheLike() {
    return apacheLike;
  }

  public static void setApacheLike(Server apacheLike) {
    Server.apacheLike = apacheLike;
  }

  public static void setupApacheLike() {
    Server.apacheLike = new Server((int) Config.get("server_port"));
  }

  // methods
  public void _start() {
    if (!this.getAlive()) {
      AppConsole.getAppConsole().printf(Time.forConsole() + " Server ready, listening on port %d\n", this.getPort());
      this.setAlive(true);
    }
  }

  public void _stop() {
    if (this.getAlive()) {
      AppConsole.getAppConsole().printf(Time.forConsole() + " Server stopped\n");
      if (this.getServerSocket() != null) {
        if (!this.getServerSocket().isClosed()) {
          try {
            this.getServerSocket().close();
          } catch (IOException e) {
            AppConsole.getAppConsole().printf(Time.forConsole() + " " + ExceptionManager.formatException(e));
          }
        }
      }
      this.setAlive(false);
    }
  }

  public void handleClient(Socket clientSocket) {
    ClientHandler clientHandler = new ClientHandler(clientSocket);
    Thread clientThread = new Thread(clientHandler);
    clientThread.start();
    AppConsole.getAppConsole().printf(clientHandler.getTimedInfo() + " " + "connected" + "\n");
  }

  @Override
  public void run() {
    try {
      this.setServerSocket(new ServerSocket(this.getPort()));
      while (this.getAlive()) {
        Socket clientSocket = this.getServerSocket().accept();
        this.handleClient(clientSocket);
      }
    } catch (SocketException e) {
      // TODO : stop accepting connexion
    } catch (IOException e) {
      AppConsole.getAppConsole().printf(Time.forConsole() + " " + ExceptionManager.formatException(e));
    }
  }

  // static methods
  public static void bootstrap() {
    ConfigManager.loadPropertiesFromFile();
    try {
      Config.updateParams();
      Server.setupApacheLike();
    } catch (ConfigException e) {
      Server.setApacheLike(null);
    }
    SettingsTableModel.loadSettings();
  }
}