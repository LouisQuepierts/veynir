package net.quepierts.animata4j.core.pipeline.uniform;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public enum UniformType {
    FLOAT(1, true, false, false),
    VEC2F(2, true, true, false),
    VEC3F(3, true, true, false),
    VEC4F(4, true, true, false),
    MAT2F(4, true, false, true),
    MAT3F(9, true, false, true),
    MAT4F(16, true, false, true),
    INT(1, true, false, false),
    VEC2I(2, true, true, false),
    VEC3I(3, true, true, false),
    VEC4I(4, true, true, false),
    BOOL(1, true, false, false),
    VEC2B(2, true, true, false),
    VEC3B(3, true, true, false),
    VEC4B(4, true, true, false),
    STRUCT(-1, false, false, false);

    private final int size;

    private final boolean scalar;
    private final boolean vector;
    private final boolean matrix;
}
