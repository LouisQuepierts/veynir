package net.quepierts.veynir.dsl.runtime;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.quepierts.veynir.core.misc.Generic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class NativeFunctionContext<T> {
    private final Map<String, Definition> definitions;
    private final T[] functions;

    public Definition get(String name) {
        return definitions.get(name);
    }

    public T get(int id) {
        return functions[id];
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PACKAGE)
    public static final class Definition {
        private final String name;
        private final int id;
        private final int args;
    }

    public static final class Builder<T> {
        private final Map<String, Definition> definitions = new HashMap<>();
        private final List<T> functions = new ArrayList<>();

        public Builder<T> add(String name, int args, T function) {
            int id = functions.size();
            definitions.put(name, new Definition(name, id, args));
            functions.add(function);
            return this;
        }

        public NativeFunctionContext<T> build() {
            return new NativeFunctionContext<>(definitions, functions.toArray(Generic::newArray));
        }
    }
}
