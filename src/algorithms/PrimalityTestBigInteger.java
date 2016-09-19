package algorithms;

import java.math.BigInteger;
import java.util.Random;

/**
 * Created by Mohammad on 9/19/2016.
 */
public class PrimalityTestBigInteger {

    private static int size = 1024;
    private static Random r = new Random(1);
    private static BigInteger two = new BigInteger("2");
    private static BigInteger three = new BigInteger("3");

    public static boolean isprime(BigInteger n) {

        if (!n.isProbablePrime(10)) {
            return false;
        }

        if (n.compareTo(BigInteger.ONE) == 0 || n.compareTo(two) == 0) {
            return true;
        }
        BigInteger root = appxRoot(n);
        //System.out.println("Using approximate root " + root);

        int cnt = 0;
        for (BigInteger i = three; i.compareTo(root) <= 0; i = i
                .nextProbablePrime()) {
            cnt++;
            if (cnt % 1000 == 0) {
                //System.out.println(cnt + " Using next prime " + i);
            }
            if (n.mod(i).equals(BigInteger.ZERO)) {
                return false;
            }

        }
        return true;

    }

    private static BigInteger appxRoot(final BigInteger n) {
        BigInteger half = n.shiftRight(1);
        while (half.multiply(half).compareTo(n) > 0) {
            half = half.shiftRight(1);
        }
        return half.shiftLeft(1);
    }

    public static boolean returnPrime(BigInteger number) {
        if (!number.isProbablePrime(5))
            return false;

        BigInteger two = new BigInteger("2");
        if (!two.equals(number) && BigInteger.ZERO.equals(number.mod(two)))
            return false;

        for (BigInteger i = new BigInteger("3"); i.multiply(i).compareTo(number) < 1; i = i.add(two)) {
            if (BigInteger.ZERO.equals(number.mod(i)))
                return false;
        }
        return true;
    }

    /**
     * test primes with 10 digits
     5915587277
     1500450271
     3267000013
     5754853343
     4093082899
     9576890767
     3628273133
     2860486313
     5463458053
     3367900313
     more test primes: https://primes.utm.edu/lists/small/small.html
     * @param args
     */

    public static void main(String[] args) {
        BigInteger bigInteger = new
                BigInteger
                ("5754853343");

        long startTime = System.nanoTime();
        boolean result1 = returnPrime(bigInteger);
        long stopTime = System.nanoTime();
        long time1 = stopTime - startTime;

        startTime = System.nanoTime();
        boolean result2 = isprime(bigInteger);
        stopTime = System.nanoTime();
        long time2 = stopTime - startTime;

        System.out.println("Method 1- returnPrime: "+result1+" time: "+time1+" ratio: "+(time1/time2));
        System.out.println("Method 2- isPrime: "+result2+" time: "+time2+" ratio: "+(time2/time1));
    }

}
