package com.epam.velosiped.challenge.level2.controller;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Aleksandr Barmin
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
  String path();

  String method();
}
