package com.epam.velosiped.challenge.level5.bean.definition;

import com.epam.velosiped.challenge.level5.bean.definition.BeanDefinition;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Aleksandr Barmin
 */
public class BeanDefinitionRegistry {
  private final Map<Class<?>, BeanDefinition> beanDefinitions = new HashMap<>();

  public void register(Class<?>... beanClasses) {
    Arrays.stream(beanClasses).forEach(this::register);
  }

  private void register(Class<?> beanClass) {
    try {
      beanDefinitions.put(
          beanClass,
          new BeanDefinition(
              beanClass,
              beanClass.getDeclaredConstructor(),
              Arrays.asList(beanClass.getInterfaces())
          )
      );
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public BeanDefinition getDefinition(Class<?> beanClass) {
    return beanDefinitions.get(beanClass);
  }

  public Collection<BeanDefinition> getDefinitions(Class<?> interfaceClass) {
    return beanDefinitions.values()
        .stream()
        .filter(definition -> definition.interfaces().contains(interfaceClass))
        .collect(Collectors.toList());
  }
}
