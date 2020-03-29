package projects.sahoo.myspringboot.controllers.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import projects.sahoo.myspringboot.models.BloodGroup;
import projects.sahoo.myspringboot.models.Department;

@Getter
@Setter
@ToString
public class EmployeeRequest {
    private String globalId;
    private String name;
    private Department department;
    private String contactNumber;
    private BloodGroup bloodGroup;
}
