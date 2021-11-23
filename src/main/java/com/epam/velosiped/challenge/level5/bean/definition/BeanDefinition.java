package com.epam.velosiped.challenge.level5.bean.definition;

import java.lang.reflect.Constructor;
import java.util.Collection;

/**
 * @author Aleksandr Barmin
 */
public record BeanDefinition(
    Class<?> beanClass,
    Constructor<?> constructor,
    Collection<Class<?>> interfaces
) {}
