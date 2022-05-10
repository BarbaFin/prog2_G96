import java.util.*;

public class ListGraph<T> implements Graph<T> {
    private final Map<T, Set<Edge <T>>> nodes = new HashMap<>();

    public void add(T T){
            nodes.putIfAbsent(T, new HashSet<>());
    }
    public void remove(T T){
        if (!nodes.containsKey(T)) {
            throw new NoSuchElementException();
        }else {
            nodes.remove(T);
        }
    }
    public void connect(T x, T y, String name, int weight){
        if (!nodes.containsKey(x) || !nodes.containsKey(y)) {
            throw new NoSuchElementException();
        }
        //Det ska in en till exception här!
        else {
            Set<Edge <T>> xEdge = nodes.get(x);
            Set<Edge <T>> yEdge = nodes.get(y);

            xEdge.add(new Edge<T>(y, name, weight));
            yEdge.add(new Edge<T>(x, name, weight));
        }
    }
//Ändra Edge till Edge<T>

    public void disconnect(T x, T y) {

        if (!nodes.containsKey(x) || !nodes.containsKey(y)) {
            throw new NoSuchElementException();
        }
        Edge <T> edgeXY = getEdgeBetween(x,y);
        Edge <T> edgeYX = getEdgeBetween(y,x);

        nodes.get(x).remove(edgeXY);
        nodes.get(y).remove(edgeYX);
        }

    public void setConnectionWeight(T x, T y, int weight) {
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
    public Set<T> getNodes() {
        return Set.copyOf(nodes.keySet());
    }

    public List<Edge<T>> getEdgesFrom(T T){
        LinkedList<Edge<T>> edges = new LinkedList<>();
        if (nodes.containsKey(T)) {
            for (Edge<T> edge : nodes.get(T)) {
                edges.add(edge);
            }
        } else {
            throw new NoSuchElementException();
        }
        return edges;
    }
    public Edge<T> getEdgeBetween(T next, T current) {
        for (Edge<T> edge : nodes.get(next)) {
            if (edge.getDestination().equals(current)) {
                return edge;
            }
        }
        return null;
    }
    public boolean pathExists(T a, T b){
        Set<T> visited = new HashSet<>();
        depthFirstVisitAll(a, visited);
        return visited.contains(b);
    }

    @Override
    public List<Edge<T>> getPath(T from, T to) {
        return null;
    }

    public List<Edge<T>> getPath(T from, T to, Map<T, T> connection) {
        LinkedList<Edge<T>> path = new LinkedList<>();
        T current = to;
        while (!current.equals(from)) {
            T next = connection.get(current);
            Edge<T> edge = getEdgeBetween(next, current);
            path.addFirst(edge);
            current = next;
        }
        return Collections.unmodifiableList(path);
    }

    private void depthFirstVisitAll(T current, Set<T> visited) {
        visited.add(current);
        for (Edge<T> edge : nodes.get(current)) {
            if (!visited.contains(edge.getDestination())) {
                depthFirstVisitAll(edge.getDestination(), visited);
            }
        }
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();

        for (T t : nodes.keySet()) {
            builder.append(t).append(": ").append(nodes.get(t)).append("\n");
        }
        return builder.toString();
    }
}
