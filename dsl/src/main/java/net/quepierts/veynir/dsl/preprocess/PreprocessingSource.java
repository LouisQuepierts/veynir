package net.quepierts.veynir.dsl.preprocess;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.quepierts.veynir.dsl.source.ProcessesSource;
import net.quepierts.veynir.dsl.source.SourceProvider;

import java.util.ArrayList;
import java.util.List;

public final class PreprocessingSource implements SourceProvider {

    private final List<Line> lines;
    private final IntList lineMapping;

    private int lineCount;

    public PreprocessingSource() {
        this.lines = new ArrayList<>();
        this.lineMapping = new IntArrayList();
        this.lineCount = 0;
    }

    public void addLine(String line, int lineNumber) {
        lines.add(new Line(line));
        lineMapping.add(lineNumber);
        lineCount++;
    }

    // set lineMapping to -1
    public void removeLine(int line) {
        if (line < 0 || line >= lineCount) {
            return;
        }

        int i = lineMapping.getInt(line);
        if (i != -1) {
            lineMapping.set(line, -1);
            lineCount--;
        }
    }

    public void removeMappedLine(int line) {
        if (line < 0 || line >= lineCount) {
            return;
        }

        int i = lineMapping.indexOf(line);
        if (i != -1) {
            lineMapping.set(i, -1);
            lineCount--;
        }
    }

    public ProcessesSource toProcessesSource() {
        String[] lines = new String[this.lineCount];
        int[] lineMapping = new int[this.lineCount];

        for (int i = 0, j = 0; i < lines.length; i++) {
            int mapping = this.lineMapping.getInt(i);
            if (mapping != -1) {
                lines[j] = this.lines.get(i).toString();
                lineMapping[j] = mapping;
                j++;
            }
        }
        return new ProcessesSource(lines, lineMapping);
    }

    public Line modifyLine(int line) {
        return lines.get(line);
    }

    @Override
    public String getLine(int line) {
        return lines.get(line).origin;
    }

    @Override
    public int getLineNumber(int line) {
        return lineMapping.getInt(line);
    }

    @Override
    public char charAt(int line, int col) {
        return lines.get(line).origin.charAt(col);
    }

    @Override
    public boolean isEof(int line) {
        return line >= lines.size();
    }

    @Override
    public boolean isEol(int line, int col) {
        return col >= lines.get(line).origin.length();
    }

    public static final class Line {
        private final String origin;
        private StringBuilder builder;

        private Line(String origin) {
            this.origin = origin;
        }

        public StringBuilder getBuilder() {
            if (builder == null) {
                builder = new StringBuilder(origin);
            }
            return builder;
        }

        @Override
        public String toString() {
            return builder != null ? builder.toString() : origin;
        }
    }
}
