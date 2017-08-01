import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class T {
    @Test
    public void test() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        list.add(5);
        list.forEach(System.out::println);
    }
}
