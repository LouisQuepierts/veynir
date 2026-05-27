package net.quepierts.animata4j.core.adapter;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.backend.skeleton.pipeline.SkeletonPoseBuffer;
import net.quepierts.animata4j.backend.skeleton.pipeline.SkeletonResultView;
import net.quepierts.animata4j.core.skeleton.PoseCache;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class SkeletonBinding {

    private final Entry[] entries;

    public static Builder builder() {
        return new Builder();
    }

    public void apply(@NotNull final SkeletonResultView view) {
        for (var entry : this.entries) {
            final var pose = view.get(entry.location());
            pose.getTransform(entry.accessor());
        }
    }

    public void apply(@NotNull final PoseCache cache) {
        for (var entry : this.entries) {
            final var pose = cache.get(entry.location());
            final var accessor = entry.accessor;
            accessor.setPosition(pose.getTx(), pose.getTy(), pose.getTz());
            accessor.setQuaternion(pose.getRx(), pose.getRy(), pose.getRz(), pose.getRw());
            accessor.setScale(pose.getSx(), pose.getSy(), pose.getSz());
        }
    }

    public void fetch(@NotNull final SkeletonPoseBuffer target) {
        for (final var entry : this.entries) {
            final var view = target.get(entry.location());
            entry.provider().write(view);
        }
    }

    @RequiredArgsConstructor
    static final class Entry {
        private final int location;
        private final TransformAccessor accessor;
        private final TransformProvider provider;

        public int location() {
            return location;
        }

        public TransformAccessor accessor() {
            return accessor;
        }

        public TransformProvider provider() {
            return provider;
        }
    }

    public static final class Builder {
        private final List<Entry> entries = new ArrayList<>();

        public @NotNull Builder bind(
                int location,
                @NotNull TransformAccessor accessor,
                @NotNull TransformProvider provider
        ) {
            if (location > -1) {
                this.entries.add(new Entry(location, accessor, provider));
            }
            return this;
        }

        public @NotNull Builder bind(
                int location,
                @NotNull Supplier<TransformAccessor> supplier,
                @NotNull Supplier<TransformProvider> provider
        ) {
            if (location > -1) {
                this.entries.add(new Entry(location, supplier.get(), provider.get()));
            }
            return this;
        }

        public @NotNull SkeletonBinding build() {
            return new SkeletonBinding(entries.toArray(Entry[]::new));
        }
    }

}
