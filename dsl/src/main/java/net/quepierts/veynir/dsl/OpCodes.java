package net.quepierts.veynir.dsl;

import lombok.experimental.UtilityClass;

import java.util.Arrays;

/**
* opcodes for veynir resolver language (or ARL)<br>
* primitive types: decimal integer boolean
* */
@UtilityClass
@SuppressWarnings("unused")
public class OpCodes {
    /**
     * control 0x00 ~ 0x1f
     */
    public static final byte NOP        = 0x00;
    public static final byte JMP        = 0x01;
    public static final byte JMP_IF     = 0x02;

    public static final byte RET        = 0x0f;

    /**
     * stack / memory 0x20 ~ 0x3f
     */
    public static final byte MASK_STACK = 0x20;
    public static final byte LOAD       = 0x20;
    public static final byte ILOAD      = 0x21;     // load from in
    public static final byte ULOAD      = 0x22;     // load from uniform
    public static final byte STORE      = 0x23;
    public static final byte OSTORE     = 0x24;     // store to out

    public static final byte POP        = 0x28;
    public static final byte DUP        = 0x29;

    public static final byte CONST_I    = 0x30;
    public static final byte CONST_D    = 0x31;
    public static final byte CONST_B    = 0x32;

    /**
     * math 0x40 ~ 0x7f
     */
    public static final byte ADD_I      = 0x40;
    public static final byte SUB_I      = 0x41;
    public static final byte MUL_I      = 0x42;
    public static final byte DIV_I      = 0x43;
    public static final byte MOD_I      = 0x44;
    public static final byte NEG_I      = 0x45;

    // decimal
    public static final byte ADD_D      = 0x50;
    public static final byte SUB_D      = 0x51;
    public static final byte MUL_D      = 0x52;
    public static final byte DIV_D      = 0x53;
    public static final byte MOD_D      = 0x54;
    public static final byte NEG_D      = 0x55;

    // boolean
    public static final byte AND        = 0x60;
    public static final byte OR         = 0x61;
    public static final byte XOR        = 0x62;
    public static final byte NOT        = 0x63;
    public static final byte SHL        = 0x64;
    public static final byte SHR        = 0x65;

    /**
     * logic 0x80 ~ 0x9f
     */
    public static final byte EQ         = (byte) 0x80;
    public static final byte NEQ        = (byte) 0x81;
    public static final byte LT         = (byte) 0x82;
    public static final byte LTE        = (byte) 0x83;
    public static final byte GT         = (byte) 0x84;
    public static final byte GTE        = (byte) 0x85;

    /**
     * function
     */
    public static final byte CALL        = (byte) 0x90;     // CALL <args>(1) <function>(2)
    public static final byte CALL_N      = (byte) 0x91;     // CALL_N <args>(1) <function>(2)

    public static String getName(byte opcode) {
        return MAPPINGS[opcode & 0xFF];
    }

    private static void put(byte opcode, String name) {
        MAPPINGS[opcode & 0xFF] = name;
    }

    private static final String[] MAPPINGS = new String[256];

    static {
        Arrays.fill(MAPPINGS, "");
        put(NOP, "NOP");
        put(JMP, "JMP");
        put(JMP_IF, "JMP_IF");

        put(RET, "RET");

        put(LOAD, "LOAD");
        put(ILOAD, "ILOAD");
        put(ULOAD, "ULOAD");
        put(STORE, "STORE");
        put(OSTORE, "OSTORE");

        put(POP, "POP");
        put(DUP, "DUP");

        put(CONST_I, "CONST_I");
        put(CONST_D, "CONST_D");
        put(CONST_B, "CONST_B");

        put(ADD_I, "ADD_I");
        put(SUB_I, "SUB_I");
        put(MUL_I, "MUL_I");
        put(DIV_I, "DIV_I");
        put(MOD_I, "MOD_I");
        put(NEG_I, "NEG_I");

        put(ADD_D, "ADD_D");
        put(SUB_D, "SUB_D");
        put(MUL_D, "MUL_D");
        put(DIV_D, "DIV_D");
        put(MOD_D, "MOD_D");
        put(NEG_D, "NEG_D");

        put(AND, "AND");
        put(OR, "OR");
        put(NOT, "NOT");
        put(XOR, "XOR");
        put(SHL, "SHL");
        put(SHR, "SHR");

        put(EQ, "EQ");
        put(NEQ, "NEQ");
        put(LT, "LT");
        put(LTE, "LTE");
        put(GT, "GT");
        put(GTE, "GTE");

        put(CALL, "CALL");
        put(CALL_N, "CALL_N");

    }
}
