package gen.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DomainEvent {
    String identifier() default "";
    String timeStamp() default "";
    Class publisher() default DefaultPublisher.class;
    Class subscriber() default DefaultSubscriber.class;
}

