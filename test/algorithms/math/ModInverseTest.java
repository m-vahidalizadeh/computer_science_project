package algorithms.math;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ModInverseTest {

    @Test
    @DisplayName("Should return correct modular inverse for valid inputs")
    void getModInverse_validInputs_returnsCorrectInverse() {
        // (3 * 4) % 11 = 12 % 11 = 1
        assertEquals(4, ModInverse.getModInverse(3, 11),
                "Inverse of 3 mod 11 should be 4");
        // (10 * 12) % 17 = 120 % 17 = 1
        assertEquals(12, ModInverse.getModInverse(10, 17),
                "Inverse of 10 mod 17 should be 12");
        // (7 * 2) % 13 = 14 % 13 = 1
        assertEquals(2, ModInverse.getModInverse(7, 13),
                "Inverse of 7 mod 13 should be 2");
        // (5 * 3) % 14 = 15 % 14 = 1 (composite modulus)
        assertEquals(3, ModInverse.getModInverse(5, 14),
                "Inverse of 5 mod 14 should be 3");
    }

    @Test
    @DisplayName("Should return 1 when a is 1")
    void getModInverse_aIsOne_returnsOne() {
        // (1 * 1) % 13 = 1
        assertEquals(1, ModInverse.getModInverse(1, 13),
                "Inverse of 1 mod 13 should be 1");
        // (1 * 1) % 2 = 1
        assertEquals(1, ModInverse.getModInverse(1, 2),
                "Inverse of 1 mod 2 should be 1");
    }

    @Test
    @DisplayName("Should return m-1 when a is m-1 (or -1 mod m)")
    void getModInverse_aIsMMinusOne_returnsMMinusOne() {
        // (10 * 10) % 11 = 100 % 11 = (99+1)%11 = 1
        assertEquals(10, ModInverse.getModInverse(10, 11),
                "Inverse of 10 mod 11 should be 10");
        // (16 * 16) % 17 = (-1 * -1) % 17 = 1
        assertEquals(16, ModInverse.getModInverse(16, 17),
                "Inverse of 16 mod 17 should be 16");
    }

    @Test
    @DisplayName("Should handle a being larger than m")
    void getModInverse_aLargerThanM_returnsCorrectInverse() {
        // 14 mod 11 = 3. Inverse of 3 mod 11 is 4.
        assertEquals(4, ModInverse.getModInverse(14, 11),
                "Inverse of 14 (3) mod 11 should be 4");
        // 27 mod 17 = 10. Inverse of 10 mod 17 is 12.
        assertEquals(12, ModInverse.getModInverse(27, 17),
                "Inverse of 27 (10) mod 17 should be 12");
    }

}