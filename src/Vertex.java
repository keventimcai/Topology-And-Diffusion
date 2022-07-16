import java.util.HashMap;

public class Vertex {
    HashMap<String, Integer> neighbors = new HashMap<String, Integer>();
    private String name;

    public Vertex(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
