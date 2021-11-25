package com.epam.velosiped.challenge.di.web.converter;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Aleksandr Barmin
 */
public class FromJsonConverter implements DataConverter<String, Map<String, String>> {
  private static final Pattern VALUE_PATTERN = Pattern.compile("\\\"(.+?)\\\":\\s+?\\\"(.+?)\\\"");

  @Override
  public boolean supports(Class<?> from, Class<?> to) {
    return from.isAssignableFrom(String.class);
  }

  @Override
  public Map<String, String> convert(String from) {
    // don't want to invent wheels today, a few regular expressions
    final String[] parts = from.replace("{", "")
        .replace("}", "")
        .split(",");

    final Map<String, String> result = new HashMap<>();
    for (int i = 0; i < parts.length; i++) {
      final String part = parts[i];
      final Matcher matcher = VALUE_PATTERN.matcher(part);
      matcher.find();
      final String key = matcher.group(1);
      final String value = matcher.group(2);

      result.put(key, value);
    }

    return result;
  }
}
