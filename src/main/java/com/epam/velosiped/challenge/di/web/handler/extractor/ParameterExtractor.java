package com.epam.velosiped.challenge.di.web.handler.extractor;

import com.sun.net.httpserver.HttpExchange;
import java.lang.annotation.Annotation;

/**
 * @author Aleksandr Barmin
 */
public interface ParameterExtractor {
  boolean supports(Annotation[] annotations);

  Object extract(Annotation[] annotations, HttpExchange exchange);
}
