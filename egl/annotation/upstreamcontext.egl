package gen.annotation;

import java.lang.annotation.*;


@Target(ElementType.PACKAGE)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(UpStreamContexts.class)
public @interface UpStreamContext {
    String downStreamContextName() default "";
    UpStreamContextType upStreamContextType() default UpStreamContextType.Default;
}


