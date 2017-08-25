import org.junit.Test;

import java.util.regex.Pattern;

public class RegExTest {
    @Test
    public void test() {
        String str = "abc123def";
        Pattern pattern = Pattern.compile("\\[0-9]+");
        System.out.println(pattern.matcher(str).matches());
    }

    @Test
    public void test1() {
        System.out.println(Object.class.isInstance(123));
    }
}