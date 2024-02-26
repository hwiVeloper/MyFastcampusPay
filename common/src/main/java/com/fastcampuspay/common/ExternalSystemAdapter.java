package com.fastcampuspay.common;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface ExternalSystemAdapter {
    // TODO: 36:24부터............
    @AliasFor(annotation = Component.class)
    String value() default "";
}
