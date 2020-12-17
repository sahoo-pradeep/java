package projects.sahoo.myspringboot.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "projects", uniqueConstraints = {
    @UniqueConstraint(name = "unq_project_code", columnNames = {Project.PROJECT_CODE})})
public class Project implements Serializable {

  private static final long serialVersionUID = 1L;
  public static final String PROJECT_CODE = "project_code";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @EqualsAndHashCode.Include
  @Column(name = PROJECT_CODE, nullable = false, updatable = false)
  private String projectCode;

  @Column(nullable = false)
  private String projectName;

  @ManyToMany(mappedBy = "projects", fetch = FetchType.LAZY)
  @JsonIgnore
  @ToString.Exclude
  @Builder.Default
  private Set<Employee> employees = new HashSet<>();

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private LocalDateTime createDate;

  @UpdateTimestamp
  @Column(nullable = false)
  private LocalDateTime updateDate;
}
