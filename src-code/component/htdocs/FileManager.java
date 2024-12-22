package component.htdocs;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import component.error.ExceptionManager;
import component.error.http.HttpClientError;
import component.error.http.HttpServerError;
import component.error.http.HttpError;
import util.AppConsole;
import util.StatusCode;
import util.Time;

public class FileManager {
    // final
    // fields
    // constructors
    // getters & setters
    // methods
    // static methods
    public static byte[] getFileContent(Path filePath) throws HttpError {
        // TODO : filePath tokony chemin absolue
        byte[] content = new byte[0];
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath.toFile());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }

            byteArrayOutputStream.close();
            fileInputStream.close();

            content = byteArrayOutputStream.toByteArray();
        } catch (FileNotFoundException e) { // TODO : info client nahapatonga erreur
            AppConsole.getAppConsole().printf(Time.forConsole() + " " + ExceptionManager.formatException(e));
            throw new HttpClientError(ExceptionManager.formatException(e), StatusCode._404);
        } catch (IOException e) { // TODO : info client nahapatonga erreur
            AppConsole.getAppConsole().printf(Time.forConsole() + " " + ExceptionManager.formatException(e));
            throw new HttpServerError(ExceptionManager.formatException(e), StatusCode._500);
        }
        return content;
    }

    public static Path relativePathToHtdocs(Path path) throws HttpError {
        // path tokony chemin absolue
        if (path.isAbsolute()) {
            String pathString = path.toString();
            String htdocs = "htdocs";
            int index = pathString.indexOf(htdocs);
            if (index != -1) {
                String relativePathString = pathString.substring(index + htdocs.length());
                Path relativePath = Paths.get(relativePathString);
                return relativePath;
            } else {
                throw new HttpServerError("Internal Server Error", StatusCode._500);
            }
        } else {
            throw new HttpServerError("Internal Server Error", StatusCode._500);
        }
    }
}
