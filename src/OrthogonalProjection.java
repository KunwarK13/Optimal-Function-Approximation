import java.util.Arrays;
import java.io.*;
import java.util.Scanner;
public class OrthogonalProjection {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        Func f = new Func(s);
        Polynomial approx = approximate(f, 5, new Interval("-1", "1"));
        System.out.println(approx.getValue());
        System.out.println(Arrays.toString(approx.getCoefficients()));
        System.out.println(approx.getExpandedForm());
        System.out.println(approx.getDesmosForm());
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
}