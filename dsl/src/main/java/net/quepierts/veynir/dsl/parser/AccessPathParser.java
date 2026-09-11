package net.quepierts.veynir.dsl.parser;

import com.google.common.collect.ImmutableList;
import net.quepierts.veynir.dsl.ast.AccessPathNode;
import net.quepierts.veynir.dsl.ast.Node;
import net.quepierts.veynir.dsl.ast.expr.ArrayAccessExpr;
import net.quepierts.veynir.dsl.ast.expr.Expression;
import net.quepierts.veynir.dsl.ast.expr.IdentifierExpr;
import net.quepierts.veynir.dsl.lexer.CacheLexer;
import net.quepierts.veynir.dsl.lexer.Token;
import net.quepierts.veynir.dsl.lexer.TokenProvider;
import net.quepierts.veynir.dsl.lexer.TokenType;
import net.quepierts.veynir.dsl.source.SourceSpan;
import org.jetbrains.annotations.NotNull;

public class AccessPathParser extends Parser {
    public AccessPathParser(@NotNull TokenProvider lexer) {
        super(lexer);
    }

    /*
    <path> ::= <identifier> ( "." <identifier> )*
    <identifier> ::= IDENTIFIER ( LBRACKET IDENTIFIER RBRACKET )?
    */
    @Override
    public Node parse() {
        ImmutableList.Builder<Node> builder = ImmutableList.builder();

        do {
            Node node = this.parseIdentifier();
            builder.add(node);
        } while (this.match(TokenType.DOT));

        if (!this.match(TokenType.EOF)) {
            this.error("Unexpected token", this.getCurrent());
        }

        ImmutableList<Node> nodes = builder.build();

        if (nodes.isEmpty()) {
            this.error("Empty access path");
        }

        final Node left = nodes.get(0);
        final Node right = nodes.get(nodes.size() - 1);
        return new AccessPathNode(
                SourceSpan.of(left.getSpan(), right.getSpan()),
                nodes
        );
    }

    private Node parseIdentifier() {
        final Token current = this.getCurrent();
        this.consume(TokenType.IDENTIFIER);

        final IdentifierExpr identifier = new IdentifierExpr(
                current.getSpan(),
                current.getValue()
        );

        final Token lbracket = this.getCurrent();
        if (lbracket.is(TokenType.LBRACKET)) {
            this.advance();

            ImmutableList.Builder<Token> contents = ImmutableList.builder();
            if (!this.findBlockBracket(contents)) {
                this.error("Unclosed bracket", lbracket);
            }

            Token rbracket = this.getCurrent();
            contents.add(Token.eof(rbracket.getSpan()));
            CacheLexer lexer = new CacheLexer(contents.build(), this.getLexer());
            ExpressionParser parser = new ExpressionParser(lexer);
            Expression expr = parser.parse();

            return new ArrayAccessExpr(
                    SourceSpan.of(identifier.getSpan(), rbracket.getSpan()),
                    identifier,
                    expr
            );

        } else {
            return identifier;
        }
    }

    private boolean findBlockBracket(ImmutableList.Builder<Token> cache) {
        int depth = 1;
        boolean found = false;
        while (this.hasNext() && !found) {
            Token token = this.getCurrent();

            if (token.is(TokenType.LBRACKET)) {
                depth ++;
            } else if (token.is(TokenType.RBRACKET)) {
                depth --;

                if (depth == 0) {
                    found = true;
                }
            }

            if (!found) {
                cache.add(token);
            }

            this.advance();
        }

        return found;
    }
}
