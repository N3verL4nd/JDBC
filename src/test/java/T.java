import com.xiya.entity.Person;
import com.xiya.test.Dao;
import org.junit.Test;

import java.util.List;

public class T {
    @Test
    public void test1() {
        Dao dao = new Dao();
        List<Person> list = dao.query(Person.class,"SELECT * FROM persons");
        list.forEach(System.out::println);
    }

}
