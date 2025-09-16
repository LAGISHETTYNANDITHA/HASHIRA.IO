import org.json.JSONObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class PolynomialConstantCalculator {
    // Converts a string in 'base' to integer
    public static int parseValue(String value, int base) {
        return Integer.parseInt(value, base);
    }

    public static void main(String[] args) throws IOException {
        String jsonStr = new String(Files.readAllBytes(Paths.get("input.json")));
        JSONObject obj = new JSONObject(jsonStr);

        int n = obj.getJSONObject("keys").getInt("n"); // number provided
        int k = obj.getJSONObject("keys").getInt("k"); // minimum required ( = m + 1 )

        List<Double> xList = new ArrayList<>();
        List<Double> yList = new ArrayList<>();

        for (int i = 1; i <= k; i++) {
            String idx = Integer.toString(i);
            if (!obj.has(idx))
                continue;
            JSONObject root = obj.getJSONObject(idx);
            int base = Integer.parseInt(root.getString("base"));
            double value = parseValue(root.getString("value"), base);
            // Using index as x-value, adjust if needed
            xList.add((double) i);
            yList.add(value);
        }

        // Lagrange interpolation at x=0 for constant term c
        double c = 0.0;
        int numRoots = xList.size();
        for (int i = 0; i < numRoots; i++) {
            double term = yList.get(i);
            for (int j = 0; j < numRoots; j++) {
                if (j == i)
                    continue;
                term *= (0.0 - xList.get(j)) / (xList.get(i) - xList.get(j));
            }
            c += term;
        }
        System.out.println("Polynomial constant c is: " + c);
    }
}