package Entity.NPC;

import Entity.Animation;
import Entity.MapObject;
import ImageProcesser.Assets;
import Map.TileMap;
import javafx.scene.canvas.GraphicsContext;

public class ChatBoxHuAn extends MapObject {

    public ChatBoxHuAn(TileMap tileMap) {
        super(tileMap);
        width=180;
        height=180;

        faceRight=true;
        faceDown=true;

        animation=new Animation();
        animation.setFrames(Assets.HuanRosechatbox);
        animation.setDuration(0.2);
    }
    public void update(){
        animation.setFrames(Assets.HuanRosechatbox);
        animation.setDuration(0.5);
        animation.updateFrame();
    }
    public void draw(GraphicsContext g)
    {
        setLocalMapPosition();
        super.draw(g);
    }

}
