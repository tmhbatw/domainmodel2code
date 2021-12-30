package gen.annotation;

import java.lang.annotation.*;


@Target(ElementType.PACKAGE)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(DownStreamContexts.class)
public @interface DownStreamContext {
    String upStreamContextName() default "";
    DownStreamContextType downStreamContextType() default DownStreamContextType.Default;
}
