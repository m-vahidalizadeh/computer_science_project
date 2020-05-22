package algorithms.mixed;

/**
 * Created by Mohammad on 5/15/2016.
 */
public class SpiralPrint {

    public static final int X_SIZE = 4;
    public static final int Y_SIZE = 4;

    public static void main(String[] args) {
        int[][] array = new int[X_SIZE][Y_SIZE];
        System.out.println("Ordinary print: ");
        for(int i = 0; i < X_SIZE; i++){
            for (int j = 0; j < Y_SIZE; j++){
                array[i][j] = i * X_SIZE + (j + 1);
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("************");

        spiralPrint(X_SIZE, Y_SIZE, array);
    }

    public static void spiralPrint(int xSize, int ySize, int matrix[][]){

        System.out.println("Spiral print: ");
        int i,  k = 0, l = 0;
        xSize--;  ySize--;

        while(k <= xSize && l <= ySize){
            for(i = l; i <= ySize; ++i) {
                System.out.print(matrix[k][i]+ " ");
            }
            k++;

            for(i = k; i <= xSize; ++i) {
                System.out.print(matrix[i][ySize] + " ");
            }
            ySize--;

            for(i = ySize; i >= l; --i) {
                System.out.print(matrix[xSize][i] + " ");
            }
            xSize--;


            for(i = xSize; i >= k; --i) {
                System.out.print(matrix[i][l] + " ");
            }
            l++;
        }
    }

}
