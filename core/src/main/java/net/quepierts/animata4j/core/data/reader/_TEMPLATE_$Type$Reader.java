package net.quepierts.animata4j.core.data.reader;

import net.quepierts.codegen.annotations.PrimitiveTemplate;
import net.quepierts.codegen.annotations.types.type;

@PrimitiveTemplate
@SuppressWarnings("unused")
public interface _TEMPLATE_$Type$Reader {
    /**
     * Get a $type$ value from memory
     * @param offset the offset of the value
     * @return the $type$ value
     * */
    type get$Type$(final long offset);

    /**
     * Get a $type$ array from memory
     * @param offset the offset of the array
     * @param out the output array
     * @param arrayOffset the offset of the output array
     * @param length the length of the output array
     * */
    void get$Type$(
            final long offset,
            final type[] out,
            final int arrayOffset,
            final int length
    );

    /**
     * Get a $type$ array from memory
     * @param offset the offset of the array
     * @param out the output array
     * */
    default void get$Type$(final long offset, final type[] out) {
        this.get$Type$(offset, out, 0, out.length);
    }
}
