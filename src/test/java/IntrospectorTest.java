import com.xiya.entity.Person;
import org.junit.Test;

import java.beans.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;

public class IntrospectorTest {
    @Test
    public void test() {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(Person.class);
            PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor descriptor : descriptors) {
                System.out.println(descriptor);
            }
            System.out.println("--------------------");

            Person person = new Person();

            System.out.print("Person的属性有:[");
            for (PropertyDescriptor propertyDescriptor : descriptors) {
                System.out.print(propertyDescriptor.getName() + " ");
            }
            System.out.println("]");

            for (PropertyDescriptor propertyDescriptor : descriptors) {
                Method method = propertyDescriptor.getWriteMethod();
                if (propertyDescriptor.getName().equals("name")) {
                    method.invoke(person, "lgh");
                    method = propertyDescriptor.getReadMethod();
                    System.out.println(method.invoke(person));
                }
                if (propertyDescriptor.getName().equals("age")) {
                    method.invoke(person, 25);
                    method = propertyDescriptor.getReadMethod();
                    System.out.println(method.invoke(person));
                }
            }
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test1() throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        Person person = new Person();
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor("name", Person.class);

        Method method = propertyDescriptor.getWriteMethod();
        method.invoke(person, "阡陌");

        method = propertyDescriptor.getReadMethod();
        System.out.println(method.invoke(person));
    }

    @Test
    public void test2() throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(Person.class);
        MethodDescriptor[] methodDescriptors = beanInfo.getMethodDescriptors();
        for (MethodDescriptor descriptor : methodDescriptors) {
            System.out.println(descriptor);
        }
    }
}
