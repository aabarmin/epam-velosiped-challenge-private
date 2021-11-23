package com.epam.velosiped.challenge.level5;

import com.epam.velosiped.challenge.level5.bean.definition.BeanDefinitionRegistry;
import com.epam.velosiped.challenge.level5.bean.factory.BeanFactory;
import com.epam.velosiped.challenge.level5.post.processor.PostProcessBeanPostProcessor;
import java.util.Arrays;
import java.util.Set;

/**
 * @author Aleksandr Barmin
 */
public class ConfigurableApplicationContext implements ApplicationContext {
  private final BeanDefinitionRegistry registry = new BeanDefinitionRegistry();
  private final BeanFactory factory = new BeanFactory(registry);

  public ConfigurableApplicationContext(Class<?>... beanClasses) {
    // registering infrastructure components
    // I'm doing it manually because I don't want to scan classpath
    Set.of(
        PostProcessBeanPostProcessor.class
    ).forEach(registry::register);

    // registering custom classes
    Arrays.stream(beanClasses).forEach(registry::register);

    // almost done
    factory.prepare();
  }

  @Override
  public <T> T getBean(Class<T> beanClass) {
    return factory.getBean(beanClass);
  }

  @Override
  public void register(Class<?> beanClass) {
    registry.register(beanClass);
  }
}
