package net.quepierts.animata4j.core.dsl.ast;

import lombok.Getter;
import net.quepierts.animata4j.core.dsl.SourceSpan;
import net.quepierts.animata4j.core.dsl.TypeIdentifier;
import net.quepierts.animata4j.core.dsl.token.ExpressionToken;

public abstract class ExpressionNode extends Node {
    protected ExpressionNode(SourceSpan span) {
        super(span);
    }

    @Getter
    public static final class Number extends ExpressionNode {
        private final String value;

        public Number(SourceSpan span, String value) {
            super(span);
            this.value = value;
        }


        @Override
        public TypeIdentifier<? extends Node> getType() {
            return null;
        }
    }

    @Getter
    public static final class Word extends ExpressionNode {
        private final String value;

        public Word(SourceSpan span, String value) {
            super(span);
            this.value = value;
        }


        @Override
        public TypeIdentifier<? extends Node> getType() {
            return null;
        }
    }

    @Getter
    public static final class BinaryOperation extends ExpressionNode {
        private final ExpressionNode left;
        private final ExpressionNode right;
        private final TypeIdentifier<ExpressionToken.Symbol> opr;

        public BinaryOperation(
                SourceSpan span,
                ExpressionNode left,
                ExpressionNode right,
                TypeIdentifier<ExpressionToken.Symbol> type
        ) {
            super(span);
            this.left = left;
            this.right = right;
            this.opr = type;
        }

        @Override
        public TypeIdentifier<? extends Node> getType() {
            return null;
        }
    }

    @Getter
    public static final class UnaryOperation extends ExpressionNode {
        private final ExpressionNode expr;
        private final TypeIdentifier<ExpressionToken.Symbol> opr;

        public UnaryOperation(
                SourceSpan span,
                ExpressionNode expr,
                TypeIdentifier<ExpressionToken.Symbol> type
        ) {
            super(span);
            this.expr = expr;
            this.opr = type;
        }

        @Override
        public TypeIdentifier<? extends Node> getType() {
            return null;
        }
    }
}
