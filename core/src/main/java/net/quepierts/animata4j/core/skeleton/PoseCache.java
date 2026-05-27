package net.quepierts.animata4j.core.skeleton;

import lombok.Getter;
import net.quepierts.animata4j.backend.skeleton.SkeletonLayout;
import net.quepierts.animata4j.backend.skeleton.pipeline.SkeletonOutput;
import net.quepierts.animata4j.backend.skeleton.pipeline.SkeletonResultView;
import net.quepierts.animata4j.core.adapter.TransformF;
import net.quepierts.animata4j.core.misc.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class PoseCache implements SkeletonOutput {

    @Getter
    private final SkeletonLayout layout;
    private final TransformF[] transforms;

    public PoseCache(SkeletonLayout layout) {
        this.layout = layout;
        this.transforms = new TransformF[layout.size()];
        ArrayUtils.init(transforms, TransformF::new);
    }

    public TransformF get(int location) {
        return this.transforms[location];
    }

    public @Nullable TransformF get(String name) {
        var location = layout.id(name);
        if (location == -1) {
            return null;
        }
        return this.transforms[location];
    }

    @Override
    public void accept(@NotNull final SkeletonResultView view) {
        for (var i = 0; i < this.transforms.length; i++) {
            final var pose = view.get(i);
            pose.getTransform(this.transforms[i]);
        }
    }
}
