package net.quepierts.animata4j.core.data.layout;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

@Getter
@RequiredArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public enum AnimataDataType {
    BYTE(1, true, "byte"),
    BOOLEAN(1, false, "boolean"),
    SHORT(2, true, "short"),
    INT(4, true, "int"),
    FLOAT(4, true, "float"),
    LONG(8, true, "long"),
    DOUBLE(8, true, "double"),
    STRUCT(0, false, "struct")
    ;

    private static final Pattern NAME_PATTERN = Pattern.compile("[a-zA-Z$_][a-zA-Z\\d$_]*(\\.[a-zA-Z$_][a-zA-Z\\d$_]*)*");
    private static final AnimataDataType[] VALUES;
    private static final Map<String, AnimataDataType> PRIMITIVE_TYPES;

    public static boolean isPrimitiveType(String name) {
        return PRIMITIVE_TYPES.containsKey(name.toUpperCase(Locale.ROOT));
    }

    public static boolean isAvailableStructName(String name) {
        return !isPrimitiveType(name) && NAME_PATTERN.matcher(name).matches();
    }

    public static boolean isAvailableType(String name) {
        return isPrimitiveType(name) || isAvailableStructName(name);
    }

    public static AnimataDataType getType(String name) {
        return PRIMITIVE_TYPES.getOrDefault(name.toUpperCase(Locale.ROOT), STRUCT);
    }

    private final int size;
    private final boolean primitive;
    private final String name;

    static {
        VALUES = values();

        ImmutableMap.Builder<String, AnimataDataType> builder = ImmutableMap.builderWithExpectedSize(VALUES.length - 2);
        for (AnimataDataType type : VALUES) {
            if (type == STRUCT) continue;
            builder.put(type.name.toUpperCase(Locale.ROOT), type);
        }
        PRIMITIVE_TYPES = builder.build();
    }
}
