package net.quepierts.animata4j.dsl.runtime;

import net.quepierts.animata4j.core.memory.MemoryAccess;

public class ARLExecutor {

    /*
    * r0~r15    system
    * r16~r31   function
    * r32~r63   temp
    * */
    public static final int REGISTER_COUNT = 64;

    /*
    * integer decimal boolean   1   slot
    * vec4                      4   slots
    * mat4                      16  slots
    * */
    public static final int REGISTER_SIZE = 16;

    /*
    * 0~
    * */
    private final MemoryAccess memory;

    public ARLExecutor(int size) {
        this.memory = MemoryAccess.allocate(REGISTER_COUNT * REGISTER_SIZE + size);
    }

}
