package net.quepierts.veynir.core.data.layout;

import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public final class MemoryAccessProcedure {
    private final Operation[] operations;
    private final List<String> parameters;
    private final boolean direct;

    public int resolve(final int @Nullable [] parameters) {
        if (this.direct) {
            return this.operations[0].shift;
        }

        if (parameters == null) {
            throw new IllegalArgumentException("Parameters cannot be null when procedure is not direct!");
        }

        int offset = 0;
        for (Operation operation : this.operations) {
            if (operation.direct) {
                offset += operation.shift;
            } else {
                final int idx = parameters[operation.parameterIndex];
                offset += operation.shift * idx;
            }
        }

        return offset;
    }

    public int getParameterIndex(@NotNull final String name) {
        return this.direct ? -1 : this.parameters.indexOf(name);
    }

    @Contract(value = "_, _ -> new", pure = true)
    public @NotNull MemoryAccessProcedure subst(
            final int left,
            final int right
    ) {
        final int length = this.operations.length;
        if (left >= length || right >= length || right <= left) {
            throw new IllegalArgumentException("Invalid index range!");
        }

        final Operation[] opr = new Operation[right - left];
        boolean direct = true;
        for (int i = left; i < right; i++) {
            opr[i - left] = this.operations[i];

            if (!opr[i - left].direct) {
                direct = false;
            }
        }

        return new MemoryAccessProcedure(opr, this.parameters, direct);
    }

    public static final class Builder {
        private final Object2IntMap<String> parameters = new Object2IntArrayMap<>();
        private final List<Operation> operations = new ArrayList<>();

        private int shift;

        public Builder shift(int offset) {
            this.shift += offset;
            return this;
        }

        public Builder shift(int offset, int index) {
            this.shift += offset * index;
            return this;
        }

        public Builder param(
                @NotNull final String name,
                final int size
        ) {
            if (this.shift != 0) {
                this.operations.add(Operation.direct(this.shift));
                this.shift = 0;
            }

            int index = this.parameters.putIfAbsent(name, this.parameters.size());
            this.operations.add(Operation.param(size, index));
            return this;
        }

        public MemoryAccessProcedure build() {
            if (this.shift != 0) {
                this.operations.add(Operation.direct(this.shift));
            }
            return new MemoryAccessProcedure(
                    this.operations.toArray(Operation[]::new),
                    List.copyOf(this.parameters.keySet()),
                    this.operations.size() == 1 && this.operations.get(0).direct
            );
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    static final class Operation {
        private final boolean direct;
        private final int shift;
        private final int parameterIndex;

        private static Operation direct(int shift) {
            return new Operation(true, shift, -1);
        }

        private static Operation param(int shift, int parameterIndex) {
            return new Operation(false, shift, parameterIndex);
        }
    }
}
