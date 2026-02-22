import java.util.Map;

public class PlainText implements Segment {
    private String text;
    
    public PlainText(String text) {
        this.text = text;
    }
    
    public String evaluate(Map<String, String> segments) {
        return text;
    }
    
    @Override
    public boolean equals(Object obj) {
        return this.text.equals(((PlainText) obj).text);
    }
    @Override
    public String toString() {
        return text;
    }
}
