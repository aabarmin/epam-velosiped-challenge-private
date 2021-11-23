package com.epam.velosiped.challenge.common;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aleksandr Barmin
 */
public class Lists {
  public static <T> List<T> of(T... items) {
    final List<T> list = new ArrayList<>();
    for (T item : items) {
      list.add(item);
    }
    return list;
  }
}
