package algorithms.math;

// The import 'java.util.Arrays' was noted as "Added for potential future use, not strictly needed for this version".
// It's good practice to remove unused imports to keep the code clean.
// For this version, it's not used, so it could be removed.
// import java.util.Arrays; // Not strictly needed for this version

/**
 * The {@code Mod} class provides utility methods for mathematical operations
 * that need to be performed under a specific modulus. This is common in
 * problems where results can be very large and only their remainder when
 * divided by a certain number (the modulus) is required.
 *
 * This class is designed with static methods and a static final modulus,
 * making it a utility class that doesn't need to be instantiated.
 */
class Mod {
    /**
     * The modulus value for all arithmetic operations performed by
     * methods in this class.
     * 1,000,000,007 (or 10^9 + 7) is a common prime modulus used in competitive programming
     * because it's large enough to prevent overflow for many intermediate calculations
     * involving standard integer types (like Java's int or long before the final modulo)
     * and being prime allows for modular multiplicative inverse calculations (though not used directly here).
     */
    public static final int MOD = 1_000_000_007;

    /**
     * Precomputes binomial coefficients, also known as "n choose k" or C(n, k),
     * for all n and k up to {@code maxVal}, modulo {@code MOD}.
     * Binomial coefficients represent the number of ways to choose k items from a set of n distinct items.
     * This method uses Pascal's identity: C(n, k) = C(n-1, k-1) + C(n-1, k).
     *
     * @param maxVal The maximum value for 'n' (and consequently 'k', since k <= n)
     *               for which binomial coefficients are needed. The table will be
     *               sized (maxVal + 1) x (maxVal + 1) to store C[0][0] through C[maxVal][maxVal].
     * @return A 2D array {@code C} of type {@code long[][]} where {@code C[i][j]} stores
     *         the value of "i choose j" (C(i, j)) modulo {@code MOD}.
     *         The dimensions of the array are (maxVal + 1) x (maxVal + 1).
     *         Access {@code C[n][k]} for the value of "n choose k".
     */
    public static long[][] precomputeBinomialCoefficients(int maxVal) {
        // Validate input to prevent issues with array sizing or negative values.
        if (maxVal < 0) {
            throw new IllegalArgumentException("maxVal cannot be negative.");
        }

        // Initialize a 2D array to store the binomial coefficients.
        // C[i][j] will store C(i, j).
        long[][] C = new long[maxVal + 1][maxVal + 1];

        // Iterate from n = 0 up to maxVal.
        for (int i = 0; i <= maxVal; i++) {
            // Base case for Pascal's identity: C(i, 0) = 1
            // There's one way to choose 0 items from i items (by choosing nothing).
            C[i][0] = 1;

            // Base case for Pascal's identity: C(i, i) = 1 (for i > 0)
            // There's one way to choose i items from i items (by choosing all of them).
            // C[0][0] is already set to 1 by C[i][0] when i=0.
            if (i > 0) {
                C[i][i] = 1;
            }

            // Calculate C(i, j) for 0 < j < i using Pascal's identity:
            // C(i, j) = C(i-1, j-1) + C(i-1, j)
            // We only need to iterate j from 1 up to i-1 because C(i,0) and C(i,i) are base cases.
            // Also, C(i,j) is 0 if j > i, but our loops naturally handle this as j < i.
            for (int j = 1; j < i; j++) {
                // Sum the two previously computed values and take the result modulo MOD
                // to prevent overflow and keep the numbers within the desired range.
                C[i][j] = (C[i - 1][j - 1] + C[i - 1][j]) % MOD;
            }
        }
        // Return the fully populated table of binomial coefficients.
        return C;
    }

    /**
     * Precomputes powers of numbers in a given array {@code nums} up to a specified
     * {@code maxExponent}, all modulo {@code MOD}.
     * The result {@code powNums[i][c]} will store {@code nums[i]^c % MOD}.
     * This is useful when a number {@code nums[i]} is used multiplicatively 'c' times
     * in a calculation where the final product is needed modulo MOD.
     *
     * @param nums        The input array of base numbers. Cannot be null.
     * @param maxExponent The maximum exponent 'c' for which powers need to be computed.
     *                    Must be non-negative. For each {@code nums[i]}, powers from
     *                    {@code nums[i]^0} to {@code nums[i]^maxExponent} will be calculated.
     * @return A 2D array {@code powNums} of type {@code long[][]} where
     *         {@code powNums[idx][exp]} stores the value of {@code nums[idx]^exp % MOD}.
     *         The dimensions are {@code nums.length} x ({@code maxExponent} + 1).
     * @throws IllegalArgumentException if nums is null or maxExponent is negative.
     */
    public static long[][] precomputePowers(int[] nums, int maxExponent) {
        // Validate inputs
        if (nums == null) {
            throw new IllegalArgumentException("Input array 'nums' cannot be null.");
        }
        if (maxExponent < 0) {
            throw new IllegalArgumentException("maxExponent cannot be negative.");
        }

        // Get the number of elements in the input array.
        int n = nums.length;
        if (n == 0) {
            // Handle empty input array gracefully, returning an empty 2D array.
            return new long[0][0];
        }

        // Initialize a 2D array to store the precomputed powers.
        // powNums[i][c] will store nums[i] raised to the power c.
        long[][] powNums = new long[n][maxExponent + 1];

        // Iterate through each number in the input array 'nums'.
        for (int i = 0; i < n; i++) {
            // Base case for exponentiation: nums[i]^0 = 1.
            // Any number (except 0, though 0^0 is often 1 in this context) to the power 0 is 1.
            powNums[i][0] = 1;

            // Get the current base number.
            // Storing it in a long variable 'base' can be useful if intermediate
            // products before modulo might exceed int limits, though here nums[i] is int.
            // It also allows for potential modification if nums[i] could be negative
            // and needs to be adjusted to be in [0, MOD-1] before multiplication.
            long base = nums[i];

            // If nums[i] could be negative, and the problem requires positive remainders,
            // one might adjust 'base' like this to ensure it's in [0, MOD-1]:
            // base = (nums[i] % MOD + MOD) % MOD;
            // However, for many competitive programming problems,
            // input numbers are non-negative, or the direct modulo behavior on negative numbers
            // is acceptable/defined by the problem. The current code assumes nums[i] is used as is,
            // or is non-negative, or that its direct value is intended for modular arithmetic.
            // If base is negative, (a * negative_base) % MOD might yield a negative result
            // depending on the language's % operator. Java's % can return negative.
            // To ensure a positive result in [0, MOD-1]:
            if (base < 0) {
                base = (base % MOD + MOD) % MOD;
            } else {
                base = base % MOD; // Ensure base is within [0, MOD-1] even if positive but large
            }


            // Calculate powers from nums[i]^1 up to nums[i]^maxExponent.
            // Each power powNums[i][c] is calculated as (powNums[i][c-1] * base) % MOD.
            // This iterative approach is efficient: nums[i]^c = nums[i]^(c-1) * nums[i].
            for (int c = 1; c <= maxExponent; c++) {
                // Multiply the previous power (nums[i]^(c-1)) by the base,
                // and take the result modulo MOD to keep the value manageable.
                // Ensure intermediate multiplication doesn't overflow long before modulo if base is large.
                // (powNums[i][c-1] is already < MOD, base is < MOD, so product < MOD*MOD)
                long product = (powNums[i][c - 1] * base) % MOD;

                // If the product is negative (can happen if one operand was negative after a previous modulo
                // and Java's % operator was used), adjust to be in [0, MOD-1].
                // This step is redundant if 'base' was already adjusted to be non-negative.
                // However, if powNums[i][c-1] could somehow be negative (it shouldn't if logic is correct),
                // this would be a safeguard. Given current logic, powNums[i][c-1] is always >= 0.
                // So, this specific adjustment for 'product' might be overly cautious or unnecessary
                // if 'base' is guaranteed non-negative and within [0, MOD-1].
                // For simplicity and robustness if base handling was different:
                // if (product < 0) {
                //     product += MOD;
                // }
                powNums[i][c] = product;
            }
        }
        // Return the fully populated table of powers.
        return powNums;
    }

    /**
     * Main method to demonstrate the usage of the utility methods in the Mod class.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        System.out.println("Demonstrating Mod class utilities with MOD = " + MOD);
        System.out.println("--------------------------------------------------");

        // --- Demonstrate precomputeBinomialCoefficients ---
        int maxNForBinomial = 5;
        System.out.println("\n1. Precomputing Binomial Coefficients (n choose k) up to n = " + maxNForBinomial + ":");
        try {
            long[][] C = precomputeBinomialCoefficients(maxNForBinomial);
            System.out.println("   Table C[n][k]:");
            for (int n_val = 0; n_val <= maxNForBinomial; n_val++) {
                System.out.print("   n=" + n_val + ": ");
                for (int k_val = 0; k_val <= n_val; k_val++) {
                    System.out.print(C[n_val][k_val] + " ");
                }
                System.out.println();
            }
            // Example: Print C(5, 2)
            if (maxNForBinomial >= 5 && C[5].length > 2) { // Check bounds
                System.out.println("   Example: C(5, 2) = " + C[5][2]); // Expected: 10
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error in precomputeBinomialCoefficients: " + e.getMessage());
        }


        // --- Demonstrate precomputePowers ---
        int[] numbers = {2, 3, 5, -2}; // Including a negative number for testing
        int maxExp = 4;
        System.out.println("\n2. Precomputing Powers (nums[i]^c) up to exponent = " + maxExp + ":");
        System.out.print("   For numbers: [");
        for(int i=0; i<numbers.length; i++) {
            System.out.print(numbers[i] + (i == numbers.length - 1 ? "" : ", "));
        }
        System.out.println("]");

        try {
            long[][] powers = precomputePowers(numbers, maxExp);
            System.out.println("   Table powers[number_index][exponent]:");
            for (int i = 0; i < powers.length; i++) {
                System.out.print("   For nums[" + i + "] (" + numbers[i] + "): ");
                for (int exp = 0; exp <= maxExp; exp++) {
                    System.out.print(powers[i][exp] + " ");
                }
                System.out.println();
            }
            // Example: Print 3^4 and (-2)^3
            if (numbers.length > 1 && powers[1].length > 4) { // Check bounds for 3^4
                System.out.println("   Example: " + numbers[1] + "^" + 4 + " = " + powers[1][4]); // Expected: 81
            }
            if (numbers.length > 3 && powers[3].length > 3) { // Check bounds for (-2)^3
                // (-2)^3 = -8.  (-8 % MOD + MOD) % MOD = (1000000007 - 8) = 999999999
                System.out.println("   Example: (" + numbers[3] + ")^" + 3 + " = " + powers[3][3]);
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error in precomputePowers: " + e.getMessage());
        }

        // --- Test with edge cases for precomputePowers ---
        System.out.println("\n3. Testing precomputePowers with edge cases:");
        try {
            System.out.println("   - Empty nums array:");
            long[][] emptyPowers = precomputePowers(new int[]{}, 5);
            System.out.println("     Resulting array dimensions: " + emptyPowers.length + "x" + (emptyPowers.length > 0 ? emptyPowers[0].length : 0));

            System.out.println("   - Max exponent 0:");
            int[] singleNum = {7};
            long[][] zeroExpPowers = precomputePowers(singleNum, 0);
            System.out.print("     For " + singleNum[0] + " up to exp 0: ");
            if (zeroExpPowers.length > 0 && zeroExpPowers[0].length > 0) {
                System.out.println(zeroExpPowers[0][0]); // Expected: 1
            } else {
                System.out.println("Unexpected array dimensions.");
            }

        } catch (IllegalArgumentException e) {
            System.err.println("Error in edge case test for precomputePowers: " + e.getMessage());
        }
        System.out.println("\n--------------------------------------------------");
        System.out.println("Demonstration complete.");
    }
}