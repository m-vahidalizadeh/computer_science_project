package algorithms.mixed;

/**
 * Created by Mohammad on 9/25/2016.
 */
public class AngleBetweenHourAndMinute {

    static double angle(int h, int m) {
        double hAngle = 0.5D * (h * 60 + m);
        double mAngle = 6 * m;
        double angle = Math.abs(hAngle - mAngle);
        angle = Math.min(angle, 360 - angle);
        return angle;
    }

    int normalizeAngle(int angle)
    {
        int newAngle = angle;
        while (newAngle <= -180) newAngle += 360;
        while (newAngle > 180) newAngle -= 360;
        return newAngle;
    }

    public void main(String[] args) {
        System.out.print(angle(3,15));
        System.out.println(normalizeAngle(Integer.parseInt(String.valueOf(angle(3,15)))));
    }

}
