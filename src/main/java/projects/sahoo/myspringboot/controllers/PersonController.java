package projects.sahoo.myspringboot.controllers;

import io.swagger.annotations.Api;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import projects.sahoo.myspringboot.dao.PersonDaoImpl;
import projects.sahoo.myspringboot.models.Person;

@RestController
@Api
public class PersonController {

  private final PersonDaoImpl personDao;

  @Autowired
  public PersonController(PersonDaoImpl personDao) {
    this.personDao = personDao;
  }

  @PostMapping("/person")
  public ResponseEntity<Person> createPerson(@RequestParam String name) {
    ResponseEntity<Person> person = ResponseEntity.ok(personDao.savePerson(name));

    return person;
  }

  @GetMapping("/person")
  public ResponseEntity<Person> findPerson(@RequestParam Long id) {
    return ResponseEntity.ok(personDao.findPerson(id));
  }

  @PutMapping("/person")
  public ResponseEntity<Person> updateName(@RequestParam Long id, @RequestParam String name) {
    return ResponseEntity.ok(personDao.updatePerson(id, name));
  }

  @GetMapping("/persons")
  public ResponseEntity<List<Person>> findAllPersons() {
    return ResponseEntity.ok(personDao.findAllPerson());
  }

  @DeleteMapping("/person")
  public ResponseEntity<Void> removePerson(@RequestParam Long id) {
    personDao.removePerson(id);
    return ResponseEntity.ok().build();
  }
}
