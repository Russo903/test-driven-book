public class Template {

    private String name;

    public Template(String stringTemplate) {
    }

    public void set(String variable, String value) {
        this.name = value;
    }

    public String evaluate() {
        return "Hello, " + name;
    }
}
