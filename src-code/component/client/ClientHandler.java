package component.client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import component.httpresponse.HttpResponse;
import component.httpresponse.StatusLine;
import util.AppConsole;
import util.MediaType;
import util.Time;
import component.error.ExceptionManager;
import component.error.http.HttpError;
import component.httprequest.HttpRequest;

public class ClientHandler implements Runnable {
    // final
    // fields
    private Socket clientSocket;

    // constructors
    public ClientHandler() {
    }

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    // getters & setters
    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    // methods
    @Override
    public void run() {
        try {
            HttpRequest clientHttpRequest = HttpRequest.of(this);

            AppConsole.getAppConsole().printf(
                    this.getTimedInfo() + " " + clientHttpRequest.getRequestLine().stringFormat()
                            + "\n");

            HttpResponse httpResponse = HttpResponse.of(clientHttpRequest);

            AppConsole.getAppConsole().printf(this.getTimedInfo() + " "
                    + httpResponse.getStatusLine().getStatusLine() + "\n");

            OutputStream outputStream = this.getClientSocket().getOutputStream();
            outputStream.write(httpResponse.getBytes());
        } catch (IOException e) {
            AppConsole.getAppConsole().printf(this.getTimedInfo() + "\n"
                    + ExceptionManager.formatException(e));
        } catch (HttpError httpError) {
            try {
                HttpResponse httpResponse = new HttpResponse();
                httpResponse.setStatusLine(new StatusLine("HTTP/1.1", httpError.getStatusCode())); // TODO :
                                                                                                   // Compatibilite de
                                                                                                   // version
                httpResponse.getResponseHeaders().add(HttpResponse.getDateHeader());
                httpResponse.getResponseHeaders().add("Content-Type: " + MediaType.of(".html").getContentType());
                httpResponse.getResponseHeaders()
                        .add("Content-Length: " + httpError.toHTML(true).toString().getBytes().length);
                httpResponse.setResponseBody(httpError.toHTML(true).toString().getBytes());

                AppConsole.getAppConsole().printf(this.getTimedInfo() + " "
                        + httpResponse.getStatusLine().getStatusLine() + "\n");

                OutputStream outputStream = this.getClientSocket().getOutputStream();
                outputStream.write(httpResponse.getBytes());
            } catch (IOException e) {
                // TODO : what ?
            } catch (HttpError e) {
                // TODO : what ?
            }
        } finally {
            try {
                if (!this.getClientSocket().isClosed())
                    this.getClientSocket().close();
            } catch (IOException e) {
                AppConsole.getAppConsole().printf(this.getTimedInfo() + " " + ExceptionManager.formatException(e));
            }
        }
    }

    public String getInfo() {
        InetAddress inetAddress = this.getClientSocket().getInetAddress();
        return "[" + inetAddress.getHostName() + "@" + inetAddress.getHostAddress() + "]";
    }

    public String getTimedInfo() {
        return Time.forConsole() + " " + this.getInfo();
    }
    // static methods
}