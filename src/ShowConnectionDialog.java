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
    private int edgeTime, nr;

    public ShowConnectionDialog(String c1, String c2, String edgeName, int edgeTime, int nr){
        super(AlertType.CONFIRMATION);
        this.edgeName = edgeName;
        this.edgeTime = edgeTime;
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        name.setEditable(false);

        //DET SKA STÅ NAME: (STRING I TEXTFIELD, EJ RÖRBART)
        //DET SKA STÅ TIME: (TIME I TEXTFIELD, EJ RÖRBART)

        if(nr == 1){
            name.setEditable(false);
            time.setEditable(false);
            String q = String.valueOf(edgeTime);
            time.setText(q);
            nr = 0;
        }if(nr == 2){
            time.clear();
            nr = 0;
        }

        name.setText(edgeName);
        grid.addRow(0, new Label("Name: "), name);
        String s = String.valueOf(edgeTime);
        grid.addRow(1, new Label("Time: "), time);

        getDialogPane().setContent(grid);

    }

    public int getTime(){
        return Integer.parseInt(time.getText());
    }
}