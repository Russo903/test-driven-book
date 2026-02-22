import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestVariableSegment {
    
    private Map<String, String> variables;
    
    @Before
    public void setUp() {
        variables = new HashMap<>();
    }

    @Test
    public void variableSegmentRendersAsVariableValue() {
        String name = "myVar";
        String value = "myVal";
        variables.put(name, value);
        assertEquals(value, new Variable(name).evaluate(variables));
    }
    
    @Test
    public void variableWithNoMatchShouldThrowException() throws MissingValueException {
        String name = "myVar";
        try {
            new Variable(name).evaluate(variables);
            fail("Exception should be throwing and we should not see this msg :)");
        } catch (MissingValueException e) {
            assertEquals("No value found for ${" + name + "}", e.getMessage());
        }
    }

}
