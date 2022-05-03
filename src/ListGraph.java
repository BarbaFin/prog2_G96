import java.util.*;

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
        if (!nodes.containsKey(x) || !nodes.containsKey(y)) {
            throw new NoSuchElementException();
        }
        //Det ska in en till exception här!
        else {
            Set<Edge> xTownEdge = nodes.get(x);
            Set<Edge> yTownEdge = nodes.get(y);

            xTownEdge.add(new Edge(y, name, weight));
            yTownEdge.add(new Edge(x, name, weight));
        }
    }
    public void disconnect(Town x, Town y){
        Set<Edge> xTownEdge = nodes.get(x);
        Set<Edge> yTownEdge = nodes.get(y);

        yTownEdge.remove(nodes.get(x));
        xTownEdge.remove(nodes.get(y));

         System.out.println(": " + xTownEdge);
        System.out.println(": " + yTownEdge);

    }
    public void setConnectionWeight(Town x, Town y, double weight){

    }
    public void getNodes(){
        HashMap<Town, Set<Edge>> nodesCopy = new HashMap<>();
    }
    public void getEdgesFrom(){

    }
    public Edge getEdgeBetween(Town next, Town current) {
        for (Edge edge : nodes.get(next)) {
            if (edge.getDestination().equals(current)) {
                return edge;
            }
        }
        return null;
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
