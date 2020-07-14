package Entity;

import ImageProcesser.Assets;
import Map.TileMap;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import java.io.FileInputStream;
import java.util.ArrayList;

public class ArrowDown extends MapObject {

    public ArrowDown(TileMap tileMap) {
        super(tileMap);
        width=30;
        height=30;
        cheight=30;
        cwidth=20;

        faceRight=true;
        faceDown=true;

        animation=new Animation();
        animation.setFrames(Assets.arrow);
        animation.setDuration(0.2);
    }

    public void update(){
        animation.setFrames(Assets.arrow);
        animation.setDuration(0.5);
        animation.updateFrame();
    }
    public void draw(GraphicsContext g)
    {
        setLocalMapPosition();
        super.draw(g);
    }
}
