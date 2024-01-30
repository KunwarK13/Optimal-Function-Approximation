import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class Polynomial extends Func{
    public Polynomial(String s) {
        super(s);
    }

    public void infoToFile() {
        try {
            FileWriter fw = new FileWriter("output.txt");
            fw.write("Coefficient Array:\n" + Arrays.toString(getCoefficients()));
            fw.write("\n\n");
            fw.write("Polynomial:\n" + getExpandedForm());
            fw.write("\n\n");
            fw.write("Desmos:\n" + getDesmosForm());
            fw.close();
            System.out.println("Successfully wrote to file.");
        } catch (IOException e) {
            System.out.println("Error occurred");
        }
    }

    public String[] getCoefficients() throws IOException {
        String coeffString = FunctionUtil.coeffs(this);
        String[] coeffs = coeffString.substring(1, coeffString.length() - 1).split(", ");
        for(int i = 0; i < coeffs.length; i++) {
            coeffs[i] = FunctionUtil.simplify(new Func(coeffs[i])).getValue();
            coeffs[i] = coeffs[i].replaceAll("\\*\\*", "^");
            coeffs[i] = coeffs[i].replaceAll("exp\\(([^)]+)\\)", "e^($1)");
            coeffs[i] = coeffs[i].replaceAll("E", "e");
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
        s = s.replaceAll("sqrt\\(([^)]*)\\)", "\\\\sqrt{$1}");
        s = s.replaceAll("\\*", "\\\\cdot ");
        return s;
    }
}
