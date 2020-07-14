package Entity.NPC;


import Entity.Animation;
import Entity.Hero;
import Entity.MapObject;
import ImageProcesser.Assets;
import Map.TileMap;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class HuanRose extends MapObject {
    Image chatbox;
    Image Huanexe;
    //AudioPlayer npcsound = new AudioPlayer("src\\Entity\\NPC\\huansound.wav");
    {
        try {
            chatbox = new Image(new FileInputStream("src\\Entity\\NPC\\boxchat.png"),300,300,false,true);
            Huanexe= new Image (new FileInputStream("src\\Entity\\NPC\\huanroseexe.png"),150,150,false,true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public HuanRose(TileMap tileMap) {
        super(tileMap);
        width=48;
        height=48;
        cheight=40;
        cwidth=30;
        animation=new Animation();
        animation.setFrames(Assets.HuanRose);
        animation.setDuration(0.2);
    }
    public void update(){
        animation.setFrames(Assets.HuanRose);
        animation.setDuration(0.5);
        animation.updateFrame();
    }
    public boolean check(Hero h){
        if (x<(h.getUpdatedX()+h.getWidth()/2)&&(h.getUpdatedX()+h.getWidth()/2)<(x+width)&&y<(h.getUpdatedY()+h.getHeight()/2)&&(h.getUpdatedY()+h.getHeight()/2)<(y+height)){
                return true;
        }
        return false;
    }
    public void draw(GraphicsContext g)
    {
        setLocalMapPosition();
        super.draw(g);
    }


}
