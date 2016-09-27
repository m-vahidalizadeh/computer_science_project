package data_structures;

/**
 * Created by Mohammad on 9/25/2016.
 */
public class ThreeStacksWithOneArray {

    //This is the stack node class
    class StackNode {
        //This is the value of the node
        int value;
        //This is showing the previous node
        int prev;
        //This is the constructor of the class
        StackNode(int value, int prev) {
            this.value = value;
            this.prev = prev;
        }
    }

    //This keeps the stack nodes
    private StackNode[] stackNodes = null;
    private static int CAPACITY = 10;
    //This keeps the top of free list
    private int freeListTop = 0;
    //This is the variable for the size
    private int size = 0;
    //These are the pointers to the three stacks
    private int[] stackPointers = { -1, -1, -1 };

    //This is the constructor of the main class
    ThreeStacksWithOneArray() {
        //Initialize the stack nodes
        stackNodes = new StackNode[CAPACITY];
        //initialize the free list
        initFreeList();
    }

    //Initialize the free list
    private void initFreeList() {
        for (int i = 0; i < CAPACITY; i++) {
            //The value of each node is 0 and it points to the next node
            stackNodes[i] = new StackNode(0, i + 1);
        }
    }

    //This is the push procedure
    public void push(int stackNum, int value) throws Exception {
        //Print the push information
        System.out.println("Push to stack "+stackNum+" value "+value);
        int freeIndex;
        int currentStackTop = stackPointers[stackNum - 1];
        //Find the free node
        freeIndex = getFreeNodeIndex();
        //Make a new node in the free index
        StackNode n = stackNodes[freeIndex];
        //Setting the previous node
        n.prev = currentStackTop;
        //Setting the value
        n.value = value;
        stackPointers[stackNum - 1] = freeIndex;
    }

    //This is the pop method
    public StackNode pop(int stackNum) throws Exception {
        //From which stack you want to pop. -1, since it starts from 0
        int currentStackTop = stackPointers[stackNum - 1];
        //This checks for stack underflow
        if (currentStackTop == -1) {
            throw new Exception("UNDERFLOW");
        }
        //Get the node as a temp node
        StackNode temp = stackNodes[currentStackTop];
        //Remove the node from stack
        stackPointers[stackNum - 1] = temp.prev;
        //Put this node as free node
        freeStackNode(currentStackTop);
        //Print the pop information
        System.out.println("Pop from stack "+stackNum+" value: "+temp.value);
        //Return the value
        return temp;
    }

    //Get a free node index
    private int getFreeNodeIndex() throws Exception {
        int temp = freeListTop;
        //Overflow
        if (size >= CAPACITY)
            throw new Exception("OVERFLOW");
        freeListTop = stackNodes[temp].prev;
        size++;
        //return the free node index
        return temp;
    }

    //Make one index free after a pop
    private void freeStackNode(int index) {
        stackNodes[index].prev = freeListTop;
        //Put the index in free list
        freeListTop = index;
        //Decrease the size by one
        size--;
    }

    public static void main(String args[]) {
        // Test Driver
        ThreeStacksWithOneArray mulStack = new ThreeStacksWithOneArray();
        try {
            //Adding to those three stacks
            mulStack.push(1, 11);
            mulStack.push(1, 12);
            mulStack.push(1, 13);
            mulStack.push(1, 14);
            mulStack.push(2, 21);
            mulStack.push(2, 22);
            mulStack.push(3, 31);
            mulStack.push(3, 32);
            //Popping from those three stacks
            mulStack.pop(1);
            mulStack.pop(2);
            mulStack.pop(3);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
