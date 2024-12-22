package component.error;

public class ExceptionManager {
  // final
  // fields
  // constructors
  // getters & setters
  // methods
  // static methods
  public static String formatException(Exception e) {
    StringBuilder formattedException = new StringBuilder();
    formattedException.append(e.getLocalizedMessage()).append("\n");
    for (StackTraceElement stackTraceElement : e.getStackTrace()) {
      formattedException.append(stackTraceElement.toString()).append(" ")
          .append(stackTraceElement.getLineNumber()).append("\n");
    }
    return formattedException.toString();
  }
}
