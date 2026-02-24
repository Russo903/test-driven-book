import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestTemplateParse {
    
    @Test
    public void emptyTemplateRendersEmptyString() {
        List<Segment> segments = parse("");
        assertSegments(segments, new PlainText(""));
    }
    
    @Test
    public void plainTextRendersPlainText() {
        List<Segment> segments = parse("Plain text");
        assertSegments(segments, new PlainText("Plain text"));
    }
    
    @Test
    public void parseMultipleVariables() {
        List<Segment> segments = parse("${a}:${b}:${c}");
        assertSegments(segments, new Variable("a"), new PlainText(":"), new Variable("b"), new PlainText(":"), new Variable("c"));
    }
    
    @Test
    public void parsingTemplateIntoSegments() {
        TemplateParser parser = new TemplateParser();
        List<Segment> segments = parser.parse("a ${b} c ${d}");
        assertSegments(segments,
                new PlainText("a "), new Variable("b"),
                new PlainText(" c "), new Variable("d"));
    }
    
    private List<Segment> parse(String template) {
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
