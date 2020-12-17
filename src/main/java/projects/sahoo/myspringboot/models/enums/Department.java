package projects.sahoo.myspringboot.models.enums;

public enum Department {
  TECHNOLOGY("Technology"),
  FINANCE("Finance"),
  HR("HR"),
  MARKETING("Marketing");

  String value;

  Department(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
