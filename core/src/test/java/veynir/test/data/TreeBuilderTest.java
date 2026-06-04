package veynir.test.data;

import net.quepierts.veynir.core.data.tree.impl.ImmutableTree;
import net.quepierts.veynir.core.data.tree.PathResolvable;
import net.quepierts.veynir.core.data.tree.definition.TreeStructureBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class TreeBuilderTest {
//    @Test
    public void s_test() {
        Object object$1 = new Object();
        Object object$2 = new Object();
        Object object$3 = new Object();

        TreeStructureBuilder builder = new TreeStructureBuilder("root")
                .begin("a").data(object$1)
                    .begin("b")
                        .add("c")
                        .add("d").data(object$2)
                    .end()
                    .begin("e")
                        .begin("f")
                            .add("g")
                        .end()
                        .add("h").data(object$3)
                        .add("i")
                    .end()
                .end()
                .optimize();

        TreeStructureBuilder.Result result = builder.build();
        ImmutableTree<Object> tree = result.collect(ImmutableTree::of);

        PathResolvable.Result<Object> resolve$a$b$c = tree.resolve("a.b.d");
        Assertions.assertTrue(resolve$a$b$c.isSuccess());
        Assertions.assertSame(resolve$a$b$c.getValue(), object$2);

        PathResolvable.Result<Object> resolve$a$b$c$position$x = tree.resolve("a.b.c.position.x");
        Assertions.assertFalse(resolve$a$b$c$position$x.isSuccess());
        Assertions.assertEquals(resolve$a$b$c$position$x.getIndex(), 3);
    }

    @Test
    public void test() {
        TreeStructureBuilder builder = new TreeStructureBuilder("root")
                .optimize();

        this.generateRandomTree(builder, 32, 5, 2);

        TreeStructureBuilder.Result result = builder.build();
    }

    // format
    private void generateTreeFromString(TreeStructureBuilder builder, String string) {

    }

    private void generateRandomTree(TreeStructureBuilder builder, int size, int maxDepth, int nameLength) {
        final Random random = new Random((long) maxDepth << 32 | size & 0x0000FFFF);
        for (int i = 0; i < size; i++) {
            final String name = this.randomName(random, nameLength);
            final int deep = builder.getEditingDeep();
            final boolean bottom = deep == 0;
            final boolean top = deep == maxDepth;

            final int randomInt = random.nextInt(top || bottom ? 2 : 3) + (bottom ? 1 : 0);
            switch (randomInt) {
                case 0:
                    builder.end();
                    break;
                case 1:
                    builder.add(name);
                    break;
                case 2:
                    builder.begin(name);
                    break;
            }
        }
    }

    private final char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private String randomName(final Random random, int length) {
        final StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            builder.append(this.chars[random.nextInt(this.chars.length)]);
        }
        return builder.toString();
    }
}
