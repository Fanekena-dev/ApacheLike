package component.error.config;

public class IllegalConfigValueException extends ConfigException {
  // final
  // fields
  private String configKey;
  private String value;

  // constructors
  public IllegalConfigValueException(String message, String configKey, String value) {
    super(message);
    this.configKey = configKey;
    this.value = value;
  }

  // getters & setters
  public String getConfigKey() {
    return configKey;
  }

  public void setConfigKey(String configKey) {
    this.configKey = configKey;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
  // methods
  // static methods
}
