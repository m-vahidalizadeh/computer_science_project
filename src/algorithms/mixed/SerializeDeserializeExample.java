package algorithms.mixed;

import base.Coordinate;

import java.io.*;

/**
 * Created by Mohammad on 9/25/2016.
 */
public class SerializeDeserializeExample {

    public static void main(String [] args) {
        Coordinate c = new Coordinate(10,77);

        //Serialize
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("coordinate.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(c);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in coordinate.ser");
        }catch(IOException i) {
            i.printStackTrace();
        }

        //Deserialize
        Coordinate d = null;
        try {
            FileInputStream fileIn = new FileInputStream("coordinate.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            d = (Coordinate) in.readObject();
            in.close();
            fileIn.close();
        }catch(IOException i) {
            i.printStackTrace();
            return;
        }catch(ClassNotFoundException e) {
            System.out.println("Coordinate class not found");
            e.printStackTrace();
            return;
        }

        System.out.println("Deserialized coordinate...");
        System.out.println("x: " + d.x);
        System.out.println("y: " + d.y);
    }

}
