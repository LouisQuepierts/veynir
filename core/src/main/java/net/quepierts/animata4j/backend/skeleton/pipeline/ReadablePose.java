package net.quepierts.animata4j.backend.skeleton.pipeline;

import net.quepierts.animata4j.core.adapter.TransformAccessor;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public interface ReadablePose {

    void getPosition(final Vector3f out);

    void getRotation(final Quaternionf out);

    void getScale(final Vector3f out);

    void getTransform(final TransformAccessor out);

}
