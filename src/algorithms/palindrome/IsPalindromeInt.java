package algorithms.palindrome;

public class IsPalindromeInt {

    public boolean isPalindrome(int x) {
        if (x < 0) return false;
        int original = x;
        int reverse = 0;
        while (original > 0) {
            reverse = reverse * 10 + original % 10;
            original = original / 10;
        }
        return x == reverse;
    }

}
