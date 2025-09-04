package net.quepierts.animata4j.core.dsl.source;

import lombok.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@RequiredArgsConstructor(staticName = "of")
@AllArgsConstructor
public final class SourcePointer {
    private final SourceProvider provider;

    private int line    = 1;
    private int col     = 1;
    private int pos     = 0;

    public static SourcePointer of(@NotNull SourceProvider provider, int line, int col, int pos) {
        return new SourcePointer(provider, line, col, pos);
    }

    public static SourcePointer of(@NotNull SourceProvider provider, @NotNull SourcePos pos) {
        return new SourcePointer(
                provider,
                pos.getLine(), pos.getCol(), pos.getPos()
        );
    }

    public boolean hasNext() {
        return this.pos < this.provider.length();
    }

    public char advance() {
        if (this.pos >= this.provider.length()) {
            return '\0';
        }

        this.pos ++;
        final char c = this.provider.charAt(this.line, this.col);
        if (c == '\n') {
            this.line ++;
            this.col = 1;
        } else {
            this.col ++;
        }
        return c;
    }

    @Contract(pure = true)
    public char peek() {
        return this.pos >= this.provider.length() ? '\0' : this.provider.charAt(this.line, this.col);
    }

    public void skipWhitespace() {
        while (Character.isWhitespace(this.peek())) {
            this.advance();
        }
    }

    public SourcePointer copy() {
        return new SourcePointer(this.provider, this.line, this.col, this.pos);
    }

    public SourcePos toSourcePos() {
        return SourcePos.of(this.line, this.col, this.pos);
    }

    public String getCurrentLine() {
        return this.provider.getLine(this.line);
    }
}
