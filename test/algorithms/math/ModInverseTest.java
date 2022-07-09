package algorithms.math;

import org.junit.Assert;
import org.junit.Test;

public class ModInverseTest {

    @Test
    public void testGetModInverse() {
        Assert.assertEquals(4, ModInverse.getModInverse(3, 11));
        Assert.assertEquals(12, ModInverse.getModInverse(10, 17));
    }

}
