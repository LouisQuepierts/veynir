package animata4j.test.dsl;

import net.quepierts.animata4j.dsl.ast.Node;
import net.quepierts.animata4j.dsl.lexer.ArlLexer;
import net.quepierts.animata4j.dsl.lexer.Lexer;
import net.quepierts.animata4j.dsl.parser.ExpressionParser;
import net.quepierts.animata4j.dsl.parser.ProgramParser;
import net.quepierts.animata4j.dsl.source.ProcessesSource;
import org.junit.jupiter.api.Test;

import java.io.File;

public class ParserTest {
    public static void main(String[] args) {
        ParserTest test = new ParserTest();
        test.test1();
    }

    @Test
    public void test1() {
        final String expr1 = "a*(b+c)";
        ExpressionParser parser = new ExpressionParser(expr1);

        Node node = parser.parse();
        System.out.println(node);
    }

    @Test
    public void testSource() {
        // read a file from resource
        final String url = "src/test/resources/lexer_test_source.glsl";
        final File file = new File(url).getAbsoluteFile();

        final ProcessesSource source = ProcessesSource.of(file);
        Lexer lexer = new ArlLexer(source);
        ProgramParser parser = new ProgramParser(lexer);

        Node node = parser.parse();
    }
}
