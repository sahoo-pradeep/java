package projects.sahoo.myspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"projects.sahoo"})
public class MySpringBootApplication {

  public static void main(String[] args) {
    ApplicationContext applicationContext =
        SpringApplication.run(MySpringBootApplication.class, args);
  }
}
