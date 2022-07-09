package algorithms.math;

public class ModInverse {

    public static void main(String[] args) {
        System.out.printf("Mode inverse of %d in mode %d is %d. \n", 3, 11, ModInverse.getModInverse(3, 11));
        System.out.printf("Mode inverse of %d in mode %d is %d. \n", 10, 17, ModInverse.getModInverse(10, 17));
    }

    /**
     * A method that returns the mod inverse of a in a specific mod. This method uses Euclid algorithm:
     * and m are co-primes if GCD(a, m) == 1
     * Time complexity O(logm)
     * Space complexity O(logm)
     *
     * @param a   The input
     * @param mod The mod
     * @return Inverse mod of a in the mod
     */
    public static int getModInverse(int a, int mod) {
        int originalMod = mod, x = 1, y = 0;
        if (mod == 1) return 0;
        while (a > 1) { // Euclid algorithm: a and m are co-primes if gcd(a,m)==1
            int quotient = a / mod;
            int temp = mod;
            mod = a % mod;
            a = temp;
            temp = y;
            y = x - quotient * y;
            x = temp;
        }
        return x < 0 ? x + originalMod : x; // Make x positive if it is negative
    }

}
