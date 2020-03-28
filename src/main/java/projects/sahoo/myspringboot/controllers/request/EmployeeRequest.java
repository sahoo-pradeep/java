package projects.sahoo.myspringboot.controllers.request;

import lombok.Getter;
import lombok.Setter;
import projects.sahoo.myspringboot.models.Department;

@Getter
@Setter
public class EmployeeRequest {
    private String globalId;
    private String name;
    private Department department;
}
