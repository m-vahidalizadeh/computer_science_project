package algorithms.mixed;

/**
 * Created by Mohammad on 5/15/2016.
 */
public class Counter2s {

    public static void main(String[] args) {
        System.out.println(twosInRange(100));


    }


        public static int twosInRange(Integer number) {
                int digits = (int) Math.floor(Math.log(number) / Math.log(10));
        int count = 0;
        for (int pos = digits; pos >= 0; --pos) {     // 3, 2, 1, 0
            int unit = (int) Math.pow(10, pos);             // 1000, 100, 10, 1
            int digit = (int) Math.floor(number / unit);    // 2, 4, 6, 8
            number -= digit * unit;                   // 468, 68, 8, 0
            // COUNT OCCURRENCES IN LOWER DIGITS:
            count += digit * pos * (unit / 10);       // + 2*3*100, 4*2*10, 6*1*1, 8*0*0
            // COUNT OCCURRENCES IN CURRENT DIGIT:
            if (digit > 2) count += unit;             // + (1000), 100, 10, 1
            else if (digit == 2) count += number + 1; // + 469, (69), (9), (1)
        }
        return (count-1);                               // 600 + 80 + 6 + 100 + 10 + 1 + 469
        }

}
