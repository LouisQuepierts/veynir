package net.quepierts.animata4j.core.dsl.lexer;

import net.quepierts.animata4j.core.dsl.source.SourceProvider;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public interface TokenProvider extends Iterator<Token> {
    @NotNull SourceProvider getSource();
}
