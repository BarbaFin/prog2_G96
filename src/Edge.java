import java.util.Objects;
public class Edge {

    private Town destination;
    private String name;
    private double weight;

    public Edge(Town destination, String name, double weight) {
        this.destination = Objects.requireNonNull(destination);
        this.name = Objects.requireNonNull(name);

        if (Double.isNaN(weight)) {
            throw new IllegalArgumentException();
        }
        this.weight = weight;
    }

    public Town getDestination() {
        return destination;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return "destination = " + destination +
                ", name = '" + name + '\'' +
                ", weight = " + weight ;
    }

}
