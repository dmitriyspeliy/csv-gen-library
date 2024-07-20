package com.effective.mobile_pr2.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Можно указать, какие столбцы не будут записаны в нижним регистре
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcludeColumn {
    String[] nameOfColumns() default "";
}
