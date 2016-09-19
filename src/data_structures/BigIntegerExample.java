package data_structures;

import java.math.BigInteger;

/**
 * Created by Mohammad on 9/19/2016.
 */
public class BigIntegerExample {

    public static void main(String[] args){
        //Create 2 BigIntegers
        BigInteger b1,b2;

        b1 = new BigInteger("1234567890");
        b2 = new BigInteger("1234567890");

        System.out.println("Add: "+b1.add(b2));
        System.out.println("Subtract: "+b1.subtract(b2));
        System.out.println("Multiply: "+b1.multiply(b2));
        System.out.println("Divide: "+b1.divide(b2));
    }

}
