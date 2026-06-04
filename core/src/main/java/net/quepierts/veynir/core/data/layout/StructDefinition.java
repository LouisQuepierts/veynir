package net.quepierts.veynir.core.data.layout;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

@Getter
@RequiredArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class StructDefinition {
    private final String name;
    private final FieldDefinition[] fields;
    private final boolean optimize;
    private final boolean wrapped;

    public static StructDefinition wrap(@NotNull final FieldDefinition type) {
        return new StructDefinition(type.getName(), new FieldDefinition[]{type}, true, true);
    }

    public static Builder builder(String name) {
        if (!VeynirDataType.isAvailableStructName(name)) {
            throw new IllegalArgumentException("Invalid struct name: " + name);
        }

        return new Builder(name);
    }

    public static final class Builder {
        private final String name;
        private final Set<String> fieldNames = Sets.newHashSet();
        private final List<FieldDefinition> fields = Lists.newArrayList();

        private boolean emptyCheck = false;
        private boolean optimize = false;

        private Builder(String name) {
            this.name = name;
        }

        public Builder field(FieldDefinition field) {
            if (!this.fieldNames.add(field.getName())) {
                throw new IllegalArgumentException("Duplicate field name: " + field.getName());
            }
            this.fields.add(field);
            return this;
        }

        public Builder optimize() {
            this.optimize = true;
            return this;
        }

        public Builder enableEmptyCheck() {
            this.emptyCheck = true;
            return this;
        }

        public StructDefinition build() {
            if (this.emptyCheck && this.fields.isEmpty()) {
                throw new IllegalArgumentException("StructDefinition cannot be empty");
            }

            FieldDefinition[] fields = this.fields.toArray(FieldDefinition[]::new);
            return new StructDefinition(this.name, fields, this.optimize, false);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("struct ")
                .append(name).append(" {\n");

        for (FieldDefinition field : fields) {
            builder.append("\t").append(field).append("\n");
        }

        return builder.append("}").toString();
    }
}
