package algorithms;

/**
 * Created by Mohammad on 5/14/2016.
 */
public class Factoriel {

    public static void main_factoriel(String[] args) {
        System.out.println(factoriel(5));
    }

    private static Integer factoriel(Integer input){
        if(input == 1 || input == 0){
            return 1;
        }else{
            return input*factoriel(input-1);
        }
    }

}
