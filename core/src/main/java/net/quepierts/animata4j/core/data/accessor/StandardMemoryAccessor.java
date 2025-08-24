package net.quepierts.animata4j.core.data.accessor;

import net.quepierts.animata4j.core.data.reader.StandardMemoryReader;
import net.quepierts.animata4j.core.data.writer.StandardMemoryWriter;

public interface StandardMemoryAccessor extends
        StandardMemoryReader, StandardMemoryWriter,
        ByteAccessor, ShortAccessor,
        IntegerAccessor, LongAccessor,
        FloatAccessor, DoubleAccessor {
}
