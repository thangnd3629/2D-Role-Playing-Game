package Entity;

import ImageProcesser.Assets;
import Map.TileMap;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.FileInputStream;
import java.util.ArrayList;

public class BulletEnemy extends Enemy {
    private ArrayList<Image[]> sprites=new ArrayList<>(); // consists of moving frame, attaking frame , idle frame ....

    private double speed;
    private boolean isdead=true;
    //action
    private static final int IDLE=0;
    private static final int WALKING=1;
    private static final int ATTACK = 2;
    private long thelastattacktime=0;
    private String thelastdamestring;
    private double makecolor=0.2;
    private int sdame=0;

    public BulletEnemy(TileMap tilemap){
        super(tilemap);

        //index
        magicaldame =300;
        health=1;
        currenthealth=health;
        attackspeed=0.5;
        height = 30;
        width = 30;

        faceRight=true;
        faceDown=true;
        sprites.add(Assets.bulletevilflowerup);
        sprites.add(Assets.bulletevilflowerright);
        sprites.add(Assets.bulletevilflowerdown);
        animation=new Animation();
        currentAction=WALKING;
    }
    public boolean heroInRange(Hero hero,int range)
    {
        int heroRow=(int)(hero.gety()/32);
        int heroCol=(int)(hero.getx()/32);
        int enemyRow=(int)y/32;
        int enemyColumn=(int)x/32;
        if(Math.abs(heroCol-enemyColumn)>range/2||Math.abs(heroRow-enemyRow)>range/2)
        {
            return false;
        }
        return true;
    }

    public void update(Hero hero, Enemy2 enemy2,GraphicsContext g) {
        //ve dame len mh -- dang bi delay
            if(System.currentTimeMillis()-thelastattacktime<2000) {
                makecolor+=0.3;
                g.setFont(Font.font(null, FontWeight.MEDIUM, 14));
                g.setFill(Color.web("#ed0707"));
                g.fillText(thelastdamestring,hero.x-hero.xCamera+hero.width/2-30,hero.y-hero.yCamera-hero.height/2-makecolor);
            } else makecolor=0.1;
        //update
        double posX = hero.getUpdatedX();
        double posY = hero.getUpdatedY();
        double posX2 = enemy2.getX();
        double posY2 = enemy2.getY();
        if (currenthealth <= 0) isdead = true;
        if (isdead) return;
        else {
            //update position
            detectHero(hero);
            //checkTileMapCollision();//and update constraint X Y stimultaneously// real X, real Y
            currentCol=(int)x/32;
            currentRow=(int)y/32;

            nextX=x+dx;
            nextY=y+dy;

            updatedX=x;
            updatedY=y;
            if(nextX<0||nextX>MAP_SIZEX||nextY<0||nextY>MAP_SIZEY){
                if(nextX<0)
                {
                    isdead = true;
                }
                if(nextX>MAP_SIZEX)
                {
                    isdead = true;
                }
                if(nextY<0)
                {
                    isdead = true;
                }
                if(nextY>MAP_SIZEY)
                {
                    isdead = true;
                }
            }
            calculateCorner(x,nextY);
            if(dy<0)//go upward
            {

                if(topLeft||topRight)
                {
                    isdead = true;

                }
                else {

                    updatedY+=dy;
                }

            }
            if(dy>0)
            {
                if(bottomLeft||bottomRight)
                {
                    isdead = true;
                }
                else {
                    updatedY+=dy;
                }
            }//go right
            calculateCorner(nextX,y);
            if(dx>0)
            {
                if(topRight||bottomRight)
                {
                    isdead = true;
                }
                else {
                    updatedX+=dx;
                }

            }
            if(dx<0)
            {
                if(topLeft||bottomLeft)
                {
                    isdead = true;
                }
                else {
                    updatedX+=dx;
                }
            }

            setPosition(updatedX, updatedY); //set Constrain to x,y //real movement of Hero
            //set animation
            sdame=0;
            if((Math.abs(posX-posX2)<=Math.abs(posY-posY2))&&(posY>posY2)){
                animation.setFrames(sprites.get(2));
                animation.setDuration(0.5);
                if((x-posX)*(x-posX)+(y-posY)*(y-posY) < 50){
                    isdead = true;
                    sdame= magicaldame - hero.magicaldef;

                }
                else if((x - posX2)*(x - posX2)+(y - posY2)*(y - posY2) >= 22500){
                    isdead = true;
                }
            }
            else if((Math.abs(posX-posX2)<=Math.abs(posY-posY2))&&(posY<=posY2)){
                animation.setFrames(sprites.get(0));
                animation.setDuration(0.5);
                if((x-posX)*(x-posX)+(y-posY)*(y-posY) < 50){
                    isdead = true;
                    sdame= magicaldame - hero.magicaldef;
                }
                else if((x - posX2)*(x - posX2)+(y - posY2)*(y - posY2) >= 22500){
                    isdead = true;
                }
            }
            else if((Math.abs(posX-posX2)>Math.abs(posY-posY2))&&(posX>posX2)){
                animation.setFrames(sprites.get(1));
                animation.setDuration(0.5);
                if((x-posX)*(x-posX)+(y-posY)*(y-posY) < 50){
                    isdead = true;
                    sdame= magicaldame - hero.magicaldef;
                }
                else if((x - posX2)*(x - posX2)+(y - posY2)*(y - posY2) >= 22500){
                    isdead = true;
                }
            }
            else if((Math.abs(posX-posX2)>Math.abs(posY-posY2))&&(posX<=posX2)){
                animation.setFrames(sprites.get(1));
                animation.setDuration(0.5);
                if((x-posX)*(x-posX)+(y-posY)*(y-posY) < 50){
                    isdead = true;
                    sdame= magicaldame - hero.magicaldef;
                }
                else if((x - posX2)*(x - posX2)+(y - posY2)*(y - posY2) >= 22500){
                    isdead = true;
                }
            }
            if (sdame!=0){
                thelastattacktime=System.currentTimeMillis();
                if (sdame>=hero.currenthealth) hero.currenthealth=-1;
                else hero.currenthealth-=sdame;
                thelastdamestring = Integer.toString(sdame);
                thelastdamestring = "-"+thelastdamestring;
            }
            animation.updateFrame();
            //set face direction
            if (right) faceRight = true;
            if (left) faceRight = false;
            if (up) faceDown = false;
            if (down) faceDown = true;
        }

    }
    public boolean isAttack(Hero hero, Enemy2 enemy2){
        double posX = hero.getx();
        double posY = hero.gety();
        double posX1 = enemy2.getX();
        double posY1 = enemy2.getY();
        if((posX <= posX1 && posY == posY1) || (posX > posX1 && posY == posY1) || (posX == posX1 && posY < posY1) || (posX == posX1 && posY >= posY1))
            return true;
        else return false;
    }
    public void draw(GraphicsContext g){
        super.draw(g);
    }
    public boolean isIsdead() {
        return isdead;
    }
    public void setIsDead(boolean isdead){
        this.isdead = isdead;
    }
}