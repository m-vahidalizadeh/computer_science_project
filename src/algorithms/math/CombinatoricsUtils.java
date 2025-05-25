package algorithms.math; // Or any other appropriate package

/**
 * A utility class for combinatorial calculations, such as binomial coefficients.
 * This class provides static methods for common mathematical operations related to combinations.
 */
public class CombinatoricsUtils {

    /**
     * A common modulus value used in competitive programming and algorithms
     * to prevent integer overflow and keep results within a manageable range.
     * 10^9 + 7 is a large prime number.
     */
    private static final int MOD = 1_000_000_007;

    // Private constructor to prevent instantiation of this utility class,
    // as all its methods are static.
    private CombinatoricsUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated.");
    }

    /**
     * Generates a table of binomial coefficients C(n, k) or "n choose k" for all n and k
     * from 0 up to a given maximum value N.
     * The result C[i][j] will store the value of "i choose j" modulo MOD.
     *
     * The calculation is based on Pascal's identity: C(n, k) = C(n-1, k-1) + C(n-1, k).
     * Base cases are C(n, 0) = 1 and C(n, n) = 1.
     * If k > n, C(n, k) = 0, which is handled by default array initialization and loop bounds.
     *
     * @param N The maximum value for n and k. The table will be of size (N+1) x (N+1).
     *          N must be non-negative.
     * @return A 2D long array {@code C} where {@code C[n][k]} stores the value of
     *         "n choose k" modulo {@code MOD}.
     * @throws IllegalArgumentException if N is negative.
     */
    public static long[][] generateBinomialCoefficients(int N) {
        // Validate the input N. It must be non-negative.
        if (N < 0) {
            throw new IllegalArgumentException("N cannot be negative for generating binomial coefficients.");
        }

        // Initialize the 2D array to store binomial coefficients.
        // C[i][j] will store "i choose j".
        // The size is (N+1)x(N+1) to accommodate C(N, N).
        long[][] C = new long[N + 1][N + 1];

        // Iterate through each possible value of 'n' (from 0 to N).
        for (int n = 0; n <= N; n++) {
            // Apply base cases for combinations:
            // C(n, 0) = 1 (There's one way to choose 0 items from n: choose nothing).
            C[n][0] = 1;

            // C(n, n) = 1 (There's one way to choose n items from n: choose all of them).
            // This also correctly sets C[0][0] = 1.
            if (n <= N) { // Ensure k (which is n here) is within bounds of C[n][k]
                C[n][n] = 1;
            }

            // Apply Pascal's identity to calculate C(n, k) for 0 < k < n.
            // k represents the number of items to choose.
            for (int k = 1; k < n; k++) {
                // C(n, k) = C(n-1, k-1) + C(n-1, k)
                // All calculations are done modulo MOD to prevent overflow.
                C[n][k] = (C[n - 1][k - 1] + C[n - 1][k]) % MOD;
            }
            // Note: For k > n, C[n][k] is 0. Since Java arrays are initialized to 0 by default,
            // and the inner loop for 'k' only goes up to 'n-1',
            // C[n][k] for k > n will correctly remain 0 if not explicitly set by C[n][n]=1.
        }

        // Return the populated table of binomial coefficients.
        return C;
    }

    /**
     * Main method for example usage and testing.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        int N = 5;
        System.out.println("Generating binomial coefficients C(n, k) for n, k up to " + N + ":");
        long[][] coefficients = generateBinomialCoefficients(N);

        for (int n = 0; n <= N; n++) {
            for (int k = 0; k <= n; k++) { // Only print relevant C(n,k) where k <= n
                System.out.printf("C(%d, %d) = %d\n", n, k, coefficients[n][k]);
            }
            System.out.println("---");
        }

        // Example for a larger N
        int N_large = 20;
        System.out.println("\nGenerating C(n,k) for n,k up to " + N_large + " (showing a few values):");
        long[][] largeCoefficients = generateBinomialCoefficients(N_large);
        System.out.printf("C(10, 3) = %d\n", largeCoefficients[10][3]); // Expected: 120
        System.out.printf("C(20, 10) = %d\n", largeCoefficients[20][10]); // Expected: 184756

        // Test edge case N=0
        System.out.println("\nGenerating C(n,k) for n,k up to 0:");
        long[][] zeroCoefficients = generateBinomialCoefficients(0);
        System.out.printf("C(0, 0) = %d\n", zeroCoefficients[0][0]); // Expected: 1

        // Example of how an invalid input would be handled (will throw an exception)
        // try {
        //     System.out.println("\nAttempting to generate for N = -1:");
        //     generateBinomialCoefficients(-1);
        // } catch (IllegalArgumentException e) {
        //     System.out.println("Caught expected exception: " + e.getMessage());
        // }
    }
}