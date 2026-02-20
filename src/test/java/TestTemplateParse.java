import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestTemplateParse {
    
    @Test
    public void emptyTemplateRendersEmptyString() {
        List<String> segments = parse("");
        assertSegments(segments, "");
    }
    
    @Test
    public void plainTextRendersPlainText() {
        List<String> segments = parse("Plain text");
        assertSegments(segments, "Plain text");
    }
    
    @Test
    public void parseMultipleVariables() {
        List<String> segments = parse("${a}:${b}:${c}");
        assertSegments(segments, "${a}", ":", "${b}", ":", "${c}");
    }
    
    private List<String> parse(String template) {
        return new TemplateParser().parse(template);
    }
    
    
    /**
     * In our tests we can asserting these two things for the parse
     *
     * <pre>{@code
     * assertEquals("Amount of segments", 1, segments.size());
     * assertEquals("Plain Text String produced", "Plain text", segments.getFirst());
     * }</pre>
     *
     * We can extract this into its own method to make this simple and make
     * both assertions for us since they are related
     * <p>
     * Using varargs allows us to pass in multiple values and they will get collected into
     * an array for us.
     */
    private void assertSegments(List<?> actual, Object... expected) {
        assertEquals("Amount of segments", expected.length, actual.size());
        assertEquals("compare contents", List.of(expected), actual);
    }
}
