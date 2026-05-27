package net.quepierts.animata4j.core.adapter;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.backend.pipeline.AnimationResultView;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface AnimationOutput {

    @Contract("_ -> new")
    static @NotNull AnimationOutput compose(@NotNull AnimationOutput... outputs) {
        return new Composed(outputs);
    }

    void accept(@NotNull AnimationResultView buffer);

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
     class Composed implements AnimationOutput {
        private final @lombok.NonNull AnimationOutput[] outputs;

        @Override
        public void accept(@NotNull final AnimationResultView buffer) {
            for (final var output : outputs) {
                output.accept(buffer);
            }
        }
    }

}
