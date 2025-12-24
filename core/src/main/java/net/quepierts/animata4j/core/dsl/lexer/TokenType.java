package net.quepierts.animata4j.core.dsl.lexer;

import com.google.common.collect.ImmutableMap;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.core.dsl.TrieTree;

import java.util.Map;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum TokenType {
    IDENTIFIER(false, false, false),
    DIRECTIVE(false, false, false),

    LITERAL_INTEGER(false, false, false),
    LITERAL_BIN(false, false, false),
    LITERAL_HEX(false, false, false),
    LITERAL_OCT(false, false, false),
    LITERAL_DECIMAL(false, false, false),
    LITERAL_STRING(false, false, false),

    LITERAL_TRUE(false, false, false),
    LITERAL_FALSE(false, false, false),

    TYPE_INT(false, true, true),
    TYPE_FLOAT(false, true, true),
    TYPE_BOOL(false, true, true),
    TYPE_VOID(false, true, true),

    TYPE_VEC2(false, true, true),
    TYPE_VEC3(false, true, true),
    TYPE_VEC4(false, true, true),
    TYPE_IVEC2(false, true, true),
    TYPE_IVEC3(false, true, true),
    TYPE_IVEC4(false, true, true),

    TYPE_MAT2(false, true, true),
    TYPE_MAT3(false, true, true),
    TYPE_MAT4(false, true, true),

    TYPE_SAMPLER1D(false, true, true),
    TYPE_SAMPLER2D(false, true, true),
    TYPE_SAMPLER3D(false, true, true),
    TYPE_SAMPLER1D_ARRAY(false, true, true),
    TYPE_SAMPLER2D_ARRAY(false, true, true),

    TYPE_ISAMPLER1D(false, true, true),
    TYPE_ISAMPLER2D(false, true, true),
    TYPE_ISAMPLER3D(false, true, true),
    TYPE_ISAMPLER1D_ARRAY(false, true, true),
    TYPE_ISAMPLER2D_ARRAY(false, true, true),

    // operator
    PLUS(true, false, false),           // +
    MINUS(true, false, false),          // -
    STAR(true, false, false),           // *
    SLASH(true, false, false),          // /
    PERCENT(true, false, false),        // %
    AND(true, false, false),            // &
    OR(true, false, false),             // |
    XOR(true, false, false),            // ^

    PLUS2(true, false, false),          // ++
    MINUS2(true, false, false),         // --
    EQUAL(true, false, false),          // =
    PLUS_EQUAL(true, false, false),     // +=
    MINUS_EQUAL(true, false, false),    // -=
    STAR_EQUAL(true, false, false),     // *=
    SLASH_EQUAL(true, false, false),    // /=
    PERCENT_EQUAL(true, false, false),  // %=
    AND_EQUAL(true, false, false),      // &=
    OR_EQUAL(true, false, false),       // |=
    XOR_EQUAL(true, false, false),      // ^=
    LSHIFT(true, false, false),         // <<
    LSHIFT_EQUAL(true, false, false),   // <<=
    RSHIFT(true, false, false),         // >>
    RSHIFT_EQUAL(true, false, false),   // >>=
    WAVE(true, false, false),           // ~

    EQEQ(true, false, false),           // ==
    NOTEQ(true, false, false),          // !=
    LT(true, false, false),             // <
    GT(true, false, false),             // >
    LTEQ(true, false, false),           // <=
    GTEQ(true, false, false),           // >=
    AND2(true, false, false),           // &&
    XOR2(true, false, false),           // ^^
    OR2(true, false, false),            // ||
    NOT(true, false, false),            // !

    // symbols
    LPAREN(false, false, false),         // (
    RPAREN(false, false, false),         // )
    LBRACE(false, false, false),         // {
    RBRACE(false, false, false),         // }
    LBRACKET(false, false, false),       // [
    RBRACKET(false, false, false),       // ]
    SEMICOLON(false, false, false),      // ;
    COMMA(false, false, false),          // ,
    DOT(false, false, false),            // .
    COLON(false, false, false),          // :
    QUESTION(false, false, false),       // ?
    HASH(false, false, false),           // #

    KEYWORD_IF(false, true, false),
    KEYWORD_ELSE(false, true, false),
    KEYWORD_FOR(false, true, false),
    KEYWORD_WHILE(false, true, false),
    KEYWORD_BREAK(false, true, false),
    KEYWORD_CONTINUE(false, true, false),
    KEYWORD_RETURN(false, true, false),
    KEYWORD_SWITCH(false, true, false),
    KEYWORD_CONST(false, true, false),
    KEYWORD_UNIFORM(false, true, false),
    KEYWORD_IN(false, true, false),
    KEYWORD_OUT(false, true, false),
    KEYWORD_INOUT(false, true, false),
    KEYWORD_STRUCT(false, true, false),
    KEYWORD_LAYOUT(false, true, false),
    KEYWORD_IMPORT(false, true, false),
    KEYWORD_PACKAGE(false, true, false),

    COMMENT_LINE(false, false, false),
    COMMENT_BLOCK(false, false, false),

    // special
    EOF(false, false, false),            // <EOF>
    CUSTOM(false, false, false),         // <CUSTOM>
    ERROR(false, false, false),
    UNDEFINED(false, false, false);      // <UNDEFINED>

    private final boolean isOperator;
    private final boolean isKeyword;
    private final boolean isPrimitiveType;

    public static final TrieTree<TokenType> SYMBOLS;
    
    public static final Map<String, TokenType> KEYWORDS;

    static {
        TrieTree.Builder<TokenType> builder = new TrieTree.Builder<>(TokenType.UNDEFINED);
        SYMBOLS = builder
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
                .put("#", TokenType.HASH)
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

                .put("vec2", TokenType.TYPE_VEC2)
                .put("vec3", TokenType.TYPE_VEC3)
                .put("vec4", TokenType.TYPE_VEC4)

                .put("ivec2", TokenType.TYPE_IVEC2)
                .put("ivec3", TokenType.TYPE_IVEC3)
                .put("ivec4", TokenType.TYPE_IVEC4)

                .put("mat2", TokenType.TYPE_MAT2)
                .put("mat3", TokenType.TYPE_MAT3)
                .put("mat4", TokenType.TYPE_MAT4)

                .put("sampler1D", TokenType.TYPE_SAMPLER1D)
                .put("sampler2D", TokenType.TYPE_SAMPLER2D)
                .put("sampler3D", TokenType.TYPE_SAMPLER3D)
                .put("sampler1DArray", TokenType.TYPE_SAMPLER1D_ARRAY)
                .put("sampler2DArray", TokenType.TYPE_SAMPLER2D_ARRAY)

                .put("isampler1D", TokenType.TYPE_ISAMPLER1D)
                .put("isampler2D", TokenType.TYPE_ISAMPLER2D)
                .put("isampler3D", TokenType.TYPE_ISAMPLER3D)
                .put("isampler1DArray", TokenType.TYPE_ISAMPLER1D_ARRAY)
                .put("isampler2DArray", TokenType.TYPE_ISAMPLER2D_ARRAY)

                // boolean literals
                .put("true", TokenType.LITERAL_TRUE)
                .put("false", TokenType.LITERAL_FALSE)
                .build();
    }
}