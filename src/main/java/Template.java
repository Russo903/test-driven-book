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
        List<String> segments = parser.parse(stringTemplate);
        return concatenate(segments);
    }
    
    private String concatenate(List<String> segments) {
        StringBuilder sb = new StringBuilder();
        for (String segment : segments) {
            append(segment, sb);
        }
        return sb.toString();
    }
    
    private void append(String segment, StringBuilder sb) {
        if (isVariable(segment)) {
            evaluateVariable(segment, sb);
        } else {
            sb.append(segment);
        }
    }
    
    private void evaluateVariable(String segment, StringBuilder sb) {
        String variable = segment.substring(2, segment.length() - 1);
        if (!variableMap.containsKey(variable)) {
            throw new MissingValueException("No value found for " + segment);
        }
        sb.append(variableMap.get(variable));
    }
    
    public static boolean isVariable(String segment) {
        return segment.startsWith("${") && segment.endsWith("}");
    }
}
