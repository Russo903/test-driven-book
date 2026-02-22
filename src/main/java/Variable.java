import java.util.Map;

public class Variable implements Segment {
    private String name;
    
    public Variable(String name) {
        this.name = name;
    }
    
    public String evaluate(Map<String, String> variables) {
        if (!variables.containsKey(name)) {
            throw new MissingValueException("No value found for ${" + name + "}");
        }
        return variables.get(name);
    }
    
    @Override
    public boolean equals(Object obj) {
        return this.name.equals(((Variable) obj).name);
    }
    
    @Override
    public String toString() {
        return name;
    }
}
