package algorithms.bit;

/**
 * Created by Mohammad on 9/25/2016.
 */
public class ModularWithBitManipulation {

    public static void main(String[] args) {
        for(int i=1; i<21; i++){
            if(isEven(i))
                System.out.println(i+" is even.");
            else if(isOdd(i))
                System.out.println(i+" is odd.");
        }
    }

    public static boolean isEven(int input){
        return ( (input & 1) == 0 );
    }

    public static boolean isOdd(int input){
        return ( (input & 1) == 1 );
    }

}
