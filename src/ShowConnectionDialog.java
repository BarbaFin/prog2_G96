import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class ShowConnectionDialog extends Alert {
    private static TextField name = new TextField();
    private static TextField time = new TextField();
    private String edgeName;

    private int edgeTime;

    public ShowConnectionDialog(String c1, String c2, String edgeName, int edgeTime){
        super(AlertType.CONFIRMATION);
        this.edgeName = edgeName;
        this.edgeTime = edgeTime;
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);

        //DET SKA STÅ NAME: (STRING I TEXTFIELD, EJ RÖRBART)
        //DET SKA STÅ TIME: (TIME I TEXTFIELD, EJ RÖRBART)

        grid.addRow(0, new Label(edgeName), name);
        String s = String.valueOf(edgeTime);
        grid.addRow(1, new Label(s), time);

        getDialogPane().setContent(grid);
    }
}