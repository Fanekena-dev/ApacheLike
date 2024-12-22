package component.htdocs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import component.config.Config;
import component.error.ExceptionManager;
import component.error.http.HttpClientError;
import component.error.http.HttpError;
import component.error.http.HttpServerError;
import component.httprequest.HttpRequest;
import util.AppConsole;
import util.StatusCode;

public class PHPInterpreter {
  // final
  // fields
  // constructors
  // getters & setters
  // methods
  // static methods
  public static byte[] interpret(HttpRequest httpRequest) throws HttpError {
    StringBuilder php_interpreted_content = new StringBuilder();
    Path absolute_path_to_php_file = Paths
        .get(Config.get("htdocs_path").toString() + httpRequest.getRequestLine().getPath());
    if (Files.exists(absolute_path_to_php_file)) {

      List<String> command = new ArrayList<>();

      Path path_to_php_executable = Paths
          .get(Config.get("php_executable_path").toString());

      command.add(path_to_php_executable.toString());
      command.add(absolute_path_to_php_file.toString());

      ProcessBuilder processBuilder = new ProcessBuilder(command);

      try {
        Process process = processBuilder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
          php_interpreted_content.append(line).append("\n");
        }
        reader.close();

        int exitCode = process.waitFor();
        if (exitCode != 0) {
          throw new HttpServerError("php process terminated incorrectly", StatusCode._500);
        }
      } catch (IOException e) {
        AppConsole.getAppConsole()
            .printf(httpRequest.getClientHandler().getTimedInfo() + " " + ExceptionManager.formatException(e));
        throw new HttpServerError(ExceptionManager.formatException(e), StatusCode._500);
      } catch (InterruptedException e) {
        AppConsole.getAppConsole()
            .printf(httpRequest.getClientHandler().getTimedInfo() + " " + ExceptionManager.formatException(e));
        throw new HttpServerError(ExceptionManager.formatException(e), StatusCode._500);
      }
    } else {
      throw new HttpClientError("Ressource not found", StatusCode._404); // TODO : message
    }
    return php_interpreted_content.toString().getBytes();
  }
}
