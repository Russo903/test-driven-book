import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Template {
    
    private HashMap<String, String> variableMap;
    private String stringTemplate;
    
    public Template(String stringTemplate) {
        this.variableMap = new HashMap<>();
        this.stringTemplate = stringTemplate;
    }
    
    public void set(String variable, String value) {
        variableMap.put(variable, value);
    }
    
    public String evaluate() {
        TemplateParser parser = new TemplateParser();
        List<Segment> segments = parser.parseSegments(stringTemplate);
        return concatenate(segments);
    }
    
    private String concatenate(List<Segment> segments) {
        StringBuilder sb = new StringBuilder();
        for (Segment segment : segments) {
            sb.append(segment.evaluate(variableMap));
        }
        return sb.toString();
    }
}
