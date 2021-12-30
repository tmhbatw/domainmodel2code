package gen.annotation;

import java.lang.annotation.*;

@Target({ ElementType.PACKAGE})
@Retention(RetentionPolicy.RUNTIME)
@interface UpStreamContexts {
    UpStreamContext[] value();
}
