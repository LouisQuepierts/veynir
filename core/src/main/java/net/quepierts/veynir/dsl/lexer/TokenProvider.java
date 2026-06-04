package net.quepierts.veynir.dsl.lexer;

import net.quepierts.veynir.dsl.source.SourceProvider;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public interface TokenProvider extends Iterator<Token> {
    @NotNull SourceProvider getSource();
}
