package net.quepierts.animata4j.core.pipeline.uniform;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public final class UniformLayout {

    private final UniformLocation[] locations;

    @Getter
    private final int size;

    public UniformLocation getUniformLocation(@NotNull String name) {
        // linear search
        for (UniformLocation location : locations) {
            if (location.getName().equals(name)) {
                return location;
            }
        }
        throw new IllegalArgumentException("Uniform not found");
    }

    public int getUniformLocationAmount() {
        return locations.length;
    }


}
