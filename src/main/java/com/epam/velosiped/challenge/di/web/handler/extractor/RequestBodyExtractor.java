package com.epam.velosiped.challenge.di.web.handler.extractor;

import com.epam.velosiped.challenge.di.Inject;
import com.epam.velosiped.challenge.di.web.RequestBody;
import com.epam.velosiped.challenge.di.web.converter.FromJsonConverter;
import com.sun.net.httpserver.HttpExchange;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author Aleksandr Barmin
 */
public class RequestBodyExtractor implements ParameterExtractor {
  @Inject
  private FromJsonConverter jsonConverter;

  @Override
  public boolean supports(Annotation[] annotations) {
    for (Annotation annotation : annotations) {
      if (annotation.annotationType().isAssignableFrom(RequestBody.class)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public Object extract(Annotation[] annotations, Class<?> targetType, HttpExchange exchange) {
    // reading body to string
    final StringBuilder builder = new StringBuilder();
    try (final BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()))) {
      String buffer;
      while ((buffer = reader.readLine()) != null) {
        builder.append(buffer);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    // converting to Map from json
    final Map<String, String> jsonData = jsonConverter.convert(builder.toString());

    // mapping map to object
    final Object newInstance;
    try {
      newInstance = targetType.getDeclaredConstructor().newInstance();
      for (Field declaredField : targetType.getDeclaredFields()) {
        if (jsonData.containsKey(declaredField.getName())) {
          declaredField.setAccessible(true);
          declaredField.set(newInstance, jsonData.get(declaredField.getName()));
        }
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return newInstance;
  }
}
