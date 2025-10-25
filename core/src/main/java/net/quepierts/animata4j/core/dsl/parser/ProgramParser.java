package net.quepierts.animata4j.core.dsl.parser;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.quepierts.animata4j.core.dsl.ast.CompilationUnit;
import net.quepierts.animata4j.core.dsl.ast.common.InterfaceQualifier;
import net.quepierts.animata4j.core.dsl.ast.common.Variable;
import net.quepierts.animata4j.core.dsl.ast.common.VariableDeclarator;
import net.quepierts.animata4j.core.dsl.ast.decl.*;
import net.quepierts.animata4j.core.dsl.ast.expr.Expression;
import net.quepierts.animata4j.core.dsl.ast.stmt.*;
import net.quepierts.animata4j.core.dsl.lexer.Token;
import net.quepierts.animata4j.core.dsl.lexer.TokenProvider;
import net.quepierts.animata4j.core.dsl.lexer.TokenType;
import net.quepierts.animata4j.core.dsl.source.SourcePos;
import net.quepierts.animata4j.core.dsl.source.SourceSpan;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProgramParser extends Parser {
    private final ExpressionParser expressionParser;

    private final Set<String> types;

    public ProgramParser(@NotNull TokenProvider lexer) {
        super(lexer);
        this.expressionParser = new ExpressionParser(this);
        this.types = new HashSet<>();
    }

    @Override
    public CompilationUnit parse() {
        return this.parseCompilationUnit();
    }

    /*
    <program> ::= <compilation-unit>
    <compilation-unit> ::= <top-declaration>* EOF
     */
    private CompilationUnit parseCompilationUnit() {

        final List<Declaration> declarations = new ArrayList<>();
        final Token first = this.getCurrent();
        Token current = first;

        while (hasNext() && current.getType() != TokenType.EOF) {
            parseTopDeclaration(declarations);
            current = this.getCurrent();
        }

        return new CompilationUnit(
                SourceSpan.of(first.getSpan(), current.getSpan()),
                declarations
        );
    }

    /*
    <top-declaration> ::=  <storage-declaration>
        | <struct-declaration>
        | <declaration>
        | SEMICOLON
     */
    private void parseTopDeclaration(final List<Declaration> out) {
        Token current = this.getCurrent();

        switch (current.getType()) {
            case KEYWORD_STRUCT:
                parseStructDeclaration(out);
                break;
            case KEYWORD_LAYOUT:
            case KEYWORD_UNIFORM:
            case KEYWORD_IN:
            case KEYWORD_OUT:
                parseStorageDeclaration(out);
                break;

            default:
                parseFunctionOrVariableDeclaration(out);
                break;
        }

        while (this.match(TokenType.SEMICOLON)); // skip semicolons
    }

    /*
    <struct-declaration> ::= STRUCT IDENTIFIER <members> <variable-declarator>? SEMICOLON
     */
    private void parseStructDeclaration(final List<Declaration> out) {
        final SourceSpan begin = this.getCurrent().getSpan();
        this.consume(TokenType.KEYWORD_STRUCT);

        final Token identifier = this.expect(TokenType.IDENTIFIER);
        final String typename = identifier.getValue();

        this.types.add(typename);

        final List<MemberDecl> members = parseMembers();

        out.add(new StructDecl(
                SourceSpan.of(begin, this.getCurrent().getSpan()),
                typename,
                members
        ));

        // if it has any field declared, add it to out
        if (this.is(TokenType.IDENTIFIER)) {
            this.parseVariableDeclarations(out, false, typename);
        }

        this.consume(TokenType.SEMICOLON);
    }

    /*
    <storage-declaration> ::= <layout>? [<interface-declaration> | <resource-declaration>] SEMICOLON

     */
    private void parseStorageDeclaration(final List<Declaration> out) {
        final SourceSpan begin = this.getCurrent().getSpan();
        LayoutQualifier layout = this.is(TokenType.KEYWORD_LAYOUT) ? this.parseLayout() : null;

        switch (this.getCurrent().getType()) {
            case KEYWORD_IN:
            case KEYWORD_OUT:
                parseInterfaceDeclaration(out, begin, layout);
                break;
            case KEYWORD_UNIFORM:
                parseResourceDeclaration(out, begin, layout);
                break;
        }

        this.consume(TokenType.SEMICOLON);
    }

    /*
    <interface-declaration> ::= <interface-identifier> IDENTIFIER ( <declarator> | <members> IDENTIFIER )?
     */
    private void parseInterfaceDeclaration(
            final List<Declaration> out,
            final SourceSpan begin,
            final LayoutQualifier layout
    ) {
        final InterfaceQualifier qualifier = InterfaceQualifier.of(this.getCurrent().getType());
        if (qualifier != InterfaceQualifier.IN && qualifier != InterfaceQualifier.OUT) {
            this.error("Invalid interface qualifier");
        }
        this.advance();

        final Token current = this.getCurrent();
        this.advance();

        if (this.is(TokenType.LBRACE)) {
            if (current.getType().isPrimitiveType()) {
                this.error("Invalid interface qualifier");
            }

            List<MemberDecl> members = this.parseMembers();
            final Token name = this.expect(TokenType.IDENTIFIER);
            out.add(BlockDecl.intf(
                    SourceSpan.of(begin, this.getCurrent().getSpan()),
                    layout,
                    qualifier,
                    current.getValue(),
                    members,
                    name.getValue()
            ));
        } else {
            VariableDeclarator declarator = this.parseDeclarator();
            out.add(VariableDecl.intf(
                    SourceSpan.of(begin, this.getCurrent().getSpan()),
                    layout,
                    qualifier,
                    Variable.of(current.getValue(), declarator)
            ));
        }
    }

    /*
    <resource-declaration> ::= <resource-identifier> IDENTIFIER ( <declarator> | <members> )?
     */
    private void parseResourceDeclaration(
            final List<Declaration> out,
            final SourceSpan begin,
            final LayoutQualifier layout
    ) {
        final InterfaceQualifier qualifier = InterfaceQualifier.of(this.getCurrent().getType());
        if (qualifier != InterfaceQualifier.UNIFORM) {
            this.error("Invalid resource qualifier");
        }
        this.advance();

        final Token current = this.getCurrent();
        this.advance();

        if (this.is(TokenType.LBRACE)) {
            if (current.getType().isPrimitiveType()) {
                this.error("Invalid resource type");
            }

            List<MemberDecl> members = this.parseMembers();
            out.add(BlockDecl.storage(
                    SourceSpan.of(begin, this.getCurrent().getSpan()),
                    layout,
                    qualifier,
                    current.getValue(),
                    members
            ));
        } else if (this.isDatatype(current)) {
            VariableDeclarator declarator = this.parseDeclarator();
            out.add(VariableDecl.intf(
                    SourceSpan.of(begin, this.getCurrent().getSpan()),
                    layout,
                    qualifier,
                    Variable.of(current.getValue(), declarator)
            ));
        }
    }

    /*
    <layout> ::= LAYOUT LPAREN <layout expr> ( COMMA <layout-expr> )* RPAREN
    <layout-expr> ::= IDENTIFIER ( EQUAL <integer> )?
     */
    private LayoutQualifier parseLayout() {
        this.consume(TokenType.KEYWORD_LAYOUT);
        this.consume(TokenType.LPAREN);

        final Object2IntMap<String> qualifiers = new Object2IntOpenHashMap<>();
        while (!this.is(TokenType.RPAREN)) {
            final Token identifier = this.expect(TokenType.IDENTIFIER);

            if (this.match(TokenType.EQUAL)) {
                final Token value = this.expect(TokenType.LITERAL_INTEGER);
                qualifiers.put(identifier.getValue(), Integer.parseInt(value.getValue()));
            } else {
                qualifiers.put(identifier.getValue(), 1);
            }

            if (!this.match(TokenType.COMMA)) {
                break;
            }
        }

        this.consume(TokenType.RPAREN);
        return new LayoutQualifier(qualifiers);
    }

    /*
    <declaration> ::= CONST? <typename> <declaration-rest>
    <declaration-rest> ::= <function-declarator>
        | <variable-declarator> SEMICOLON
    <function-declarator> ::= IDENTIFIER LPAREN <parameters>? RPAREN [ SEMICOLON | <statements> ]
    <variable-declarator> ::= <variable-declaration> ( COMMA <variable-declaration> )*
    <variable-declaration> ::= <declarator> ( EQUAL <expression> )?
     */
    private void parseFunctionOrVariableDeclaration(final List<Declaration> out) {
        final SourceSpan begin = this.getCurrent().getSpan();

        final boolean isConst = this.match(TokenType.KEYWORD_CONST);
        final String typename = this.parseTypename();
        final String name = this.expect(TokenType.IDENTIFIER).getValue();

        if (this.is(TokenType.LPAREN)) { // function
            out.add(this.parseFunction(begin, typename, name));
        } else {

            IntList dimensions = this.match(TokenType.LBRACKET) ? this.parseArraySubfix() : new IntArrayList();
            Expression init = this.match(TokenType.EQUAL) ? this.parseExpression() : Expression.empty();

            out.add(VariableDecl.variable(
                    SourceSpan.of(begin, this.getCurrent().getSpan()),
                    Variable.of(name, typename, dimensions),
                    init,
                    isConst
            ));

            if (this.match(TokenType.COMMA)) {
                this.parseVariableDeclarations(out, isConst, typename);
            }

            this.consume(TokenType.SEMICOLON);
        }

    }

    /*
    <function-declarator> ::= IDENTIFIER LPAREN <parameters>? RPAREN [ SEMICOLON | <statements> ]
                                            ^ here
    <parameters> ::= <parameter> ( COMMA <parameter> )*
    <parameter> ::= <qualifier>? <typename> <declarator>
    <qualifier> ::= IN | OUT | INOUT
     */
    private Declaration parseFunction(
            final SourceSpan begin,
            final String returnType,
            final String name
    ) {
        this.consume(TokenType.LPAREN);

        List<ParameterDecl> parameters = new ArrayList<>();
        while (!this.is(TokenType.RPAREN)) {
            // parse parameter decl

            InterfaceQualifier qualifier = InterfaceQualifier.NONE;
            final TokenType type = this.getCurrent().getType();

            switch (type) {
                case KEYWORD_IN: {
                    qualifier = InterfaceQualifier.IN;
                    break;
                }
                case KEYWORD_OUT: {
                    qualifier = InterfaceQualifier.OUT;
                    break;
                }
                case KEYWORD_INOUT: {
                    qualifier = InterfaceQualifier.INOUT;
                    break;
                }
                default: {
                    this.error("Expected 'IN', 'OUT' or 'INOUT' but accepted " + this.getCurrent().getType());
                }
            }

            this.advance();

            Token identifier = this.expect(this::isDatatype, "Invalid datatype");
            VariableDeclarator declarator = this.parseDeclarator();

            parameters.add(new ParameterDecl(
                    SourceSpan.of(begin, identifier.getSpan()),
                    qualifier,
                    Variable.of(returnType, declarator)
            ));

            if (!this.match(TokenType.COMMA)) {
                break;
            }
        }

        this.consume(TokenType.RPAREN);
        final SourceSpan semicolon = this.getCurrent().getSpan();
        if (this.match(TokenType.SEMICOLON)) {
            return new FunctionDecl(
                    SourceSpan.of(begin, semicolon),
                    name,
                    parameters
            );
        }

        BlockStmt body = this.parseStatements();
        return new FunctionDef(
                SourceSpan.of(begin, body.getSpan()),
                name,
                parameters,
                body
        );
    }

    /*
    <declarator> ::= IDENTIFIER <array-suffix>?
    <variable-declarator> ::= <variable-declaration> ( COMMA <variable-declaration> )*
     */
    private void parseVariableDeclarations(
            final List<Declaration> out,
            final boolean isConst,
            final String type
    ) {

        do {
            final SourcePos left = this.getCurrent().getSpan().getEnd();
            VariableDeclarator declarator = this.parseDeclarator();
            Expression expression = null;

            if (this.match(TokenType.EQUAL)) {
                expression = this.expressionParser.parse();
            }

            final SourcePos right = this.getCurrent().getSpan().getBegin();

            out.add(VariableDecl.variable(
                    SourceSpan.of(left, right),
                    Variable.of(type, declarator),
                    expression,
                    isConst
            ));
        } while (this.match(TokenType.COMMA));
    }

    /*
    <members> ::= LBRACE <member> <member>* RBRACE
    <member> ::= <typename> <declarator> SEMICOLON
     */
    private List<MemberDecl> parseMembers() {
        this.consume(TokenType.LBRACE);
        final List<MemberDecl> members = new ArrayList<>();

        do {
            final SourcePos left = this.getCurrent().getSpan().getEnd();
            final String typename = this.parseTypename();
            final VariableDeclarator declarator = this.parseDeclarator();
            final SourcePos right = this.getCurrent().getSpan().getBegin();
            members.add(new MemberDecl(
                    SourceSpan.of(left, right),
                    Variable.of(typename, declarator)
            ));
            this.expect(TokenType.SEMICOLON);
        } while (!this.match(TokenType.RBRACE));

        return members;
    }

    private String parseTypename() {
        Token current = this.getCurrent();
        TokenType type = current.getType();

        if (type.isPrimitiveType() || type == TokenType.IDENTIFIER) {
            this.advance();
            return current.getValue();
        }

        this.error("Unexpected token " + type);
        return ""; // unreachable
    }

    /*
    <declarator> ::= IDENTIFIER <array-suffix>?
     */
    private VariableDeclarator parseDeclarator() {
        final Token identifier = this.expect(TokenType.IDENTIFIER);
        final String name = identifier.getValue();

        if (!this.match(TokenType.LBRACKET)) {
            return VariableDeclarator.of(name);
        }

        final IntList dimensions = this.parseArraySubfix();

        return VariableDeclarator.of(name, dimensions);
    }

    /*
    <array-suffix> ::= LBRACKET LITERAL_INTEGER? RBRACKET ( LBRACKET LITERAL_INTEGER? RBRACKET )*
     */
    private IntList parseArraySubfix() {
        IntList dimensions = new IntArrayList();

        do {
            final Token current = this.expect(TokenType.LITERAL_INTEGER);
            dimensions.add(Integer.parseInt(current.getValue()));
            this.consume(TokenType.RBRACKET);
        } while (this.match(TokenType.LBRACKET));

        return dimensions;
    }

    /*
    <statements> ::= LBRACE <statement>* RBRACE
     */
    private BlockStmt parseStatements() {
        final SourceSpan begin = this.getCurrent().getSpan();

        this.consume(TokenType.LBRACE);

        List<Statement> statements = new ArrayList<>();
        while (!this.is(TokenType.RBRACE)) {
            if (this.match(TokenType.SEMICOLON)) continue;
            this.parseStatement(statements);
        }

        this.consume(TokenType.RBRACE);

        return new BlockStmt(
                SourceSpan.of(begin, this.getCurrent().getSpan()),
                statements
        );
    }

    /*
    <statement> ::= <if> | <for>
        | <while> | <return>
        | <break> | <continue>
        | <statements>
        | SEMICOLON
        | <var-decl>
        | <expression>
     */
    private void parseStatement(final List<Statement> out) {
        final Token current = this.getCurrent();
        switch (current.getType()) {
            case KEYWORD_IF: {
                out.add(this.parseIfStatement());
                break;
            }
            case KEYWORD_FOR: {
                out.add(this.parseForStatement());
                break;
            }
            case KEYWORD_WHILE: {
                this.error("Unsupported keyword", current);
                break;
            }
            case KEYWORD_RETURN: {
                out.add(this.parseReturnStatement());
                break;
            }
            case KEYWORD_BREAK: {
                this.advance();
                out.add(new BreakStmt(
                        SourceSpan.of(current.getSpan(), current.getSpan())
                ));
                this.expect(TokenType.SEMICOLON);
                break;
            }
            case KEYWORD_CONTINUE: {
                this.advance();
                out.add(new ContinueStmt(
                        SourceSpan.of(current.getSpan(), current.getSpan())
                ));
                this.expect(TokenType.SEMICOLON);
                break;
            }
            case LBRACE: {  // <statements>
                out.add(this.parseStatements());
                break;
            }
            case KEYWORD_CONST: { // var-decl
                this.advance();
                this.parseVariableDeclarationStatement(out, true);
                break;
            }
            default:{
                if (this.isDatatype(current)) { // var-decl
                    this.parseVariableDeclarationStatement(out, false);
                    return;
                }

                Expression expression = this.parseExpression();
                out.add(new ExpressionStmt(
                        expression.getSpan(),
                        expression
                ));
                this.expect(TokenType.SEMICOLON);
            }
        }
    }

    private Statement parseStatement() {
        List<Statement> single = new ArrayList<>();
        this.parseStatement(single);

        if (single.size() != 1) {
            throw new RuntimeException("Unexpected statement");
        }

        return single.get(0);
    }

    /*
    <if> ::= IF LPAREN <expression> RPAREN <statement> ( ELSE <statement> )?
     */
    private Statement parseIfStatement() {
        this.consume(TokenType.KEYWORD_IF);
        this.consume(TokenType.LPAREN);

        Expression condition = this.parseExpression();

        this.consume(TokenType.RPAREN);

        Statement thenStatement = this.parseStatement();
        Statement elseStatement = null;

        if (this.match(TokenType.KEYWORD_ELSE)) {
            elseStatement = this.parseStatement();
        }

        return new IfStmt(
                SourceSpan.of(condition.getSpan().getBegin(), elseStatement != null ? elseStatement.getSpan().getEnd() : thenStatement.getSpan().getEnd()),
                condition,
                thenStatement,
                elseStatement
        );
    }

    /*
    <for> ::= FOR LPAREN <statement> SEMICOLON <expression> SEMICOLON <expression> RPAREN <statement>
    */
    private Statement parseForStatement() {
        final SourceSpan begin = this.getCurrent().getSpan();

        this.consume(TokenType.KEYWORD_FOR);
        this.consume(TokenType.LPAREN);


        final List<Statement> declarations = new ArrayList<>();
        final SourcePos left = this.getCurrent().getSpan().getBegin();
        this.parseVariableDeclarationStatement(declarations, false);
        final SourcePos right = this.getCurrent().getSpan().getBegin();
        Statement init = new BlockStmt(
                SourceSpan.of(left, right),
                declarations
        );

        this.consume(TokenType.SEMICOLON);

        Expression condition = this.parseExpression();
        this.consume(TokenType.SEMICOLON);

        Expression update = this.parseExpression();

        this.consume(TokenType.RPAREN);

        Statement statement = this.parseStatement();
        return new ForStmt(
                SourceSpan.of(begin.getBegin(), this.getCurrent().getSpan().getBegin()),
                init,
                condition,
                update,
                statement
        );
    }

    /*
    <return> ::= RETURN <expression>? SEMICOLON
     */
    private Statement parseReturnStatement() {
        final SourceSpan begin = this.getCurrent().getSpan();
        this.consume(TokenType.KEYWORD_RETURN);

        Expression expr = Expression.empty();
        if (!this.is(TokenType.SEMICOLON)) {
            expr = this.parseExpression();
        }
        this.consume(TokenType.SEMICOLON);

        return new ReturnStmt(
                SourceSpan.of(begin, this.getCurrent().getSpan()),
                expr
        );
    }

    /*
    <var-decl> ::= CONST? <typename> <variable-declarator>? SEMICOLON
     */
    private void parseVariableDeclarationStatement(
            final List<Statement> out,
            final boolean isConst
    ) {
        final String type = this.parseTypename();
        List<Declaration> declarations = new ArrayList<>();
        this.parseVariableDeclarations(declarations, isConst, type);

        for (Declaration declaration : declarations) {
            out.add(new VariableDeclarationStmt(
                    declaration.getSpan(),
                    (VariableDecl) declaration
            ));
        }

        this.consume(TokenType.SEMICOLON);
    }

    private boolean isDatatype(Token token) {
        final TokenType type = token.getType();
        return type.isPrimitiveType() || types.contains(token.getValue());
    }

    private Expression parseExpression() {
        return this.expressionParser.parse();
    }
}
