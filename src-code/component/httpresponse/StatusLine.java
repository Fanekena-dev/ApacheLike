package component.httpresponse;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import component.config.Config;
import component.httprequest.RequestLine;
import util.StatusCode;

public class StatusLine {
    // final
    // fields
    private String httpVersion;
    private StatusCode statusCode;

    // constructors
    public StatusLine() {
    }

    public StatusLine(String httpVersion, StatusCode statusCode) {
        this.httpVersion = httpVersion;
        this.statusCode = statusCode;
    }

    // getters & setters
    public String getHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    // methods
    public String getStatusLine() {
        StringBuilder statusLine = new StringBuilder();
        statusLine.append(this.getHttpVersion()).append(" ");
        statusLine.append(this.getStatusCode().getStatusCode()).append(" ");
        statusLine.append(this.getStatusCode().getMessage());
        return statusLine.toString();
    }

    // static methods
    public static StatusLine of(RequestLine requestLine) {
        StatusLine statusLine = new StatusLine();

        // TODO : tsy tokony null ilay requestLine.getHtpVersion()
        statusLine.setHttpVersion(requestLine.getHttpVersion());

        // TODO : ohatrany possible feno erreur be eto fa verifieo ihany aloha ilay
        // request_path
        // Path htdocs
        // r_path
        Path request_path = Paths.get(Config.get("htdocs_path").toString() + requestLine.getPath());
        if (Files.exists(request_path)) {
            statusLine.setStatusCode(StatusCode._200);
        } else {
            statusLine.setStatusCode(StatusCode._404);
        }
        return statusLine;
    }
}