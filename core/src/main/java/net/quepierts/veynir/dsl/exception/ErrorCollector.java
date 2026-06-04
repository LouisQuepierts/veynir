package net.quepierts.veynir.dsl.exception;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ErrorCollector implements Iterable<CompileException> {
    
    private final List<CompileException> errors;

    public ErrorCollector() {
        this.errors = new ArrayList<>();
    }

    public void push(CompileException error) {
        this.errors.add(error);
    }

    @Override
    public @NotNull Iterator<CompileException> iterator() {
        return this.errors.iterator();
    }

}
