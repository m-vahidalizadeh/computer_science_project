package algorithms;

/**
 * Created by Mohammad on 5/14/2016.
 */
public class Doit {

    private static String first;
    private static String second;

    public static void main_doit(String[] args) {
        Doit o= new Doit();
        o.first= "fizz";
        o.second= "buzz";
        print(o, "first");
        print(o, "second");


    }

    private static void print(Doit o, String key){
        switch(key){
            case "first":
                System.out.println(o.first);
                break;
            case "second":
                System.out.println(o.second);
                break;
        }
    }


}
