import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestTemplate {

    @Test
    public void oneVariable() throws Exception {
        Template template = new Template("Hello, ${name}");
        template.set("name", "Gino");
        assertEquals("Hello, Gino", template.evaluate());
    }

    @Test
    public void differentVariable() throws Exception {
        Template template = new Template("Hello, ${name}");
        template.set("name", "Reader");
        assertEquals("Hello, Reader", template.evaluate());
    }
}
