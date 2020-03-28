package projects.sahoo.myspringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projects.sahoo.myspringboot.models.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project findByProjectCode(String projectCode);

}
