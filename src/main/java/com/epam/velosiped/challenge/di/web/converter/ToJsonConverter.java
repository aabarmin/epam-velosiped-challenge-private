package com.epam.velosiped.challenge.di.web.converter;

import java.lang.reflect.Field;

/**
 * @author Aleksandr Barmin
 */
public class ToJsonConverter implements DataConverter<Object, String> {
  @Override
  public boolean supports(Class<?> from, Class<?> to) {
    return from.isAssignableFrom(Object.class) &&
        to.isAssignableFrom(String.class);
  }

  @Override
  public String convert(Object from) {
    final StringBuilder builder = new StringBuilder();
    boolean isFirst = true;
    builder.append("{");
    for (Field field : from.getClass().getDeclaredFields()) {
      if (!isFirst) {
        builder.append(", ");
      }
      isFirst = false;

      builder.append(String.format(
          "\"%s\": \"%s\"",
          field.getName(),
          getStringValue(from, field)
      ));
    }
    builder.append("}");
    return builder.toString();
  }

  private Object getStringValue(Object from, Field field) {
    try {
      field.setAccessible(true);
      return field.get(from);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }
}
