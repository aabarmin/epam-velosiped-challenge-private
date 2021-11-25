package com.epam.velosiped.challenge.di.web.handler;

import com.epam.velosiped.challenge.di.Inject;
import com.epam.velosiped.challenge.di.PostConstruct;
import com.epam.velosiped.challenge.di.web.handler.extractor.ParameterExtractor;
import com.epam.velosiped.challenge.di.web.handler.extractor.QueryParameterExtractor;
import com.epam.velosiped.challenge.di.web.handler.extractor.RequestBodyExtractor;
import com.sun.net.httpserver.HttpExchange;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Aleksandr Barmin
 */
public class ParameterProvider {
  @Inject
  private QueryParameterExtractor queryParameterExtractor;

  @Inject
  private RequestBodyExtractor requestBodyExtractor;

  private Collection<ParameterExtractor> extractors = new ArrayList<>();

  @PostConstruct
  public void init() {
    // this is not the best way of doing this but
    // I don't want to implement collection injection right now
    extractors.add(queryParameterExtractor);
    extractors.add(requestBodyExtractor);
  }

  public Object[] extract(Method method, HttpExchange exchange) {
    final Object[] parameters = new Object[method.getParameterCount()];

    for (int i = 0; i < method.getParameterCount(); i++) {
      final Annotation[] annotations = method.getParameterAnnotations()[i];
      final Class<?> targetType = method.getParameters()[i].getType();
      final Object parameter = extractors.stream()
          .filter(extractor -> extractor.supports(annotations))
          .findFirst()
          .map(extractor -> extractor.extract(annotations, targetType, exchange))
          .orElseThrow();

      parameters[i] = parameter;
    }

    return parameters;
  }
}
