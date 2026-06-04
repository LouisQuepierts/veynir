package net.quepierts.veynir.core.misc;

import lombok.experimental.UtilityClass;

/**
 * A utility class that provides generic type operations.
 */
@UtilityClass
@SuppressWarnings("unused")
public class Generic {
    /**
     * Casts an object to the specified type without type checking.
     *
     * @param o the object to cast
     * @param <T> the target type
     * @return the object cast to the target type
     */
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object o) {
        return (T) o;
    }

    /**
     * Creates a new array of the specified size with Object elements.
     *
     * @param size the size of the array
     * @param <T> the type of elements in the array
     * @return a new array of the specified size
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] newArray(int size) {
        return (T[]) new Object[size];
    }
}