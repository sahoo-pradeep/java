package projects.sahoo.myspringboot.dao;

import java.util.List;
import javax.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import projects.sahoo.myspringboot.models.Person;

@Repository
@Slf4j
public class PersonDaoImpl {

  private final EntityManager entityManager;

  @Autowired
  public PersonDaoImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Transactional
  public Person savePerson(String name) {
    //transaction-begin
    Person person = new Person();
    person.setName(name);

    //in-place modification
    entityManager.persist(person);
    log.info("After persist: {}", person);

    // On commit, this update will also get reflected because we are modifying the persisted object
    // To avoid updating the object in DB, detach it from persistence context
    entityManager.detach(person);
    person.setName(name + " (Modified)");

    return person;
    //transaction-end : commit
  }

  public Person findPerson(Long id) {
    Person person = entityManager.find(Person.class, id);

    return person;
  }

  @Transactional
  public Person updatePerson(Long id, String name) {
    Person person = new Person();
    person.setId(id);
    person.setName(name);

    // Note: merge() is used for detached object -- to --> persistent object
    // If, entityManager.persist(person) is used to update, will get an exception as:
    //     detached entity passed to persist
    return entityManager.merge(person);
  }

  @SuppressWarnings("unchecked")
  public List<Person> findAllPerson() {
    List<Person> persons = entityManager
        .createQuery("SELECT P FROM Person P")
        .getResultList();

    return persons;
  }

  @Transactional
  public void removePerson(Long id) {
    Person person = findPerson(id);
    entityManager.remove(person);
  }
}
