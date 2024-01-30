import java.io.*;
import java.util.Scanner;
public class OrthogonalProjection {
    public static void main(String[] args) throws IOException {
        Scanner sc1 = new Scanner(System.in);
        System.out.println("Enter function:");
        String s = sc1.nextLine();
        Func f = new Func(processInput(s));

        System.out.print("Enter degree: ");
        int deg = sc1.nextInt();

        Scanner sc2 = new Scanner(System.in);
        System.out.println("Enter interval start:");
        String start = processInput(sc2.nextLine());
        System.out.println("Enter interval end:");
        String end = processInput(sc2.nextLine());

        System.out.println("Finding approximation...");
        Polynomial approx = approximate(f, deg, new Interval(start, end));
        approx.infoToFile();
    }

    public static Polynomial approximate(Func f, int deg, Interval I) throws IOException {
        // find orthonormal basis
        Polynomial[] orthBasis = new Polynomial[deg + 1];
        for(int i = 0; i <= deg; i++) {
            Polynomial ogBasisPolynomial = new Polynomial(i == 0 ? "1" : (i == 1 ? "x" : "x**" + i));

            Polynomial numeratorPolynomial = new Polynomial(ogBasisPolynomial.getValue());
            for(int j = i - 1; j >= 0; j--) {
                Func IP = innerProduct(ogBasisPolynomial, orthBasis[j], I);
                Func term = FunctionUtil.scMul(IP, orthBasis[j]);
                numeratorPolynomial = new Polynomial(FunctionUtil.sub(numeratorPolynomial, term).getValue());
            }
            orthBasis[i] = new Polynomial(FunctionUtil.scMul(FunctionUtil.div(new Func("1"), norm(numeratorPolynomial, I)), numeratorPolynomial).getValue());
        }

        // calculate orthogonal projection
        Polynomial approx = new Polynomial("0");
        for(Polynomial p : orthBasis) {
            approx = new Polynomial(FunctionUtil.add(approx, FunctionUtil.scMul(innerProduct(f, p, I), p)).getValue());
        }

        return approx;
    }

    public static Func norm(Func f, Interval I) throws IOException {
        return FunctionUtil.sqrt(innerProduct(f, f, I));
    }

    public static Func innerProduct(Func f1, Func f2, Interval I) throws IOException {
        return FunctionUtil.integrate(f1, f2, I);
    }

    public static String processInput(String input) {
        input = input.replaceAll("e\\^\\(([^)]+)\\)|e\\^(\\w+)", "exp($1$2)");
        input = input.replaceAll("(\\w+)\\^([^\\s]+|\\([^)]+\\))", "$1**($2)");
        input = input.replaceAll("\\be(?![\\^\\w])\\b", "E");
        return input;
    }
}