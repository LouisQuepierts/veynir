package net.quepierts.veynir.dsl.preprocess;

import it.unimi.dsi.fastutil.booleans.BooleanArrayList;
import it.unimi.dsi.fastutil.booleans.BooleanStack;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.quepierts.veynir.dsl.StringSplitter;
import net.quepierts.veynir.dsl.preprocess.directive.Directive;
import net.quepierts.veynir.dsl.preprocess.directive.DirectiveFactory;
import net.quepierts.veynir.dsl.preprocess.macro.Macro;
import net.quepierts.veynir.dsl.source.SourceProvider;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.*;

public class Preprocessor {

    public static Preprocessor of(@NotNull String source) {
        return new Preprocessor(new StringReader(source));
    }

    public static Preprocessor of(@NotNull File file) {
        try (final FileReader fileReader = new FileReader(file)) {
            return new Preprocessor(fileReader);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Preprocessor of(@NotNull Reader reader) {
        return new Preprocessor(reader);
    }

    private final Reader reader;
    private final List<LineInfo> lines;

    private final List<Directive> directives;
    private final List<String> sources;

    Preprocessor(Reader reader) {
        this.lines = new ArrayList<>();

        this.sources = new ArrayList<>();
        this.directives = new ArrayList<>();
        this.reader = reader;

        try {
            this.scan();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void scan() throws IOException {
        StringBuilder currentLine = new StringBuilder();

        int lineNumber = 1;

        boolean inLineComment = false;
        boolean inBlockComment = false;
        boolean isEmptyLine = true;

        boolean inDirective = false;

        int last, current = 0;
        int read;
        while ((read = this.reader.read()) != -1) {
            last = current;
            current = read;

            if (current == '\n') {
                int currentLineNumber = lineNumber;
                inLineComment = false;
                lineNumber++;
                if (inDirective && last == '\\') {
                    currentLine.append(' ');
                    continue;
                }

                if (!isEmptyLine) {
                    if (inDirective) {
                        StringSplitter splitter = StringSplitter.of(currentLine);
                        int mapping = this.directives.size();
                        this.lines.add(new LineInfo(currentLineNumber, mapping, true));
                        this.directives.add(DirectiveFactory.of(splitter));
                        inDirective = false;
                    } else {
                        String line = currentLine.toString();
                        int mapping = this.sources.size();
                        this.lines.add(new LineInfo(currentLineNumber, mapping, false));
                        this.sources.add(line);
                    }
                }
                currentLine.setLength(0);
                isEmptyLine = true;
                continue;
            }

            if (inBlockComment) {
                if (last == '*' && current == '/') {
                    inBlockComment = false;
                }
                continue;
            }

            if (inLineComment) {
                continue;
            }

            if (last == '/') {
                int len = currentLine.length() - 1;
                if (current == '/') {
                    inLineComment = true;
                    // remove last char in builder
                    currentLine.setLength(len);
                    if (len == 0 || currentLine.charAt(len - 1) == ' ') {
                        isEmptyLine = true;
                    }
                    continue;
                } else if (current == '*') {
                    inBlockComment = true;
                    // remove last char in builder
                    currentLine.setLength(len);
                    if (len == 0 || currentLine.charAt(len - 1) == ' ') {
                        isEmptyLine = true;
                    }
                    continue;
                }
            }

            boolean space = Character.isWhitespace(current);
            if (isEmptyLine && !space) {
                isEmptyLine = false;

                if (current == '#') {
                    inDirective = true;
                }
            }
            currentLine.append(space ? ' ' : (char) current);
        }
    }

    public SourceProvider process() {
        PreprocessingSource source = new PreprocessingSource();
        PreprocessLexer lexer = new PreprocessLexer(source);

        Context context = new Context();

        Iterator<String> sourceIterator = this.sources.iterator();
        Iterator<Directive> directiveIterator = this.directives.iterator();

        for (LineInfo lineInfo : this.lines) {
            if (lineInfo.isDirective()) {
                Directive directive = directiveIterator.next();

                if (directive.igConditionIgnorable() || context.peekCondition()) {
                    directive.handle(context);
                }
            } else {
                String line = sourceIterator.next();
                if (context.peekCondition()) {
                    source.addLine(line, lineInfo.lineNumber);
                }
            }
        }

        return source.toProcessesSource();
    }

    private static final class Context implements PreprocessContext {

        private final Map<String, Macro> defines = new HashMap<>();
        private final BooleanStack conditionStack = new BooleanArrayList();

        @Override
        public void define(@NotNull String macro) {
            this.defines.put(macro, Macro.empty());
        }

        @Override
        public void define(@NotNull String macro, @NotNull Macro value) {
            this.defines.put(macro, value);
        }

        @Override
        public void undefine(@NotNull String macro) {
            this.defines.remove(macro);
        }

        @Override
        public boolean isDefined(@NotNull String macro) {
            return this.defines.containsKey(macro);
        }

        @Override
        public Macro getDefined(@NotNull String macro) {
            return this.defines.get(macro);
        }

        @Override
        public void pushCondition(boolean condition) {
            this.conditionStack.push(condition);
        }

        @Override
        public boolean popCondition() {
            return this.conditionStack.popBoolean();
        }

        @Override
        public boolean peekCondition() {
            return this.conditionStack.isEmpty() || this.conditionStack.topBoolean();
        }
    }

    @Getter
    @RequiredArgsConstructor
    private static final class LineInfo {
        private final int lineNumber;
        private final int mappingIndex;
        private final boolean isDirective;
    }
}
