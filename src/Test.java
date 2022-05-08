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

        //graph.remove(kiruna);

        graph.connect(stockholm, malmö, "e20", 10);
        graph.connect(stockholm, kiruna, "väg", 1);

        //graph.disconnect(stockholm);
        //graph.setConnectionWeight(stockholm,kiruna, 35.0);

        //graph.getEdgeBetween(stockholm, malmö);

        graph.disconnect(stockholm, malmö);

        //System.out.println(graph.getEdgesFrom(stockholm));

        System.out.println(graph);
    }
}
