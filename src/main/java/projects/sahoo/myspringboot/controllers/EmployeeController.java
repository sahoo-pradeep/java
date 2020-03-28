package projects.sahoo.myspringboot.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projects.sahoo.myspringboot.controllers.request.EmployeeRequest;
import projects.sahoo.myspringboot.controllers.request.PreviousCompanyRequest;
import projects.sahoo.myspringboot.controllers.request.ProjectRequest;
import projects.sahoo.myspringboot.models.Employee;
import projects.sahoo.myspringboot.services.EmployeeService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @PostMapping("/create")
    public ResponseEntity<Employee> createEmployee(@RequestBody EmployeeRequest employeeRequest) {
        Employee employee = employeeService.createEmployee(employeeRequest);
        return ResponseEntity.ok(employee);
    }

    @PostMapping("/addProject")
    public ResponseEntity<Employee> addProject(@RequestBody ProjectRequest projectRequest) {
        Employee employee = employeeService.addProject(projectRequest);
        return ResponseEntity.ok(employee);
    }

    @PostMapping("/addPreviousCompany")
    public ResponseEntity<Employee> addPreviousCompany(
            @RequestBody PreviousCompanyRequest previousCompanyRequest) {
        Employee employee = employeeService.addPreviousCompany(previousCompanyRequest);
        return ResponseEntity.ok(employee);
    }
}
