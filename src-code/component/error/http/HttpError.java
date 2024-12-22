package component.error.http;

import util.StatusCode;

public class HttpError extends Throwable {
    // final
    // fields
    private StatusCode statusCode;

    // constructors
    public HttpError() {
    }

    public HttpError(String message, StatusCode statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    // getters & setters
    public StatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    // methods
    public String toHTML(boolean addMessage) {
        StringBuilder html = new StringBuilder();
        html.append(
                "<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "<head lang=\"en\">\n" +
                        "<meta charset=\"UTF-8\">\n" +
                        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                        "<title>ApacheLike</title>\n" +
                        "</head>\n" +
                        "<body>\n");
        html.append(
                "<h2>" + this.getStatusCode().name().substring(1) + " " + this.getStatusCode().getMessage() + "</h2>"
                        + "<hr>");
        if (addMessage) {
            html.append("<p>" + this.getMessage() + "</p>");
        }
        html.append(
                "</body>\n" +
                        "</html>");
        return html.toString();
    }
    // static methods
}
