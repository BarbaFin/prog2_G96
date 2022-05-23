import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class PathDialog extends Alert {

    private TextArea pathText = new TextArea();

    private String c1, c2, edgeName, text;
    private int time;

    public PathDialog(String c1, String c2, String edgeName, int time, String text ){
        super(AlertType.CONFIRMATION);
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);

        grid.addRow(0,pathText);
        pathText.setText(text);

        getDialogPane().setContent(grid);
    }

    public TextArea getPathText() {
        return pathText;
    }
}
