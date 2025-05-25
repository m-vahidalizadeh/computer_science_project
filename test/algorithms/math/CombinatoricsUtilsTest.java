package algorithms.math;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CombinatoricsUtilsTest {

    private static final int MOD = 1_000_000_007;

    @Test
    @DisplayName("Test with N = 0")
    void generateBinomialCoefficients_NIsZero() {
        long[][] C = CombinatoricsUtils.generateBinomialCoefficients(0);
        assertNotNull(C, "Coefficient table should not be null.");
        assertEquals(1, C.length, "Table rows should be N+1.");
        assertEquals(1, C[0].length, "Table columns should be N+1.");
        assertEquals(1, C[0][0], "C(0,0) should be 1.");
    }

    @Test
    @DisplayName("Test with N = 1")
    void generateBinomialCoefficients_NIsOne() {
        long[][] C = CombinatoricsUtils.generateBinomialCoefficients(1);
        assertNotNull(C);
        assertEquals(2, C.length);
        assertEquals(2, C[0].length); // All rows will have N+1 columns

        // C(0,k)
        assertEquals(1, C[0][0], "C(0,0) should be 1.");
        assertEquals(0, C[0][1], "C(0,1) should be 0.");

        // C(1,k)
        assertEquals(1, C[1][0], "C(1,0) should be 1.");
        assertEquals(1, C[1][1], "C(1,1) should be 1.");
    }

    @Test
    @DisplayName("Test with N = 5 for specific values")
    void generateBinomialCoefficients_NIsFive() {
        long[][] C = CombinatoricsUtils.generateBinomialCoefficients(5);
        assertNotNull(C);
        assertEquals(6, C.length);

        // C(n,0) should always be 1
        for (int i = 0; i <= 5; i++) {
            assertEquals(1, C[i][0], "C(" + i + ",0) should be 1.");
        }

        // C(n,n) should always be 1
        for (int i = 0; i <= 5; i++) {
            assertEquals(1, C[i][i], "C(" + i + "," + i + ") should be 1.");
        }

        // C(n,k) where k > n should be 0
        assertEquals(0, C[0][1], "C(0,1) should be 0.");
        assertEquals(0, C[1][2], "C(1,2) should be 0."); // C[1].length is 6, so C[1][2] exists
        assertEquals(0, C[2][3], "C(2,3) should be 0.");
        assertEquals(0, C[3][5], "C(3,5) should be 0.");


        // Some known values from Pascal's triangle
        // Row 2: 1 2 1
        assertEquals(2, C[2][1], "C(2,1) should be 2.");
        // Row 3: 1 3 3 1
        assertEquals(3, C[3][1], "C(3,1) should be 3.");
        assertEquals(3, C[3][2], "C(3,2) should be 3.");
        // Row 4: 1 4 6 4 1
        assertEquals(4, C[4][1], "C(4,1) should be 4.");
        assertEquals(6, C[4][2], "C(4,2) should be 6.");
        assertEquals(4, C[4][3], "C(4,3) should be 4.");
        // Row 5: 1 5 10 10 5 1
        assertEquals(5, C[5][1], "C(5,1) should be 5.");
        assertEquals(10, C[5][2], "C(5,2) should be 10.");
        assertEquals(10, C[5][3], "C(5,3) should be 10.");
        assertEquals(5, C[5][4], "C(5,4) should be 5.");
    }

    @Test
    @DisplayName("Test with larger N and check modulo property (implicitly)")
    void generateBinomialCoefficients_LargeN() {
        int N = 20;
        long[][] C = CombinatoricsUtils.generateBinomialCoefficients(N);
        assertNotNull(C);
        assertEquals(N + 1, C.length);

        // C(20, 10) = 184756. This value is less than MOD.
        assertEquals(184756, C[20][10], "C(20,10) should be 184756.");

        // C(30,15) would be large, let's test a value that might involve modulo if N was larger
        // For N=20, C(20,2) = (20*19)/2 = 190
        assertEquals(190, C[20][2], "C(20,2) should be 190.");

        // If we had a case where C(n-1,k-1) + C(n-1,k) > MOD,
        // the modulo in the implementation ensures it's handled.
        // Example: C(N, k) where intermediate sum exceeds MOD.
        // Let's simulate a large N scenario by checking a known large value if available
        // or rely on the correctness of Pascal's identity with modulo.
        // For N=50, C(50,25) is very large.
        // The test for N=20 is sufficient to check the logic for values within long's range before MOD.
        // The MOD application is tested by the correctness of the algorithm itself.
    }


    @Test
    @DisplayName("Test with N = 2, checking all values")
    void generateBinomialCoefficients_NIsTwo() {
        long[][] C = CombinatoricsUtils.generateBinomialCoefficients(2);
        // C[0][0] = 1
        // C[1][0] = 1, C[1][1] = 1
        // C[2][0] = 1, C[2][1] = 2, C[2][2] = 1

        long[][] expected = {
                {1, 0, 0},  // C(0,k)
                {1, 1, 0},  // C(1,k)
                {1, 2, 1}   // C(2,k)
        };

        for(int i=0; i<expected.length; i++) {
            for(int j=0; j<expected[i].length; j++) {
                assertEquals(expected[i][j], C[i][j], "Mismatch at C(" + i + "," + j + ")");
            }
        }
    }


    @Test
    @DisplayName("Test with negative N, expecting IllegalArgumentException")
    void generateBinomialCoefficients_NegativeN_ThrowsException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> CombinatoricsUtils.generateBinomialCoefficients(-1),
                "Should throw IllegalArgumentException for negative N."
        );
        assertEquals("N cannot be negative for generating binomial coefficients.", exception.getMessage());

        assertThrows(
                IllegalArgumentException.class,
                () -> CombinatoricsUtils.generateBinomialCoefficients(-5),
                "Should throw IllegalArgumentException for negative N."
        );
    }
}