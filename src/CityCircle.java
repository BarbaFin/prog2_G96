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
import java.util.ArrayList;
import java.util.Objects;

public class CityCircle extends Circle{
    private boolean selected = false;
    private MapFX getCircleList = new MapFX();
    //private int i;
    public CityCircle(double x, double y,int i){
        super(x,y,i);
        setFill(Color.BLUE);
        setOnMouseClicked(new clickHandler());
    }

    class clickHandler implements EventHandler<MouseEvent> {
        public void handle(MouseEvent e) {

            /*
            CityCircle f = (CityCircle) e.getSource();

            for(int i = 0; i < 1; i++){
                getCircleList.getList().get(i);
                System.out.println(getCircleList);
            }
            */

            if(selected){
                setFill(Color.RED);
            }else {
                setFill(Color.BLUE);
            }
            selected =! selected;

        }
    }
}
