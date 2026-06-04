package net.quepierts.veynir.dsl.runtime;

@FunctionalInterface
public interface IntFunction {
    int run(int[] args);

    NativeFunctionContext<IntFunction> FUNCTIONS = new NativeFunctionContext.Builder<IntFunction>()
            .add("min", 2, args -> Math.min(args[0], args[1]))
            .add("max", 2, args -> Math.max(args[0], args[1]))
            .add("abs", 1, args -> Math.abs(args[0]))
            .add("step", 1, args -> args[0] < 0 ? 0 : 1)
            .add("select", 3, args -> args[0] == 1 ? args[2] : args[1])
            .add("pow", 2, args -> (int) Math.pow(args[0], args[1]))
            .add("pow2", 1, args -> 2 << args[0])
            .add("sqrt", 1, args -> (int) Math.sqrt(args[0]))
            .add("log", 1, args -> (int) Math.log(args[0]))
            .build();
}
