package com.epam.velosiped.challenge.level5.bean.factory;

import com.epam.velosiped.challenge.level5.bean.annotation.Inject;
import com.epam.velosiped.challenge.level5.bean.definition.BeanDefinition;
import com.epam.velosiped.challenge.level5.bean.definition.BeanDefinitionRegistry;
import com.epam.velosiped.challenge.level5.post.processor.BeanPostProcessor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Aleksandr Barmin
 */
public class BeanFactory {
  private final BeanDefinitionRegistry registry;
  private final Map<String, Object> availableBeans = new ConcurrentHashMap<>();

  private final Collection<BeanPostProcessor> postProcessors = new ArrayList<>();

  public BeanFactory(BeanDefinitionRegistry registry) {
    this.registry = registry;
  }

  public void prepare() {
    // preparing post-processors
    registry.getDefinitions(BeanPostProcessor.class)
        .stream()
        .map(BeanDefinition::beanClass)
        .map(this::getBean)
        .map(BeanPostProcessor.class::cast)
        .forEach(postProcessors::add);
  }

  public <T> T getBean(Class<T> beanClass) {
    final BeanDefinition definition = registry.getDefinition(beanClass);
    if (definition == null) {
      throw new IllegalArgumentException(String.format(
          "No bean with type %s",
          beanClass
      ));
    };

    final String className = beanClass.getName();
    return (T) availableBeans.computeIfAbsent(className, this::createInstance);
  }

  private Object createInstance(String className) {
    try {
      final Class<?> targetClass = Class.forName(className);
      final Object newInstance = targetClass.getDeclaredConstructor().newInstance();
      for (Field field : targetClass.getDeclaredFields()) {
        if (field.isAnnotationPresent(Inject.class)) {
          field.setAccessible(true);
          final Class<?> fieldType = field.getType();
          field.set(newInstance, getBean(fieldType));
        }
      }

      // post-processing
      for (BeanPostProcessor postProcessor : postProcessors) {
        postProcessor.afterPropertiesSet(newInstance);
      }

      return newInstance;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
