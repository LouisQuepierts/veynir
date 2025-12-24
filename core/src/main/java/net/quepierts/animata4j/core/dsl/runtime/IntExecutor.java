package net.quepierts.animata4j.core.dsl.runtime;

import it.unimi.dsi.fastutil.Stack;
import it.unimi.dsi.fastutil.booleans.BooleanObjectPair;
import it.unimi.dsi.fastutil.bytes.ByteArrayList;
import it.unimi.dsi.fastutil.bytes.ByteList;
import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.val;
import net.quepierts.animata4j.core.dsl.OpCodes;
import net.quepierts.animata4j.core.dsl.ast.Node;
import net.quepierts.animata4j.core.dsl.ast.expr.*;
import net.quepierts.animata4j.core.dsl.lexer.ArlLexer;
import net.quepierts.animata4j.core.dsl.parser.ExpressionParser;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@SuppressWarnings("unused")
public interface IntExecutor {
    default int run() {
        return this.run(this.getEntryPoint());
    }

    default int getEntryPoint() {
        return 0;
    }

    int run(int functionIndex);

    int getUniformId(String name);

    int getUniform(int id);

    void setUniform(int id, int value);

    default void setUniform(String name, int value) {
        this.setUniform(this.getUniformId(name), value);
    }

    final class Compiler {
        private final Int2IntMap constants = new Int2IntArrayMap();
        private final Object2IntMap<String> uniforms = new Object2IntArrayMap<>();
        private final ByteList codes = new ByteArrayList();
        private final IntList functions = new IntArrayList();

        private boolean compiled = false;

        public Compiler add(@NotNull final String source) {
            ExpressionParser parser = new ExpressionParser(new ArlLexer(source));
            this.add(parser.parse());
            return this;
        }

        public Compiler add(@NotNull final Node root) {
            if (this.compiled) {
                throw new IllegalStateException("Compiler has been compiled");
            }

            this.functions.add(this.codes.size()); // function entry point

            final Stack<BooleanObjectPair<Node>> stack = new ObjectArrayList<>();
            stack.push(BooleanObjectPair.of(false, root));

            while (!stack.isEmpty()) {
                val pair = stack.pop();
                val visited = pair.leftBoolean();
                val node = pair.right();

                switch (node.getType()) {
                    case EXPR_LITERAL_INTEGER: {
                        final int value = ((LiteralIntegerExpr) node).getValue();
                        int idx = this.constants.getOrDefault(value, -1);
                        if (idx == -1) {
                            idx = this.constants.size();
                            constants.put(value, idx);
                        }
                        this.codes.add(OpCodes.CONST_I);
                        this.codes.add((byte) idx);
                        break;
                    }
                    case EXPR_IDENTIFIER: { // parameter
                        final String value = ((IdentifierExpr) node).getValue();
                        int idx = this.uniforms.getOrDefault(value, -1);
                        if (idx == -1) {
                            idx = this.uniforms.size();
                            this.uniforms.put(value, idx);
                        }
                        this.codes.add(OpCodes.ULOAD);
                        this.codes.add((byte) idx);
                        break;
                    }
                    case EXPR_UNARY: {
                        val unary = ((UnaryExpr) node);
                        if (!visited) {
                            stack.push(BooleanObjectPair.of(true, node));
                            stack.push(BooleanObjectPair.of(false, unary.getExpr()));
                        } else if (unary.getOpr() == UnaryExpr.Operator.MINUS) {
                            this.codes.add(OpCodes.NEG_I);
                        }
                        break;
                    }
                    case EXPR_BINARY: {
                        val binary = ((BinaryExpr) node);
                        if (!visited) {
                            stack.push(BooleanObjectPair.of(true, node));
                            stack.push(BooleanObjectPair.of(false, binary.getRight()));
                            stack.push(BooleanObjectPair.of(false, binary.getLeft()));
                        } else {
                            switch (binary.getOpr()) {
                                case ADD:
                                    this.codes.add(OpCodes.ADD_I);
                                    break;
                                case SUB:
                                    this.codes.add(OpCodes.SUB_I);
                                    break;
                                case MUL:
                                    this.codes.add(OpCodes.MUL_I);
                                    break;
                                case DIV:
                                    this.codes.add(OpCodes.DIV_I);
                                    break;
                                case MOD:
                                    this.codes.add(OpCodes.MOD_I);
                                    break;
                            }
                        }
                        break;
                    }
                    case EXPR_CALL: {
                        CallExpr call = (CallExpr) node;
                        NativeFunctionContext.Definition definition = IntFunction.FUNCTIONS.get(call.getFunctionName());

                        if (definition == null) {
                            throw new RuntimeException("Function " + call.getFunctionName() + " not found");
                        }

                        List<Expression> arguments = call.getArguments();
                        int argsAmount = definition.getArgs();
                        if (!visited) {
                            if (arguments.size() != argsAmount) {
                                throw new RuntimeException("Function " + call.getFunctionName() + " requires " + argsAmount + " arguments, but " + arguments.size() + " were provided");
                            }

                            stack.push(BooleanObjectPair.of(true, node));
                            for (Expression argument : arguments) {
                                stack.push(BooleanObjectPair.of(false, argument));
                            }
                        } else {
                            // split int function id into 2 bytes
                            final int functionId = definition.getId();

                            // CALL_N(1) size(1) function_id(2)
                            this.codes.add(OpCodes.CALL_N);
                            this.codes.add((byte) (argsAmount & 0xFF));
                            this.codes.add((byte) ((functionId >> 8) & 0xFF));
                            this.codes.add((byte) (functionId & 0xFF));
                        }
                        break;
                    }
                }
            }

            this.codes.add(OpCodes.RET);
            return this;
        }

        public IntExecutor compile() {
            if (this.compiled) {
                throw new IllegalStateException("Compiler has been compiled");
            }

            this.compiled = true;
            byte[] codes = this.codes.toByteArray();
            int[] functions = this.functions.toIntArray();
            int[] constants = new int[this.constants.size()];
            int[] uniforms = new int[this.uniforms.size()];
            for (Int2IntMap.Entry entry : this.constants.int2IntEntrySet()) {
                constants[entry.getIntValue()] = entry.getIntKey();
            }

            return new IntExpressionExecutor(
                    codes,
                    functions,
                    constants,
                    uniforms,
                    this.uniforms
            );
        }
    }
}
