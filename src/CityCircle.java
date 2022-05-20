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
    private final String name;
    private boolean selected = false;
    private MapFX getCircleList = new MapFX();
    //private int i;
    public CityCircle(double x, double y,String name){
        super(x,y, 15);
        this.name = name;
        setFill(Color.BLUE);
    }

    public String getName(){
        return name;
    }

    public boolean isSelected(){
        return selected;
    }

    public void changeSelected(boolean i){
        selected = i;

        if(i){
            setFill(Color.RED);
        }else {
            setFill(Color.BLUE);
        }
    }
}
