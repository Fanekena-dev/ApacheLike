package component.error.config;

public class NullConfigValueException extends ConfigException {
  // final
  // fields
  private String configKey;

  // constructors
  public NullConfigValueException(String message, String configKey) {
    super(message);
    this.configKey = configKey;
  }

  // getters & setters
  public String getConfigKey() {
    return configKey;
  }

  public void setConfigKey(String configKey) {
    this.configKey = configKey;
  }
  // methods
  // static methods
}
