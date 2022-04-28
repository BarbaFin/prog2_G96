import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.NoSuchElementException;

public class ListGraph {

    private final Map<Town, Set<Edge>> nodes = new HashMap<>();

    public void add(Town town){
            nodes.putIfAbsent(town, new HashSet<>());
    }
    public void remove(Town town){
        if (!nodes.containsKey(town)) {
            throw new NoSuchElementException();
        }else {
            nodes.remove(town);
        }

    }
    public void connect(Town x, Town y, String name, double weight){
        if (nodes.containsKey(x) && nodes.containsKey(y)) {
            add(x);
            add(y);

            Set<Edge> xTownEdge = nodes.get(x);
            Set<Edge> yTownEdge = nodes.get(y);



            xTownEdge.add(new Edge(x, name, weight));
            yTownEdge.add(new Edge(y, name, weight));
        } else {
            throw new NoSuchElementException();
        }

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
