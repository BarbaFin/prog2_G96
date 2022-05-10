import java.util.Objects;
public class Edge<T> {

    private T destination;
    private String name;
    private int weight;

    public Edge(T destination, String name, int weight) {
        this.destination = Objects.requireNonNull(destination);
        this.name = Objects.requireNonNull(name);

        if (weight < 0) {
            throw new IllegalArgumentException();
        }
        this.weight = weight;
    }

    public T getDestination() {
        return destination;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    //Fel av någon anlednig
    public String toString() {
        return "Till" + destination + "med" + name + "tar" + weight;
    }
}
