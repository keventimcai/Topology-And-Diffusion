import org.graphstream.graph.Graph;

import java.util.*;

public class DiffusionSimulator {


    //Currently only generates some number of 3D self avoiding random walkers
    public static void main(String[] args) {
        // Take integer n and runs, and seed from user
        int n, runs, seed, vertices;
        Scanner sc = new Scanner(System.in);
        System.out.print("Give the seed for random sequence: ");
        seed = sc.nextInt();
        System.out.print("Give the number of random steps: ");
        n = sc.nextInt();
        System.out.print("Give the number runs: ");
        runs = sc.nextInt();
        System.out.print("Give the number of starting vertices for graph: ");
        vertices = sc.nextInt();
        sc.close();


        //Create the random walkers
        //Perhaps we would like to randomly choose starting vertices based on weight
        //This way hot spots are generated

        Random rand = new Random(seed);
        Graph_M g = generateGraph(vertices, rand, 0.5);
        g.display_Map();
        ArrayList<RandomWalker> rws = new ArrayList<RandomWalker>();

        Set<String> ks = g.getVertices().keySet();
        String[] keys = ks.toArray(new String[ks.size()]);

        for (int i = 0; i < runs; i++) {
            int max = g.numVertex();
            int vertex = rand.nextInt(max);
            rws.add(new RandomWalker(g.getVertex(keys[vertex]), g));
        }


        // do random walk n times

        for (int i = 0; i < runs; i++) {
            for (int j = 0; j < n; j++) {
                rws.get(i).run(rand);

            }
        }
    }

    /**
     * Generates graph as stochastic process
     *
     * @return the generated graph
     */
    private static Graph_M generateGraph(int num, Random rand, double probability) {
        Graph_M graph = new Graph_M();

        Integer start = 0;
        Integer end = 1;
        graph.addVertex(start.toString());
        graph.addVertex(end.toString());
        graph.addEdge(start.toString(), end.toString(), 1);
        int i = 2;
        while(i < num) {
            Set<String> ks = graph.getVertices().keySet();
            String[] keys = ks.toArray(new String[ks.size()]);
            int max = graph.numVertex();
            int vertex = rand.nextInt(max);

            if (rand.nextDouble() < probability) {
                Integer index = i;
                graph.addVertex(index.toString());
                graph.addEdge(index.toString(), keys[vertex], 1);
                i++;
            } else {
                startRW(graph, keys[vertex], rand);
            }
        }
        return graph;
    }

    private static void startRW(Graph_M graph, String key, Random rand) {
        RandomWalker rw = new RandomWalker(graph.getVertex(key), graph);
        rw.generateRun(rand);
        while((!rw.getCurVertex().getName().equals(key)) && (rw.getCurVertex().getDegree() != 1)){
            rw.generateRun(rand);
            System.out.println(rw.getCurVertex().getName());
        }
        String curVert = rw.getCurVertex().getName();
        if(curVert != key && !graph.containsEdge(key, curVert)){
            graph.addEdge(key, curVert, 1);
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
