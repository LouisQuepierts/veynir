package animata4j.test.dsl;

import net.quepierts.animata4j.core.dsl.ast.Node;
import net.quepierts.animata4j.core.dsl.lexer.ArlLexer;
import net.quepierts.animata4j.core.dsl.lexer.Token;
import net.quepierts.animata4j.core.dsl.parser.AccessPathParser;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PathLexerTest {
    @Test
    void a() {
        String path = "root.bones[2 * n].transform.position.y";
        List<Token> tokens = parse(path);
        for (Token token : tokens) {
            System.out.println(token);
        }

        AccessPathParser parser = new AccessPathParser(new ArlLexer(path));
        Node node = parser.parse();
    }

    private List<Token> parse(String path) {
        ArlLexer lexer = new ArlLexer(path);
        return lexer.tokenize();
    }
}
