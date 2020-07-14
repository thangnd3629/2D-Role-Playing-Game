package Entity;

import ImageProcesser.Assets;
import Map.TileMap;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.util.ArrayList;

public class Enemy2 extends Enemy {
    private ArrayList<Image[]> sprites=new ArrayList<>();
    private double speed;
    private boolean isdead=false;
    //action
    private static final int IDLE=0;
    private static final int WALKING=1;
    private static final int ATTACK = 2;
    private long timeToAttack;
    private Canvas gameCanvas;
    public long startTimeAttack = 0;

    public Enemy2(TileMap tileMap) {
        super(tileMap);
        width=40;
        height=40;
        cheight=20;
        cwidth=20;
        health = 1000;
        currenthealth=health;
        timeToAttack = 0;
        sprites.add(Assets.evilflowerup);
        sprites.add(Assets.evilflowerleft);
        sprites.add(Assets.evilflowerdown);
        sprites.add(Assets.idleevilflower);
        animation=new Animation();
        currentAction=IDLE;
        animation.setFrames(Assets.idleevilflower);
        animation.setDuration(0.2);
        faceRight=true;
        faceDown=true;
    }

    public void update(Hero hero){
        double posX = hero.getx();
        double posY = hero.gety();
        double posX2=x;
        double posY2=y;
        if (currenthealth <= 0) isdead = true;
        if (isdead) return;
        else {
            //update position
            detectHero(hero);
            checkTileMapCollision();//and update updated X Y stimultaneously// real X, real Y
            setPosition(updatedX, updatedY); //set Constrain to x,y //real movement of Hero
            //set animation
            if (up) {
                if((posX-x)*(posX-x)+(posY-y)*(posY-y) <= 10000 ){
                    if(currentAction != ATTACK){
                        currentAction = ATTACK;
                        animation.setFrames(sprites.get(0));
                        animation.setDuration(0.4);
                    }
                }
                else{
                    if (currentAction != WALKING) {
                        currentAction = WALKING;
                        animation.setFrames(sprites.get(0));
                        animation.setDuration(0.4);
                    }
                }

            }
            else if(right ||left){
                if((posX-x)*(posX-x)+(posY-y)*(posY-y) <= 10000 ){
                    if(currentAction != ATTACK){
                        currentAction = ATTACK;
                        animation.setFrames(sprites.get(1));
                        animation.setDuration(0.4);
                    }
                }
                else {
                    if (currentAction != WALKING) {
                        currentAction = WALKING;
                        animation.setFrames(sprites.get(1));
                        animation.setDuration(0.4);
                    }
                }
            }
            else if(down){
                if((posX-x)*(posX-x)+(posY-y)*(posY-y) <= 10000 ){
                    if(currentAction != ATTACK){
                        currentAction = ATTACK;
                        animation.setFrames(sprites.get(2));
                        animation.setDuration(0.4);
                    }
                }
                else {
                    if (currentAction != WALKING) {
                        currentAction = WALKING;
                        animation.setFrames(sprites.get(2));
                        animation.setDuration(0.4);
                    }
                }
            }
            else
            {
                if (currentAction != IDLE) {
                    currentAction = IDLE;
                    animation.setFrames(sprites.get(3));
                    animation.setDuration(0.4);
                }
            }

            animation.updateFrame();
            //set face direction
            if (right) faceRight = true;
            if (left) faceRight = false;
            if (up) faceDown = false;
            if (down) faceDown = true;
        }
    }

    public void detectHero(Hero hero)
    {
        double posX=hero.getx();
        double posY=hero.gety();

        setUp(false);
        setDown(false);
        setRight(false);
        setLeft(false);
        if((posX-x)*(posX-x)+(posY-y)*(posY-y)<10000) {
            if (x > posX) {
                setLeft(true);

                dx = 0;

            } else if (x < posX) {

                setRight(true);
                dx = 0;

            }  if (y > posY) {

                setDown(true);
                dy = 0;

            } else if (y < posY ) {

                setUp(true);
                dy = 0;
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
    public boolean isIsdead() {
        return isdead;
    }

    public long getTimeToAttack(){
        return timeToAttack;
    }
    public void setTimeToAttack(long timeToAttack){
        this.timeToAttack = timeToAttack;
    }
}
