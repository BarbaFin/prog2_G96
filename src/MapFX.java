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
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
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
    private Button newPlaceButton, newConnectionButton, changeConnectionButton, findPathButton,showConnectionButton;
    private VBox vbox;

    private int amount = 0;

    private PointerInfo a;

    private int x,y;

    private CityCircle c1, c2 = null;
    private BorderPane root;
    private Pane center, top;

    private ArrayList<Circle> cityCircleArray = new ArrayList<Circle>();

    ListGraph<CityCircle> cities = new ListGraph<>();

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

        showConnectionButton = new Button("Show Connection");
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
        showConnectionButton.setOnAction(new showConnectHandler());
        changeConnectionButton.setOnAction(new changeTimeHandler());
        findPathButton.setOnAction(new seekPathHandler());

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
        try {
            File file = new File("europa.graph");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null)
                //if (!line.isEmpty()) {
                //String[] tokens = line.split(";", 3);
                    System.out.println(line);

                //}

            //for (int i =0; i < tokens.length; i++) {

            //}

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

    //VISA CONNECTION, ej klar
    class showConnectHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            if(c1 == null ||c2 == null){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Mark two cities");
                alert.showAndWait();
            } else if (cities.getEdgeBetween(c1,c2) == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "No connection between cities!");
                alert.showAndWait();
            } else {
                cities.getEdgeBetween(c1,c2);

                String connectionName = cities.getEdgeBetween(c1,c2).getName();
                int connectionInt = cities.getEdgeBetween(c1,c2).getWeight();


                ShowConnectionDialog dialog = new ShowConnectionDialog(c1.getName(), c1.getName(), connectionName, connectionInt, 1);
                dialog.setHeaderText("Connection from " + c1.getName() + " to " + c2.getName());

                dialog.showAndWait();
            }
        }
    }

    //LÄGGA TILL EN CONNECTION, ej klar
    class connectHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            if(c1 == null ||c2 == null){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Mark two cities");
                alert.showAndWait();
            }else {
                ConnectionDialog dialog = new ConnectionDialog();

                Optional<ButtonType> answer = dialog.showAndWait();
                if(answer.isPresent() && answer.get() != ButtonType.OK){
                    return;
                }
                //FUNGERAR INTE
                /*else if(ConnectionDialog.getName() == null || ConnectionDialog.getName().contains("[a-zA-Z]+") == false){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Must contain a name");
                    alert.showAndWait();
                    //SKA IN EN TILL ALERT HÄR
                }

                 */
                else {
                    String name = ConnectionDialog.getName();
                    int time = ConnectionDialog.getTime();

                    cities.connect(c1,c2,name,time);

                    Line line = new Line(c1.getCenterX(), c1.getCenterY(), c2.getCenterX(), c2.getCenterY());
                    center.getChildren().addAll(line);
                }
            }
        }
    }

    //SE VÄGEN MELLAN TVÅ STÄDER
    class seekPathHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            if(c1 == null || c2 == null){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Mark two cities!");
                alert.showAndWait();
            }
            if(!cities.pathExists(c1,c2)){
                Alert alert = new Alert(Alert.AlertType.ERROR, "No connection between cities!");
                alert.showAndWait();
            }

            else {

                cities.getEdgeBetween(c1,c2);
                String connectionName = cities.getEdgeBetween(c1,c2).getName();
                int connectionInt = cities.getEdgeBetween(c1,c2).getWeight();
                cities.getPath(c1,c2);


                PathDialog dialog = new PathDialog(c1.getName(),c2.getName(),connectionName,connectionInt, "HEYO");

                dialog.setHeaderText("Connection from " + c1.getName() + " to " + c2.getName());
                dialog.showAndWait();


                dialog.setHeaderText("Connection from " + c1.getName() + " to " + c2.getName());
                dialog.showAndWait();
            }
        }
    }

    //ÄNDRA EN CONNECTION TIME
    class changeTimeHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            if(c1 == null || c2 == null){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Mark two cities!");
                alert.showAndWait();
            }
            if(!cities.pathExists(c1,c2)){
                Alert alert = new Alert(Alert.AlertType.ERROR, "No connection between cities!");
                alert.showAndWait();
            }
            else {
                cities.getEdgeBetween(c1,c2);
                String connectionName = cities.getEdgeBetween(c1,c2).getName();
                int connectionInt = cities.getEdgeBetween(c1,c2).getWeight();

                ShowConnectionDialog dialog = new ShowConnectionDialog(c1.getName(), c1.getName(), connectionName, connectionInt, 2);

                dialog.setHeaderText("Connection from " + c1.getName() + " to " + c2.getName());
                dialog.showAndWait();

                cities.setConnectionWeight(c1, c2, dialog.getTime());
            }
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

    public void addCity(String name, double xCord, double yCord){

        CityCircle circle = new CityCircle(xCord,yCord,name);

        circle.setFill(BLUE);
        circle.setOnMouseClicked(new ClickHandler());
        cityCircleArray.add(circle);

        Text cityName = new Text(xCord + 20, yCord + 20, name);
        cities.add(circle);

        center.getChildren().addAll(circle,cityName);
    }

    //LÄGG TILL CIRKEL
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