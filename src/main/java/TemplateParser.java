import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateParser {
    
    public TemplateParser() {
    
    }
    
    /**
     * Parses a template string into a {@code List<String>}, splitting it into
     *  alternating segments of plain text and template variables (e.g. {@code ${name}}).
     *
     *  <p>Each iteration of the loop finds the next template variable. We track an
     *  {@code index} that acts as a cursor, marking where the previous match ended.
     *  This lets us extract the plain text between variables by slicing from {@code index}
     *  to where the next variable starts. After each match, {@code index} is advanced
     *  to the end of that variable, so the next iteration picks up from the right place.
     *
     */
    public List<String> parse(String template) {
        List<String> segments = new ArrayList<>();
        int index = breakIntoSegments(segments, template);
        addTail(segments, template, index);
        renderEmptyStringOnEmptyTemplate(segments);
        return segments;
    }
    
    private void renderEmptyStringOnEmptyTemplate(List<String> segments) {
        if (segments.isEmpty()) {
            segments.add("");
        }
    }
    
    private void addTail(List<String> segments, String template, int index) {
        if (index < template.length()) {
            segments.add(template.substring(index));
        }
    }
    
    private int breakIntoSegments(List<String> segments, String template) {
        Pattern pattern = Pattern.compile("\\$\\{[^}]*}");
        Matcher matcher = pattern.matcher(template);
        
        int index = 0;
        // will keep returning true until no more regex matches left
        while (matcher.find()) {
            addPlainText(segments, template, index, matcher);
            addVariable(segments, template, matcher);
            // index will be our starting point on subsequent loops
            index = matcher.end();
        }
        
        return index;
    }
    
    private void addVariable(List<String> segments, String template, Matcher matcher) {
        segments.add(template.substring(matcher.start(), matcher.end()));
    }
    
    // uses the running index to know where to start from
    private void addPlainText(List<String> segments, String template, int index, Matcher matcher) {
        // a check to see if where we are picking up from is a variable
        // if not, add text up until varible, if is, do nothing
        if (index != matcher.start()) {
            segments.add(template.substring(index, matcher.start()));
        }
    }
    
    
    /**
     * use the parse we already have and add a loop
     * to seperate our strings into there own
     * {@code Segment} objects. Also using
     * {@code isVariable()} util from Template
     */
    public List<Segment> parseSegments(String template) {
        List<String> strings = parse(template);
        List<Segment> segments = new ArrayList<>();
        for (String string : strings) {
            if (Template.isVariable(string)) {
                segments.add(new Variable(string.substring(2, string.length() - 1)));
            } else {
                segments.add(new PlainText(string));
            }
        }
        return segments;
    }
}
