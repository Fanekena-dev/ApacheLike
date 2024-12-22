package component.httpresponse;

import component.config.Config;
import component.error.ExceptionManager;
import component.error.http.HttpClientError;
import component.error.http.HttpError;
import component.error.http.HttpServerError;
import component.htdocs.FileManager;
import component.htdocs.PHPInterpreter;
import component.httprequest.HttpRequest;
import util.AppConsole;
import util.HttpMethod;
import util.MediaType;
import util.StatusCode;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.Vector;

public class HttpResponse {
  // final
  // fields
  /*
   * Structure d'une reponse http
   * 1. Status Line
   * HTTP version
   * statusCode
   * message
   * 2. Response Headers
   * Date
   * Content-Lenght
   * Content-Type
   * 3. Response Body
   */
  private StatusLine statusLine;
  private Vector<String> responseHeaders;
  private byte[] responseBody;

  // constructors
  public HttpResponse() {
    this.statusLine = null;
    this.responseHeaders = new Vector<>();
    this.responseBody = null;
  }

  // getters & setters
  public StatusLine getStatusLine() {
    return statusLine;
  }

  public void setStatusLine(StatusLine statusLine) {
    this.statusLine = statusLine;
  }

  public Vector<String> getResponseHeaders() {
    return responseHeaders;
  }

  public void setResponseHeaders(Vector<String> responseHeaders) {
    this.responseHeaders = responseHeaders;
  }

  public byte[] getResponseBody() {
    return responseBody;
  }

  public void setResponseBody(byte[] responseBody) {
    this.responseBody = responseBody;
  }

  // methods
  public byte[] getBytes() {
    StringBuilder statusLine_headers = new StringBuilder();
    statusLine_headers.append(this.getStatusLine().getStatusLine()).append("\n");
    for (String responseHeader : this.getResponseHeaders()) {
      statusLine_headers.append(responseHeader).append("\n");
    }
    statusLine_headers.append("\n");
    byte[] response = null;
    response = merge(statusLine_headers.toString().getBytes(), this.responseBody);
    return response;
  }

  // static methods
  public static byte[] merge(byte[] array1, byte[] array2) {
    byte[] result = new byte[array1.length + array2.length];
    System.arraycopy(array1, 0, result, 0, array1.length);
    System.arraycopy(array2, 0, result, array1.length, array2.length);
    return result;
  }

  public static String getDateHeader() {
    SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
    sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
    Date now = new Date();
    return ("Date: " + sdf.format(now));
  }

  public static String getContentTypeHeader(HttpRequest httpRequest) throws HttpError {
    Path http_path_request = Paths.get(httpRequest.getRequestLine().getPath());
    // TODO : Tsy tokony directory ilay izy ary tokony misy extension ilay fichier
    // izay pris en charge par le serveur
    String fileName = http_path_request.getFileName().toString();
    int dotIndex = fileName.lastIndexOf(".");
    String fileExtension = null;
    if (dotIndex != -1) {
      fileExtension = fileName.substring(dotIndex);
    } else {
      throw new HttpServerError(StatusCode._500.getMessage(), StatusCode._500);
    }
    MediaType mediaType = MediaType.of(fileExtension);
    return "Content-Type: " + mediaType.getContentType();
  }

  public static String getContentLengthHeader(HttpRequest httpRequest) throws HttpError {
    Path http_path_request = Paths.get(httpRequest.getRequestLine().getPath());
    Path absolute_path_to_file = Paths.get(Config.get("htdocs_path").toString() + http_path_request.toString());
    return "Content-Length: " + FileManager.getFileContent(absolute_path_to_file).length;
  }

  public static Vector<String> responseHeadersFor(HttpRequest httpRequest) throws HttpError {
    Vector<String> responseHeaders = new Vector<>();
    responseHeaders.add(getDateHeader());
    responseHeaders.add(getContentTypeHeader(httpRequest));
    responseHeaders.add(getContentLengthHeader(httpRequest));
    return responseHeaders;
  }

  public static Boolean isAskingForDirectory(HttpRequest httpRequest) throws HttpError {
    Path absolute_path = Paths.get(Config.get("htdocs_path").toString() + httpRequest.getRequestLine().getPath());
    if (Files.exists(absolute_path)) {
      return absolute_path.toFile().isDirectory();
    } else {
      throw new HttpClientError(StatusCode._404.getMessage(), StatusCode._404);
    }
  }

  public static byte[] buildIndexFor(HttpRequest httpRequest) throws HttpError {
    // Mamerina menu kely en html mampiseho hoe iza no dossier na fichier afaka
    // jerena
    StringBuilder indexOf = new StringBuilder();
    Path absolute_path_to_directory = Paths
        .get(Config.get("htdocs_path").toString() + httpRequest.getRequestLine().getPath());
    indexOf.append(
        "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head lang=\"en\">\n" +
            "<meta charset=\"UTF-8\">\n" +
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "<title>ApacheLike</title>\n" +
            "</head>\n" +
            "<body>\n");
    indexOf.append(
        "<h2>Index of " +
            FileManager.relativePathToHtdocs(absolute_path_to_directory).toString() + "/" +
            "</h2>" +
            "<hr>");

    StringBuilder childList = new StringBuilder();
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(absolute_path_to_directory)) {
      childList.append("<ul>\n");
      for (Path path : stream) {
        String f_path = FileManager.relativePathToHtdocs(path).toString();
        String f_name = path.getFileName().toString();
        if (Files.isDirectory(path)) {
          childList.append("<li><a href=\"" + f_path + "\">");
          childList.append(f_name).append("/");
          childList.append("</a></li>\n");
        } else {
          childList.append("<li><a href=\"" + f_path + "\">");
          childList.append(f_name);
          childList.append("</a></li>\n");
        }
      }
      childList.append("</ul>\n");
    } catch (IOException e) {
      AppConsole.getAppConsole()
          .printf(httpRequest.getClientHandler().getTimedInfo() + " " + ExceptionManager.formatException(e));
      throw new HttpServerError(ExceptionManager.formatException(e), StatusCode._500);
    }

    indexOf.append(childList.toString());

    indexOf.append(
        "</body>\n" +
            "</html>");

    return indexOf.toString().getBytes();
  }

  public static HttpResponse of(HttpRequest httpRequest) throws HttpError {
    HttpResponse httpResponse = new HttpResponse();
    StatusLine statusLine = StatusLine.of(httpRequest.getRequestLine());
    httpResponse.setStatusLine(statusLine);
    if (statusLine.getStatusCode().equals(StatusCode._200)) {
      if (httpRequest.getRequestLine().getHttpVersion().equals("HTTP/1.1")) {
        if (httpRequest.getRequestLine().getHttpMethod().equals(HttpMethod.GET)) {
          // Raha mangataka fichier ilay client
          if (!isAskingForDirectory(httpRequest)) {
            if (!httpRequest.getRequestLine().getPath().endsWith(".php")) {
              httpResponse.setResponseHeaders(responseHeadersFor(httpRequest));
              httpResponse.setResponseBody(
                  FileManager.getFileContent(Paths.get(
                      Config.get("htdocs_path").toString()
                          + httpRequest.getRequestLine().getPath())));
            } else {
              if ((Boolean) Config.get("php_enable")) {
                httpResponse.getResponseHeaders().add(getDateHeader());
                httpResponse.getResponseHeaders()
                    .add("Content-Type: " + MediaType.of(".html").getContentType());
                httpResponse.getResponseHeaders()
                    .add("Content-Length: " + PHPInterpreter.interpret(httpRequest).length);
                httpResponse.setResponseBody(PHPInterpreter.interpret(httpRequest));
              } else {
                throw new HttpServerError("PHP is not supported by this server", StatusCode._501);
              }
            }
          }
          // Raha mangataka repertoire
          else {
            httpResponse.getResponseHeaders().add(getDateHeader());
            httpResponse.getResponseHeaders()
                .add("Content-Type: " + MediaType.of(".html").getContentType());
            httpResponse.getResponseHeaders().add("Content-Length: " + buildIndexFor(httpRequest).length);
            httpResponse.setResponseBody(buildIndexFor(httpRequest));
          }
        } else {
          throw new HttpServerError(StatusCode._501.getMessage(), StatusCode._501);
        }
      } else {
        throw new HttpServerError(StatusCode._505.getMessage(), StatusCode._505);
      }
    } else {
      throw new HttpError(statusLine.getStatusCode().getMessage(), statusLine.getStatusCode());
    }
    return httpResponse;
  }
}