package net.quepierts.veynir.core.misc;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Only for static and parameterized path, but not for dynamic path like function call or expression.
 * */
@UtilityClass
@SuppressWarnings("unused")
public class PathResolveHelper {
    private static final Pattern WORD = Pattern.compile("[a-zA-Z_][a-zA-Z0-9_]*");
    private static final Pattern SUBSCRIPT = Pattern.compile("\\d+|[a-zA-Z_][a-zA-Z0-9_]*");

    public static @NotNull Token[] tokenize(@NotNull String path) {
        final List<Token> tokens = new ArrayList<>();
        int left = 0;
        int right = 0;

        char last = 0;

        while (right < path.length()) {
            final char c = path.charAt(right);

            switch (c) {
                case '.':
                    PathResolveHelper.checkDot(path, last, right);

                    if (right > left) {
                        final String word = PathResolveHelper.word(path.substring(left, right), path, left);
                        tokens.add(Token.simple(word));
                    }
                    left = right + 1;
                    break;
                case '[':
                    PathResolveHelper.checkDot(path, last, right);

                    if (right > left) {
                        final String word = PathResolveHelper.word(path.substring(left, right), path, left);
                        tokens.add(Token.simple(word));
                    }
                    left = right;
                    right = PathResolveHelper.findRightBracket(path, left);

                    final String word = PathResolveHelper.subscript(path.substring(left + 1, right), path, left);
                    tokens.add(Token.subscript(word));
                    left = right + 1;
                    break;
                case ']':
                    PathResolveHelper.error("Unexpected bracket", path, right);
                    break;
            }

            right++;
            last = c;
        }

        PathResolveHelper.checkDot(path, last, right);

        if (left < path.length()) {
            tokens.add(Token.simple(path.substring(left)));
        }

        return tokens.toArray(Token[]::new);
    }

    private static int findRightBracket(
            @NotNull String path,
            int left
    ) {
        int i = left + 1;
        while (i < path.length()) {
            final char c = path.charAt(i);

            switch (c) {
                case ']':
                    if (i - left < 2) {
                        PathResolveHelper.error("Empty subscript", path, i);
                    }
                    return i;
                case '.':
                    PathResolveHelper.error("Unexpected dot", path, i);
                    break;
                case '[':
                    PathResolveHelper.error("Unexpected bracket", path, i);
                    break;
            }

            i ++;
        }

        PathResolveHelper.error("Unclosed bracket", path, i);
        return -1;
    }

    private static String word(
            @NotNull String name,
            @NotNull String path,
            int left
    ) {
        if (!WORD.matcher(name).matches()) {
            PathResolveHelper.error("Invalid token: " + name, path, left + 1);
        }

        return name;
    }

    private static String subscript(
            @NotNull String name,
            @NotNull String path,
            int left
    ) {
        if (!SUBSCRIPT.matcher(name).matches()) {
            PathResolveHelper.error("Invalid subscript content: " + name, path, left + 1);
        }

        return name;
    }

    private static void checkDot(
            @NotNull String path,
            char last,
            int ptr
    ) {
        if (last == '.') {
            PathResolveHelper.error("Unexpected dot", path, ptr - 1);
        }
    }

    private static void error(
            @NotNull String message,
            @NotNull String path,
            int ptr
    ) {
        StringBuilder builder = new StringBuilder()
                .append(message).append("\n")
                .append(path).append("\n");

        builder.append(" ".repeat(Math.max(0, ptr)));
        builder.append("^");
        throw new IllegalArgumentException(builder.toString());
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Token {
        private final String raw;
        private final String content;
        private final boolean subscript;

        public boolean isIntegerSubscript() {
            return subscript && content.matches("\\d+");
        }

        public int getIntegerSubscript() {
            return Integer.parseInt(content);
        }

        static @NotNull Token simple(@NotNull String content) {
            return new Token(content, content, false);
        }

        static @NotNull Token subscript(@NotNull String content) {
            return new Token("[" + content + "]", content, true);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Token && raw.equals(((Token) obj).raw);
        }
    }
}
