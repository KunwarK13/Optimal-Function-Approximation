import java.io.IOException;

public class Polynomial extends Func{
    public Polynomial(String s) {
        super(s);
    }

    public String[] getCoefficients() throws IOException {
        String coeffString = FunctionUtil.coeffs(this);
        String[] coeffs = coeffString.substring(1, coeffString.length() - 1).split(", ");
        for(int i = 0; i < coeffs.length; i++) {
            coeffs[i] = FunctionUtil.simplify(new Func(coeffs[i])).getValue();
            coeffs[i] = coeffs[i].replace("**", "^");
            coeffs[i] = coeffs[i].replace("exp", "e^");
        }
        return coeffs;
    }

    public String getExpandedForm() throws IOException {
        int degree = FunctionUtil.degree(this);
        String[] coeffs = getCoefficients();

        StringBuilder ans = new StringBuilder();
        for(int i = degree; i >= 0; i--) {
            if(!(coeffs[degree - i].equals("0"))) {
                ans.append(i == degree ? "" : " + ").append("(").append(coeffs[degree - i]).append(")").append(i == 0 ? "" : (i == 1 ? "x" : "x^" + i));
            }
        }

        return ans.toString();
    }

    public String getDesmosForm() throws IOException {
        String s = getExpandedForm();
        s = s.replaceAll("(\\w+)\\^(\\d+|\\([^)]+\\))", "$1^{$2}");
        s = s.replaceAll("\\bpi\\b", "\\\\pi");
        return s;
    }
}
