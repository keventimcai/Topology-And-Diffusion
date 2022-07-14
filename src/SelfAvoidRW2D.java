import SelfAvoiding.Point;
import SelfAvoiding.StdDraw;

import java.util.Random;
import java.util.Scanner;

public class SelfAvoidRW2D {
    private static final double pi2 = 2.0 * Math.PI;

    public static void main(String[] args) {
        // Take integer n and runs, and seed from user
        int n, runs, seed;
        Scanner sc = new Scanner(System.in);
        System.out.print("Give the seed for random sequence: ");
        seed = sc.nextInt();
        System.out.print("Give the number of random steps: ");
        n = sc.nextInt();
        System.out.print("Give the number runs: ");
        runs = sc.nextInt();
        sc.close();

        //create array which stores the position of each random walk
        Point[] points = new Point[runs];

        for (int i = 0; i < runs; i++) {
            points[i] = new Point(0, 0);
        }

        //create double array of runs size which stores RMS from the origin
        double[] RMS = new double[n];
        //keep track of traversed points
        Point[][] traversed = new Point[runs][n];
        // do random walk n times
        Random rand = new Random(seed);
        for (int i = 0; i < n; i++) {
            runAndAverage(runs, RMS, points, traversed, i, rand);
        }

        double[] rmsRegression = new double[2];
        fit_rs(rmsRegression, RMS);

        //plot
        draw(n, RMS);
        System.out.println("Fitted equation for <rms(t)> : " + rmsRegression[0] + " * "
                + "x^" + rmsRegression[1]);

    }

    /**
     * Runs the randomwalker algorithm once, and calculates the values for rms
     *
     * @param runs     number of runs
     * @param points   the array to store the current coordinate of randomwalker of each run
     * @param traversed the array to store the traversed coordinates of the RWs
     */
    private static void runAndAverage(int runs, double[] averageRMS, Point[] points,
                                      Point[][] traversed, int index, Random rand) {
        String[] directions = {"y+1", "y-1", "x+1", "x-1"};

        double r2Sum = 0;
        for (int i = 0; i < runs; i++) {
            boolean[] moves = new boolean[4];
            makeAllTrue(moves);
            boolean movable = true;

            while(movable){
                int choice = rand.nextInt(3);
                //move randomwalker in 2D by 1 step
                if(points[i] == null){
                    break;
                }
                int x = points[i].getX(), y = points[i].getY();
                double r2;
                String dir = directions[choice];

                if (dir == "y+1") {
                    if (checkIntersection(x, y + 1, traversed, i)) {
                        moves[2] = false;
                        if(checkAllFalse(moves)){
                            movable = false;
                            points[i] = null;
                        }
                        continue;
                    }
                    y += 1;
                } else if (dir == "y-1") {
                    if (checkIntersection(x, y - 1, traversed, i)) {
                        moves[3] = false;
                        if(checkAllFalse(moves)){
                            movable = false;
                            points[i] = null;
                        }
                        continue;
                    }
                    y -= 1;
                } else if (dir == "x+1") {
                    if (checkIntersection(x + 1, y, traversed, i)) {
                        moves[0] = false;
                        if(checkAllFalse(moves)){
                            movable = false;
                            points[i] = null;
                        }
                        continue;
                    }
                    x += 1;
                } else if (dir == "x-1") {
                    if (checkIntersection(x - 1, y, traversed, i)) {
                        moves[1] = false;
                        if(checkAllFalse(moves)){
                            movable = false;
                            points[i] = null;
                        }
                        continue;
                    }
                    x -= 1;
                }

                points[i] = new Point(x, y);
                traversed[i][index] = points[i];

                r2 = x * x + y * y;
                r2Sum = r2 + r2Sum;
                movable = false;
            }

        }
        averageRMS[index] = Math.sqrt(r2Sum / (double) runs);
    }

    // Checks if an array of booleans is all false
    public static boolean checkAllFalse(boolean[] moves) {
        for (int i = 0; i < moves.length; i++){
            if(moves[i]){
                return false;
            }
        }
        return true;
    }

    // Makes an array of booleans all true
    public static void makeAllTrue(boolean[] moves) {
        for (int i = 0; i < moves.length; i++){
            moves[i] = true;
        }
    }

    /**
     * Checks if the target x and y is already traversed by RW
     */
    private static boolean checkIntersection(int x, int y, Point[][] traversed, int run) {
        for (int i = 0; i < traversed[run].length; i++) {
            if (traversed[run][i] != null) {
                Point point = traversed[run][i];
                if (point.getX() == x && point.getY() == y) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void draw(int n, double[] rms) {
        // initialize drawing parameters
        RandomWalk_rs.StdDraw.setPenRadius(0.002);
        RandomWalk_rs.StdDraw.setPenColor(RandomWalk_rs.StdDraw.BLACK);
        RandomWalk_rs.StdDraw.setXscale(0, (double) n - 1);

        //draw step averages, or <r(t)>
        double averageMax = rms[rms.length - 1];
        RandomWalk_rs.StdDraw.setYscale(0, averageMax * 1.1);
        RandomWalk_rs.StdDraw.text((double) n / 2, averageMax, "<r(t)>");
        for (int i = 0; i < n - 1; i++){
            RandomWalk_rs.StdDraw.line(i, rms[i], i + 1, rms[i + 1]);
        }
        for (int j = 1; j <= n; j++){
            System.out.println("<r(" + j + ")> :" +  rms[j - 1]);
        }
        StdDraw.pause(2000);

    }

    public static void fit_rs(double[] rmsRegression, double[] RMS) {
        int n = RMS.length;
        double[] ln_x = new double[n];
        double[] ln_r = new double[n];
        //transform the data using natural log
        for (int i = 0; i < n; i++){
            ln_x[i] = Math.log(i + 1);
            ln_r[i] = Math.log(RMS[i]);
        }
        //do simple linear regression on the transformed data to find the slope and intercept
        double rsum_x = 0, rsum_y = 0, rsumx2 = 0, rsumy2 = 0, rsumxy = 0;
        for (int i = 0; i < n; i++){
            rsum_x = rsum_x + ln_x[i];
            rsum_y = rsum_y + ln_r[i];
            rsumxy = rsumxy + ln_x[i] * ln_r[i];
            rsumx2 = rsumx2 + ln_x[i] * ln_x[i];
            rsumy2 = rsumy2 + ln_r[i] * ln_r[i];
        }

        double r_slope, r_intcpt;

        r_intcpt = (rsum_y * rsumx2 - rsum_x * rsumxy) / (n * rsumx2 - rsum_x * rsum_x);
        r_slope = (n * rsumxy - rsum_x * rsum_y) / (n * rsumx2 - rsum_x * rsum_x);
        rmsRegression[0] = Math.exp(r_intcpt);
        rmsRegression[1] = r_slope;
    }

}
