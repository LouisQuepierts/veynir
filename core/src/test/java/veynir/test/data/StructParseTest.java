package veynir.test.data;

import net.quepierts.veynir.core.data.layout.VeynirDataType;
import net.quepierts.veynir.core.data.layout.StructDefinition;
import net.quepierts.veynir.core.data.layout.mapper.VeynirField;
import net.quepierts.veynir.core.data.layout.mapper.VeynirField.*;
import net.quepierts.veynir.core.data.layout.mapper.VeynirStruct;
import net.quepierts.veynir.core.data.layout.mapper.StructParser;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StructParseTest {
    @VeynirStruct("A")
    static class TestStructA {
        @VeynirField
        private int a;

        @VeynirField(value = "arr", length = 3)
        private int[] b;

        @VeynirField(type = @Type(value = VeynirDataType.FLOAT, count = 3))
        private Vector3f transform;

        @VeynirField(type = @Type(typename = "mat4"))
        private Matrix4f matrix;
    }

    @Test
    public void test_pattern() {
        String name1 = "A";
        String name2 = "B.C";

        Assertions.assertTrue(VeynirDataType.isAvailableStructName(name1));
        Assertions.assertTrue(VeynirDataType.isAvailableStructName(name2));
    }

    @Test
    public void test() {
        StructDefinition definition = StructParser.parse(TestStructA.class);
        System.out.println(definition);
    }
}
