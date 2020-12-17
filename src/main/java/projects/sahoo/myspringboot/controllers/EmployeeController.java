package projects.sahoo.myspringboot.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import projects.sahoo.myspringboot.controllers.request.EmployeeRequest;
import projects.sahoo.myspringboot.controllers.request.PreviousCompanyRequest;
import projects.sahoo.myspringboot.controllers.request.ProjectRequest;
import projects.sahoo.myspringboot.models.entities.Employee;
import projects.sahoo.myspringboot.services.EmployeeService;

@Slf4j
@RestController
@Api
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    @ApiOperation("Get list of all Employee Details")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.getAllEmployees());
    }

    @GetMapping("/employee/{globalId}")
    @ApiOperation("Get Employee Details by globalId")
    public ResponseEntity<Employee> getEmployee(@PathVariable("globalId") String globalId) {
        return employeeService.getEmployee(globalId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/employee")
    @ApiOperation(value = "Create a new Employee")
    public ResponseEntity<Employee> createEmployee(@RequestBody @Valid EmployeeRequest employeeRequest) {
        return ResponseEntity.ok(employeeService.createEmployee(employeeRequest));
    }

    @PostMapping("/employee/project")
    @ApiOperation(value = "Add a new Project to Employee")
    public ResponseEntity<Employee> addProject(@RequestBody ProjectRequest projectRequest) {
        Employee employee = employeeService.addProject(projectRequest);
        return ResponseEntity.ok(employee);
    }

    @PostMapping("/employee/company")
    @ApiOperation(value = "Add Employee Previous Company")
    public ResponseEntity<Employee> addPreviousCompany(
            @RequestBody PreviousCompanyRequest previousCompanyRequest) {
        Employee employee = employeeService.addPreviousCompany(previousCompanyRequest);
        return ResponseEntity.ok(employee);
    }
}
