package util;

import component.error.http.HttpError;
import component.error.http.HttpServerError;

public enum MediaType {
    // enum
    // Images
    IMAGE_JPEG("image/jpeg", ".jpg", ".jpeg"),
    IMAGE_PNG("image/png", ".png"),
    IMAGE_GIF("image/gif", ".gif"),
    IMAGE_SVG("image/svg+xml", ".svg"),
    IMAGE_WEBP("image/webp", ".webp"),

    // Audio
    AUDIO_MP3("audio/mp3", ".mp3"),
    AUDIO_OGG("audio/ogg", ".ogg"),
    AUDIO_WAV("audio/wav", ".wav"),
    AUDIO_FLAC("audio/flac", ".flac"),

    // Vidéos
    VIDEO_MP4("video/mp4", ".mp4"),
    VIDEO_WEBM("video/webm", ".webm"),
    VIDEO_OGG("video/ogg", ".ogg"),

    // Texte
    TEXT_HTML("text/html", ".html"),
    TEXT_CSS("text/css", ".css"),
    TEXT_PLAIN("text/plain", ".txt"),
    TEXT_JSON("application/json", ".json"),
    TEXT_MARKDOWN("text/markdown", ".md"),

    // Documents
    APPLICATION_PDF("application/pdf", ".pdf"),
    APPLICATION_ZIP("application/zip", ".zip"),
    APPLICATION_RAR("application/x-rar-compressed", ".rar"),

    // Fonts
    FONT_TTF("font/ttf", ".ttf"),
    FONT_OTF("font/otf", ".otf"),
    FONT_WOFF("font/woff", ".woff"),
    FONT_WOFF2("font/woff2", ".woff2");

    // final
    private final String contentType;
    private final String[] extensions;

    // fields
    // contructors
    MediaType(String contentType, String... extensions) {
        this.contentType = contentType;
        this.extensions = extensions;
    }

    // getters & setters
    public String getContentType() {
        return contentType;
    }

    public String[] getExtensions() {
        return extensions;
    }
    // methods

    // static methods
    public static MediaType of(String extension) throws HttpError {
        for (MediaType mediaType : MediaType.values()) {
            for (String ext : mediaType.getExtensions()) {
                if (ext.equalsIgnoreCase(extension)) {
                    return mediaType;
                }
            }
        }
        throw new HttpServerError(extension + " " + StatusCode._500.getMessage(), StatusCode._500);
    }
}
