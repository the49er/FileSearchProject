package other.test;
import java.util.regex.Pattern;

public class OtherTest {
    public static void main(String[] args) {
        String text = "Егор Алла Анна";
        Pattern pattern = Pattern.compile("\\s");
        String[] strings = pattern.split(text, 2);
        for (String s : strings) {
            System.out.println(s);
        }
        System.out.println("---------");
        String[] strings1 = pattern.split(text);
        for (String s : strings1) {
            System.out.println(s);
        }
    }
}
