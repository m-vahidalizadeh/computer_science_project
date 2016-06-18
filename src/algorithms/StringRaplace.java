package algorithms;

/**
 * Created by Mohammad on 5/19/2016.
 */
public class StringRaplace {

    public static void main(String a[]){

        String str = "This is an example string 777 is is is is is";
        System.out.println("Replace char 's' with 'o':"
                +str.replace('s', 'o'));

        System.out.println("Replace first occurance of string \"is\" with \"ui\":"
                +str.replaceFirst("is", "ui"));

        System.out.println("Replacing \"is\" every where with \"no\":"
                +str.replaceAll("is", "no"));
    }

}
