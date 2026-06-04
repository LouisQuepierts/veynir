package net.quepierts.veynir.core.reference;

import com.google.common.collect.ImmutableList;
import net.quepierts.veynir.core.data.layout.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@SuppressWarnings("unused")
public class BuiltinStruct {

    public static final StructDefinition VEC2;
    public static final StructDefinition VEC3;
    public static final StructDefinition VEC4;

    public static final StructDefinition MAT2;
    public static final StructDefinition MAT3;
    public static final StructDefinition MAT4;

    public static final StructDefinition TRANSFORM;

    private static final List<StructDefinition> BUILTIN_STRUCTS;

    public static MemoryLayoutManager newMemoryLayoutManager(@NotNull final MemoryLayoutBehaviour behaviour) {
        final MemoryLayoutManager manager = MemoryLayoutManager.of(behaviour);
        BUILTIN_STRUCTS.forEach(manager::getLayout);
        return manager;
    }

    static {
        ImmutableList.Builder<StructDefinition> builder = ImmutableList.builder();

        VEC2 = StructDefinition.builder("vec2")
                .field(FieldDefinition.primitive(VeynirDataType.FLOAT).name("x").build())
                .field(FieldDefinition.primitive(VeynirDataType.FLOAT).name("y").build())
                .build();
        builder.add(VEC2);
        
        VEC3 = StructDefinition.builder("vec3")
                .field(FieldDefinition.primitive(VeynirDataType.FLOAT).name("x").build())
                .field(FieldDefinition.primitive(VeynirDataType.FLOAT).name("y").build())
                .field(FieldDefinition.primitive(VeynirDataType.FLOAT).name("z").build())
                .build();
        builder.add(VEC3);
        
        VEC4 = StructDefinition.builder("vec4")
                .field(FieldDefinition.primitive(VeynirDataType.FLOAT).name("x").build())
                .field(FieldDefinition.primitive(VeynirDataType.FLOAT).name("y").build())
                .field(FieldDefinition.primitive(VeynirDataType.FLOAT).name("z").build())
                .field(FieldDefinition.primitive(VeynirDataType.FLOAT).name("w").build())
                .build();
        builder.add(VEC4);

        MAT2 = StructDefinition.wrap(
                FieldDefinition.struct("vec2").length(2).build()
        );
        builder.add(MAT2);

        MAT3 = StructDefinition.wrap(
                FieldDefinition.struct("vec3").length(3).build()
        );
        builder.add(MAT3);

        MAT4 = StructDefinition.wrap(
                FieldDefinition.struct("vec4").length(4).build()
        );

        TRANSFORM = StructDefinition.builder("transform").optimize()
                .field(FieldDefinition.struct("vec3").name("position").build())
                .field(FieldDefinition.struct("vec3").name("rotation").build())
                .field(FieldDefinition.struct("vec3").name("scale").build())
                .build();
        builder.add(TRANSFORM);
        BUILTIN_STRUCTS = builder.build();
    }
}
