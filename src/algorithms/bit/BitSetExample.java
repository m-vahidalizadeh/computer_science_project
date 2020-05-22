package algorithms.bit;

import java.util.BitSet;

/**
 * Created by Mohammad on 9/19/2016.
 */
public class BitSetExample {

    public static void main(String args[]){
        BitSet bits1 = new BitSet(7);
        BitSet bits2 = new BitSet(7);

        // set some bits
        for(int i = 0; i < 7; i++) {
            if((i % 2) == 0) bits1.set(i);
            if((i % 3) != 0) bits2.set(i);
        }

        System.out.println("BitSet1: ");

        for(int i = 0; i < 7; i++) {
            System.out.print(bits1.get(i)? "1 ": "0 ");
        }

        System.out.println("\nBitSet2: ");

        for(int i = 0; i < 7; i++) {
            System.out.print(bits2.get(i)? "1 ": "0 ");
        }

        System.out.println();

        //And
        bits1.and(bits2);

        System.out.println("b1 = b1 AND b2\nBitSet1: ");

        for(int i = 0; i < 7; i++) {
            System.out.print(bits1.get(i)? "1 ": "0 ");
        }

        System.out.println();
        System.out.println("BitSet2: ");

        for(int i = 0; i < 7; i++) {
            System.out.print(bits2.get(i)? "1 ": "0 ");
        }

        System.out.println();

        //Or
        bits1.or(bits2);

        System.out.println("b1 = b1 OR b2\nBitSet1: ");

        for(int i = 0; i < 7; i++) {
            System.out.print(bits1.get(i)? "1 ": "0 ");
        }

        System.out.println();
        System.out.println("BitSet2: ");

        for(int i = 0; i < 7; i++) {
            System.out.print(bits2.get(i)? "1 ": "0 ");
        }

        System.out.println();

        //Xor
        bits1.xor(bits2);

        System.out.println("b1 = b1 XOR b2\nBitSet1: ");

        for(int i = 0; i < 7; i++) {
            System.out.print(bits1.get(i)? "1 ": "0 ");
        }

        System.out.println();
        System.out.println("BitSet2: ");

        for(int i = 0; i < 7; i++) {
            System.out.print(bits2.get(i)? "1 ": "0 ");
        }

        System.out.println();

        //Setting bits to zero and one
        bits1.set(1);
        bits2.set(1,false);

        System.out.println("set bit 1 of BitSet1 to one and set bit 1 of BitSet2 to zero\nBitSet1: ");

        for(int i = 0; i < 7; i++) {
            System.out.print(bits1.get(i)? "1 ": "0 ");
        }

        System.out.println();
        System.out.println("BitSet2: ");

        for(int i = 0; i < 7; i++) {
            System.out.print(bits2.get(i)? "1 ": "0 ");
        }

        System.out.println();




    }
}
