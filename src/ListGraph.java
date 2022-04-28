import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ListGraph {

    private final Map<Town, Set<Edge>> nodes = new HashMap<>();

    public void add(Town town){
            nodes.putIfAbsent(town, new HashSet<>());
    }
    public void remove(Town town){
        nodes.remove(town);
    }
    public void connect(){

    }
    public void disconnect(){

    }
    public void setConnectionWeight(){

    }
    public void getNodes(){

    }
    public void getEdgesFrom(){

    }
    public void getEdgeBetween(){

    }
    public void pathExists(){

    }
    public void getPath(){

    }

    public String toString(){
        StringBuilder builder = new StringBuilder();

        for (Town town : nodes.keySet()) {
            builder.append(town).append(": ").append(nodes.get(town)).append("\n");
        }
        return builder.toString();
    }
}
