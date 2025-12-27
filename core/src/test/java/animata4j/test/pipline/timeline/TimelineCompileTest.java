package animata4j.test.pipline.timeline;

import net.quepierts.animata4j.core.pipeline.datasource.timeline.CompiledTimeline;
import net.quepierts.animata4j.core.pipeline.datasource.timeline.KeyFrame;
import net.quepierts.animata4j.core.pipeline.datasource.timeline.SourceTimeline;
import org.junit.jupiter.api.Test;

public class TimelineCompileTest {

    @Test
    public void test1() {
        SourceTimeline source = new SourceTimeline(24, 1, new KeyFrame[]{
                KeyFrame.simple(0, 0.0f),
                KeyFrame.simple(6, 1.0f),
                KeyFrame.simple(12, 2.0f),
                KeyFrame.simple(18, 3.0f),
                KeyFrame.simple(24, 0.0f)
        });

        CompiledTimeline compiled = SourceTimeline.compile(source);
        System.out.println(compiled);
    }

}
