package net.quepierts.animata4j.core.dsl.lexer;

import net.quepierts.animata4j.core.dsl.TrieTree;

public enum TokenType {
    IDENTIFIER,

    LITERAL_STRING,
    LITERAL_INTEGER,
    LITERAL_DECIMAL,
    LITERAL_HEX,
    LITERAL_OCT,

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
    PLUS_PLUS,      // ++
    MINUS_MINUS,    // --
    EQUAL,          // =
    PLUS_EQUAL,     // +=
    MINUS_EQUAL,    // -=
    STAR_EQUAL,     // *=
    SLASH_EQUAL,    // /=
    PERCENT_EQUAL,  // %=
    LSHIFT,         // <<
    LSHIFT_EQUAL,   // <<=
    RSHIFT,         // >>
    RSHIFT_EQUAL,   // >>=
    BIT_AND,        // &
    OR,             // |
    XOR,            // ^
    BIT_NOT,        // ~

    EQEQ,           // ==
    NOTEQ,          // !=
    LT,             // <
    GT,             // >
    LTEQ,           // <=
    GTEQ,           // >=
    LOGIC_AND,      // &&
    LOGIC_OR,       // ||
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

    MACRO_DEF,
    MACRO_UNDEF,
    MACRO_INCLUDE,
    MACRO_VERSION,
    MACRO_PRAGMA,
    MACRO_IFDEF,
    MACRO_IFNDEF,
    MACRO_IF,
    MACRO_ELIF,
    MACRO_ELSE,
    MACRO_ENDIF,
    MACRO_LINE,

    COMMENT_CONTENT,
    COMMENT_LB,     //  /*
    COMMENT_RB,     //  */
    COMMENT_LINE,   //  //

    // special
    EOF,            // <EOF>
    CUSTOM,         // <CUSTOM>
    UNDEFINED;      // <UNDEFINED>

    public static final TrieTree<TokenType> OPERATORS;
    public static final TrieTree<TokenType> KEYWORDS;
    public static final TrieTree<TokenType> SYMBOLS;

    static {
        TrieTree.Builder<TokenType> builder = new TrieTree.Builder<>(TokenType.UNDEFINED);
        OPERATORS = builder
                .insert("+", TokenType.PLUS)
                .insert("-", TokenType.MINUS)
                .insert("*", TokenType.STAR)
                .insert("/", TokenType.SLASH)
                .insert("%", TokenType.PERCENT)
                .insert("++", TokenType.PLUS_PLUS)
                .insert("--", TokenType.MINUS_MINUS)
                .insert("=", TokenType.EQUAL)
                .insert("+=", TokenType.PLUS_EQUAL)
                .insert("-=", TokenType.MINUS_EQUAL)
                .insert("*=", TokenType.STAR_EQUAL)
                .insert("/=", TokenType.SLASH_EQUAL)
                .insert("%=", TokenType.PERCENT_EQUAL)
                .insert("<<", TokenType.LSHIFT)
                .insert("<<=", TokenType.LSHIFT_EQUAL)
                .insert(">>", TokenType.RSHIFT)
                .insert(">>=", TokenType.RSHIFT_EQUAL)
                .insert("==", TokenType.EQEQ)
                .insert("!=", TokenType.NOTEQ)
                .insert("<", TokenType.LT)
                .insert(">", TokenType.GT)
                .insert("<=", TokenType.LTEQ)
                .insert(">=", TokenType.GTEQ)
                .insert("&&", TokenType.LOGIC_AND)
                .insert("||", TokenType.LOGIC_OR)
                .insert("!", TokenType.NOT)
                .build();

        KEYWORDS = builder
                // Keywords
                .insert("if", TokenType.KEYWORD_IF)
                .insert("else", TokenType.KEYWORD_ELSE)
                .insert("for", TokenType.KEYWORD_FOR)
                .insert("while", TokenType.KEYWORD_WHILE)
                .insert("break", TokenType.KEYWORD_BREAK)
                .insert("continue", TokenType.KEYWORD_CONTINUE)
                .insert("return", TokenType.KEYWORD_RETURN)
                .insert("switch", TokenType.KEYWORD_SWITCH)
                .insert("const", TokenType.KEYWORD_CONST)
                .insert("uniform", TokenType.KEYWORD_UNIFORM)
                .insert("in", TokenType.KEYWORD_IN)
                .insert("out", TokenType.KEYWORD_OUT)
                .insert("inout", TokenType.KEYWORD_INOUT)
                .insert("struct", TokenType.KEYWORD_STRUCT)
                .insert("layout", TokenType.KEYWORD_LAYOUT)

                // primitive types
                .insert("int", TokenType.TYPE_INT)
                .insert("float", TokenType.TYPE_FLOAT)
                .insert("bool", TokenType.TYPE_BOOL)
                .insert("void", TokenType.TYPE_VOID)

                // boolean literals
                .insert("true", TokenType.LITERAL_TRUE)
                .insert("false", TokenType.LITERAL_FALSE)

                // macros
                .insert("#define", TokenType.MACRO_DEF)
                .insert("#undefine", TokenType.MACRO_UNDEF)
                .insert("#include", TokenType.MACRO_INCLUDE)
                .insert("#version", TokenType.MACRO_VERSION)
                .insert("#pragma", TokenType.MACRO_PRAGMA)
                .insert("#ifdef", TokenType.MACRO_IFDEF)
                .insert("#ifndef", TokenType.MACRO_IFNDEF)
                .insert("#if", TokenType.MACRO_IF)
                .insert("#elif", TokenType.MACRO_ELIF)
                .insert("#else", TokenType.MACRO_ELSE)
                .insert("#endif", TokenType.MACRO_ENDIF)
                .insert("#line", TokenType.MACRO_LINE)

                // comment
                .insert("/*", TokenType.COMMENT_LB)
                .insert("*/", TokenType.COMMENT_RB)
                .insert("//", TokenType.COMMENT_LINE)
                .build();

        SYMBOLS = new TrieTree.Builder<>(TokenType.UNDEFINED)
                .insert("(", TokenType.LPAREN)
                .insert(")", TokenType.RPAREN)
                .insert("{", TokenType.LBRACE)
                .insert("}", TokenType.RBRACE)
                .insert("[", TokenType.LBRACKET)
                .insert("]", TokenType.RBRACKET)
                .insert(";", TokenType.SEMICOLON)
                .insert(",", TokenType.COMMA)
                .insert(".", TokenType.DOT)
                .insert(":", TokenType.COLON)
                .insert("?", TokenType.QUESTION)
                .build();
    }
}