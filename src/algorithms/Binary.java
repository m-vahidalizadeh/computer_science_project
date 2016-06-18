package algorithms;

/**
 * Created by Mohammad on 5/14/2016.
 */
public class Binary {

    public static void main_binary(String[] args) {
        String number1= "111", number2= "1";
        Integer number1Int= -1, number2Int= -1, sum= -1;
        number1Int= Integer.parseInt(number1, 2);
        number2Int= Integer.parseInt(number2, 2);
        sum= number1Int+ number2Int;
        System.out.print(Integer.toBinaryString(sum));
    }
}
