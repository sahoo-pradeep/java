package projects.sahoo.myspringboot.models.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import projects.sahoo.myspringboot.models.enums.Department;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees", uniqueConstraints = {
    @UniqueConstraint(name = "unq_global_id", columnNames = {Employee.GLOBAL_ID})})
public class Employee implements Serializable {

  private static final long serialVersionUID = 1L;
  public static final String GLOBAL_ID = "global_id";
  public static final String EMPLOYEE_ID = "employee_id";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = GLOBAL_ID, nullable = false, updatable = false, length = 20)
  @EqualsAndHashCode.Include
  private String globalId;

  @Column(nullable = false)
  private String name;

  @Enumerated(value = EnumType.STRING)
  private Department department;

  @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private PersonalDetails personalDetails;

  // Note: orphanRemoval: if previousCompanies is set to null, or some other Set, then the
  // existing previousCompanies will become orphan. Setting orphanRemoval = true will remove such
  // orphan entities (clean up process)
  @Setter(AccessLevel.PRIVATE)
  @Builder.Default
  @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY,
      orphanRemoval = true)
  private Set<PreviousCompany> previousCompanies = new HashSet<>();

  // Note: Do not use CascadeType.REMOVE for ManyToMany. It will remove all mapped entities as well
  @Setter(AccessLevel.PRIVATE)
  @Builder.Default
  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
  @JoinTable(name = "employee_project",
      joinColumns = {
          @JoinColumn(name = "employee_id", foreignKey = @ForeignKey(name = "fk_employees_project_employee_id"))},
      inverseJoinColumns = {
          @JoinColumn(name = "project_id", foreignKey = @ForeignKey(name = "fk_employees_project_project_id"))})
  private Set<Project> projects = new HashSet<>();

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private LocalDateTime createDate;

  @UpdateTimestamp
  @Column(nullable = false)
  private LocalDateTime updateDate;

  // mandatory for OneToOne mapping
  public void setPersonalDetails(PersonalDetails personalDetails) {
    if (personalDetails == null) {
      if (this.personalDetails != null) {
        this.personalDetails.setEmployee(null);
      }
    } else {
      personalDetails.setEmployee(this);
    }

    this.personalDetails = personalDetails;
  }

  // for OneToMany mapping - add and remove mothods
  public void addPreviousCompany(PreviousCompany previousCompany) {
    previousCompanies.add(previousCompany);
    previousCompany.setEmployee(this);
  }

  public void removePreviousCompany(PreviousCompany previousCompany) {
    previousCompanies.remove(previousCompany);
    previousCompany.setEmployee(null);
  }

  // mandatory for ManyToMany entities to be in sync
  public void addProject(Project project) {
    projects.add(project);
    project.getEmployees().add(this);
  }

  public void removeProject(Project project) {
    projects.remove(project);
    project.getEmployees().remove(this);
  }
}