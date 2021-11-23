package com.epam.velosiped.challenge.di;

/**
 * @author Aleksandr Barmin
 */
public interface ApplicationContext {
  <T> T getBean(Class<T> beanClass);

  void register(Class<?> beanClass);
}
