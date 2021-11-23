package com.epam.velosiped.challenge.level5.post.processor;

/**
 * @author Aleksandr Barmin
 */
public interface BeanPostProcessor {
  default void afterPropertiesSet(Object bean) {

  }
}
