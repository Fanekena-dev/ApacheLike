package util;

import component.error.http.HttpError;
import component.error.http.HttpServerError;

public enum StatusCode {
    // enum
    _200("OK"),
    _201("Created"),
    _202("Accepted"),
    _204("No Content"),
    _301("Moved Permanently"),
    _302("Found"),
    _400("Bad Request"),
    _401("Unauthorized"),
    _403("Forbidden"),
    _404("Not Found"),
    _500("Internal Server Error"),
    _501("Not Implemented"),
    _502("Bad Gateway"),
    _503("Service Unavailable"),
    _505("HTTP Version Not Supported");

    // final
    private final String message;
    // fields

    // contructors
    StatusCode(String message) {
        this.message = message;
    }

    // getters & setters
    public String getMessage() {
        return message;
    }

    public String getStatusCode() {
        return this.name().substring(1);
    }

    // methods
    // static methods
    public static StatusCode of(String sC) throws HttpError {
        for (StatusCode statusCode : StatusCode.values()) {
            if (statusCode.name().equals("_" + sC)) {
                return statusCode;
            }
        }
        throw new HttpServerError(sC + " " + StatusCode._500.getMessage(), StatusCode._500);
    }
}