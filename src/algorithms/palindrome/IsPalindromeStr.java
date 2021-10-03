package algorithms.palindrome;

public class IsPalindromeStr {

    public boolean isPalindrome(String s) {
        if (s.isBlank()) return false;
        int n = s.length();
        int l = 0;
        int r = n - 1;
        while (l < r) {
            if (s.charAt(l++) != s.charAt(r--)) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        IsPalindromeStr isPalindromeStr = new IsPalindromeStr();
        System.out.println(isPalindromeStr.isPalindrome("a"));
        System.out.println(isPalindromeStr.isPalindrome("abba"));
        System.out.println(isPalindromeStr.isPalindrome("geeks"));
    }

}
