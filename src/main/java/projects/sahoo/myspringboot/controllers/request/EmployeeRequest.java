package projects.sahoo.myspringboot.controllers.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import projects.sahoo.myspringboot.models.enums.BloodGroup;
import projects.sahoo.myspringboot.models.enums.Department;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
public class EmployeeRequest {
    private String globalId;
    private String name;
    private Department department;
    @NotEmpty(message = "Contact Number should not be empty")
    private String contactNumber;
    private BloodGroup bloodGroup;

    public EmployeeRequest(String globalId, String name, Department department,
            String contactNumber, BloodGroup bloodGroup) {
        this.globalId = globalId;
        this.name = name;
        this.department = department;
        this.contactNumber = contactNumber;
        this.bloodGroup = bloodGroup;
    }
}
