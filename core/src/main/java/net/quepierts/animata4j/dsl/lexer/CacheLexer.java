package net.quepierts.animata4j.dsl.lexer;

import net.quepierts.animata4j.dsl.source.SourceProvider;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CacheLexer implements TokenProvider {
    private final List<Token> cache;
    private final TokenProvider delegate;

    private final int right;

    public CacheLexer(
            @NotNull List<Token> cache,
            @NotNull TokenProvider delegate
    ) {
        this(cache, delegate, 0, cache.size());
    }

    public CacheLexer(
            @NotNull List<Token> cache,
            @NotNull TokenProvider delegate,
            int left,
            int right
    ) {
        this.cache = cache;
        this.delegate = delegate;

        this.right = right;
        this.index = left;
    }

    private int index;

    @Override
    public @NotNull SourceProvider getSource() {
        return this.delegate.getSource();
    }

    @Override
    public boolean hasNext() {
        return this.index < this.right;
    }

    @Override
    public Token next() {
        if (this.index == this.right) {
            return Token.eof(this.cache.get(this.right - 1).getSpan());
        }
        return this.cache.get(this.index++);
    }
}
