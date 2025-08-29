package animata4j.test.data;

import net.quepierts.animata4j.core.data.block.MemoryBlock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MemoryBlockTest {
    @Test
    public void test_createDefault() {
        // create new data block
        MemoryBlock block = MemoryBlock.create();

        // check size
        Assertions.assertEquals(MemoryBlock.DEFAULT_SIZE, block.size());

        // check getter and setter
        block.putByte(0, (byte) 0x01);
        float[] floats = new float[]{0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f, 0.7f, 0.8f, 0.9f, 1.0f};
        float[] accept = new float[floats.length];
        block.putFloat(4, floats);

        Assertions.assertEquals(0x01, block.getByte(0));
        Assertions.assertEquals(floats[0], block.getFloat(4));

        block.getFloat(4, accept);
        Assertions.assertArrayEquals(floats, accept);

        float float_4 = block.getFloat(4 + 4 * 4);
        Assertions.assertEquals(floats[4], float_4);

        // release memory
        block.free();
    }
}
