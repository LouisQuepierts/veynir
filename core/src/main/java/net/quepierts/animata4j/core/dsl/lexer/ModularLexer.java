package net.quepierts.animata4j.core.dsl.lexer;

import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.core.dsl.lexer.module.LexerModule;
import net.quepierts.animata4j.core.dsl.source.SourcePointer;
import net.quepierts.animata4j.core.dsl.source.SourcePos;
import net.quepierts.animata4j.core.dsl.source.SourceSpan;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class ModularLexer extends Lexer {

    private final List<LexerModule> modules;
    private final Map<String, TokenType> keywords;
    private final LexerView view;

    protected ModularLexer(
            @NotNull String source,
            @NotNull List<LexerModule> modules,
            @NotNull Map<String, TokenType> keywords
    ) {
        super(source);
        this.modules = modules;
        this.keywords = keywords;
        this.view = new LexerViewImpl(this);
        modules.sort(null);
    }

    @Override
    public @Nullable Token next() {

        this.skipWhitespace();
        if (!this.hasNext()) {
            return this.eof();
        }

        final SourcePos leftPos = this.getSourcePos();
        char c = this.peek();
        char last = this.getPointer().last();

        for (LexerModule module : modules) {
            if (module.isStart(c, last)) {
                final Token token = module.parse(leftPos, this.view);
                if (token != null) {
                    return token;
                }
            }
        }

        if (LexerHelper.isIdentifierStart(c)) {
            StringBuilder identifier = new StringBuilder()
                    .append(c);
            this.readWhile(LexerHelper::isIdentifierPart, identifier);
            String str = identifier.toString();
            return new Token(
                    this.keywords.getOrDefault(str, TokenType.IDENTIFIER),
                    str,
                    this.span(leftPos)
            );
        }

        this.error("Unexpected character: " + c);
        return null;
    }

    @RequiredArgsConstructor
    private static final class LexerViewImpl implements LexerView {

        private final Lexer lexer;

        @Override
        public boolean isEof() {
            return this.lexer.getPointer().isEof();
        }

        @Override
        public boolean isEol() {
            return this.lexer.getPointer().isEol();
        }

        @Override
        public char peek() {
            return this.lexer.peek();
        }

        @Override
        public char last() {
            return this.lexer.getPointer().last();
        }

        @Override
        public char advance() {
            return this.lexer.advance();
        }

        @Override
        public @NotNull SourcePointer pointer() {
            return this.lexer.getPointer();
        }

        @Override
        public @NotNull SourceSpan span(@NotNull SourcePos start) {
            return this.lexer.span(start);
        }

        @Override
        public void error(@NotNull String message) {
            this.lexer.error(message);
        }
    }
}
