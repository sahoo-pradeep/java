package projects.sahoo.myspringboot.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projects.sahoo.myspringboot.models.entities.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

  Optional<Project> findByProjectCode(String projectCode);

}
