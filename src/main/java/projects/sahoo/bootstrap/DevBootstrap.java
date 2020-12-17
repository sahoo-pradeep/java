package projects.sahoo.bootstrap;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import projects.sahoo.myspringboot.controllers.request.EmployeeRequest;
import projects.sahoo.myspringboot.controllers.request.PreviousCompanyRequest;
import projects.sahoo.myspringboot.controllers.request.ProjectRequest;
import projects.sahoo.myspringboot.models.enums.BloodGroup;
import projects.sahoo.myspringboot.models.enums.Department;
import projects.sahoo.myspringboot.services.EmployeeService;

@Component
public class DevBootstrap {

  private static final String GLOBAL_ID = "TEC-001";
  private static final String NAME = "John Cena";
  private static final Department DEPARTMENT = Department.TECHNOLOGY;
  private static final String CONTACT_NUMBER = "8811881188";
  private static final BloodGroup BLOOD_GROUP = BloodGroup.A_POSITIVE;

  private static final String PROJECT_CODE = "PROJ-001";
  private static final String PROJECT_NAME = "Project 001";

  private static final String COMPANY_NAME = "Company A";
  private static final LocalDate START_DATE = LocalDate.of(2018, 1, 1);
  private static final LocalDate END_DATE = LocalDate.of(2019, 3, 31);


  private final EmployeeService employeeService;

  @Autowired
  public DevBootstrap(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @EventListener(ContextRefreshedEvent.class)
  public void init() {
    EmployeeRequest employeeRequest =
        new EmployeeRequest(GLOBAL_ID, NAME, DEPARTMENT, CONTACT_NUMBER, BLOOD_GROUP);
    employeeService.createEmployee(employeeRequest);

    ProjectRequest projectRequest = new ProjectRequest(GLOBAL_ID, PROJECT_CODE, PROJECT_NAME);
    employeeService.addProject(projectRequest);

    PreviousCompanyRequest previousCompanyRequest =
        new PreviousCompanyRequest(GLOBAL_ID, COMPANY_NAME, START_DATE, END_DATE);
    employeeService.addPreviousCompany(previousCompanyRequest);
  }
}
