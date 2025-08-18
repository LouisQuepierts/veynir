package net.quepierts.animata4j.core.data.writer;

import net.quepierts.codegen.annotations.PrimitiveTemplate;
import net.quepierts.codegen.annotations.types.type;

@PrimitiveTemplate
@SuppressWarnings("unused")
public interface _TEMPLATE_$Type$Writer {
    /**
     * Put $type$ to the memory
     * @param offset the offset in the memory
     * @param value the value to put
     * */
    void put$Type$(final long offset, final type value);

    /**
     * Put $type$ array to the memory
     * @param offset the offset in the memory
     * @param value the value to put
     * @param arrayOffset the offset in the array
     * @param length the length of the array
     * */
    void put$Type$(final long offset, final type[] value, final int arrayOffset, final int length);

    /**
     * Put $type$ array to the memory
     * @param offset the offset in the memory
     * @param value the value to put
     * */
    default void put$Type$(final long offset, final type[] value) {
        this.put$Type$(offset, value, 0, value.length);
    }
}
