package net.quepierts.animata4j.core.data.tree;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * An interface for resolving paths to values using tokens.
 * Implementations can resolve a path string or token array to a value of type T.
 *
 * @param <T> the type of value that can be resolved
 */
@SuppressWarnings("unused")
public interface PathResolvable<T> {
    /**
     * Resolves a path represented by an array of tokens to a value.
     *
     * @param tokens the array of tokens representing the path
     * @return a Result object containing the resolution outcome
     */
    @Contract(value = "_ -> new", pure = true)
    @NotNull Result<T> resolve(@NotNull final String[] tokens);

    /**
     * Resolves a dot-separated path string to a value by splitting it into tokens.
     *
     * @param path the dot-separated path string
     * @return a Result object containing the resolution outcome
     */
    @Contract(value = "_ -> new", pure = true)
    default @NotNull Result<T> resolve(@NotNull final String path) {
        return this.resolve(this.tokenize(path));
    }

    /**
     * Splits a dot-separated path string into an array of tokens.
     *
     * @param path the dot-separated path string
     * @return an array of tokens
     */
    @Contract(value = "_ -> new", pure = true)
    default @NotNull String[] tokenize(@NotNull final String path) {
        return path.split("\\.");
    }

    /**
     * A result class that encapsulates the outcome of a path resolution operation.
     * It contains a boolean flag indicating whether the resolution was successful,
     * an index indicating the last resolved token, and the resolved value.
     *
     * @param <T> the type of value that was resolved
     */
    @Getter
    @AllArgsConstructor(staticName = "of")
    class Result<T> {
        private final boolean success;
        private final int index;
        private final T value;
    }
}