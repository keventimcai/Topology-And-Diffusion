import java.util.Random;
import java.util.Scanner;

public class DiffusionSimulator {


    //Currently only generates some number of 3D self avoiding random walkers
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
        Point_3D[] points = new Point_3D[runs];

        SelfAvoidRW3D[] rws = new SelfAvoidRW3D[runs];

        for (int i = 0; i < runs; i++) {
            rws[i] = new SelfAvoidRW3D(n);
        }

        // do random walk n times
        Random rand = new Random(seed);
        for (int i = 0; i < runs; i++) {
            for (int j = 0; j < n; j++){
                rws[j].runAndAverage(rand);
            }
        }

        for (int i = 0; i < runs; i++) {
            for (int j = 0; j < n; j++){
                System.out.println(rws[j].getR2(i));
            }
        }
    }

}
