import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class SelfAvoidRW {
    private static final double pi2 = 2.0 * Math.PI;
    private Vertex curVertex;
    private int[] distances;
    private int distance;
    private boolean isStuck;
    private int hashKey;
    private HashMap<Integer, String> traversed;
    private Graph_M graph;

    public SelfAvoidRW(Vertex vertex, int steps, Graph_M graph) {
        this.curVertex = vertex;
        this.isStuck = false;
        this.traversed = new HashMap<Integer, String>();
        traversed.put(0, vertex.getName());
        this.hashKey = 1;
        this.distances = new int[steps + 1];
        this.distances[0] = 0;
        this.distance = 0;
        this.graph = graph;
    }

    public void stuck() {
        this.isStuck = true;
    }

    public boolean movable(){
        return !this.isStuck;
    }

    public int getHashKey(){
        return this.hashKey;
    }

    public void setCurVertex(Vertex curVertex) {
        this.curVertex = curVertex;
    }

    public Vertex getCurVertex() {
        return curVertex;
    }

    public int getDistances(int index) {
        return this.distances[index];
    }

    public void setDistances(int index, int value) {
        this.distances[index] = value;
    }

    public void incrementKey() {
        this.hashKey = this.hashKey + 1;
    }

    /**
     * Runs the randomwalker algorithm once, and calculates the values
     */
    public void runAndAverage(Random rand) {

        Object[] neighbors = curVertex.neighbors.keySet().toArray();
        for (int i = 0; i < neighbors.length; i++){
            System.out.println("neighbor: " + neighbors[i]);
        }
        HashSet<String> blockedMoves = new HashSet<String>();

        while (movable()) {
            int choice = rand.nextInt(neighbors.length);
            //move randomwalker by 1 step
            String nextVertex = (String) neighbors[choice];

            if (checkIntersection(nextVertex)){
                if (!blockedMoves.contains(nextVertex)) {
                    blockedMoves.add(nextVertex);
                }
                System.out.println(curVertex.getName());
                System.out.println(blockedMoves.size() + " , " + curVertex.neighbors.keySet().size());
                if (blockedMoves.size() == curVertex.neighbors.keySet().size()){
                    stuck();
                }

                continue;
            }

            System.out.println("next: " + nextVertex);
            traversed.put(hashKey, nextVertex);
            this.distances[hashKey] = distance + curVertex.neighbors.get(nextVertex);
            curVertex = this.graph.getVertex(nextVertex);
            incrementKey();
            break;
        }

    }

    /**
     * Checks if the target location is already traversed by RW
     */
    private boolean checkIntersection(String vertex) {
        for (int i = 0; i < this.traversed.size(); i++) {
            if (traversed.get(i) != null) {
                String v = traversed.get(i);
                if (v.equals(vertex)) {
                    return true;
                }
            }
        }
        return false;
    }
}
