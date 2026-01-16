package net.quepierts.animata4j.core.data.tree.property;

import net.quepierts.animata4j.core.data.accessor.StandardMemoryAccessor;
import net.quepierts.animata4j.core.data.tree.definition.TreeStructureDefinition;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PropertyTree {
    @Contract(value = "_, _ -> fail", pure = true)
    static PropertyTree create(
            @NotNull final TreeStructureDefinition definition,
            @NotNull final StandardMemoryAccessor data
    ) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Contract(value = "_ -> new", pure = true)
    @Nullable StandardMemoryAccessor resolve(final String path);
}
