package net.quepierts.animata4j.core.misc.collection;

public interface ObjectPool<T> {
    T request();

    void release(T object);
}
