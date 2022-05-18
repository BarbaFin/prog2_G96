import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CityCircle extends Circle{
    private boolean clicked = false;
    public CityCircle(double x, double y, int i){
        super(x,y,i);
        setFill(Color.BLUE);
        setOnMouseClicked(new clickHandler());
    }

    class clickHandler implements EventHandler<MouseEvent> {
        public void handle(MouseEvent e) {
            if(clicked){
                setFill(Color.RED);
            }else {
                setFill(Color.BLUE);
                clicked =! clicked;
            }


        }
    }


}
