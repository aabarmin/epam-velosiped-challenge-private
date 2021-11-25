package com.epam.velosiped.challenge.level3;

import com.epam.velosiped.challenge.level2.Level2TaskImpl;

/**
 * @author Aleksandr Barmin
 */
public class Level3TaskImpl extends Level2TaskImpl implements Level3Task {
  public static void main(String[] args) throws Exception {
    new Level3TaskImpl().start(8080);
  }
}
