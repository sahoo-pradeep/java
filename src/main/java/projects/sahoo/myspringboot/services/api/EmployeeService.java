package projects.sahoo.myspringboot.services.api;

import java.util.List;
import java.util.Optional;
import projects.sahoo.myspringboot.controllers.request.EmployeeRequest;
import projects.sahoo.myspringboot.controllers.request.PreviousCompanyRequest;
import projects.sahoo.myspringboot.controllers.request.ProjectRequest;
import projects.sahoo.myspringboot.models.entities.Employee;

public interface EmployeeService {

  Optional<Employee> getEmployee(String globalId);

  List<Employee> getAllEmployees();

  Employee createEmployee(EmployeeRequest employeeRequest);

  Employee addProject(ProjectRequest projectRequest);

  Employee addPreviousCompany(PreviousCompanyRequest previousCompanyRequest);

}
