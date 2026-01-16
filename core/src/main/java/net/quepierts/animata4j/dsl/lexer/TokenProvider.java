package net.quepierts.animata4j.dsl.lexer;

import net.quepierts.animata4j.dsl.source.SourceProvider;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public interface TokenProvider extends Iterator<Token> {
    @NotNull SourceProvider getSource();
}
