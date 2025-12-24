package net.quepierts.animata4j.core.dsl.preprocess.macro;

import lombok.Getter;

@Getter
public final class FunctionMacro extends Macro {

    private final String[] parameters;
    private final String[] body;

    public FunctionMacro(String[] parameters, String[] body) {
        this.parameters = parameters;
        this.body = body;
    }

    public String apply(String[] args) {
    }
}
