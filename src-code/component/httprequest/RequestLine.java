package component.httprequest;

import java.util.Vector;

import component.error.http.HttpServerError;
import component.error.http.HttpClientError;
import component.error.http.HttpError;
import util.HttpMethod;
import util.StatusCode;

public class RequestLine {
    // final
    // fields
    private HttpMethod httpMethod;
    private String path;
    private String httpVersion;

    public static Vector<HttpMethod> AV_HTTP_METHOD;
    public static Vector<String> AV_HTTP_VERSION;

    // constructors
    public RequestLine() {
        this.httpMethod = null;
        this.path = null;
        this.httpVersion = null;
    }

    public RequestLine(HttpMethod httpMethod, String path, String version) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.httpVersion = version;
    }

    // getters & setters
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public void setVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    // methods

    public String stringFormat() {
        return this.getHttpMethod().name() + " " + this.getPath() + " " + this.getHttpVersion();
    }

    // static methods
    static {
        AV_HTTP_METHOD = new Vector<>();
        AV_HTTP_METHOD.add(HttpMethod.GET);
        // ---
        AV_HTTP_VERSION = new Vector<>();
        AV_HTTP_VERSION.add("HTTP/1.1");
    }

    public static Boolean httpMethodIsAvailable(HttpMethod httpMethod) {
        return AV_HTTP_METHOD.contains(httpMethod);
    }

    public static Boolean httpVesrionIsAvailable(String httpVersion) {
        return AV_HTTP_VERSION.contains(httpVersion);
    }

    public static RequestLine of(String rL) throws HttpError {
        RequestLine requestLine = new RequestLine();
        String[] splitted_rL = rL.split("\\s+");
        if (splitted_rL.length == 3) {
            // mandefa exception raha ohatrany ka tsy mety ilay methode
            HttpMethod httpMethod = HttpMethod.of(splitted_rL[0]);

            if (httpMethodIsAvailable(httpMethod)) {
                requestLine.setHttpMethod(httpMethod);
            } else {
                throw new HttpServerError(StatusCode._501.getMessage() + " " + rL, StatusCode._501);
            }

            requestLine.setPath(splitted_rL[1]);

            if (httpVesrionIsAvailable(splitted_rL[2])) {
                requestLine.setVersion(splitted_rL[2]);
            } else {
                throw new HttpServerError(StatusCode._505.getMessage() + " " + rL, StatusCode._505);
            }

        } else {
            throw new HttpClientError(StatusCode._400.getMessage(), StatusCode._400);
        }
        return requestLine;
    }
}
