package URLGrab;

import javax.xml.bind.Element;
import java.lang.annotation.*;

/**
 * Created by lyl on 2017/11/8.
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ObjectToMapField {

    String name();

}
