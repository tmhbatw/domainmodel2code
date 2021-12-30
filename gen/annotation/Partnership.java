package gen.annotation;


import java.lang.annotation.*;

@Documented
@Target(ElementType.PACKAGE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Partnership {
    String anotherPartnershipContext() default "";
}
