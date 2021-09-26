package algorithms.combinatorics;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class FindPermutationsOfStringTest {

    @Test
    public void findPermutationsTest() {
        FindPermutationsOfString findPermutationsOfString = new FindPermutationsOfString();
        List<String> res = findPermutationsOfString.findPermutations("ABC");
        Assertions.assertEquals(List.of("CAB", "ACB", "ABC", "CBA", "BCA", "BAC"), res);
    }

}
