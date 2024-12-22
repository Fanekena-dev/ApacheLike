package component.error.config;

public class MissingKeyException extends ConfigException {
  // final
  // fields
  private String missingKey;

  // constructors
  public MissingKeyException(String message, String missingKey) {
    super(message);
    this.missingKey = missingKey;
  }

  // getters & setters
  public String getMissingKey() {
    return missingKey;
  }

  public void setMissingKey(String missingKey) {
    this.missingKey = missingKey;
  }
  // methods
  // static methods
}
