package algorithms;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Mohammad on 5/14/2016.
 */
public class RectangleIntersect {

    public static void main_rect(String[] args) throws IOException {
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("rect.out")));
        Integer r1x1 = -1,r1y1 = -1,r1x2 = -1,r1y2 = -1,r2x1 = -1,r2y1 = -1,r2x2 = -1,r2y2 = -1;
        BufferedReader br = new BufferedReader(new FileReader("rect.in"));
        StringTokenizer st = new StringTokenizer(br.readLine()," ");
        r1x1 = Integer.parseInt(st.nextToken());
        r1y1 = Integer.parseInt(st.nextToken());
        r1x2 = r1x1 + Integer.parseInt(st.nextToken());
        r1y2 = r1y1 + Integer.parseInt(st.nextToken());
        r2x1 = Integer.parseInt(st.nextToken());
        r2y1 = Integer.parseInt(st.nextToken());
        r2x2 = r2x1 + Integer.parseInt(st.nextToken());
        r2y2 = r2y1 + Integer.parseInt(st.nextToken());
        System.out.println(((r1x1 < r2x2) && (r1x2 > r2x1) &&  (r1y1 < r2y2) && (r1y2 > r2y1)));
        pw.print(((r1x1 < r2x2) && (r1x2 > r2x1) &&  (r1y1 < r2y2) && (r1y2 > r2y1)));
        pw.close();
    }


}
