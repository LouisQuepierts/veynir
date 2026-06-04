package net.quepierts.veynir.dsl.source;

public interface SourceProvider {

    String getLine(int line);

    int getLineNumber(int line);

    char charAt(int line, int col);

    boolean isEof(int line);

    boolean isEol(int line, int col);
}
