import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.util.*;
import java.io.*;
import java.util.List;

import javafx.scene.paint.Color;



import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.RED;

public class MapFX extends Application{
    private MenuItem newMap;
    private MenuItem open;
    private MenuItem saveImage;
    private Image image;
    private ImageView imageView;
    private Scene scene;
    private Button newPlaceButton, newConnectionButton, changeConnectionButton, findPathButton;
    private VBox vbox;

    private int amount = 0;

    private PointerInfo a;

    private int x,y;

    private CityCircle c1, c2 = null;
    private BorderPane root;
    private Pane center, top;

    private boolean selected = false;

    private ArrayList<Circle> cityCircleArray = new ArrayList<Circle>();
    //private  ArrayList<Town> townList
    private ListGraph graph = new ListGraph();

    private HashMap<String, String> map = new HashMap<String, String>();

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
        exit.setOnAction(e -> Platform.exit());


        root = new BorderPane();
        root.setPadding(new Insets(7));
        //root.setHgap(5);
        //root.setVgap(5);
        //root.setAlignment(Pos.TOP_CENTER);

        center = new Pane();
        top = new Pane();

        root.setCenter(center);
        root.setTop(top);

        findPathButton = new Button("Find Path");
        findPathButton.setLayoutX(10);
        top.getChildren().add(findPathButton);

        Button showConnectionButton = new Button("Show Connection");
        showConnectionButton.setLayoutX(80);
        top.getChildren().add(showConnectionButton);

        newPlaceButton = new Button("New Place");
        newPlaceButton.setLayoutX(200);
        top.getChildren().add(newPlaceButton);


        newConnectionButton = new Button("New Connection");
        newConnectionButton.setLayoutX(280);
        top.getChildren().add(newConnectionButton);

        changeConnectionButton = new Button("Change Connection");
        changeConnectionButton.setLayoutX(400);
        top.getChildren().add(changeConnectionButton);

        //File file = new File("europa.graph");
        //if (file.exists()){
        //    openFile();
        //}


        vbox.getChildren().add(root);

        image = new Image("europa.gif");
        imageView = new ImageView(image);
        imageView.setVisible(false);

        newPlaceButton.setOnAction(new newPlaceHandler());
        newConnectionButton.setOnAction(new connectHandler());

        center.getChildren().add(imageView);

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
//Man läser in filen (graph, hur?), itererar över den med hjälp av en lista och typ String[] tokens = line.split(";");
        try {
            File file = new File("europa.graph");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null)
                if (!line.isEmpty()) {
                    String[] tokens = line.split(";", 4);
                    System.out.println(line);
                }

        } catch (FileNotFoundException noFile) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No such file found!");
            alert.showAndWait();
        }
        catch (IOException e) {
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

            center.setOnMouseClicked(new placeCircleHandler());
        }
    };

    //LÄGGA TILL EN CONNECTION
    class connectHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            System.out.println("LOL");
        }
    }

    //LÄGG TILL EN STAD
    class placeCircleHandler implements EventHandler<MouseEvent>{

        @Override
        public void handle(MouseEvent event) {
            a = MouseInfo.getPointerInfo();

            Point b = a.getLocation();
            x = (int) b.getX();
            y = (int) b.getY();

            double n = event.getX();
            double m = event.getY();


            String s = String.valueOf(x);
            String o = String.valueOf(y);

            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("Name");
            dialog.setHeaderText("");
            dialog.setContentText("Name of place:");
            Optional<String> result = dialog.showAndWait();

            if (result.isPresent()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Your name: " + result.get());
                addCity(result.get(), n,m);
                alert.showAndWait();
            }

            scene.setCursor(Cursor.DEFAULT);
            newPlaceButton.setDisable(false);

            center.setOnMouseClicked(null);
        }
    }

    //CityCircle.f = event.getSource();



    public void addCity(String name, double xCord, double yCord){


        //CityCircle test = new CityCircle(xCord,yCord,15);
        CityCircle circle = new CityCircle(xCord,yCord,name);

        circle.setFill(BLUE);
        circle.setOnMouseClicked(new ClickHandler());
        cityCircleArray.add(circle);

        Text cityName = new Text(xCord + 20, yCord + 20, name);


        center.getChildren().addAll(circle,cityName);
        //System.out.println(cityCircleArray);

        //graph.add(newTown);

    }

    class ClickHandler implements EventHandler<MouseEvent> {
        public void handle(MouseEvent e) {

            CityCircle f = (CityCircle)e.getSource();

            if(f.isSelected()){
                f.changeSelected(false);

                //När man avmarkerar
                if(f == c1){
                    c1 = null;
                }else{
                    c2 = null;
                }

            }else { //När man markerar
                if(c1 == null){
                    f.changeSelected(true);
                    c1 = f;
                }else if(c2 == null && f != c1){
                    f.changeSelected(true);
                    c2 = f;
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}