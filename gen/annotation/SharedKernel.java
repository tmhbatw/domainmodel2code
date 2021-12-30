package gen.annotation;

import java.lang.annotation.*;

//用于两个限界上下文
@Target({ElementType.TYPE, ElementType.PACKAGE,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SharedKernel {
    String name() default "";
    String oneContextName() default "";
    String theOtherContextName() default "";
}
