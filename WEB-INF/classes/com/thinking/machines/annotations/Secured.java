package com.thinking.machines.annotations;
import java.lang.annotation.*;
import java.lang.reflect.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Secured
{
Class value() default void.class;
}
