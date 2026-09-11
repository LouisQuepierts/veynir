package net.quepierts.veynir.dsl.source;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@RequiredArgsConstructor(staticName = "of")
public final class SourcePointer {
    private final SourceProvider provider;

    private int line    = 0;
    private int col     = 0;

    private char current = '\0';
    private char last    = '\0';

    SourcePointer(@NotNull SourceProvider provider, int line, int col) {
        this.provider = provider;
        this.line = line;
        this.col = col;

        this.current = provider.charAt(line, col);
    }

    public static SourcePointer of(@NotNull SourceProvider provider, int line, int col) {
        return new SourcePointer(provider, line, col);
    }

    public static SourcePointer of(@NotNull SourceProvider provider, @NotNull SourcePos pos) {
        return new SourcePointer(
                provider,
                pos.getLine(), pos.getCol()
        );
    }

    public boolean hasNext() {
        return !this.provider.isEof(this.line);
    }

    public boolean isEol() {
        return this.provider.isEol(this.line, this.col);
    }

    public boolean isEof() {
        return this.provider.isEof(this.line);
    }

    public char advance() {
        if (this.provider.isEof(this.line)) {
            return '\0';
        }

        final char c = this.provider.charAt(this.line, this.col);
        if (c == '\n') {
            this.line ++;
            this.col = 0;
        } else {
            this.col ++;
        }
        this.last = this.current;
        this.current = c;
        return c;
    }

    @Contract(pure = true)
    public char peek() {
        return this.current;
    }

    @Contract(pure = true)
    public char last() {
        return this.last;
    }

    public void skipWhitespace() {
        while (Character.isWhitespace(this.peek())) {
            this.advance();
        }
    }

    public SourcePointer copy() {
        return new SourcePointer(this.provider, this.line, this.col);
    }

    public SourcePos toSourcePos() {
        return SourcePos.of(this.line, this.col);
    }

    public String getCurrentLine() {
        return this.provider.getLine(this.line);
    }

    public int getLineNumber() {
        return this.provider.getLineNumber(this.line);
    }
}
