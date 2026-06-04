package net.quepierts.veynir.core.misc.collection;

import it.unimi.dsi.fastutil.Stack;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import java.util.function.Supplier;

public class StackObjectPool<T> implements ObjectPool<T> {

    private final Stack<T> pool;
    private final Supplier<T> constructor;

    public StackObjectPool(Supplier<T> constructor) {
        this.pool = new ObjectArrayList<>();
        this.constructor = constructor;
    }

    @Override
    public T request() {
        if (pool.isEmpty()) {
            return constructor.get();
        } else {
            return pool.pop();
        }
    }

    public void release(T object) {
        pool.push(object);
    }

}
