public class Variable implements Segment {
    private String name;
    
    public Variable(String name) {
        this.name = name;
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
