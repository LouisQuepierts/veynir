package net.quepierts.animata4j.core.dsl.visitor;

import net.quepierts.animata4j.core.dsl.ast.CompilationUnit;
import net.quepierts.animata4j.core.dsl.ast.decl.*;
import net.quepierts.animata4j.core.dsl.ast.stmt.*;
import net.quepierts.animata4j.core.dsl.ast.expr.*;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public interface TypedVisitor<R, A> {
    R visit(final @NotNull CompilationUnit node, A args);

    // top declarations
    R visit(final @NotNull VariableDecl node, A args);

    R visit(final @NotNull BlockDecl node, A args);

    R visit(final @NotNull StructDecl node, A args);

    R visit(final @NotNull FunctionDecl node, A args);

    R visit(final @NotNull FunctionDef node, A args);

    // item declarations
    R visit(final @NotNull Parameter node, A args);

    R visit(final @NotNull MemberDecl node, A args);

    // statements
    R visit(final @NotNull BlockStmt node, A args);

    R visit(final @NotNull VariableDeclarationStmt node, A args);

    R visit(final @NotNull IfStmt node, A args);

    R visit(final @NotNull WhileStmt node, A args);

    R visit(final @NotNull ForStmt node, A args);

    R visit(final @NotNull ContinueStmt node, A args);

    R visit(final @NotNull BreakStmt node, A args);

    R visit(final @NotNull ReturnStmt node, A args);

    R visit(final @NotNull ExpressionStmt node, A args);

    R visit(final @NotNull EmptyStmt node, A args);

    // expressions
    R visit(final @NotNull UnaryExpr node, A args);

    R visit(final @NotNull BinaryExpr node, A args);

    R visit(final @NotNull TernaryExpr node, A args);

    R visit(final @NotNull AssignExpr node, A args);

    R visit(final @NotNull IdentifierExpr node, A args);

    R visit(final @NotNull CallExpr node, A args);

    R visit(final @NotNull ConstructExpr node, A args);

    R visit(final @NotNull LiteralIntegerExpr node, A args);

    R visit(final @NotNull LiteralDecimalExpr node, A args);

    R visit(final @NotNull LiteralStringExpr node, A args);

    R visit(final @NotNull LiteralBooleanExpr node, A args);

    R visit(final @NotNull ArrayAccessExpr node, A args);

    R visit(final @NotNull MemberAccessExpr node, A args);

    R visit(final @NotNull EmptyExpr node, A args);
}
