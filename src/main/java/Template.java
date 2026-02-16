import java.util.HashMap;
import java.util.Map;

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
        String result = stringTemplate;
        for (Map.Entry<String, String> entry : variableMap.entrySet()) {
            String regex = "\\$\\{" + entry.getKey() +"\\}";
            result = result.replaceAll(regex, entry.getValue());
        }

        return result;
    }
}
