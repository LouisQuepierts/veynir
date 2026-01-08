package net.quepierts.animata4j.core.pipeline.common.target;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class TargetHelper {

    /**
     * Copy data from src to dst
     * @param src the source
     * @param srcOffset the offset of the source
     * @param dst the destination
     * @param dstOffset the offset of the destination
     * @param length the length of the data to copy
     */
    public static void memcpy(
            @NotNull AnimationReadableTarget src,
            int srcOffset,
            @NotNull AnimationWritableTarget dst,
            int dstOffset,
            int length
    ) {
        dst.write(dstOffset, src, srcOffset, length);
    }

}
