package net.quepierts.veynir.core.misc.collection;

public interface ObjectPool<T> {
    T request();

    void release(T object);
}
