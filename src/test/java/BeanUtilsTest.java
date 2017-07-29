import com.xiya.entity.Person;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

public class BeanUtilsTest {
    @Test
    public void test() {
        Object object = new Person();
        System.out.println(object);

        try {
            BeanUtils.setProperty(object, "name", "lgh");
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        try {
            System.out.println(BeanUtils.getProperty(object, "name"));
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
