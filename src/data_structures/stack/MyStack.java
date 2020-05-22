package data_structures.stack;

import java.util.Stack;

/**
 * Created by Mohammad on 6/18/2016.
 */
public class MyStack {
        public static void main(String args[]) {
            // creating stack
            Stack st = new Stack();

            // populating stack
            st.push("Mohammad");
            st.push("Ali");
            st.push("Mahnaz");
            st.push("Parinaz");
            st.push("Vahid");

            // removing top object
            System.out.println("Removed object is: "+st.pop());

            // elements after remove
            System.out.println("Elements after remove: "+st);

            // Peak
            System.out.println("Peak: "+st.peek());

            // elements after push
            st.push("Zahra");
            System.out.println("Elements after pushing Zahra: "+st);

        }
    }
