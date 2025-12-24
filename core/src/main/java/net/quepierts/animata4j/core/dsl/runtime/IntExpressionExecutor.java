package net.quepierts.animata4j.core.dsl.runtime;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntStack;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import net.quepierts.animata4j.core.dsl.OpCodes;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class IntExpressionExecutor implements IntExecutor {
    private static final int STACK_SIZE = 8;

    private final byte[] codes;
    private final int[] functions;
    private final int[] constants;
    private final int[] uniforms;
    private final Object2IntMap<String> name2Uniforms;

    @Override
    public int run(int functionIndex) {
        if (functionIndex < -1 || functionIndex >= this.functions.length) {
            throw new IndexOutOfBoundsException("Function index out of bounds: " + functionIndex);
        }

        int result = -1;
        int a, b;

        final IntStack stack = new IntArrayList(STACK_SIZE);
        final int[] args = new int[16];

        int pc = this.functions[functionIndex];
        final int eof = codes.length;

        loop:
        while (pc < eof) {
            final byte opcode = this.getOpcode(pc ++);

            switch (opcode) {
                case OpCodes.RET:
                    result = stack.popInt();
                    break loop;
                case OpCodes.ULOAD:
                    a = this.getOpcode(pc ++);
                    b = this.getUniform(a);
                    stack.push(b);
                    break;
                case OpCodes.POP:
                    stack.popInt();
                    break;
                case OpCodes.DUP:
                    a = stack.topInt();
                    stack.push(a);
                    break;
                case OpCodes.CONST_I:
                    a = this.getOpcode(pc ++);
                    b = this.getConstant(a);
                    stack.push(b);
                    break;
                case OpCodes.ADD_I:
                    a = stack.popInt();
                    b = stack.popInt();
                    stack.push(a + b);
                    break;
                case OpCodes.SUB_I:
                    a = stack.popInt();
                    b = stack.popInt();
                    stack.push(a - b);
                    break;
                case OpCodes.MUL_I:
                    a = stack.popInt();
                    b = stack.popInt();
                    stack.push(a * b);
                    break;
                case OpCodes.DIV_I:
                    a = stack.popInt();
                    b = stack.popInt();
                    stack.push(a / b);
                    break;
                case OpCodes.MOD_I:
                    a = stack.popInt();
                    b = stack.popInt();
                    stack.push(a % b);
                    break;
                case OpCodes.NEG_I:
                    a = stack.popInt();
                    stack.push(-a);
                    break;
                case OpCodes.CALL_N: // opcode <count>(1) <function id>(2)
                    b = this.getOpcode(pc ++);
                    byte high = this.getOpcode(pc ++);
                    byte low = this.getOpcode(pc ++);

                    int functionId = (high << 8) | low;
                    for (a = 0; a < b; a ++) {
                        args[a] = stack.popInt();
                    }
                    a = IntFunction.FUNCTIONS.get(functionId).run(args);
                    stack.push(a);
                    break;
            }
        }

        return result;
    }

    @Override
    public int getUniformId(String name) {
        return name2Uniforms.getInt(name);
    }

    @Override
    public int getUniform(int id) {
        if (id < 0 || id >= uniforms.length) {
            throw new IndexOutOfBoundsException("Uniform index out of bounds: " + id);
        }
        return uniforms[id];
    }

    @Override
    public void setUniform(int id, int value) {
        if (id < 0 || id >= uniforms.length) {
            throw new IndexOutOfBoundsException("Uniform index out of bounds: " + id);
        }
        uniforms[id] = value;
    }

    private int getConstant(int idx) {
        if (idx >= constants.length) {
            throw new IndexOutOfBoundsException("Constant index out of bounds: " + idx);
        }

        return constants[idx];
    }

    private byte getOpcode(int pc) {
        if (pc >= codes.length) {
            throw new IndexOutOfBoundsException("pc: " + pc);
        }
        return codes[pc];
    }
}
