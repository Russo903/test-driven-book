import org.junit.Before;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class TestTemplate {
    private Template template;

    @Before
    public void setUp() throws Exception {
        template = new Template("${one}, ${two}, ${three}");
        template.set("one", "1");
        template.set("two", "2");
        template.set("three", "3");
    }

    @Test
    public void multipleVariablesTemplate() throws Exception {
        assertTemplateEvaluatesTo("1, 2, 3");
    }

    @Test
    public void unknownVariablesAreIgnored() throws Exception {
        template.set("doesNotExist", "dev");
        assertTemplateEvaluatesTo("1, 2, 3");
    }

    @Test
    public void throwsExceptionWhenVariableLeft() throws Exception {
        try {
            new Template("${foo}").evaluate();
            fail("evaluate() should throw if template still has variables after calling evaluate");
        } catch (MissingValueException exception) {
            assertEquals("No value found for ${foo}", exception.getMessage());
        }
    }

//    @Test
//    public void variablesGetProcessedJustOnce() {
//        template.set("one", "${one}");
//        template.set("two", "${three}");
//        template.set("three", "${two}");
//        assertTemplateEvaluatesTo("${one}, ${three}, ${two}");
//    }

    private void assertTemplateEvaluatesTo(String expected) {
        assertEquals(expected, template.evaluate());
    }
    
    
    /**
     * Learning Tests - Tests we use to learn and test our assumptions to ensure we understand
     * here we ASSUMED that group count would return the number of matches, but it actually
     * just returns the amount of capturing groups in your matchers pattern
     */
    
    @Test
    public void learningRegexGroupMethod() {
        String message = "something needle about something else needles";
        Pattern pattern = Pattern.compile("(needle)");
        Matcher matcher = pattern.matcher(message);
        assertEquals(1, matcher.groupCount());
    }
    
    @Test
    public void testFindStartandEnd() {
        String message = "something needle about something else needles";
        Pattern pattern = Pattern.compile("(needle)");
        Matcher matcher = pattern.matcher(message);
        assertTrue(matcher.find());
        assertEquals("wrong start index", 10, matcher.start());
        assertEquals("wrong end index", 16, matcher.end());
        assertTrue(matcher.find());
        assertEquals("wrong start index", 38, matcher.start());
        assertEquals("wrong end index", 44, matcher.end());
        
        
    }
}
