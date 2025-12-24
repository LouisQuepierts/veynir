package net.quepierts.animata4j.core.dsl.preprocess.macro;

import lombok.Getter;

@Getter
public final class ObjectMacro extends Macro {

    private final String content;
    private long number;

    public ObjectMacro(String content) {
        this.content = content;

        try {
            this.number = Long.parseLong(content);
        } catch (Exception e) {
            this.number = 0;
        }
    }

    public String apply(String[] args) {
        return content;
    }

    @Override
    public long toLong() {
        return this.number;
    }
}
