package main.gen.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//共享内核被用于该限界上下文被用于两个限界上下文的情况
@Target({ElementType.TYPE, ElementType.PACKAGE,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SharedKernel {
    String name() default "default";
    String oneContextName() default "default";
    String theOtherContextName() default "default";
}
