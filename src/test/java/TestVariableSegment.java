import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TestVariableSegment {

    @Test
    public void variableSegmentRendersAsVariableValue() {
        Map<String, String> variables = new HashMap<>();
        String name = "myVar";
        String value = "myVal";
        variables.put(name, value);
        assertEquals(value, new Variable(name).evaluate(variables));
        
    }

}
