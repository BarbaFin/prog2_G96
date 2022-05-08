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

    public void disconnect(Town x, Town y) {
        Set<Edge> xTownEdge = nodes.get(x);
        Set<Edge> yTownEdge = nodes.get(y);

        xTownEdge.remove(y);
        yTownEdge.remove(x);
        //System.out.println("TEST" + yTownEdge);
        }

    public void setConnectionWeight(Town x, Town y, double weight) {
        if (nodes.containsKey(x) && nodes.containsKey(y)) {
            if (weight >= 0) {
                getEdgeBetween(x,y).setWeight(weight);
            } else {
                throw new IllegalArgumentException("Weight cant be negative");
            }
        } else {
            throw new NoSuchElementException();
        }
    }
    public Set<Town> getNodes() {
        return Set.copyOf(nodes.keySet());
    }

    public List<Edge> getEdgesFrom(Town town){
        LinkedList<Edge> edges = new LinkedList<>();
        if (nodes.containsKey(town)) {
            for (Edge edge : nodes.get(town)) {
                edges.add(edge);
            }
        } else {
            throw new NoSuchElementException();
        }
        return edges;
    }
    public Edge getEdgeBetween(Town next, Town current) {
        for (Edge edge : nodes.get(next)) {
            if (edge.getDestination().equals(current)) {
                return edge;
            }
        }
        return null;
    }
    public boolean pathExists(Town a, Town b){
        Set<Town> visited = new HashSet<>();
        depthFirstVisitAll(a, visited);
        return visited.contains(b);
    }
    public List<Edge> getPath(Town from, Town to, Map<Town, Town> connection) {
        LinkedList<Edge> path = new LinkedList<>();
        Town current = to;
        while (!current.equals(from)) {
            Town next = connection.get(current);
            Edge edge = getEdgeBetween(next, current);
            path.addFirst(edge);
            current = next;
        }
        return Collections.unmodifiableList(path);
    }

    private void depthFirstVisitAll(Town current, Set<Town> visited) {
        visited.add(current);
        for (Edge edge : nodes.get(current)) {
            if (!visited.contains(edge.getDestination())) {
                depthFirstVisitAll(edge.getDestination(), visited);
            }
        }
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();

        for (Town town : nodes.keySet()) {
            builder.append(town).append(": ").append(nodes.get(town)).append("\n");
        }
        return builder.toString();
    }
}
