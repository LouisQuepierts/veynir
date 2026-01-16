package animata4j.test.dsl;

import net.quepierts.animata4j.dsl.lexer.ArlLexer;
import net.quepierts.animata4j.dsl.lexer.Lexer;
import net.quepierts.animata4j.dsl.lexer.Token;
import net.quepierts.animata4j.dsl.source.ProcessesSource;
import org.junit.jupiter.api.Test;

import java.io.*;

public class LexerTest {
    @Test
    public void testLine() {
        final String number = "1. + .2e4 * 3 / 0xef - 0124 + 123.456";
        System.out.println("number");
        for (Token token : new ArlLexer(number).tokenize()) {
            System.out.println(token);
        }

        final String path = "2 * select(0, alpha, beta)";
        System.out.println("path");
        for (Token token : new ArlLexer(path).tokenize()) {
            System.out.println(token);
        }
    }

    @Test
    public void testSource() {
        // read a file from resource
        final String url = "src/test/resources/lexer_test_source.glsl";
        final File file = new File(url).getAbsoluteFile();

        final ProcessesSource source = ProcessesSource.of(file);
        Lexer lexer = new ArlLexer(source);
        for (Token token : lexer.tokenize()) {
            System.out.println(token);
        }
    }
}
