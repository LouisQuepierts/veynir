package net.quepierts.animata4j.core.data.layout;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.core.misc.BinarySearch;
import net.quepierts.animata4j.core.misc.PathResolveHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@RequiredArgsConstructor(staticName = "of")
public final class MemoryLayout {

    private final FieldOffset[] fields;

    @Getter private final boolean arrayLike;
    @Getter private final boolean wrapped;
    @Getter private final int size;
    @Getter private final int alignment;

    /*public static MemoryAccessProcedure compile(
            @NotNull MemoryLayout layout,
            @NotNull String path
    ) {
        final List<Token> tokens = new PathLexer(path).tokenize();

        final int length = tokens.size();
        int i = 0;
        MemoryLayout current = layout;
        MemoryAccessProcedure.Builder builder = new MemoryAccessProcedure.Builder();

        while (i != length) {
            final Token token = tokens.get(i);
            final TypeIdentifier<? extends Token> type = token.getType();

            if (type == TokenTypes.SIMPLE) {
                final FieldOffset field = current.binarySearch(token.getValue());
            }
        }
    }*/

    public static MemoryAccessProcedure resolve(
            @NotNull MemoryLayout layout,
            @NotNull String path
    ) {
        final PathResolveHelper.Token[] tokens = PathResolveHelper.tokenize(path);

        int i = 0;
        MemoryLayout current = layout;
        MemoryAccessProcedure.Builder builder = new MemoryAccessProcedure.Builder();
        while (i != tokens.length) {
            final PathResolveHelper.Token token = tokens[i];
            FieldOffset next;

            if (token.isSubscript()) {
                if (!current.isArrayLike()) {
                    MemoryLayout.error("Non-array element", path, token);
                }

                final int size = current.getElementSize();
                if (token.isIntegerSubscript()) {
                    final int index = token.getIntegerSubscript();
                    builder.shift(size, index);
                } else {
                    final String param = token.getContent();
                    builder.param(param, size);
                }

                next = current.fields[0];
            } else {
                final FieldOffset field = current.binarySearch(token.getContent());

                if (field == null) {
                    MemoryLayout.error("Unknown element", path, token);
                    break;
                }

                next = field;
            }

            i++;
            current = next.getNestedLayout();
            if (i != tokens.length && current == null) {
                MemoryLayout.error("Non-struct element", path, token);
                break;
            }
        }

        return builder.build();
    }

    public static int resolveStatic(
            @NotNull MemoryLayout layout,
            @NotNull String path
    ) {
        MemoryAccessProcedure procedure = resolve(layout, path);
        return procedure.resolve(null);
    }

    private static void error(
            @NotNull String message,
            @NotNull String path,
            @NotNull PathResolveHelper.Token token
    ) {
        StringBuilder builder = new StringBuilder()
                .append(message).append("\n")
                .append("Element name: ").append(token.getContent()).append("\n")
                .append(path).append("\n");

        int index = path.indexOf(token.getContent());
        builder.append(" ".repeat(index + 1));
        builder.append("^");
        throw new IllegalArgumentException(builder.toString());
    }

    public MemoryAccessProcedure resolve(@NotNull String path) {
        return MemoryLayout.resolve(this, path);
    }

    FieldOffset unwrap() {
        if (!this.wrapped) {
            throw new IllegalStateException("This layout is not wrapped.");
        }

        return this.fields[0];
    }

    private int getElementSize() {
        if (!this.arrayLike) {
            throw new IllegalStateException("This layout is not array like.");
        }

        return this.fields[0].getElementSize();
    }

    private FieldOffset binarySearch(final String name) {
        final int index = BinarySearch.search(
                this.fields,
                name,
                FieldOffset::getName
        );
        return index == -1 ? null : this.fields[index];
    }

    @Getter
    @RequiredArgsConstructor(staticName = "of")
    public static final class FieldOffset {
        private final String name;
        private final int offset;
        private final int size;
        private final int alignment;
        private final int arrayLength;

        @Nullable
        private final MemoryLayout nestedLayout;

        public boolean isArray() {
            return this.arrayLength > 1;
        }

        public boolean isStruct() {
            return this.nestedLayout != null;
        }

        public int getElementSize() {
            return this.size / this.arrayLength;
        }
    }
}
