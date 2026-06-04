package veynir.test.data;

import net.quepierts.veynir.core.misc.PathResolveHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PathResolveTest {

    @Test
    void testDotError() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            test("root.", "dotError1");
        });
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            test("root...", "dotError2");
        });
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            test("root.[2]", "dotError3");
        });
    }

    @Test
    void testSubscriptError() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            test("root[2]]", "subscriptError1");
        });
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            test("root[2[3]]", "subscriptError2");
        });
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            test("root[2n]", "subscriptError3");
        });
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            test("root[o*]", "subscriptError4");
        });
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            test("root[2.3]", "subscriptError5");
        });
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            test("root[2.body", "subscriptError6");
        });
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            test("root[].object", "subscriptError7");
        });
    }

    static PathResolveHelper.Token[] test(String path, String id) {
        System.out.println("Test " + id + ": ");
        System.out.println(path);
        return wrappedExec(path);
    }

    static PathResolveHelper.Token[] wrappedExec(String path) {
        try {
            return PathResolveHelper.tokenize(path);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            throw e;
        }
    }
}
