package algorithms;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by Mohammad on 9/25/2016.
 */
public class TopKWordsTextFile {

    static class SortObject implements Comparable<SortObject>{

        private String key;
        private int value;

        public SortObject(String key, int value) {
            super();
            this.key = key;
            this.value = value;
        }

//        public static void main(String[] args) {
//            SortObject[] objects = new SortObject[5];
//            objects[0] = new SortObject("Mohammad",3);
//            objects[1] = new SortObject("Ali",30);
//            objects[2] = new SortObject("Mahnaz",27);
//            objects[3] = new SortObject("Zahra",31);
//            objects[4] = new SortObject("Zohreh",13);
//            //Sort the array
//            Arrays.sort(objects);
//            //Print the array
//            for(int i=0; i<objects.length; i++){
//                System.out.println(objects[i].key+":"+objects[i].value);
//            }
//        }

        @Override
        public int compareTo(SortObject o) {
            //ascending order
            //return this.value - o.value;

            //descending order
            return o.value - this.value;
        }
    }


    public static void main(String[] args) {
        HashMap<String,Integer> hm = new HashMap<>();
        int k = 1;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("words.in")));

            String line;
            while ((line = br.readLine()) != null) {
                // process the line.
                //System.out.println(line);
                String[] tokens = line.split(" ");
                for(int i=0; i<tokens.length; i++){
                    if(hm.containsKey(tokens[i])){
                        Integer prev = hm.get(tokens[i]);
                        hm.put(tokens[i],prev+1);
                    }else{
                        hm.put(tokens[i],1);
                    }
                }
            }
            br.close();
            k = hm.size();

            // Get a set of the entries
            Set set = hm.entrySet();
            // Get an iterator
            Iterator i = set.iterator();
            int index = 0;
            // Display elements
            SortObject[] objects = new SortObject[hm.size()];
            while(i.hasNext()) {
                Map.Entry e = (Map.Entry)i.next();
                //System.out.print("Key: "+e.getKey() + ": ");
                String tempS = (String) e.getKey();
                int tempI = (int) e.getValue();
                //System.out.println(" Value: "+e.getValue());
                objects[index] = new SortObject(tempS,tempI);
//                objects[index].key = (String) e.getKey();
//                objects[index].value = (Integer) e.getValue();
                index++;
            }
            System.out.println();
            //Sort the array
            Arrays.sort(objects);
            //Print top k
            for(int j=0; j<k; j++){
                System.out.println(objects[j].key+":"+objects[j].value);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
