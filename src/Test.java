public class Test {
    public static void main(String[] args) {
        Town stockholm = new Town("Stockholm");
        Town malmö = new Town("Malmö");
        Town oslo = new Town("Oslo");
        Town kiruna = new Town("Kiruna");
        Town köpenhamn = new Town("Köpenhamn");
        Town berlin = new Town("Berlin");

        ListGraph graph = new ListGraph();
        graph.add(stockholm);
        graph.add(malmö);
        graph.add(oslo);
        graph.add(kiruna);
        graph.add(köpenhamn);
        graph.add(berlin);

        graph.remove(kiruna);

        graph.connect(stockholm,malmö, "e20", 10);

        System.out.println(graph);
    }
}
