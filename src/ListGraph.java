import java.util.*;

public class ListGraph<T> implements Graph<T> {
    private final Map<T, Set<Edge <T>>> nodes = new HashMap<>();

    public void add(T t){
            nodes.putIfAbsent(t, new HashSet<>());
    }

    //FEL, KOLLA MODULEN
    public void remove(T t){
        if (!nodes.containsKey(t)) {
            throw new NoSuchElementException();
        }else {
            nodes.remove(t);
        }
    }
    public void connect(T x, T y, String name, int weight){
        if (!nodes.containsKey(x) || !nodes.containsKey(y)) {
            throw new NoSuchElementException();
        }
        if(getEdgeBetween(x,y) == null){
            Set<Edge <T>> xedge = nodes.get(x);
            Set<Edge <T>> yedge = nodes.get(y);

            xedge.add(new Edge<T>(y, name, weight));
            yedge.add(new Edge<T>(x, name, weight));
        }
        else {
            throw new IllegalStateException();
        }
    }

    public void disconnect(T x, T y) {

        if (!nodes.containsKey(x) || !nodes.containsKey(y)) {
            throw new NoSuchElementException();
        }
        if(getEdgeBetween(x,y) == null){
            throw new IllegalStateException();
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
                getEdgeBetween(y,x).setWeight(weight);
            } else {
                throw new IllegalArgumentException();
            }
        } else {
            throw new NoSuchElementException();
        }
    }
    public Set<T> getNodes() {
        return Set.copyOf(nodes.keySet());
    }

    public List<Edge<T>> getEdgesFrom(T t){
        LinkedList<Edge<T>> edges = new LinkedList<>();
        if (nodes.containsKey(t)) {
            for (Edge<T> edge : nodes.get(t)) {
                edges.add(edge);
            }
        } else {
            throw new NoSuchElementException();
        }
        return edges;
    }
    public Edge<T> getEdgeBetween(T next, T current) {
        for (Edge<T> edge : nodes.get(next)) {
            if(nodes.get(next) == null || nodes.get(current) == null){
                throw new NoSuchElementException();
            }
            if (edge.getDestination().equals(current)) {
                return edge;
            }
        }
        return null;
    }
    public boolean pathExists(T x, T y){
        if(nodes.get(x) == null || nodes.get(y) == null){
            return false;
        }
        Set<T> visited = new HashSet<>();
        depthFirstVisitAll(x, visited);
        return visited.contains(y);
    }

    //FEL, SKA RETURNERA EN VÄG
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
