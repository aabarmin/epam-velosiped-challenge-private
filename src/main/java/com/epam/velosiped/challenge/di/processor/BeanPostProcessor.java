package com.epam.velosiped.challenge.di.processor;

/**
 * @author Aleksandr Barmin
 */
public interface BeanPostProcessor {
  default void afterPropertiesSet(Object bean) {

  }
}
