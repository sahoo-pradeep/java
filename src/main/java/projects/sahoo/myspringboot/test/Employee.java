package projects.sahoo.myspringboot.test;

public class Employee {

  private String firstName;
  private String lastName;
  private String age;

  @Override
  public String toString() {
    return "Employee{" +
        "firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", age='" + age + '\'' +
        '}';
  }
}
