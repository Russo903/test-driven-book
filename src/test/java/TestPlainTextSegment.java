import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TestPlainTextSegment {
    
    @Test
    public void plainTextEvaluatesAsIs() {
        Map<String, String> variables = new HashMap<>();
        Segment plainTextSegment = new PlainText("this should render as is");
        assertEquals("this should render as is", plainTextSegment.evaluate(variables));
    }
}
