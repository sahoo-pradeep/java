package projects.sahoo.myspringboot.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projects.sahoo.myspringboot.controllers.request.EmployeeRequest;
import projects.sahoo.myspringboot.controllers.request.PreviousCompanyRequest;
import projects.sahoo.myspringboot.controllers.request.ProjectRequest;
import projects.sahoo.myspringboot.models.Employee;
import projects.sahoo.myspringboot.models.PersonalDetails;
import projects.sahoo.myspringboot.models.PreviousCompany;
import projects.sahoo.myspringboot.models.Project;
import projects.sahoo.myspringboot.repositories.EmployeeRepository;
import projects.sahoo.myspringboot.repositories.ProjectRepository;

import java.util.List;

@Service
@Slf4j
public class EmployeeService {
    private EmployeeRepository employeeRepository;
    private ProjectRepository projectRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository,
            ProjectRepository projectRepository) {
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
    }

    @Transactional(readOnly = true)
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Transactional
    public Employee createEmployee(EmployeeRequest employeeRequest) {
        log.debug("Creating Employee with request: {}", employeeRequest);
        Employee employee = new Employee();

        PersonalDetails personalDetails = new PersonalDetails();
        personalDetails.setContactNumber(employeeRequest.getContactNumber());
        personalDetails.setBloodGroup(employeeRequest.getBloodGroup());
        employee.setGlobalId(employeeRequest.getGlobalId());
        employee.setName(employeeRequest.getName());
        employee.setDepartment(employeeRequest.getDepartment());

        employee.setPersonalDetails(personalDetails);
        employee = employeeRepository.save(employee);
        return employee;
    }

    @Transactional
    public Employee addProject(ProjectRequest projectRequest) {
        log.debug("Adding Project with request: {}", projectRequest);
        Employee employee = employeeRepository.findByGlobalId(projectRequest.getGlobalId());
        if (employee == null) {
            throw new RuntimeException("Invalid Global ID");
        }

        Project project = projectRepository.findByProjectCode(projectRequest.getProjectCode());
        if (project == null) {
            project = new Project();
            project.setProjectName(projectRequest.getProjectName());
            project.setProjectCode(projectRequest.getProjectCode());
        }

        employee.addProject(project);
        return employeeRepository.save(employee);
    }

    @Transactional
    public Employee addPreviousCompany(PreviousCompanyRequest previousCompanyRequest) {
        log.debug("Adding Previous Company with request: {}", previousCompanyRequest);
        Employee employee = employeeRepository.findByGlobalId(previousCompanyRequest.getGlobalId());
        if (employee == null) {
            throw new RuntimeException("Invalid Global ID");
        }

        PreviousCompany previousCompany = new PreviousCompany();
        previousCompany.setCompanyName(previousCompanyRequest.getCompanyName());
        previousCompany.setStartDate(previousCompanyRequest.getStartDate());
        previousCompany.setEndDate(previousCompanyRequest.getEndDate());
        employee.addPreviousCompany(previousCompany);
        return employeeRepository.save(employee);
    }
}
