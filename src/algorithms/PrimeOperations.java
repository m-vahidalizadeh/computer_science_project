package algorithms;

import java.util.stream.LongStream;

public class PrimeOperations {

    public static void main(String[] args) {
        System.out.format("%d is prime: %s.\n", 199, isPrime(199)); // Prime
        System.out.format("%d is prime: %s.\n", 198, isPrime(198)); // Not prime
        System.out.format("%d is prime: %s.\n", 104729, isPrime(104729)); // Prime
        System.out.format("%d is prime: %s.\n", 104727, isPrime(982443529)); // Prime
    }

    /**
     * Tells if a number is prime or not.
     *
     * @param input the input
     * @return If the input is prime or not
     */
    public static boolean isPrime(long input) {
        if (input < 2) return false; // Primes start from 2
        else if (input == 2 || input == 3 || input == 5 || input == 7) return true; // 2,3,5 and 7 are one digit primes
        else if (input % 2 == 0 || input % 3 == 0) return false;// Input should not be divisible by 2 or 3
        return LongStream.iterate(12, n -> n < Math.ceil(Math.sqrt(input)), n -> n + 6)
                .filter(n -> input % (n - 1) == 0 || input % (n + 1) == 0).findAny().isEmpty();
    }

}
