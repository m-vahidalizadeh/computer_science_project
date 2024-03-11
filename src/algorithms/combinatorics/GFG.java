package algorithms.combinatorics;

public class GFG {

    public static void printAllPermutations(String str, String ans) {
        if (str.isEmpty()) System.out.print(ans + " ");
        else for (int i = 0; i < str.length(); i++)
            printAllPermutations(str.substring(0, i) + str.substring(i + 1), ans + str.charAt(i));
    }

    public static void main(String[] args) {
        String str = "abb";
        printAllPermutations(str, "");
    }

}
