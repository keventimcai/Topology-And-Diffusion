import java.util.HashMap;

public class Vertex {
    HashMap<String, Integer> neighbors = new HashMap<String, Integer>();
    private String name;
    private int degree;

    public Vertex(String name){
        this.name = name;
        this.degree = 0;
    }
    public void updateDegree(){
        this.degree = this.neighbors.size();
    }

    public int getDegree(){
        return this.degree;
    }
    public String getName() {
        return name;
    }


}
