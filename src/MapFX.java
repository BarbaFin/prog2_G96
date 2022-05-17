import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MapFX extends Application{

    @Override
    public void start(Stage primaryStage) {

        VBox vbox = new VBox();
        MenuBar menuBar = new MenuBar();
        vbox.getChildren().add(menuBar);

        Menu fileMenu = new Menu("File");
        menuBar.getMenus().add(fileMenu);
        MenuItem newMap = new MenuItem("New Map");
        fileMenu.getItems().add(newMap);
        MenuItem open = new MenuItem("Open");
        fileMenu.getItems().add(open);
        MenuItem save = new MenuItem("Save");
        fileMenu.getItems().add(save);
        MenuItem saveImage = new MenuItem("Save Image");
        fileMenu.getItems().add(saveImage);
        MenuItem exit = new MenuItem("Exit");
        fileMenu.getItems().add(exit);

        FlowPane root = new FlowPane();
        root.setPadding(new Insets(7));
        root.setHgap(5);
        root.setVgap(5);
        root.setAlignment(Pos.TOP_CENTER);
        Button findPathButton = new Button("Find Path");
        root.getChildren().add(findPathButton);
        Button showConnectionButton = new Button("Show Connection");
        root.getChildren().add(showConnectionButton);
        Button newPlaceButton = new Button("New Place");
        root.getChildren().add(newPlaceButton);
        Button newConnectionButton = new Button("New Connection");
        root.getChildren().add(newConnectionButton);
        Button changeConnectionButton = new Button("Change Connection");
        root.getChildren().add(changeConnectionButton);

        vbox.getChildren().add(root);

        Image image = new Image("europa.gif");
        ImageView imageView = new ImageView(image);

        vbox.getChildren().add(imageView);

        Scene scene = new Scene(vbox,620,780);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}