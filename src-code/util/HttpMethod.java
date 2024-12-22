package util;

import component.error.http.HttpClientError;
import component.error.http.HttpError;

public enum HttpMethod {
    // enum
    GET,
    POST,
    PUT,
    DELETE,
    PATCH,
    HEAD,
    OPTIONS,
    TRACE;

    // final
    // fields
    // contructors
    // getters & setters
    // methods
    // static methods
    public static HttpMethod of(String method) throws HttpError {
        for (HttpMethod httpMethod : HttpMethod.values()) {
            if (httpMethod.name().equalsIgnoreCase(method)) {
                return httpMethod;
            }
        }
        throw new HttpClientError(method + " " + StatusCode._400.getMessage(), StatusCode._400);
    }
}