import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class analysis {
    private final String DATAPATH = "data.csv";
    private final String DEGREEPATH = "degree.csv";
    private final String GRAPHPATH = "graph.csv";
    private HashMap<String, Integer> frequency;
    private HashMap<String, Integer> degree;

    void addNodes(HashMap<String, Vertex> vertex) {
        frequency = new HashMap<String, Integer>();
        String[] vertices = vertex.keySet().toArray(new String[0]);
        for (String i : vertices) {
            frequency.put(i, 0);
        }
    }

    void getDegree(HashMap<String, Vertex> vertex) {
        degree = new HashMap<String, Integer>();
        Vertex[] vertices = vertex.values().toArray(new Vertex[0]);
        for (Vertex i : vertices) {
            degree.put(i.getName(), i.neighbors.keySet().size());
        }
    }

    void updateFre(String vertex) {
        frequency.put(vertex, frequency.get(vertex) + 1);
    }

    void test() {
        //
    }

    void setup(){
        addNodes(Graph_M.vertices);
    }

    void saveGraphAndDegree(HashMap<String, Vertex> vertex) {
        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.GRAPHPATH), "UTF-8"));
            BufferedWriter outDegree = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.DEGREEPATH), "UTF-8"));
            out.write("node\tneighbors\n");
            out.flush();
            outDegree.write("node\tdegree\n");
            outDegree.flush();
            for(String i:vertex.keySet().toArray(new String[0])){
                Vertex tmp=vertex.get(i);
                out.write(i+"\t");
                outDegree.write(i+"\t"+tmp.neighbors.size()+"\n");
                for(String j:tmp.neighbors.keySet().toArray(new String[0])){
                    out.write(j+",");
                }
                out.write("\n");
            }
            out.flush();
            out.close();
            outDegree.flush();
            outDegree.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    void initializeSaving() {
        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.DATAPATH), "UTF-8"));
            out.write("step\tnode\twallker\n");
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("create the csvs");
    }

    void saveData(Integer step, String node, String walker) {// save data to csv, the python file will process the data
        try {
            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(this.DATAPATH, true), "UTF-8"));
            out.write(step.toString() + "\t" + node + "\t" + walker + "\n");
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // System.out.println("saving the data");
    }
}
