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

        Graph_M g = new Graph_M();
        Graph_M.Create_Metro_Map(g);
        //Create the random walkers
        SelfAvoidRW[] rws = new SelfAvoidRW[runs];
        for (int i = 0; i < runs; i++){
            rws[i] = new SelfAvoidRW(g.getVertex("Botanical Garden~B"), n, g);
        }

        //int radius = estimateRadius(runs);
        //System.out.println(radius);
        //int[] sphere = {0, 0, 0, radius};
        //System.out.println(createWalks(sphere, rws, n));


        // do random walk n times
        Random rand = new Random(seed);
        for (int i = 0; i < runs; i++) {
            for (int j = 0; j < n; j++) {
                if(rws[i].movable()){
                    rws[i].runAndAverage(rand);
                }
            }
        }

        for (int i = 0; i < runs; i++) {
            for (int j = 0; j < n; j++) {
                System.out.println(rws[i].getDistances(j));
            }
        }
    }

//    /**
//     * Given integer runs, return the radius of the circle of random walkers
//     * @param runs the number of runs of the simulation
//     * @return the radius of the random walkers
//     */
//    private static int estimateRadius(int runs) {
//        int points = 0;
//        int radius = 0;
//
//        //When the number of points is equal to or greater than the number of runs,
//        //then return that radius
//        while(points < runs){
//            radius++;
//            points = 0;
//            //goes through each point in the cube of the radius and counts points in the sphere
//            for (int x = -radius - 1; x < radius + 1; x++) {
//                for (int y = -radius - 1; y < radius + 1; y++) {
//                    for (int z = -radius - 1; z < radius + 1; z++) {
//                        if ((- x) * (- x) + (- y) * (- y)
//                                + (- z) * (- z) <= radius * radius) {
//                            points++;
//                        }
//                    }
//                }
//            }
//        }
//        return radius;
//    }
//
//
//    /**
//     * Create random walkers in a sphere
//     * @param c The sphere's coordinates and radius given in (x, y, z, r)
//     * @param rws The array of random walkers
//     * @param steps The number of steps of each random walker
//     * @return The successfully created number of random walkers
//     */
//    private static int createWalks(int[] c, SelfAvoidRW3D[] rws, int steps) {
//        int index = 0;
//
//        int radius = c[3];
//
//        for (int x = -radius - 1; x < radius + 1; x++) {
//            for (int y = -radius - 1; y < radius + 1; y++) {
//                for (int z = -radius - 1; z < radius + 1; z++) {
//                    if ((c[0] - x) * (c[0] - x) + (c[1] - y) * (c[1] - y)
//                            + (c[2] - z) * (c[2] - z) <= c[3] * c[3]) {
//                        //only creates random walker if we have space in the array rws
//                        if (index < rws.length) {
//                            rws[index] = new SelfAvoidRW3D(x, y, z, steps);
//                            index++;
//                        }else{
//                            return index;
//                        }
//                    }
//                }
//            }
//        }
//
//        return index;
//    }

}
