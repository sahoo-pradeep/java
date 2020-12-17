package projects.sahoo.myspringboot.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projects.sahoo.myspringboot.models.entities.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  Optional<Employee> findByGlobalId(String globalId);
}
