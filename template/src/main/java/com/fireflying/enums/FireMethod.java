package com.fireflying.enums;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * @program: tortoise
 * @author: Fire Flying
 * @create: 2021-10-04 10:17
 **/

@Target(ElementType.METHOD)
public @interface FireMethod {

    String[] value();
}
