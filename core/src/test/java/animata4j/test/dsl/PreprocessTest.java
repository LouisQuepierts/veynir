package animata4j.test.dsl;

import net.quepierts.animata4j.dsl.preprocess.Preprocessor;
import net.quepierts.animata4j.dsl.source.SourceProvider;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;

public class PreprocessTest {

    /*@Test
    public void test1() {
        final String url = "src/test/resources/preprocess_test_source_1.glsl";
        final String out = "src/test/resources/preprocess_test_processed_1.glsl";
        final File file = new File(url).getAbsoluteFile();

        Preprocessor preprocessor = Preprocessor.of(file);
        SourceProvider source = preprocessor.process();

        try (FileWriter writer = new FileWriter(out)) {
            int line = 0;
            while (!source.isEof(line)) {
                writer.write(source.getLine(line));
                writer.write('\n');
                line++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

}
