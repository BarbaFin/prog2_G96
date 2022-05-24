import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.List;

public class PathDialog extends Alert {
    public PathDialog(List<Edge <CityCircle> > path){

        super(AlertType.CONFIRMATION);
        Pane pane = new Pane();
        TextArea area = new TextArea();
        int total = 0;
        String str = "";
        for(Edge<CityCircle> edge : path){
            total += edge.getWeight();
            str += "to " + edge.getDestination() + " by " + edge.getName() + " takes " + edge.getWeight() + "\n";
        }

        area.setText(str + "Total: " + total);
        area.setDisable(true);
        pane.getChildren().add(area);
        getDialogPane().setContent(pane);
    }
}
