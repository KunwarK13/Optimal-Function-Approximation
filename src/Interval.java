public class Interval {
    private final String a;
    private final String b;
    public Interval(String a, String b) {
        this.a = a;
        this.b = b;
    }
    public String start() {
        return a;
    }
    public String end() {
        return b;
    }
}