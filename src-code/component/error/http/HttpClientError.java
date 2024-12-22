package component.error.http;

import util.StatusCode;

public class HttpClientError extends HttpError {
    /*
     * Classe pour gerer les erreurs clients
     */
    // final
    // fields

    // constructors
    public HttpClientError(String message, StatusCode statusCode) {
        super(message, statusCode);
    }

    // getters & setters
    // methods
    // static methods
}