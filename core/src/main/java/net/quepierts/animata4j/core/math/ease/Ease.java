package net.quepierts.animata4j.core.math.ease;

public interface Ease {

    Ease LINEAR = (t) -> t;

    float ease(float t);
}
