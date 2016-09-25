package algorithms;

import java.util.Arrays;

/**
 * Created by Mohammad on 9/25/2016.
 */
public class SortObject implements Comparable<SortObject>{

    private String key;
    private int value;

    public SortObject(String key, int value) {
        super();
        this.key = key;
        this.value = value;
    }

    public static void main(String[] args) {
        SortObject[] objects = new SortObject[5];
        objects[0] = new SortObject("Mohammad",3);
        objects[1] = new SortObject("Ali",30);
        objects[2] = new SortObject("Mahnaz",27);
        objects[3] = new SortObject("Zahra",31);
        objects[4] = new SortObject("Zohreh",13);
        //Sort the array
        Arrays.sort(objects);
        //Print the array
        for(int i=0; i<objects.length; i++){
            System.out.println(objects[i].key+":"+objects[i].value);
        }
    }

    @Override
    public int compareTo(SortObject o) {
        //ascending order
        //return this.value - o.value;

        //descending order
        return o.value - this.value;
    }
}
