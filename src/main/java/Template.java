import java.util.HashMap;
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
        String result = fillInTemplateWithValues();
        checkForMissingValues(result);

        return result;
    }

    private String fillInTemplateWithValues() {
        String result = stringTemplate;
        for (Map.Entry<String, String> entry : variableMap.entrySet()) {
            String regex = "\\$\\{" + entry.getKey() +"\\}";
            result = result.replaceAll(regex, entry.getValue());
        }
        return result;
    }

    private void checkForMissingValues(String result) {
        Pattern pattern = Pattern.compile(".*\\$\\{[^}]*\\}.*");
        Matcher matcher = pattern.matcher(result);

        if (matcher.find()) {
            throw new MissingValueException("No value found for " + matcher.group());
        }
    }
}
