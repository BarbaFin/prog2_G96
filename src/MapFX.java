import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.Light;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.util.Collections;
import java.util.Map;
import java.io.*;
import java.util.Optional;

public class MapFX extends Application{
    private MenuItem newMap;
    private MenuItem open;
    private Image image;
    private ImageView imageView;
    private Scene scene;
    private Button newPlaceButton;
    private VBox vbox;

    private PointerInfo a;

    @Override
    public void start(Stage primaryStage) {

        vbox = new VBox();
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

        newPlaceButton = new Button("New Place");
        root.getChildren().add(newPlaceButton);


        Button newConnectionButton = new Button("New Connection");
        root.getChildren().add(newConnectionButton);
        Button changeConnectionButton = new Button("Change Connection");
        root.getChildren().add(changeConnectionButton);

        File file = new File("europa.graph");
        if (file.exists()){
            openFile();
        }



        vbox.getChildren().add(root);

        image = new Image("europa.gif");
        imageView = new ImageView(image);
        imageView.setVisible(false);

        newPlaceButton.setOnAction(new newPlaceHandler());

        vbox.getChildren().add(imageView);

        newMap.setOnAction(new newMapHandler());
        open.setOnAction(new openHandler());

        scene = new Scene(vbox,620,780);
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

    private void openFile(){
        try {
            FileInputStream graph = new FileInputStream("europa.graph");
            ObjectInputStream in = new ObjectInputStream(graph);;
            in.close();
            graph.close();
        } catch (FileNotFoundException noFile) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "test");

            alert.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    class openHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e)
        {
            openFile();
        }
    };

    class newPlaceHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e)
        {
            scene.setCursor(Cursor.CROSSHAIR);
            newPlaceButton.setDisable(true);

            vbox.setOnMouseClicked(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    a = MouseInfo.getPointerInfo();

                    Point b = a.getLocation();
                    int x = (int) b.getX();
                    int y = (int) b.getY();

                    String s = String.valueOf(x);
                    String o = String.valueOf(y);

                    TextInputDialog dialog = new TextInputDialog("");
                    dialog.setTitle("Name");
                    dialog.setHeaderText("");
                    dialog.setContentText("Name of place:");
                    Optional<String> result = dialog.showAndWait();

                    //Alert alert = new Alert(Alert.AlertType.INFORMATION, "");
                    //alert.showAndWait();
                    scene.setCursor(Cursor.DEFAULT);
                    newPlaceButton.setDisable(false);
                }
            });
        }
    };

/*


 */


    public static void main(String[] args) {
        launch(args);
    }
}