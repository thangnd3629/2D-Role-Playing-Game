package Entity;

import AStar.AStarSearch;
import ImageProcesser.Assets;
import Map.Tile;
import Map.TileMap;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class Enemy4 extends Enemy {
    private ArrayList<Image[]> sprites=new ArrayList<>(); // consists of moving frame, attaking frame , idle frame ....

    private double speed;
    private boolean isdead=false;
    //action
    private static final int IDLE=0;
    private static final int WALKING=1;
    private static final int ATTACKING =2;

    //epsilon for double precision
    public static double epsilon=0.001;
    //enemy
    private long thelastenemyattacktime;
    private String thelastdamestring;
    private double defaultx=x;
    private double defailty=y;

    //hero detection
    private Hero hero;

    //algorithm implementation
    //localMap //range of enemy
    public Tile[][] localMap;
    int range;

    public Enemy4(TileMap tilemap,int range,Hero hero){
        super(tilemap);

        //index
        dame=500;
        magicaldame=300;
        health=2000;
        currenthealth=health;
        attackspeed=0.5;
        faceRight=true;
        faceDown=true;
        sprites.add(Assets.evilflyrunup);
        sprites.add(Assets.evilflyrunright);
        sprites.add(Assets.evilflyrundown);
        sprites.add(Assets.idleevilfly);
        sprites.add(Assets.evilflyfightright);
        sprites.add(Assets.evilflyfightup);
        sprites.add(Assets.evilflyfightdown);
        animation=new Animation();
        currentAction=IDLE;
        animation.setFrames(Assets.idleevilfly);
        animation.setDuration(0.2);

        //hero detection
        this.hero=hero;

        //algorithm initialization
        localMap=new Tile[range][range];
        this.range=range;

    }

    //path
    public List<Tile> optimumPath;
    //khong co path , return null
    private int nextPosIndex;


    public void updateLocalMap()// phai le?
    {

        updateAnimation();
        int enemyRow=(int)y/32;
        int enemyColumn=(int)x/32;
        int startRowInMap=enemyRow-(range/2);
        int startColumnInMap=enemyColumn-(range/2);

        for(int row=0;row<range;row++)
        {
            for(int column=0;column<range;column++)
            {

                localMap[row][column]=new Tile(null,tileMap.getType(startRowInMap,startColumnInMap));

                localMap[row][column].setPos(startColumnInMap*32,startRowInMap*32);

                startColumnInMap++;
            }
            startRowInMap++;
            startColumnInMap=enemyColumn-(range/2);
        }
    }
    public Tile findHero()//test
    {
        int heroRow=(int)(hero.gety()/32);
        int heroCol=(int)(hero.getx()/32);
        int enemyRow=(int)y/32;
        int enemyColumn=(int)x/32;

        int startRow=enemyRow-range/2;
        int startCol=enemyColumn-range/2;
        int localHeroRow=heroRow-startRow;
        int localHeroCol=heroCol-startCol;

        return localMap[localHeroRow][localHeroCol];
    }
    public boolean heroInRange()//test
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
    public Tile findEnemy()//tested
    {

        return localMap[range/2][range/2];
    }


    public void updatePath()
    {
        updateLocalMap();
        Tile heroInLocalMap=findHero();
        Tile enemyInMap=findEnemy();
        optimumPath= AStarSearch.findPath(enemyInMap,heroInLocalMap,localMap,range);
        nextPosIndex=0;
    }

    double distancesX=0;
    double distancesY=0;//per tile
    public void detectHero()
    {
        if (currenthealth==0) {isdead=true;
            System.out.println(currenthealth);}
        if (isdead==true) return;
        else
        {
            Tile currentPosition = findEnemy();
            if(optimumPath==null||optimumPath.size()==0) return;
            Tile nextPosition = optimumPath.get(nextPosIndex);
            updateDxDy(currentPosition, nextPosition);
            updatedX = x + dx;
            updatedY = y + dy;


            if (up || down) {

                distancesX += veclocity;
                if (Math.abs(distancesX - 32) < epsilon) {

                    distancesX = 0;
                    nextPosIndex++;

                }
            }
            if (left || right) {

                distancesY += veclocity;
                if (Math.abs(distancesY - 32) < epsilon) {

                    distancesY = 0;
                    nextPosIndex++;

                }
            }
            setPosition(updatedX, updatedY);

        }
    }
    public void updateDxDy(Tile currentTile,Tile nextTile)
    {


        if((int)distancesX!=0||(int)distancesY!=0)
        {
            return;
        }

        int currentPosX=currentTile.getPosX();
        int currentPosY=currentTile.getPosY();
        int nextTileX=nextTile.getPosX();
        int nextTileY=nextTile.getPosY();
        if(currentPosX<nextTileX)
        {
            dx=veclocity;

            setRight(true);
            setLeft(false);
            setDown(false);
            setUp(false);
            dy=0;
        }
        else if(currentPosX>nextTileX)
        {
            dx=-veclocity;
            setLeft(true);
            setRight(false);
            setUp(false);
            setDown(false);
            dy=0;
        }
        else {
            if(currentPosY<nextTileY)
            {
                dy=veclocity;
                setDown(true);
                setUp(false);
                setRight(false);
                setLeft(false);
                dx=0;
            }
            else if(currentPosY>nextTileY)
            {
                dy=-veclocity;
                setUp(true);
                setDown(false);
                setLeft(false);
                setRight(false);
                dx=0;
            }
        }

    }

    public void updateAnimation()
    {


        if (up) {
            if (isattack){
                currentAction=ATTACKING;
                animation.setFrames(sprites.get(4));
                animation.setDuration(0.4);
            } else if (currentAction != WALKING) {
                currentAction = WALKING;
                animation.setFrames(sprites.get(0));
                animation.setDuration(0.2);
            }
        }
        else if(right ||left){
            if (isattack) {
                currentAction = ATTACKING;
                animation.setFrames(sprites.get(5));
                animation.setDuration(0.4);
            }else if (currentAction != WALKING) {
                currentAction = WALKING;
                animation.setFrames(sprites.get(1));
                animation.setDuration(0.2);
            }
        }
        else if(down){
            if(isattack){
                currentAction = ATTACKING;
                animation.setFrames(sprites.get(6));
                animation.setDuration(0.4);
            } else if (currentAction != WALKING) {
                currentAction = WALKING;
                animation.setFrames(sprites.get(2));
                animation.setDuration(0.2);
            }
        }
        else
        {
            if (currentAction != IDLE) {
                currentAction = IDLE;
                animation.setFrames(sprites.get(3));
                animation.setDuration(0.5);
            }
        }

        animation.updateFrame();
        //set face direction
        if (right) faceRight = true;
        if (left) faceRight = false;
        if (up) faceDown = false;
        if (down) faceDown = true;
    }


    public void draw(GraphicsContext g){
        super.draw(g);
    }
    public boolean isIsdead() {
        return isdead;
    }
}
