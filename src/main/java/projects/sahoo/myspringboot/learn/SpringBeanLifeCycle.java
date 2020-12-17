package projects.sahoo.myspringboot.learn;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/*
Spring IoC (Inversion of Control) container manages Spring beans.
Spring Bean is just a Spring-managed instantiation of Java class.

Spring IoC is responsible for instantiating, initializing, and wiring beans. It also manages the
lifecycle of a Bean.
*/
//@Component
public class SpringBeanLifeCycle
    implements InitializingBean, DisposableBean, BeanNameAware, BeanFactoryAware,
    ApplicationContextAware, BeanPostProcessor {

  // 1. Instantiate
  public SpringBeanLifeCycle() {
    System.out.println("## I'm in LifeCycleDemoBean Constructor");
  }

  // 3. Call setBeanName of BeanNameAware
  @Override
  public void setBeanName(String s) {
    System.out.println("## My Bean Name is: " + s);
  }

  // 4. Call setBeanFactory of BeanFactoryAware
  @Override
  public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    System.out.println("## Bean Factory has been set");
  }

  // 5. Call setApplicationContext of ApplicationContextAware
  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    System.out.println("## Application context has been set. id: " + applicationContext.getId()
        + ", applicationName: " + applicationContext.getApplicationName()
        + ", displayName: " + applicationContext.getDisplayName());
  }

  // 6. Call to postProcessBeforeInitialization of BeanPostProcessor
  public void beforeInit() {
    System.out.println("## BeforeInit - called by Bean Post Processor");
  }

  // 7. Call method annotated with PostConstruct, which is Lifecycle Annotation
  @PostConstruct
  public void postConstruct() {
    System.out.println("## PostConstructed annotated method has been called");
  }

  // 8. Call afterPropertiesSet of InitializingBean
  @Override
  public void afterPropertiesSet() {
    System.out.println("## The Lifecycle bean has its property set");
  }

  // 9. Call to postProcessAfterInitialization of BeanPostProcessor
  public void afterInit() { //BeanPostProcessor
    System.out.println("## AfterInit - called by Bean Post Processor");
  }

  // 10. Call to @PreDestroy annotated method (Lifecycle Annotation) before destroy is called.
  @PreDestroy
  public void preDestroy() {
    System.out.println("## PreDestroy annotated method has been called");
  }

  // 11. Call to destroy of DisposableBean
  @Override
  public void destroy() {
    System.out.println("## The Lifecycle bean has been terminated");
  }
}
