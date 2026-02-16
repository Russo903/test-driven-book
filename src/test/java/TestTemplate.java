import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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

    @Test
    public void variablesGetProcessedJustOnce() {
        template.set("one", "${one}");
        template.set("two", "${three}");
        template.set("three", "${two}");
        assertTemplateEvaluatesTo("${one}, ${three}, ${two}");
    }

    private void assertTemplateEvaluatesTo(String expected) {
        assertEquals(expected, template.evaluate());
    }
}
