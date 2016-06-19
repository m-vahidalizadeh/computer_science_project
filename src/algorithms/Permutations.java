package algorithms;

/**
 * Created by Mohammad on 5/14/2016.
 */
public class Permutations {

    public static void main(String[] args) {
        String input = "ABC";
        permutation(input);
    }

    public static void permutation(String s) {
        int N = s.length();
        char[] a = new char[N];
        for (int i = 0; i < N; i++)
            a[i] = s.charAt(i);
        permutation(a, N);
    }

    private static void permutation(char[] a, int n) {
        if (n == 1) {
            System.out.println(a);
            return;
        }
        for (int i = 0; i < n; i++) {
            swap(a, i, n-1);
            permutation(a, n-1);
            swap(a, i, n-1);
        }
    }

    private static void swap(char[] a, int i, int j) {
        char c = a[i];
        a[i] = a[j];
        a[j] = c;
    }

}
