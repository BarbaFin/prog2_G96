import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
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
import javafx.scene.image.WritableImage;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.io.*;
import java.util.Optional;

import static javafx.scene.paint.Color.RED;

public class MapFX extends Application{
    private MenuItem newMap;
    private MenuItem open;
    private MenuItem saveImage;
    private Image image;
    private ImageView imageView;
    private Scene scene;
    private Button newPlaceButton;
    private VBox vbox;

    private PointerInfo a;

    private int x,y;

    private Circle circle;

    private ArrayList<Circle> cityCircleArray = new ArrayList<Circle>();;

    private ListGraph graph = new ListGraph();

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

        saveImage = new MenuItem("Save Image");
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
        saveImage.setOnAction(new saveImageHandler());

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

    class saveImageHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            try {
                WritableImage image = imageView.snapshot(null, null);
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
                ImageIO.write(bufferedImage, "png", new File("capture.png"));
            } catch (IOException i) {
                Alert alert = new Alert(Alert.AlertType.ERROR,"IO-fel "+i.getMessage());
                alert.showAndWait();
            }
        }
    }


    //FÖR ATT LÄGGA TILL EN NY STAD PÅ KARTAN
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
                    x = (int) b.getX();
                    y = (int) b.getY();

                    String s = String.valueOf(x);
                    String o = String.valueOf(y);

                    TextInputDialog dialog = new TextInputDialog("");
                    dialog.setTitle("Name");
                    dialog.setHeaderText("");
                    dialog.setContentText("Name of place:");
                    Optional<String> result = dialog.showAndWait();

                    if (result.isPresent()){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Your name: " + result.get());
                        AddCity(result.get());
                        alert.showAndWait();
                    }

                    scene.setCursor(Cursor.DEFAULT);
                    newPlaceButton.setDisable(false);

                    vbox.setOnMouseClicked(null);
                }
            });
        }
    };


    public void AddCity(String name){
        Town newTown = new Town(name);

        circle = new Circle(x, y, 10);
        circle.setFill(RED);

        Circle lol;

        lol = new Circle(x,y,10);

        vbox.getChildren().add(lol);




        //cityCircleArray


        graph.add(newTown);
        System.out.println(graph);
    }
    public static void main(String[] args) {
        launch(args);
    }
}