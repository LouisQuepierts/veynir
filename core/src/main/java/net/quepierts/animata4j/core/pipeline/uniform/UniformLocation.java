package net.quepierts.animata4j.core.pipeline.uniform;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter(AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class UniformLocation {

    private final String name;

    private final int location;
    private final int offset;
    private final int size;
    private final int components;

    private final UniformType type;

}
