package net.quepierts.animata4j.core.dsl.token;

import lombok.experimental.UtilityClass;
import net.quepierts.animata4j.core.dsl.TypeIdentifier;

@UtilityClass
public class TokenTypes {

    public static final TypeIdentifier<EOFToken> EOF = TypeIdentifier.of(EOFToken.Keys.EOF);

    public static final TypeIdentifier<PathToken.Simple> SIMPLE = TypeIdentifier.of(PathToken.Keys.SIMPLE);
    public static final TypeIdentifier<PathToken.Subscript> SUBSCRIPT = TypeIdentifier.of(PathToken.Keys.SUBSCRIPT);

    public static final TypeIdentifier<ExpressionToken.Number> NUMBER = TypeIdentifier.of(ExpressionToken.Keys.NUMBER);
    public static final TypeIdentifier<ExpressionToken.Word> WORD = TypeIdentifier.of(ExpressionToken.Keys.WORD);

    public static final TypeIdentifier<ExpressionToken.Symbol> ADD = TypeIdentifier.of(ExpressionToken.Keys.ADD);
    public static final TypeIdentifier<ExpressionToken.Symbol> SUB = TypeIdentifier.of(ExpressionToken.Keys.SUB);
    public static final TypeIdentifier<ExpressionToken.Symbol> MUL = TypeIdentifier.of(ExpressionToken.Keys.MUL);
    public static final TypeIdentifier<ExpressionToken.Symbol> DIV = TypeIdentifier.of(ExpressionToken.Keys.DIV);
    public static final TypeIdentifier<ExpressionToken.Symbol> MOD = TypeIdentifier.of(ExpressionToken.Keys.MOD);
    public static final TypeIdentifier<ExpressionToken.Symbol> POW = TypeIdentifier.of(ExpressionToken.Keys.POW);
    public static final TypeIdentifier<ExpressionToken.Symbol> LPAREN = TypeIdentifier.of(ExpressionToken.Keys.LPAREN);
    public static final TypeIdentifier<ExpressionToken.Symbol> RPAREN = TypeIdentifier.of(ExpressionToken.Keys.RPAREN);

}
