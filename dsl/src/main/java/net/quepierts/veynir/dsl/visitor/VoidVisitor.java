package net.quepierts.veynir.dsl.visitor;

import net.quepierts.veynir.dsl.ast.CompilationUnit;
import net.quepierts.veynir.dsl.ast.decl.*;
import net.quepierts.veynir.dsl.ast.expr.*;
import net.quepierts.veynir.dsl.ast.stmt.*;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public interface VoidVisitor<A> {
    void visit(final @NotNull CompilationUnit node, A args);

    // top declarations
    void visit(final @NotNull VariableDecl node, A args);

    void visit(final @NotNull BlockDecl node, A args);

    void visit(final @NotNull StructDecl node, A args);

    void visit(final @NotNull FunctionDecl node, A args);

    void visit(final @NotNull FunctionDef node, A args);

    // item declarations
    void visit(final @NotNull Parameter node, A args);

    void visit(final @NotNull MemberDecl node, A args);

    // statements
    void visit(final @NotNull BlockStmt node, A args);

    void visit(final @NotNull VariableDeclarationStmt node, A args);

    void visit(final @NotNull IfStmt node, A args);

    void visit(final @NotNull WhileStmt node, A args);

    void visit(final @NotNull ForStmt node, A args);

    void visit(final @NotNull ContinueStmt node, A args);

    void visit(final @NotNull BreakStmt node, A args);

    void visit(final @NotNull ReturnStmt node, A args);

    void visit(final @NotNull ExpressionStmt node, A args);

    void visit(final @NotNull EmptyStmt node, A args);

    // expressions
    void visit(final @NotNull UnaryExpr node, A args);

    void visit(final @NotNull BinaryExpr node, A args);

    void visit(final @NotNull TernaryExpr node, A args);

    void visit(final @NotNull AssignExpr node, A args);

    void visit(final @NotNull IdentifierExpr node, A args);

    void visit(final @NotNull CallExpr node, A args);

    void visit(final @NotNull ConstructExpr node, A args);

    void visit(final @NotNull LiteralIntegerExpr node, A args);

    void visit(final @NotNull LiteralDecimalExpr node, A args);

    void visit(final @NotNull LiteralStringExpr node, A args);

    void visit(final @NotNull LiteralBooleanExpr node, A args);

    void visit(final @NotNull ArrayAccessExpr node, A args);

    void visit(final @NotNull MemberAccessExpr node, A args);

    void visit(final @NotNull EmptyExpr node, A args);
}
