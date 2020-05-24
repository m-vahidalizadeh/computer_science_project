package algorithms.math;

/**
 * Calculates the greatest common denominator of two integers.
 */

public class GCD {

    private static int gcd(int a, int b) {
        int t;
        while (b != 0) {
            t = a;
            a = b;
            b = t % b;
        }
        return a;
    }

    public static void main(String args[]) {
        System.out.println(gcd(20, 8));
    }

}
