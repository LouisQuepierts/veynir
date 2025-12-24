package net.quepierts.animata4j.core.dsl.preprocess.macro;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class EmptyMacro extends Macro {
    public static final EmptyMacro INSTANCE = new EmptyMacro();
}
