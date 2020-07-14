package Entity;

import Map.TileMap;
import javafx.scene.canvas.GraphicsContext;

public class Enemy extends MapObject {

    //

    public Enemy(TileMap tileMap)
    {
        super(tileMap);
        width=40;
        height=40;
        cheight=40;
        cwidth=30;
        veclocity=1.0;//fixed
        maxVec=1.0;
    }
    public void detectHero(Hero hero)
    {
        double posX=hero.getx();
        double posY=hero.gety();

        setUp(false);
        setDown(false);
        setRight(false);
        setLeft(false);


        if((posX-x)*(posX-x)+(posY-y)*(posY-y)<225000) {
            if (x > posX) {
                setLeft(true);

                dx -= veclocity;
                if (dx < -maxVec) {
                    dx = -maxVec;
                }

            } else if (x < posX) {

                setRight(true);
                dx += veclocity;
                if (dx > maxVec) {
                    dx = maxVec;
                }

            }  if (y > posY) {

                setDown(true);
                dy -= veclocity;
                if (dy < -maxVec) {
                    dy = -maxVec;
                }
            } else if (y < posY ) {

                setUp(true);
                dy += veclocity;

                if (dy > maxVec) {
                    dy = maxVec;
                }
            }
        }
        else {
            dx=0;
            dy=0;
        }

    }
    public void draw(GraphicsContext g)
    {
        setLocalMapPosition();
        super.draw(g);
    }

}
