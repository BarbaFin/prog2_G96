import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class ConnectionDialog extends Alert {
    private static TextField name = new TextField();
    private static TextField time = new TextField();

    public ConnectionDialog(){
        super(AlertType.CONFIRMATION);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);

        grid.addRow(0, new Label("Name: "), name);
        grid.addRow(1, new Label("Time: "), time);

        getDialogPane().setContent(grid);
    }

    public static String getName(){
        return name.getText();
    }

    public static int getTime(){
        int test = Integer.parseInt(time.getText());
        return test;
    }

}
