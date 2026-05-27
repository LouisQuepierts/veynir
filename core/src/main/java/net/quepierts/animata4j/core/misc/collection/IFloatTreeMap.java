package net.quepierts.animata4j.core.misc.collection;

import net.quepierts.animata4j.core.misc.Mth;

public interface IFloatTreeMap<T> {
    int getLowerIndex(float pKey);

    int getUpperIndex(float pKey);

    int size();

    T getLowerEntry(float pKey);

    T getUpperEntry(float pKey);

    T first();

    T last();

    T get(int pIndex);

    default T getClamped(int pIndex) {
        return this.get(Mth.clamp(pIndex, 0, this.size() - 1));
    }

    float getKey(int pIndex);

    void insert(float pKey, T pValue);

    void removeSpecific(float pKey);

    void removeRange(float pMin, float pMax);
}
