package net.quepierts.animata4j.core.dsl.lexer;

import com.google.common.collect.ImmutableMap;
import net.quepierts.animata4j.core.dsl.TrieTree;

import java.util.Map;

public enum TokenType {
    IDENTIFIER,

    LITERAL_INTEGER,
    LITERAL_HEX,
    LITERAL_OCT,
    LITERAL_DECIMAL,
    LITERAL_STRING,

    LITERAL_TRUE,
    LITERAL_FALSE,

    TYPE_INT,
    TYPE_FLOAT,
    TYPE_BOOL,
    TYPE_VOID,

    // operator
    PLUS,           // +
    MINUS,          // -
    STAR,           // *
    SLASH,          // /
    PERCENT,        // %
    AND,            // &
    OR,             // |
    XOR,            // ^

    PLUS2,          // ++
    MINUS2,         // --
    EQUAL,          // =
    PLUS_EQUAL,     // +=
    MINUS_EQUAL,    // -=
    STAR_EQUAL,     // *=
    SLASH_EQUAL,    // /=
    PERCENT_EQUAL,  // %=
    AND_EQUAL,      // &=
    OR_EQUAL,       // |=
    XOR_EQUAL,      // ^=
    LSHIFT,         // <<
    LSHIFT_EQUAL,   // <<=
    RSHIFT,         // >>
    RSHIFT_EQUAL,   // >>=
    WAVE,           // ~

    EQEQ,           // ==
    NOTEQ,          // !=
    LT,             // <
    GT,             // >
    LTEQ,           // <=
    GTEQ,           // >=
    AND2,           // &&
    XOR2,           // ^^
    OR2,            // ||
    NOT,            // !

    // symbols
    LPAREN,         // (
    RPAREN,         // )
    LBRACE,         // {
    RBRACE,         // }
    LBRACKET,       // [
    RBRACKET,       // ]
    SEMICOLON,      // ;
    COMMA,          // ,
    DOT,            // .
    COLON,          // :
    QUESTION,       // ?

    KEYWORD_IF,
    KEYWORD_ELSE,
    KEYWORD_FOR,
    KEYWORD_WHILE,
    KEYWORD_BREAK,
    KEYWORD_CONTINUE,
    KEYWORD_RETURN,
    KEYWORD_SWITCH,
    KEYWORD_CONST,
    KEYWORD_UNIFORM,
    KEYWORD_IN,
    KEYWORD_OUT,
    KEYWORD_INOUT,
    KEYWORD_STRUCT,
    KEYWORD_LAYOUT,
    KEYWORD_IMPORT,
    KEYWORD_PACKAGE,

    // special
    EOF,            // <EOF>
    CUSTOM,         // <CUSTOM>
    UNDEFINED;      // <UNDEFINED>

    public static final TrieTree<TokenType> OPERATORS;
    public static final TrieTree<TokenType> SYMBOLS;
    
    public static final Map<String, TokenType> KEYWORDS;

    static {
        TrieTree.Builder<TokenType> builder = new TrieTree.Builder<>(TokenType.UNDEFINED);
        OPERATORS = builder
                .put("+", TokenType.PLUS)
                .put("-", TokenType.MINUS)
                .put("*", TokenType.STAR)
                .put("/", TokenType.SLASH)
                .put("%", TokenType.PERCENT)
                .put("++", TokenType.PLUS2)
                .put("--", TokenType.MINUS2)
                .put("=", TokenType.EQUAL)
                .put("+=", TokenType.PLUS_EQUAL)
                .put("-=", TokenType.MINUS_EQUAL)
                .put("*=", TokenType.STAR_EQUAL)
                .put("/=", TokenType.SLASH_EQUAL)
                .put("%=", TokenType.PERCENT_EQUAL)
                .put("&=", TokenType.AND_EQUAL)
                .put("|=", TokenType.OR_EQUAL)
                .put("^=", TokenType.XOR_EQUAL)
                .put("<<", TokenType.LSHIFT)
                .put("<<=", TokenType.LSHIFT_EQUAL)
                .put(">>", TokenType.RSHIFT)
                .put(">>=", TokenType.RSHIFT_EQUAL)
                .put("==", TokenType.EQEQ)
                .put("!=", TokenType.NOTEQ)
                .put("<", TokenType.LT)
                .put(">", TokenType.GT)
                .put("<=", TokenType.LTEQ)
                .put(">=", TokenType.GTEQ)
                .put("&&", TokenType.AND2)
                .put("^^", TokenType.XOR2)
                .put("||", TokenType.OR2)
                .put("!", TokenType.NOT)
                .put("&", TokenType.AND)
                .put("|", TokenType.OR)
                .put("^", TokenType.XOR)
                .put("~", TokenType.WAVE)
                .build();

        KEYWORDS = ImmutableMap.<String, TokenType>builder()
                // Keywords
                .put("if", TokenType.KEYWORD_IF)
                .put("else", TokenType.KEYWORD_ELSE)
                .put("for", TokenType.KEYWORD_FOR)
                .put("while", TokenType.KEYWORD_WHILE)
                .put("break", TokenType.KEYWORD_BREAK)
                .put("continue", TokenType.KEYWORD_CONTINUE)
                .put("return", TokenType.KEYWORD_RETURN)
                .put("switch", TokenType.KEYWORD_SWITCH)
                .put("const", TokenType.KEYWORD_CONST)
                .put("uniform", TokenType.KEYWORD_UNIFORM)
                .put("in", TokenType.KEYWORD_IN)
                .put("out", TokenType.KEYWORD_OUT)
                .put("inout", TokenType.KEYWORD_INOUT)
                .put("struct", TokenType.KEYWORD_STRUCT)
                .put("layout", TokenType.KEYWORD_LAYOUT)

                // package management
                .put("import", TokenType.KEYWORD_IMPORT)
                .put("package", TokenType.KEYWORD_PACKAGE)

                // primitive types
                .put("int", TokenType.TYPE_INT)
                .put("float", TokenType.TYPE_FLOAT)
                .put("bool", TokenType.TYPE_BOOL)
                .put("void", TokenType.TYPE_VOID)

                // boolean literals
                .put("true", TokenType.LITERAL_TRUE)
                .put("false", TokenType.LITERAL_FALSE)
                .build();

        SYMBOLS = new TrieTree.Builder<>(TokenType.UNDEFINED)
                .put("(", TokenType.LPAREN)
                .put(")", TokenType.RPAREN)
                .put("{", TokenType.LBRACE)
                .put("}", TokenType.RBRACE)
                .put("[", TokenType.LBRACKET)
                .put("]", TokenType.RBRACKET)
                .put(";", TokenType.SEMICOLON)
                .put(",", TokenType.COMMA)
                .put(".", TokenType.DOT)
                .put(":", TokenType.COLON)
                .put("?", TokenType.QUESTION)
                .build();
    }
}