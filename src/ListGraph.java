import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ListGraph {
<<<<<<< Updated upstream
=======

    public void add(){
>>>>>>> Stashed changes

    private final Map<Town, Set<Edge>> nodes = new HashMap<>();

    public void add(Town town){
            nodes.putIfAbsent(town, new HashSet<>());
    }
    public void remove(){

    }
    public void connect(){

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
        return "LOL";
    }
}
