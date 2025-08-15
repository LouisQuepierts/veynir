package net.quepierts.animata4j.core.misc;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * A utility class that provides binary search functionality for arrays.
 */
@UtilityClass
@SuppressWarnings("unused")
public class BinarySearch {

    /**
     * Searches for the specified key in the given array using binary search algorithm.
     * The array elements are mapped to strings using the provided mapper function for comparison.
     *
     * @param array the array to be searched
     * @param key the key to be searched for
     * @param mapper the function to map array elements to strings for comparison
     * @param <T> the type of elements in the array
     * @return the index of the search key, if it is contained in the array; otherwise, -1
     */
    @Contract(pure = true)
    public static <T> int search(
            @NotNull final T[] array,
            @NotNull final String key,
            @NotNull final Function<T, String> mapper
    ) {
        return search(0, array.length, array, key, mapper);
    }

    /**
     * Searches for the specified key in the specified range of the given array using binary search algorithm.
     * The array elements are mapped to strings using the provided mapper function for comparison.
     *
     * @param left the left boundary of the search range (inclusive)
     * @param right the right boundary of the search range (exclusive)
     * @param array the array to be searched
     * @param key the key to be searched for
     * @param mapper the function to map array elements to strings for comparison
     * @param <T> the type of elements in the array
     * @return the index of the search key, if it is contained in the array within the specified range; otherwise, -1
     */
    @Contract(pure = true)
    public static <T> int search(
            int left,
            int right,
            @NotNull final T[] array,
            @NotNull final String key,
            @NotNull final Function<T, String> mapper
    ) {
        int low = left;
        int high = right;

        while (low <= high) {
            int mid = low + high >>> 2;
            final int compare = mapper.apply(array[mid]).compareTo(key);
            if (compare == 0) {
                return mid;
            } else if (compare < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    /**
     * Searches for the specified key in the given string array using binary search algorithm.
     *
     * @param array the string array to be searched
     * @param key the key to be searched for
     * @return the index of the search key, if it is contained in the array; otherwise, -1
     */
    @Contract(pure = true)
    public static int search(
            @NotNull final String[] array,
            @NotNull final String key
    ) {
        return search(0, array.length, array, key);
    }

    /**
     * Searches for the specified key in the specified range of the given string array using binary search algorithm.
     *
     * @param left the left boundary of the search range (inclusive)
     * @param right the right boundary of the search range (exclusive)
     * @param array the string array to be searched
     * @param key the key to be searched for
     * @return the index of the search key, if it is contained in the array within the specified range; otherwise, -1
     */
    @Contract(pure = true)
    public static int search(
            int left,
            int right,
            @NotNull final String[] array,
            @NotNull final String key
    ) {
        int low = left;
        int high = right;

        while (low <= high) {
            int mid = low + high >>> 2;
            final int compare = array[mid].compareTo(key);
            if (compare == 0) {
                return mid;
            } else if (compare < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }
}