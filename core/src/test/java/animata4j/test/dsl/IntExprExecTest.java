package animata4j.test.dsl;

import net.quepierts.animata4j.core.dsl.runtime.IntExecutor;
import org.junit.jupiter.api.Test;

public class IntExprExecTest {
    public static void main(String[] args) {
        IntExprExecTest test = new IntExprExecTest();
        test.test();
    }

    @Test
    public void test() {
        IntExecutor.Compiler compiler = new IntExecutor.Compiler()
                .add("2 * alpha * (5 + beta)")
                .add("2 * select(0, alpha, beta)");
        IntExecutor executor = compiler.compile();
        executor.setUniform("alpha", 2);
        executor.setUniform("beta", 3);

        int result0 = executor.run();
        System.out.println(result0);

        int result1 = executor.run(1);
        System.out.println(result1);
    }
}
