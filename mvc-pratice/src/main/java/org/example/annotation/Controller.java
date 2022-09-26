package org.example.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Target : 해당 어노테이션을 어디에 붙일 것인가</p>
 * <p>ElementType.TYPE : Class, interface (including annotation type), or enum declaration</p>
 * <p>Retention : 유지기간</p>
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {

}
