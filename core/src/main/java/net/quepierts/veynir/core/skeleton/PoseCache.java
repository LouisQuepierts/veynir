package net.quepierts.veynir.core.skeleton;

import lombok.Getter;
import net.quepierts.veynir.core.SkeletonLayout;
import net.quepierts.veynir.core.adapter.TransformF;
import net.quepierts.veynir.core.pipeline.SkeletonOutput;
import net.quepierts.veynir.core.pipeline.SkeletonResultView;
import net.quepierts.veynir.core.util.ArrayUtils;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

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
    public void accept(@NonNull final SkeletonResultView view) {
        for (var i = 0; i < this.transforms.length; i++) {
            final var pose = view.get(i);
            pose.getTransform(this.transforms[i]);
        }
    }
}
