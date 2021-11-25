package com.epam.velosiped.challenge.di.web.response;

import com.epam.velosiped.challenge.di.Inject;
import com.epam.velosiped.challenge.di.web.converter.ToJsonConverter;

/**
 * @author Aleksandr Barmin
 */
public class ResponseConverter {
  @Inject
  private ToJsonConverter toJsonConverter;

  public String convert(Object response) {
    if (response.getClass().isPrimitive()) {
      return String.valueOf(response);
    }
    return toJsonConverter.convert(response);
  }
}
