package projects.sahoo.myspringboot.services;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projects.sahoo.myspringboot.controllers.request.EmployeeRequest;
import projects.sahoo.myspringboot.controllers.request.PreviousCompanyRequest;
import projects.sahoo.myspringboot.controllers.request.ProjectRequest;
import projects.sahoo.myspringboot.exception.EmployeeException;
import projects.sahoo.myspringboot.models.entities.Employee;
import projects.sahoo.myspringboot.models.entities.PersonalDetails;
import projects.sahoo.myspringboot.models.entities.PreviousCompany;
import projects.sahoo.myspringboot.models.entities.Project;
import projects.sahoo.myspringboot.repositories.EmployeeRepository;
import projects.sahoo.myspringboot.repositories.ProjectRepository;

import java.util.List;

@Service
@Slf4j
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;

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

    @Transactional(readOnly = true)
    public Optional<Employee> getEmployee(String globalId) {
        return employeeRepository.findByGlobalId(globalId);
    }

    @Transactional
    public Employee createEmployee(EmployeeRequest employeeRequest) {
        log.debug("Creating Employee with request: {}", employeeRequest);

        Employee employee = Employee.builder()
            .globalId(employeeRequest.getGlobalId())
            .name(employeeRequest.getName())
            .department(employeeRequest.getDepartment())
            .build();

        PersonalDetails personalDetails = PersonalDetails.builder()
            .contactNumber(employeeRequest.getContactNumber())
            .bloodGroup(employeeRequest.getBloodGroup())
            .build();

        employee.setPersonalDetails(personalDetails);

        return employeeRepository.save(employee);
    }

    @Transactional
    public Employee addProject(ProjectRequest projectRequest) {
        log.debug("Adding Project with request: {}", projectRequest);
        Employee employee = employeeRepository
            .findByGlobalId(projectRequest.getGlobalId())
            .orElseThrow(() -> new EmployeeException("Invalid Global ID"));

        Project project = projectRepository
            .findByProjectCode(projectRequest.getProjectCode())
            .orElseGet(() -> Project.builder()
                .projectCode(projectRequest.getProjectCode())
                .projectName(projectRequest.getProjectName())
                .build());

        employee.addProject(project);

        return employeeRepository.save(employee);
    }

    @Transactional
    public Employee addPreviousCompany(PreviousCompanyRequest previousCompanyRequest) {
        log.debug("Adding Previous Company with request: {}", previousCompanyRequest);
        Employee employee = employeeRepository
            .findByGlobalId(previousCompanyRequest.getGlobalId())
            .orElseThrow(() -> new EmployeeException("Invalid Global ID"));

        PreviousCompany previousCompany = PreviousCompany.builder()
            .companyName(previousCompanyRequest.getCompanyName())
            .startDate(previousCompanyRequest.getStartDate())
            .endDate(previousCompanyRequest.getEndDate())
            .build();


        employee.addPreviousCompany(previousCompany);
        return employeeRepository.save(employee);
    }
}
