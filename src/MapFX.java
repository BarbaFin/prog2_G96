import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.*;
import java.io.*;
import java.util.List;

import static javafx.scene.paint.Color.BLUE;

public class MapFX extends Application {

    private MenuItem menuSaveImage;
    private MenuItem menuExit;
    private MenuItem menuNewMap;
    private MenuItem menuOpen;
    private MenuItem menuSave;
    private MenuBar menuBar;
    private Image image;
    private ImageView imageView;
    private Scene scene;
    private Button newPlaceButton;
    private Button newConnectionButton;
    private Button changeConnectionButton;
    private Button findPathButton;
    private Button showConnectionButton;
    private VBox vbox;
    private int x;
    private int y;
    private CityCircle c1;
    private CityCircle c2;
    private BorderPane root;
    private Pane center;
    private Pane top;
    private Stage primaryStage;

    private boolean changes;
    //This is the ONE:
    private ListGraph<CityCircle> cities = new ListGraph<>();
    private HashMap<String, CityCircle> nodes = new HashMap<>();
    private ArrayList<CityCircle> allCities = new ArrayList<>();
    private String fileName = "file:europa.gif";
    private String outputFile = "europa.graph";

    @Override
    public void start(Stage primaryStage) {
        changes = false;
        c1 = null;
        c2 = null;
        this.primaryStage = primaryStage;
        vbox = new VBox();
        menuBar = new MenuBar();
        menuBar.setId("menu");
        vbox.getChildren().add(menuBar);

        Menu fileMenu = new Menu("File");
        fileMenu.setId("menuFile");
        menuBar.getMenus().add(fileMenu);

        menuNewMap = new MenuItem("New Map");
        menuNewMap.setId("menuNewMap");
        fileMenu.getItems().add(menuNewMap);

        menuOpen = new MenuItem("Open");
        menuOpen.setId("menuOpenFile");
        fileMenu.getItems().add(menuOpen);

        menuSave = new MenuItem("Save");
        menuSave.setId("menuSaveFile");
        fileMenu.getItems().add(menuSave);

        menuSaveImage = new MenuItem("Save Image");
        menuSaveImage.setId("menuSaveImage");
        fileMenu.getItems().add(menuSaveImage);

        menuExit = new MenuItem("Exit");
        menuExit.setId("menuExit");
        fileMenu.getItems().add(menuExit);

        root = new BorderPane();
        root.setPadding(new Insets(7));

        center = new Pane();
        center.setId("outputArea");
        top = new Pane();

        root.setCenter(center);
        root.setTop(top);

        findPathButton = new Button("Find Path");
        findPathButton.setId("btnFindPath");
        findPathButton.setLayoutX(10);
        top.getChildren().add(findPathButton);

        showConnectionButton = new Button("Show Connection");
        showConnectionButton.setId("btnShowConnection");
        showConnectionButton.setLayoutX(80);
        top.getChildren().add(showConnectionButton);

        newPlaceButton = new Button("New Place");
        newPlaceButton.setId("btnNewPlace");
        newPlaceButton.setLayoutX(200);
        top.getChildren().add(newPlaceButton);

        newConnectionButton = new Button("New Connection");
        newConnectionButton.setId("btnNewConnection");
        newConnectionButton.setLayoutX(280);
        top.getChildren().add(newConnectionButton);

        changeConnectionButton = new Button("Change Connection");
        changeConnectionButton.setId("btnChangeConnection");
        changeConnectionButton.setLayoutX(400);
        top.getChildren().add(changeConnectionButton);

        vbox.getChildren().add(root);

        image = new Image("file:europa.gif");
        imageView = new ImageView(image);
        imageView.setVisible(false);

        newPlaceButton.setOnAction(new NewPlaceHandler());
        newConnectionButton.setOnAction(new ConnectHandler());
        showConnectionButton.setOnAction(new ShowConnectHandler());
        changeConnectionButton.setOnAction(new ChangeTimeHandler());
        findPathButton.setOnAction(new SeekPathHandler());

        center.getChildren().add(imageView);

        menuExit.setOnAction(new ExitHandler());
        menuNewMap.setOnAction(new NewMapHandler());
        menuOpen.setOnAction(new OpenHandler());
        menuSave.setOnAction(new SaveHandler());
        menuSaveImage.setOnAction(new SaveImageHandler());
        primaryStage.setOnCloseRequest(new ExHandler());

        primaryStage.setTitle("PathFinder");

        scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    class ExHandler implements EventHandler<WindowEvent> {
        @Override
        public void handle(WindowEvent e) {
            if (changes) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Unsaved changes, exit anyway?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.CANCEL) {
                    e.consume();
                }
            }
        }
    }

    class NewMapHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            newMap();
        }
    }

    private void newMap() {
        if (changes) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Unsaved changes, continue anyway?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                c1 = null;
                c2 = null;
                cities = new ListGraph<>();
                center.getChildren().retainAll(imageView);
                imageView.setVisible(true);
                imageView.setImage(new Image(fileName));
                primaryStage.sizeToScene();
                changes = true;
            }
        } else {
            c1 = null;
            c2 = null;
            cities = new ListGraph<>();
            center.getChildren().retainAll(imageView);
            imageView.setVisible(true);
            imageView.setImage(new Image(fileName));
            primaryStage.sizeToScene();
            changes = true;
        }
    }

    private void openFile() {

        try {
            c1 = null;
            c2 = null;
            changes = true;
            cities = new ListGraph<>();
            center.getChildren().retainAll(imageView);
            BufferedReader in = new BufferedReader(new FileReader(outputFile));

            String line = in.readLine();
            fileName = line;

            Image image = new Image(fileName);
            imageView.setVisible(true);
            imageView.setImage(image);
            primaryStage.sizeToScene();

            line = in.readLine();

            String[] tokens = line.split(";");
            for (int i = 0; i < tokens.length; i += 3) {
                String name = tokens[i];
                double x = Double.parseDouble(tokens[i + 1]);
                double y = Double.parseDouble(tokens[i + 2]);
                CityCircle location = new CityCircle(x, y, name);
                addName(name, x, y);
                nodes.put(name, location);
                cities.add(location);
                center.getChildren().add(location);
                allCities.add(location);
                location.setOnMouseClicked(new ClickHandler());
                location.setId(name);
            }

            while ((line = in.readLine()) != null) {
                tokens = line.split(";");
                CityCircle from = nodes.get(tokens[0]);
                CityCircle to = nodes.get(tokens[1]);
                String name = tokens[2];
                int time = Integer.parseInt(tokens[3]);

                if (cities.getEdgeBetween(from, to) == null) {
                    Line test = new Line(from.getCenterX(), from.getCenterY(), to.getCenterX(), to.getCenterY());
                    test.setDisable(true);
                    test.setStrokeWidth(5);
                    test.setDisable(true);
                    center.getChildren().addAll(test);
                    cities.connect(from, to, name, time);
                }

            }

        } catch (FileNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No file found!");
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class OpenHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            if (changes) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Unsaved changes, continue anyway?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {
                    openFile();
                }
            } else {
                openFile();
            }
        }
    }

    class SaveHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            try {
                File file = new File(outputFile);
                BufferedWriter bf = null;

                bf = new BufferedWriter(new FileWriter(file));
                bf.write(image.getUrl());
                bf.newLine();

                for (CityCircle town : cities.getNodes()) {
                    bf.write(town + ";" + town.getCenterX() + ";" + town.getCenterY() + ";");

                }
                bf.newLine();

                for (CityCircle town : cities.getNodes()) {
                    for (Edge edge : cities.getEdgesFrom(town)) {
                        bf.write(town + ";" + edge);
                        bf.newLine();
                    }
                }

                changes = false;
                bf.flush();
                bf.close();
            } catch (IOException i) {
                i.printStackTrace();
            }
        }
    }

    class ExitHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            primaryStage.fireEvent(new WindowEvent(primaryStage, WindowEvent.WINDOW_CLOSE_REQUEST));
        }
    }

    class SaveImageHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            try {
                WritableImage image = imageView.snapshot(null, null);
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
                ImageIO.write(bufferedImage, "png", new File("capture.png"));
            } catch (IOException i) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "IO-fel " + i.getMessage());
                alert.showAndWait();
            }
        }
    }

    class NewPlaceHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            scene.setCursor(Cursor.CROSSHAIR);
            newPlaceButton.setDisable(true);

            center.setOnMouseClicked(new PlaceCircleHandler());
        }
    }

    class ShowConnectHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            if (c1 == null || c2 == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Mark two cities");
                alert.showAndWait();
            } else if (cities.getEdgeBetween(c1, c2) == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "No connection between cities!");
                alert.showAndWait();
            } else {
                cities.getEdgeBetween(c1, c2);

                String connectionName = cities.getEdgeBetween(c1, c2).getName();
                int connectionInt = cities.getEdgeBetween(c1, c2).getWeight();

                ShowConnectionDialog dialog = new ShowConnectionDialog(c1.getName(), c1.getName(), connectionName, connectionInt, 1);
                dialog.setHeaderText("Connection from " + c1.getName() + " to " + c2.getName());

                dialog.showAndWait();
            }
        }
    }

    class ConnectHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            if (c1 == null || c2 == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Mark two cities");
                alert.showAndWait();
            } else if (cities.getEdgeBetween(c1, c2) == null) {

                ConnectionDialog dialog = new ConnectionDialog();

                Optional<ButtonType> answer = dialog.showAndWait();
                if (answer.isPresent() && answer.get() != ButtonType.OK) {
                    return;
                }

                else {
                    String name = ConnectionDialog.getName();
                    int time = ConnectionDialog.getTime();

                    cities.connect(c1, c2, name, time);

                    Line line = new Line(c1.getCenterX(), c1.getCenterY(), c2.getCenterX(), c2.getCenterY());
                    line.setDisable(true);
                    line.setStrokeWidth(5);
                    center.getChildren().addAll(line);
                    changes = true;
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Already connected!");
                alert.showAndWait();
            }
        }
    }
    class SeekPathHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            if (c1 == null || c2 == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Mark two cities!");
                alert.showAndWait();
            } else if (cities.getPath(c1, c2) == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "No path exists!");
                alert.showAndWait();
            } else if (!cities.pathExists(c1, c2)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "No connection between cities!");
                alert.showAndWait();
            } else {
                List<Edge<CityCircle>> path = cities.getPath(c1, c2);
                PathDialog dialog = new PathDialog(path);
                dialog.setTitle("Message");

                dialog.setHeaderText("Path from " + c1.getName() + " to " + c2.getName());
                dialog.showAndWait();
            }
        }
    }

    class ChangeTimeHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            if (c1 == null || c2 == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Mark two cities!");
                alert.showAndWait();
            } else if (!cities.pathExists(c1, c2)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "No connection between cities!");
                alert.showAndWait();
            } else {
                cities.getEdgeBetween(c1, c2);
                String connectionName = cities.getEdgeBetween(c1, c2).getName();
                int connectionInt = cities.getEdgeBetween(c1, c2).getWeight();

                ShowConnectionDialog dialog = new ShowConnectionDialog(c1.getName(), c1.getName(), connectionName, connectionInt, 2);

                dialog.setHeaderText("Connection from " + c1.getName() + " to " + c2.getName());
                dialog.showAndWait();

                if (dialog.getTime() == 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Only numbers over 0 accepted!");
                    alert.showAndWait();
                } else {
                    cities.setConnectionWeight(c1, c2, dialog.getTime());
                    changes = true;
                }
            }
        }
    }

    class PlaceCircleHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            double x = event.getX();
            double y = event.getY();

            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("Name");
            dialog.setHeaderText("");
            dialog.setContentText("Name of place:");
            Optional<String> result = dialog.showAndWait();

            if (result.isPresent()) {
                addCity(result.get(), x, y);
                addName(result.get(), x, y);
            }

            scene.setCursor(Cursor.DEFAULT);
            newPlaceButton.setDisable(false);
            center.setOnMouseClicked(null);
        }
    }

    public void addCity(String name, double x, double y) {
        CityCircle circle = new CityCircle(x, y, name);

        circle.setFill(BLUE);
        circle.setOnMouseClicked(new ClickHandler());
        circle.setId(name);

        allCities.add(circle);
        cities.add(circle);
        center.getChildren().addAll(circle);
        changes = true;
    }

    public void addName(String name, double x, double y) {
        Text cityName = new Text(x + 20, y + 20, name);
        cityName.setFont(Font.font("verdana", FontWeight.BOLD, 12));

        center.getChildren().addAll(cityName);
    }

    class ClickHandler implements EventHandler<MouseEvent> {
        public void handle(MouseEvent e) {
            CityCircle f = (CityCircle) e.getSource();

            if (f.isSelected()) {
                f.changeSelected(false);

                if (f == c1) {
                    c1 = null;
                } else {
                    c2 = null;
                }

            } else {
                if (c1 == null) {
                    f.changeSelected(true);
                    c1 = f;
                } else if (c2 == null && f != c1) {
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