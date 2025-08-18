package net.quepierts.codegen.plugin;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class PackageWalker {
    public static void walk(
            @NotNull final File directory,
            @NotNull final Predicate<String> filter,
            @NotNull final Consumer<File> consumer
    ) {
        searchFromFile(directory, filter, consumer);
    }

    private static void searchFromFile(
            @NotNull final File directory,
            @NotNull final Predicate<String> filter,
            @NotNull final Consumer<File> consumer
    ) {
        final File[] files = directory.listFiles();

        assert files != null;
        for (File file : files) {
            if (file.isDirectory()) {
                searchFromFile(file, filter, consumer);
            } else {
                String name = file.getName();
                if (!filter.test(name))
                    continue;

                consumer.accept(file);
            }
        }
    }
}
