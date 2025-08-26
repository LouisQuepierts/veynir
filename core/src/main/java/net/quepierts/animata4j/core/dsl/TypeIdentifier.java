package net.quepierts.animata4j.core.dsl;

import net.quepierts.animata4j.core.dsl.token.Token;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public interface TypeIdentifier<T> {
    TypeKey key();

    @Contract(value = "_ -> param1", pure = true)
    default T cast(@NotNull Token token) {
        return TypeIdentifier.cast(this, token);
    }
    @Contract(value = "_ -> new", pure = true)
    static <T> @NotNull TypeIdentifier<T> of(@NotNull final TypeKey key) {
        return () -> key;
    }

    @SuppressWarnings("unchecked")
    @Contract(value = "_, _ -> param2", pure = true)
    static <T> @NotNull T cast(
            @NotNull TypeIdentifier<T> type,
            @NotNull Token token
    ) {
        if (token.getType() != type) {
            throw new ClassCastException("Cannot cast " + token.getClass().getName() + " to " + type.getClass().getName());
        }
        return (T) token;
    }
}
