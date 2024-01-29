import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FunctionUtil {
    final static String[] baseArgs = {"./py_env/bin/python3.11", "./src/command.py"};

    public static Func sqrt(Func x) throws IOException {
        return new Func(runCmd("sqrt", x.getValue()));
    }

    public static String coeffs(Func p) throws IOException {
        return runCmd("coeffs", p.getValue());
    }

    public static int degree(Func p) throws IOException {
        return Integer.parseInt(runCmd("deg", p.getValue()));
    }

    public static Func simplify(Func p) throws IOException {
        return new Func(runCmd("simplify", p.getValue()));
    }

    public static Func add(Func p1, Func p2) throws IOException {
        return new Func(runCmd("add", p1.getValue(), p2.getValue()));
    }

    public static Func sub(Func p1, Func p2) throws IOException {
        return new Func(runCmd("sub", p1.getValue(), p2.getValue()));
    }

    public static Func div(Func p1, Func p2) throws IOException {
        return new Func(runCmd("div", p1.getValue(), p2.getValue()));
    }

    public static Func scMul(Func p1, Func p2) throws IOException {
        return new Func(runCmd("sc_mul", p1.getValue(), p2.getValue()));
    }

    public static Func integrate(Func p1, Func p2, Interval I) throws IOException {
        String s = I.start();
        String e = I.end();
        return new Func(runCmd("int", p1.getValue(), p2.getValue(), s, e));
    }

    private static String runCmd(String... cmd) throws IOException {
        String[] toRun = concatArr(baseArgs, cmd);
        return new BufferedReader(new InputStreamReader((new ProcessBuilder(toRun)).start().getInputStream())).readLine();
    }

    public static String[] concatArr(String[] arr1, String[] arr2) {
        String[] newArr = new String[arr1.length + arr2.length];
        int curr = 0;
        for (String s : arr1) {
            newArr[curr] = s;
            curr++;
        }
        for(String s : arr2) {
            newArr[curr] = s;
            curr++;
        }

        return newArr;
    }
}
