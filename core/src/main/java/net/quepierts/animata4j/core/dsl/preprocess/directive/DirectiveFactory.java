package net.quepierts.animata4j.core.dsl.preprocess.directive;

import lombok.AllArgsConstructor;
import net.quepierts.animata4j.core.dsl.StringSplitter;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class DirectiveFactory {

    private static final Map<String, ConstructorInfo> CONSTRUCTORS;

    public static Directive of(@NotNull StringSplitter splitter) {
        String name = splitter.next().substring(1);

        ConstructorInfo info = CONSTRUCTORS.get(name);
        if (info == null) {
            throw new RuntimeException("Unknown directive: " + name);
        }

        final String[] args = splitter.auto(info.argsCount);
        return info.constructor.construct(args);
    }

    public static void register(
            @NotNull String name,
            @NotNull DirectiveConstructor constructor,
            int argsCount
    ) {
        if (argsCount < 0) {
            throw new IllegalArgumentException("Invalid args count: " + argsCount);
        }
        CONSTRUCTORS.put(name, ConstructorInfo.of(constructor, argsCount));
    }

    @AllArgsConstructor(access = lombok.AccessLevel.PRIVATE, staticName = "of")
    private static final class ConstructorInfo {
        private final DirectiveConstructor constructor;
        private final int argsCount;
    }

    static {
        CONSTRUCTORS = new HashMap<>();
        register("define", DefineDirective::construct, 2);
        register("undef", UndefineDirective::construct, 1);
        register("undefine", UndefineDirective::construct, 1);
        register("ifdef", IfdefDirective::construct, 1);
        register("ifndef", IfndefDirective::construct, 1);
        register("else", ElseDirective::construct, 0);
        register("endif", EndifDirective::construct, 0);
        register("if", IfDirective::construct, 0);
        register("elif", ElifDirective::construct, 0);
    }
}
