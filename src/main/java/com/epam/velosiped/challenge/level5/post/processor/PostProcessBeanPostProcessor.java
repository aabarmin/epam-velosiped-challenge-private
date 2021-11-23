package com.epam.velosiped.challenge.level5.post.processor;

import java.lang.reflect.Method;

/**
 * @author Aleksandr Barmin
 */
public class PostProcessBeanPostProcessor implements BeanPostProcessor {
  @Override
  public void afterPropertiesSet(Object bean) {
    for (Method method : bean.getClass().getDeclaredMethods()) {
      if (method.isAnnotationPresent(PostConstruct.class)) {
        method.setAccessible(true);
        try {
          method.invoke(bean);
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      }
    }
  }
}
