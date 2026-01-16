package animata4j.test.data;

import net.quepierts.animata4j.core.data.layout.AnimataDataType;
import net.quepierts.animata4j.core.data.layout.StructDefinition;
import net.quepierts.animata4j.core.data.layout.mapper.AnimataField;
import net.quepierts.animata4j.core.data.layout.mapper.AnimataField.*;
import net.quepierts.animata4j.core.data.layout.mapper.AnimataStruct;
import net.quepierts.animata4j.core.data.layout.mapper.StructParser;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StructParseTest {
    @AnimataStruct("A")
    static class TestStructA {
        @AnimataField
        private int a;

        @AnimataField(value = "arr", length = 3)
        private int[] b;

        @AnimataField(type = @Type(value = AnimataDataType.FLOAT, count = 3))
        private Vector3f transform;

        @AnimataField(type = @Type(typename = "mat4"))
        private Matrix4f matrix;
    }

    @Test
    public void test_pattern() {
        String name1 = "A";
        String name2 = "B.C";

        Assertions.assertTrue(AnimataDataType.isAvailableStructName(name1));
        Assertions.assertTrue(AnimataDataType.isAvailableStructName(name2));
    }

    @Test
    public void test() {
        StructDefinition definition = StructParser.parse(TestStructA.class);
        System.out.println(definition);
    }
}
