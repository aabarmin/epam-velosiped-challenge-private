package com.epam.velosiped.challenge.di.web.handler;

import com.epam.velosiped.challenge.di.web.RequestMapping;
import com.sun.net.httpserver.HttpExchange;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author Aleksandr Barmin
 */
public class MethodHandler implements Handler {
  private final Object controller;
  private final Method method;
  private final ParameterProvider parameterProvider;

  private final String requestMethod;
  private final String endpoint;

  public MethodHandler(Object controller, Method method, ParameterProvider parameterProvider) {
    this.controller = controller;
    this.method = method;
    this.parameterProvider = parameterProvider;

    final RequestMapping mapping = method.getAnnotation(RequestMapping.class);

    this.requestMethod = mapping.method().name();
    this.endpoint = mapping.path();
  }

  @Override
  public boolean supports(HttpExchange exchange) {
    return exchange.getRequestMethod().equalsIgnoreCase(this.requestMethod) &&
        exchange.getRequestURI().getPath().equalsIgnoreCase(this.endpoint);
  }

  @Override
  public Object handle(HttpExchange exchange) {
    try {
      final Object[] parameters = parameterProvider.extract(method, exchange);
      return method.invoke(controller, parameters);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private <T> T getAnnotation(Annotation[] annotations, Class<T> annotationClass) {
    for (Annotation annotation : annotations) {
      if (annotation.annotationType().equals(annotationClass)) {
        return (T) annotation;
      }
    }
    throw new IllegalArgumentException();
  }
}
