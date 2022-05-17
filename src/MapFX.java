import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Collections;
import java.util.Map;

public class MapFX extends Application{
    private MenuItem newMap;
    private MenuItem open;
    private Image image;
    private ImageView imageView;
    @Override
    public void start(Stage primaryStage) {

        VBox vbox = new VBox();
        MenuBar menuBar = new MenuBar();
        vbox.getChildren().add(menuBar);

        Menu fileMenu = new Menu("File");
        menuBar.getMenus().add(fileMenu);
        newMap = new MenuItem("New Map");
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

        image = new Image("europa.gif");
        imageView = new ImageView(image);
        imageView.setVisible(false);


        vbox.getChildren().add(imageView);

        newMap.setOnAction(new newMapHandler());
        open.setOnAction(new openHandler());

        Scene scene = new Scene(vbox,620,780);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    class newMapHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e)
        {
            imageView.setVisible(true);
            imageView.setImage(new Image("europa.gif"));
        }
    };

    class openHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e)
        {
            FileInputStream graph = null;
            try {
                graph = new FileInputStream("europa.graph");
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            ObjectInputStream in = null;
            try {
                in = new ObjectInputStream(graph);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            ;
            try {
                in.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            try {
                graph.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    };

    public static void main(String[] args) {
        launch(args);
    }
}