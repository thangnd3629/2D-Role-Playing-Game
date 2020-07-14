package Entity;

import ImageProcesser.Assets;
import Inventory.Item;
import Map.TileMap;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Random;

public class ItemDrop extends MapObject {
    Random r=new Random() ;
    private int id ;
    Item i;
    Image[] image =new Image[1];

    public ItemDrop(TileMap tileMap) {
        super(tileMap);
        width=20;
        height=20;
        cheight=20;
        cwidth=20;

        faceRight=true;
        faceDown=true;
        id=r.nextInt(6)+1;
        i=Item.items[id];
        i.setPickedUp(false);
        i.setCount(1);
        image[0]=i.getTexture();

        animation=new Animation();
        animation.setFrames(image);
        animation.setDuration(0.2);
    }
    public void draw(GraphicsContext g)
    {
        setLocalMapPosition();
        super.draw(g);
    }

    public Item getI() {
        return i;
    }
}
