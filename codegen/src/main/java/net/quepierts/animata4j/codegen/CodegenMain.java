package net.quepierts.animata4j.codegen;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class CodegenMain {
    public static void main(String[] args) {
        final Map<String, String> argmap = getArgs(args);
        final String src = argmap.get("src");
        final String dst = argmap.get("dst");
        new CodegenPipeline(src, dst).run();
    }

    private static Map<String, String> getArgs(String[] args) {
        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("--")) {
                builder.put(args[i].substring(2), args[i + 1]);
                i++;
            } else {
                builder.put(args[i], "");
            }
        }
        return builder.build();
    }
}
