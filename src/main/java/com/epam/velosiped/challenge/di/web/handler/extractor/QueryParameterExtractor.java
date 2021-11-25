package com.epam.velosiped.challenge.di.web.handler.extractor;

import com.epam.velosiped.challenge.di.web.QueryParameter;
import com.sun.net.httpserver.HttpExchange;
import java.lang.annotation.Annotation;

/**
 * @author Aleksandr Barmin
 */
public class QueryParameterExtractor implements ParameterExtractor {
  @Override
  public boolean supports(Annotation[] annotations) {
    return getAnnotation(annotations) != null;
  }

  private QueryParameter getAnnotation(Annotation[] annotations) {
    for (Annotation annotation : annotations) {
      if (annotation.annotationType().equals(QueryParameter.class)) {
        return (QueryParameter) annotation;
      }
    }
    return null;
  }

  @Override
  public Object extract(Annotation[] annotations, Class<?> targetClass, HttpExchange exchange) {
    final QueryParameter parameter = getAnnotation(annotations);
    final String name = parameter.value();

    final String query = exchange.getRequestURI().getQuery();
    final String toFind = name + "=";
    String value = query.substring(query.indexOf(toFind) + toFind.length());
    if (value.indexOf("&") != -1) {
      value = value.substring(0, value.indexOf("&"));
    }
    return value;
  }
}
