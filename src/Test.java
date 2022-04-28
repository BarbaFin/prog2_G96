public class Test {
    public static void main(String[] args) {
        City stockholm = new City("Stockholm");
        City malmö = new City("Malmö");
        City oslo = new City("Oslo");
        City kiruna = new City("Kiruna");
        City köpenhamn = new City("Köpenhamn");
        City berlin = new City("Berlin");

        ListGraph graph = new ListGraph();
        graph.add(stockholm);
        graph.add(malmö);
        graph.add(oslo);
        graph.add(kiruna);
        graph.add(köpenhamn);
        graph.add(berlin);

        System.out.println("Hello world!");
    }
}
