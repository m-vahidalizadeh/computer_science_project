package algorithms;

import java.awt.*;
import java.util.HashMap;

/**
 * Created by Mohammad on 8/6/2016.
 * How to find all the connected islands in an adjacency matrix of a graph
 */
public class ConnectedComponetns {

    static HashMap[] hm = new HashMap[5];
    static int row = 5;
    static int column = 5;
    static int clusterNumber = 0;
    static int[][] m = {  {1, 1, 0, 0, 0},
            {0, 1, 0, 0, 1},
            {1, 0, 0, 1, 1},
            {0, 0, 0, 0, 0},
            {1, 0, 1, 0, 1}};
    static boolean[][] visited = {  {false, false, false, false, false},
            {false, false, false, false, false},
            {false, false, false, false, false},
            {false, false, false, false, false},
            {false, false, false, false, false}
    };
    static int[][] cluster = {  {-1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1}};

    public static void main(String[] args) {
        //int clusterNumber = 0;
        for(int i=0; i<row; i++){
            for(int j=0; j<column; j++){
                if( (!visited[i][j]) && (m[i][j] == 1) ){
                    clusterNumber++;
                    hm[clusterNumber-1] = new HashMap();
                    visited[i][j] = true;
                    System.out.println(i+" "+j+" -> "+m[i][j]+" cluster: "+(clusterNumber-1));
                    DFS(i,j);
                    System.out.println("----------------------------------");
                }
            }
        }
        System.out.println("number of clusters: "+clusterNumber);

        for(int i=0; i<5; i++){
            System.out.println(i+": ");
            for(int j=0; j<hm[i].size(); j++){
                Point point = (Point) hm[i].get(j);
                System.out.print("("+(int) point.getX()+","+(int) point.getY()+")");
            }
            System.out.println();
        }

    }

    private static void DFS(int i,int j){
        cluster[i][j] = clusterNumber;
        Point point = new Point();
        point.setLocation(i,j);
        int index = hm[clusterNumber-1].size();
        hm[clusterNumber-1].put(index,point);
        for(int a=-1; a<2; a++){
            for(int b=-1; b<2; b++){
                if((-1<i+a) && (i+a<row) && (-1<j+b) && (j+b<column)){
                    if( (!visited[i+a][j+b]) && (m[i+a][j+b] == 1)  ){
                        System.out.println((i+a)+" "+(j+b)+" -> "+m[i+a][j+b]+" cluster: "+(clusterNumber-1));
                        visited[i+a][j+b] = true;
                        DFS(i+a,j+b);
                    }
                }


            }
        }
    }
}
