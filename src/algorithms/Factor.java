package algorithms;

/**
 * Created by Mohammad on 5/14/2016.
 */
public class Factor {

    public static void main6(String[] args) {
        Boolean multiple3= false, multiple5= false;
        for(int i=1; i<= 100; i++){
            multiple3= false;
            multiple5= false;
            if(i % 3 == 0){
                multiple3= true;
            }
            if(i % 5 == 0){
                multiple5= true;
            }
            if(multiple3 && multiple5){
                System.out.print("fuzzbuzz ");
            }else if(multiple3){
                System.out.print("fuzz ");
            }else if(multiple5){
                System.out.print("buzz ");
            }else{
                System.out.print(i+" ");
            }
        }
    }

}
