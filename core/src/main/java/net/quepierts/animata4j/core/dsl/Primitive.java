package net.quepierts.animata4j.core.dsl;

import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.core.dsl.ast.type.PrimitiveType;
import net.quepierts.animata4j.core.dsl.lexer.TokenType;

import java.util.Map;

@RequiredArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public enum Primitive {
    INT(true, 1),
    FLOAT(true, 1),
    BOOL(true, 1),

    VEC2(true, 2),
    VEC3(true, 3),
    VEC4(true, 4),
    IVEC2(true, 2),
    IVEC3(true, 3),
    IVEC4(true, 4),

    MAT2(true, 4),
    MAT3(true, 9),
    MAT4(true, 16),

    SAMPLER1D(false, -1),
    SAMPLER2D(false, -1),
    SAMPLER3D(false, -1),
    SAMPLER1D_ARRAY(false, -1),
    SAMPLER2D_ARRAY(false, -1),

    ISAMPLER1D(false, -1),
    ISAMPLER2D(false, -1),
    ISAMPLER3D(false, -1),
    ISAMPLER1D_ARRAY(false, -1),
    ISAMPLER2D_ARRAY(false, -1);

    private static final Map<TokenType, Primitive> TOKEN2PRIMITIVE;
    public final boolean constructable;
    public final int componentCount;

    public static Primitive fromToken(TokenType token) {
        return TOKEN2PRIMITIVE.get(token);
    }

    static {
        ImmutableMap.Builder<TokenType, Primitive> builder = ImmutableMap.builder();
        builder.put(TokenType.TYPE_INT, INT);
        builder.put(TokenType.TYPE_FLOAT, FLOAT);
        builder.put(TokenType.TYPE_BOOL, BOOL);
        builder.put(TokenType.TYPE_VEC2, VEC2);
        builder.put(TokenType.TYPE_VEC3, VEC3);
        builder.put(TokenType.TYPE_VEC4, VEC4);
        builder.put(TokenType.TYPE_IVEC2, IVEC2);
        builder.put(TokenType.TYPE_IVEC3, IVEC3);
        builder.put(TokenType.TYPE_IVEC4, IVEC4);
        builder.put(TokenType.TYPE_MAT2, MAT2);
        builder.put(TokenType.TYPE_MAT3, MAT3);
        builder.put(TokenType.TYPE_MAT4, MAT4);

        builder.put(TokenType.TYPE_SAMPLER1D, SAMPLER1D);
        builder.put(TokenType.TYPE_SAMPLER2D, SAMPLER2D);
        builder.put(TokenType.TYPE_SAMPLER3D, SAMPLER3D);
        builder.put(TokenType.TYPE_SAMPLER1D_ARRAY, SAMPLER1D_ARRAY);
        builder.put(TokenType.TYPE_SAMPLER2D_ARRAY, SAMPLER2D_ARRAY);

        builder.put(TokenType.TYPE_ISAMPLER1D, ISAMPLER1D);
        builder.put(TokenType.TYPE_ISAMPLER2D, ISAMPLER2D);
        builder.put(TokenType.TYPE_ISAMPLER3D, ISAMPLER3D);
        builder.put(TokenType.TYPE_ISAMPLER1D_ARRAY, ISAMPLER1D_ARRAY);
        builder.put(TokenType.TYPE_ISAMPLER2D_ARRAY, ISAMPLER2D_ARRAY);

        TOKEN2PRIMITIVE = builder.build();
    }
}
