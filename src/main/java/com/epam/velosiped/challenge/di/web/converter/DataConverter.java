package com.epam.velosiped.challenge.di.web.converter;

/**
 * @author Aleksandr Barmin
 */
public interface DataConverter<F, T> {
  boolean supports(Class<?> from, Class<?> to);

  T convert(F from);
}
