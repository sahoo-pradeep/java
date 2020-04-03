package projects.sahoo.myspringboot.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projects.sahoo.myspringboot.controllers.request.EmployeeRequest;
import projects.sahoo.myspringboot.controllers.request.PreviousCompanyRequest;
import projects.sahoo.myspringboot.controllers.request.ProjectRequest;
import projects.sahoo.myspringboot.models.entities.Employee;
import projects.sahoo.myspringboot.services.EmployeeService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/employee")
@Api(value = "Employee Management APIs") //TODO: Not Working
public class EmployeeController {
    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/getAll")
    @ApiOperation(value = "Get list of all Employee Details")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved Employee List"),
            @ApiResponse(code = 500, message = "Failed to retrieve Employee List"),})
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/get/{globalId}")
    public ResponseEntity<Employee> getEmployee(
            @ApiParam(value = "Get Employee Details by globalId", required = true)
            @PathVariable("globalId") String globalId) {
        Employee employee = employeeService.getEmployee(globalId);
        return ResponseEntity.ok(employee);
    }

    @PostMapping("/create")
    @ApiOperation(value = "Create a new Employee")
    public ResponseEntity<Employee> createEmployee(@RequestBody EmployeeRequest employeeRequest) {
        Employee employee = employeeService.createEmployee(employeeRequest);
        return ResponseEntity.ok(employee);
    }

    @PostMapping("/addProject")
    @ApiOperation(value = "Add a new Project to Employee")
    public ResponseEntity<Employee> addProject(@RequestBody ProjectRequest projectRequest) {
        Employee employee = employeeService.addProject(projectRequest);
        return ResponseEntity.ok(employee);
    }

    @PostMapping("/addPreviousCompany")
    @ApiOperation(value = "Add Employee Previous Company")
    public ResponseEntity<Employee> addPreviousCompany(
            @RequestBody PreviousCompanyRequest previousCompanyRequest) {
        Employee employee = employeeService.addPreviousCompany(previousCompanyRequest);
        return ResponseEntity.ok(employee);
    }
}
