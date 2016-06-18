package algorithms;

/**
 * Created by Mohammad on 5/15/2016.
 */

public class SingleThreadExecuterDemo {

    public static void main(String args[]) {

        RunnableEven R1 = new RunnableEven( "Thread-Even");
        R1.start();

        RunnableOdd R2 = new RunnableOdd( "Thread-Odd");
        R2.start();

    }

    public static class RunnableEven implements Runnable {
        private Thread t;
        private String threadName;

        RunnableEven( String name){
            this.threadName = name;
            System.out.println("Creating " +  this.threadName );
        }
        public void run() {
            System.out.println("Running " +  threadName );
            try {
                for(int i = 0; i < 100; i= i+2) {
                    System.out.println("Thread: " + threadName + ", " + i);
                    // Let the thread sleep for a while.
                    Thread.sleep(50);
                }
            } catch (InterruptedException e) {
                System.out.println("Thread " +  threadName + " interrupted.");
            }
            System.out.println("Thread " +  threadName + " exiting.");
        }

        public void start ()
        {
            System.out.println("Starting " +  threadName );
            if (t == null)
            {
                t = new Thread (this, threadName);
                t.start ();
            }
        }

    }


    public static class RunnableOdd implements Runnable {
        private Thread t;
        private String threadName;

        RunnableOdd( String name){
            this.threadName = name;
            System.out.println("Creating " +  this.threadName );
        }
        public void run() {
            System.out.println("Running " +  threadName );
            try {
                for(int i = 1; i < 100; i= i+2) {
                    System.out.println("Thread: " + threadName + ", " + i);
                    // Let the thread sleep for a while.
                    Thread.sleep(50);
                }
            } catch (InterruptedException e) {
                System.out.println("Thread " +  threadName + " interrupted.");
            }
            System.out.println("Thread " +  threadName + " exiting.");
        }

        public void start ()
        {
            System.out.println("Starting " +  threadName );
            if (t == null)
            {
                t = new Thread (this, threadName);
                t.start ();
            }
        }

    }

}
