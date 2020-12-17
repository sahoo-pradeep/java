package projects.sahoo.myspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"projects.sahoo.myspringboot", "projects.sahoo.bootstrap"})
public class MySpringBootApplication {

  public static void main(String[] args) {
    SpringApplication.run(MySpringBootApplication.class, args);
  }
}
