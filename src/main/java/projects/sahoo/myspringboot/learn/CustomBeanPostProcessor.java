package projects.sahoo.myspringboot.learn;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class CustomBeanPostProcessor implements BeanPostProcessor {
    //TODO: Not Working
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        if (bean instanceof SpringBeanLifeCycle) {
            ((SpringBeanLifeCycle) bean).beforeInit();
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        if (bean instanceof SpringBeanLifeCycle) {
            ((SpringBeanLifeCycle) bean).afterInit();
        }
        return bean;
    }
}
