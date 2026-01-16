package net.quepierts.animata4j.dsl.preprocess.macro;

public abstract class Macro {

    public static Macro empty() {
        return EmptyMacro.INSTANCE;
    }

    public long toLong() {
        return 0;
    }

}
