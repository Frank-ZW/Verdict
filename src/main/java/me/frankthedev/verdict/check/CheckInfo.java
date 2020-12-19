package me.frankthedev.verdict.check;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CheckInfo {
    String name();
    int maxViolation() default Integer.MAX_VALUE;
}
