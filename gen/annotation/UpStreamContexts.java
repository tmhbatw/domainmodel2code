package gen.annotation;

import java.lang.annotation.*;

@Target({ ElementType.PACKAGE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UpStreamContexts {
    UpStreamContext[] value();
}
