package component.httprequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import component.client.ClientHandler;
import component.error.ExceptionManager;
import component.error.http.HttpClientError;
import component.error.http.HttpError;
import component.error.http.HttpServerError;
import util.AppConsole;
import util.StatusCode;

public class HttpRequest {
    // final
    // fields
    /*
     * Structure d'une requete http
     * 1. Request Line
     * Methode HTTP
     * URL/URI cible
     * Version du protocole
     * 2. Request Headers
     * 3. Request Body
     */
    private String request;
    private RequestLine requestLine;
    private Vector<String> requestHeaders;
    private String requestBody;
    private ClientHandler clientHandler;

    // constructors
    public HttpRequest(ClientHandler clientHandler) {
        this.requestHeaders = new Vector<>();
        this.clientHandler = clientHandler;
    }

    // getters & setters
    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    public void setClientHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public Vector<String> getRequestHeaders() {
        return requestHeaders;
    }

    public void setRequestHeaders(Vector<String> requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public void setRequestLine(RequestLine requestLine) {
        this.requestLine = requestLine;
    }

    // methods
    // static methods
    public static HttpRequest of(ClientHandler clientHandler) throws HttpError {
        try {
            InputStream inputStream = clientHandler.getClientSocket().getInputStream();
            HttpRequest httpRequest = new HttpRequest(clientHandler);
            httpRequest.setRequest(readInputStream(inputStream));

            String[] splitted_r_B = httpRequest.getRequest().split("\n\n");
            if (splitted_r_B.length == 2)
                httpRequest.setRequestBody(splitted_r_B[1]);
            else if (splitted_r_B.length != 1) {
                throw new HttpClientError("Bad Request", StatusCode._400);
            }

            String[] splitted_rL_Hs = splitted_r_B[0].split("\n");

            RequestLine requestLine = RequestLine.of(splitted_rL_Hs[0]);
            httpRequest.setRequestLine(requestLine);

            for (int i = 1; i < splitted_rL_Hs.length; i++)
                httpRequest.getRequestHeaders().add(splitted_rL_Hs[i]);
            return httpRequest;
        } catch (IOException e) {
            AppConsole.getAppConsole().printf(clientHandler.getTimedInfo() + " " + ExceptionManager.formatException(e));
            throw new HttpServerError(ExceptionManager.formatException(e), StatusCode._500);
        }
    }

    public static String readInputStream(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            content.append(line).append(System.lineSeparator());
        }
        return content.toString();
    }
}
