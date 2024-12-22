package component.config;

public class Param {
  // final
  // fields
  private String name;
  private String confKey;
  private Class<?> type;
  private Object value;

  // constructors
  public Param(String name, String confKey, Class<?> type) {
    this.name = name;
    this.confKey = confKey;
    this.type = type;
  }

  // getters & setters
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getConfKey() {
    return confKey;
  }

  public void setConfKey(String confKey) {
    this.confKey = confKey;
  }

  public Class<?> getType() {
    return type;
  }

  public void setType(Class<?> type) {
    this.type = type;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }
  // methods
  // static methods
}
