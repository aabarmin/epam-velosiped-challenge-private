package com.epam.velosiped.challenge.level2.handler;

import com.epam.velosiped.challenge.level2.controller.QueryParameter;
import com.epam.velosiped.challenge.level2.controller.RequestMapping;
import com.sun.net.httpserver.HttpExchange;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author Aleksandr Barmin
 */
public class MethodHandler implements Handler {
  private final Object controller;
  private final Method method;
  private final ParameterExtractor extractor;

  private final String requestMethod;
  private final String endpoint;

  public MethodHandler(Object controller, Method method, ParameterExtractor extractor) {
    this.controller = controller;
    this.method = method;
    this.extractor = extractor;

    final RequestMapping mapping = method.getAnnotation(RequestMapping.class);

    this.requestMethod = mapping.method();
    this.endpoint = mapping.path();
  }

  @Override
  public boolean supports(HttpExchange exchange) {
    return exchange.getRequestMethod().equalsIgnoreCase(this.requestMethod) &&
        exchange.getRequestURI().getPath().equalsIgnoreCase(this.endpoint);
  }

  @Override
  public Object handle(HttpExchange exchange) {
    // check if necessary to extract some parameters from request
    Object[] parameters = new Object[method.getParameterCount()];
    for (int i = 0; i < method.getParameterCount(); i++) {
      final Annotation[] parameterAnnotations = method.getParameterAnnotations()[i];
      // other annotations may be supported but not now
      final QueryParameter annotation = getAnnotation(parameterAnnotations, QueryParameter.class);
      parameters[i] = extractor.queryParameter(exchange, annotation.value());
    }

    try {
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

  private boolean hasAnnotation(Annotation[] annotations, Class<?> annotationClass) {
    for (Annotation annotation : annotations) {
      if (annotation.annotationType().equals(annotationClass)) {
        return true;
      }
    }
    return false;
  }
}
