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
    public List<Segment> parse(String template) {
        List<Segment> segments = new ArrayList<>();
        int index = breakIntoSegments(segments, template);
        addTail(segments, template, index);
        renderEmptyStringOnEmptyTemplate(segments);
        return segments;
    }
    
    private void renderEmptyStringOnEmptyTemplate(List<Segment> segments) {
        if (segments.isEmpty()) {
            segments.add(new PlainText(""));
        }
    }
    
    private void addTail(List<Segment> segments, String template, int index) {
        if (index < template.length()) {
            segments.add(new PlainText(template.substring(index)));
        }
    }
    
    private int breakIntoSegments(List<Segment> segments, String template) {
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
    
    private void addVariable(List<Segment> segments, String template, Matcher matcher) {
        segments.add(new Variable(template.substring(matcher.start() + 2, matcher.end() - 1)));
    }
    
    // uses the running index to know where to start from
    private void addPlainText(List<Segment> segments, String template, int index, Matcher matcher) {
        // a check to see if where we are picking up from is a variable
        // if not, add text up until varible, if is, do nothing
        if (index != matcher.start()) {
            segments.add(new PlainText(template.substring(index, matcher.start())));
        }
    }
}
