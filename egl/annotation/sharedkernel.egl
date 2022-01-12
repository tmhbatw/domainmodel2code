package gen.annotation;

import java.lang.annotation.*;

//共享内核被用于该限界上下文被用于两个限界上下文的情况
@Target({ElementType.TYPE, ElementType.PACKAGE,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SharedKernel {
    String name() default "default";
    String oneContextName() default "default";
    String theOtherContextName() default "default";
}
