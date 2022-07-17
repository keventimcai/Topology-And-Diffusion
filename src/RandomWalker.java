import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class RandomWalker {
    private Vertex curVertex;
    private ArrayList<Integer> distances;
    private int distance;
    private int hashKey;
    private Graph_M graph;

    public RandomWalker(Vertex vertex, Graph_M graph) {
        this.curVertex = vertex;
        this.hashKey = 1;
        this.distances = new ArrayList<Integer>();
        this.distances.add(0);
        this.distance = 0;
        this.graph = graph;
    }

    public int getHashKey() {
        return this.hashKey;
    }

    public void setCurVertex(Vertex curVertex) {
        this.curVertex = curVertex;
    }

    public Vertex getCurVertex() {
        return curVertex;
    }

    public int getDistances(int index) {
        return this.distances.get(index);
    }

    public void setDistances(int index, int value) {
        this.distances.set(index, value);
    }

    public void incrementKey() {
        this.hashKey = this.hashKey + 1;
    }

    /**
     * Runs the randomwalker algorithm once, and calculates the values
     */
    public void run(Random rand) {

        Object[] neighbors = curVertex.neighbors.keySet().toArray();
        HashSet<String> blockedMoves = new HashSet<String>();

        int choice = rand.nextInt(neighbors.length);
        //move randomwalker by 1 step
        String nextVertex = (String) neighbors[choice];
        this.distances.add(distance + curVertex.neighbors.get(nextVertex));
        this.graph.updateEdge(curVertex.getName(), nextVertex);
        curVertex = this.graph.getVertex(nextVertex);
        incrementKey();
    }

    public void generateRun(Random rand){
        Object[] neighbors = curVertex.neighbors.keySet().toArray();
        HashSet<String> blockedMoves = new HashSet<String>();

        int choice = rand.nextInt(neighbors.length);
        //move randomwalker by 1 step
        String nextVertex = (String) neighbors[choice];
        curVertex = this.graph.getVertex(nextVertex);
        incrementKey();
    }
}
