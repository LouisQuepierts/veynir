package net.quepierts.animata4j.core.data.tree.property;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.core.data.accessor.StandardMemoryAccessor;
import net.quepierts.animata4j.core.data.block.MemoryBlock;
import net.quepierts.animata4j.core.data.layout.MemoryLayout;
import org.jetbrains.annotations.Nullable;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class FlatPropertyTree implements PropertyTree {

    private final MemoryBlock memory;
    private final MemoryLayout layout;

    @Override
    public @Nullable StandardMemoryAccessor resolve(String path) {
        return null;
    }
}
