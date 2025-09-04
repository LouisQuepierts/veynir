package net.quepierts.animata4j.core.dsl.lexer;

public class LexerHelper {
    public static boolean isIdentifierStart(char c) {
        return Character.isJavaIdentifierStart(c) || c == '_';
    }

    public static boolean isIdentifierPart(char c) {
        return Character.isJavaIdentifierPart(c) || c == '_';
    }

    public static boolean isDigit(char c) {
        return Character.isDigit(c);
    }

    public static boolean isHexDigit(char c) {
        return LexerHelper.isDigit(c) || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F');
    }

    public static boolean isOctDigit(char c) {
        return LexerHelper.isDigit(c) && c < '8';
    }

    public static boolean isNumberStart(char c) {
        return LexerHelper.isDigit(c);
    }

    public static boolean isBlank(char c) {
        return c == ' ' || c == '\t' || c == '\r' || c == '\n';
    }
}
