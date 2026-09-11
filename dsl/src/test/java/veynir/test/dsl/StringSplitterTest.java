package veynir.test.dsl;

import net.quepierts.veynir.dsl.StringSplitter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class StringSplitterTest {

    static final String TEXT_PREFIX = "text_";

    @Test
    public void test1() {
        StringBuilder builder = new StringBuilder();
        gen(builder, 10, 10);

        StringSplitter splitter = StringSplitter.of(builder);

        int i = 0;
        while (splitter.hasNext()) {
            String next = splitter.next();
            Assertions.assertTrue(checkSplit(i, next));
            System.out.println(i);
            System.out.println(splitter.remain());
            i++;
        }
    }

    private static boolean checkSplit(
            int id,
            String text
    ) {
        String strid = String.valueOf(id);
        return text.startsWith(TEXT_PREFIX) && text.endsWith(strid);
    }

    private static void gen(
            StringBuilder builder,
            int amount,
            int maxSpaceLength
    ) {
        Random random = new Random();
        builder.append(" ".repeat(1 + random.nextInt(maxSpaceLength)));

        for (int i = 0; i < amount; i++) {
            builder.append(TEXT_PREFIX).append(i);
            builder.append(" ".repeat(1 + random.nextInt(maxSpaceLength)));
        }
    }
}
