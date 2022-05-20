import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class ShowConnectionDialog extends Alert {
    private static TextField name = new TextField();
    private static TextField time = new TextField();
    private String ja;

    private int nej;

    public ShowConnectionDialog(String c1, String c2, String ja, int nej){
        super(AlertType.CONFIRMATION);
        this.ja = ja;
        this.nej = nej;
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);

        grid.addRow(0, new Label(ja), name);
        String s = String.valueOf(nej);
        grid.addRow(1, new Label(s), time);

        getDialogPane().setContent(grid);
    }
}