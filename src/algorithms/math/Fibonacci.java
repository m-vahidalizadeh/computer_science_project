package algorithms.math;

import java.math.BigInteger;

/**
 * Created by Mohammad on 5/19/2016.
 */
public class Fibonacci {

    public static void main(String args[]) {
        //input to print Fibonacci series upto how many numbers
        final int number= 100;
        System.out.println("Enter number upto which Fibonacci series to print: ");
        //int number = new Scanner(System.in).nextInt();
        // System.out.println("Fibonacci series upto " + number +" numbers : ");
        // printing Fibonacci series upto number
        for(int i=1; i<=number; i++){
            System.out.println(fibonacci2(i) +" ");
        }
    }

    public static BigInteger fibonacci2(int number){
        if(number == 1 || number == 2){
            return new BigInteger("1");
        }
        BigInteger fibo1=new BigInteger("1"), fibo2=new BigInteger("1"), fibonacci=new BigInteger("1");
        for(int i= 3; i<= number; i++){

            //Fibonacci number is sum of previous two Fibonacci number
            fibonacci = fibo1.add(fibo2);
            fibo1 = fibo2;
            fibo2 = fibonacci;

        }
        return fibonacci; //Fibonacci number

    }

}
