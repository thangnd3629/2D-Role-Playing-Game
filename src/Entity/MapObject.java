package Entity;


import Map.Tile;
import Map.TileMap;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public abstract class MapObject {


    //index
    protected double health;
    protected double currenthealth;
    protected int dame;
    protected int def;
    protected int crit;
    protected int magicaldame;
    protected int magicaldef;
    protected double attackspeed;

    //map
    protected TileMap tileMap;
    protected double xCamera;//map position
    protected double yCamera;
    public final int MAP_SIZEX=32*40,MAP_SIZEY=32*30;

    //hero attack
    protected int attackrange=35;
    protected boolean isattack=false;
    protected boolean attacked=false;
    protected boolean isfire = false;
    protected boolean fired = false;
    //enemy attack
    protected int enemyattackrange=10;
    protected boolean enemyattacked=true;
    //object position
    protected double x;// in the tilemap itself
    protected double y;
    protected double dx;
    protected double dy;

    //dimension
    protected  int width;
    protected int height;

    //colision rec
    protected  int cwidth;
    protected  int cheight;

    //colision
    protected int currentRow;
    protected int currentCol;
    protected  double nextX;//hypothesis
    protected double nextY;
    protected  double updatedX;//real after move(colision detected or not)
    protected double updatedY;
    protected  boolean topLeft;
    protected boolean topRight;
    protected  boolean bottomLeft;
    protected boolean bottomRight;

    //animation
    protected Animation animation;
    protected int currentAction;
    protected int prevAction;
    protected boolean faceRight;
    protected boolean faceDown;

    //movement
    protected boolean left;
    public void setLeftButtonPressed(boolean leftButtonPressed) {
        this.leftButtonPressed = leftButtonPressed;
    }

    public void setRightButtonPressed(boolean rightButtonPressed) {
        this.rightButtonPressed = rightButtonPressed;
    }

    public void setUpButtonPressed(boolean upButtonPressed) {
        this.upButtonPressed = upButtonPressed;
    }

    public void setDownButtonPressed(boolean downButtonPressed) {
        this.downButtonPressed = downButtonPressed;
    }
    protected boolean right;
    protected boolean up;
    protected boolean down;

    //keyevent
    protected boolean leftButtonPressed;
    protected boolean rightButtonPressed;
    protected boolean upButtonPressed;
    protected boolean downButtonPressed;

    //movement attribute
    protected  double veclocity;
    protected double maxVec;
    protected  double decelerateSpeed;

    //effect
    private long thelastheroattacktime=0;
    private String thelastdamestring;
    private double makecolor1=0.2;
    private double makecolor2=0.2;
    //enemy
    private long thelastenemyattacktime;

    //constructor
    public MapObject(TileMap tileMap)
    {
        this.tileMap=tileMap;


    }
    public boolean collide(MapObject o)
    {
        Rectangle r1=new Rectangle((int)x-cwidth,(int)y-cheight,cwidth,cheight);
        Rectangle r2=o.getRec();
        Shape intesect=Shape.intersect(r1,r2);
        if(intesect.getBoundsInParent().getWidth()<0)//getBoundLocal
        {
            return true;
        }
        return false;

    }
    public Rectangle getRec()
    {
        return new Rectangle(
                (int)x-cwidth,(int)y-cheight,cwidth,cheight

        );
    }

    public void calculateCorner(double x,double y)
    {
        int leftTileCorner=(int)(x-cwidth/2+0.0001)/32; // +-1 de xu ly cho giao cua 2 tile do se bi chong` type (block/normal)
        int rightTileCorner=(int)(x+cwidth/2-0.0001)/32;
        int topTile=(int)((y-cheight/2+0.0001)/32);
        int bottomTile=(int)(y+cheight/2-0.0001)/32;

        int typeTopLeft=tileMap.getType(topTile,leftTileCorner);
        int typeTopRight=tileMap.getType(topTile,rightTileCorner);
        int typeBottomLeft=tileMap.getType(bottomTile,leftTileCorner);
        int typeBottomRight=tileMap.getType(bottomTile,rightTileCorner);

        this.topLeft= (typeTopLeft== Tile.BLOCKED);
        this.topRight=(typeTopRight==Tile.BLOCKED);
        this.bottomLeft=(typeBottomLeft==Tile.BLOCKED);
        this.bottomRight=(typeBottomRight==Tile.BLOCKED);

    }
    public void checkTileMapCollision()
    {

        currentCol=(int)x/32;
        currentRow=(int)y/32;

        nextX=x+dx;
        nextY=y+dy;

        updatedX =x;
        updatedY =y;

        if(nextX>0+cwidth/2  &&  nextX<32*40-cwidth/2) {


            calculateCorner(nextX, y);
            if (dx > 0) {
                if (topRight || bottomRight) {
                    dx = 0;
                    updatedX = (currentCol + 1) * 32 - cwidth / 2;
                } else {
                    updatedX += dx;
                }

            }
            if (dx < 0) {
                if (topLeft || bottomLeft) {
                    dx = 0;
                    updatedX = (currentCol) * 32 + cwidth / 2;
                } else {
                    updatedX += dx;
                }
            }


        }

        if(nextY>0+cheight/2  &&  nextY<32*30-cheight/2) {

            calculateCorner(x, nextY);
            if (dy < 0)//go upward
            {

                if (topLeft || topRight) {
                    dy = 0;
                    updatedY = currentRow * 32 + cheight / 2;

                } else {

                    updatedY += dy;
                }

            }
            if (dy > 0) {
                if (bottomLeft || bottomRight) {
                    dy = 0;
                    updatedY = (currentRow + 1) * 32 - cheight / 2;
                } else {
                    updatedY += dy;
                }
            }//go right

        }


    }
    public void setPosition(double x,double y)
    {
        this.x=x;
        this.y=y;
    }
    public void setLocalMapPosition()
    {
        xCamera=tileMap.getX();
        yCamera=tileMap.getY();
    }
    public void setLeft(boolean b) { left = b; }
    public void setRight(boolean b) { right = b; }
    public void setUp(boolean b) { up = b; }
    public void setDown(boolean b) { down = b; }
    public void draw(GraphicsContext gc)
    {
        if(!faceRight) {
            gc.drawImage(animation.getImage(),x-xCamera+width/2,y-yCamera-height/2,-width,height);

        }
        else
        {
            gc.drawImage(animation.getImage(),x-xCamera-width/2,y-yCamera-height/2,width,height);

        }
    }

    public void attack(Enemy e,GraphicsContext g){
        double xr= (x+width)/2;
        double yr= (y+height)/2;
        double exr=(e.updatedX+e.getWidth())/2;
        double eyr=(e.updatedY+e.getHeight())/2;
        //attack
        if(isattack&&!attacked&&(System.currentTimeMillis()-thelastheroattacktime)>1000)
        {
            attacked=true;
            if (((int) Math.sqrt(Math.pow((exr-xr),2)+Math.pow((eyr-yr),2))<attackrange)&&e.currenthealth>0) {
            thelastheroattacktime = System.currentTimeMillis();
            int sdame;
            if (dame < e.def && magicaldame < e.magicaldef) {
                sdame = 1;
            } else if (dame < e.def) {
                sdame = (magicaldame - e.magicaldef);
            } else if (magicaldame < e.magicaldame) {
                sdame = (dame - e.def);
            } else {
                sdame = (magicaldame - e.magicaldef) + (dame - e.def);
            }
            if (sdame>=e.currenthealth) e.currenthealth=0;
            else e.currenthealth-=sdame;
            thelastdamestring = Integer.toString(sdame);
            thelastdamestring = "-"+thelastdamestring;
        }
            System.out.println("enemy hp:"+e.currenthealth+"/"+e.health);
        }
        makecolor1+=0.3;
        if(System.currentTimeMillis()-thelastheroattacktime<1500*0.5/attackspeed) {
            g.setFont(Font.font(null, FontWeight.MEDIUM, 14));
            g.setFill(Color.web("#ed0707"));
            g.fillText(thelastdamestring,e.x-e.xCamera+e.width/2-35,e.y-e.yCamera-e.height/2+10-makecolor1);
        } else makecolor1=0.1;
    }
    //private AudioPlayer skillSFX=new AudioPlayer("src/Audio/Hasagi.wav");
    private double startTime=System.currentTimeMillis()/1000.0;
    private double curTime;
    public void fire(Hero h, Enemy e,GraphicsContext g){

        double xr= (updatedX+width)/2;
        double yr= (updatedY+height)/2;
        double exr=(e.getX()+e.getWidth())/2;
        double eyr=(e.getY()+e.getHeight())/2;
        if(isfire&&!fired&& (System.currentTimeMillis()-thelastheroattacktime)/1000>1/attackspeed && h.getCurrentmana() > 10)
        {

            if(((int)(x/32))==((int)(e.x/32)) && Math.abs(y-e.y)<1000.0001 || ((int)(y/32))==((int)(e.y/32))){
                curTime=System.currentTimeMillis()/1000.0;
                if(curTime-startTime>10) {
                    //skillSFX.play();
                    startTime=curTime;
                }
                //if (((int) Math.sqrt(Math.pow((exr-xr),2)+Math.pow((eyr-yr),2))<attackrange)&&e.currenthealth>0) {
                thelastheroattacktime = System.currentTimeMillis();
                int sdame;
                if (dame < e.def && magicaldame < e.magicaldef) {
                    sdame = 1;
                } else if (dame < e.def) {
                    sdame = (magicaldame - e.magicaldef);
                } else if (magicaldame < e.magicaldame) {
                    sdame = (dame - e.def);
                } else {
                    sdame = (magicaldame - e.magicaldef) + (dame - e.def);
                }
                if (sdame>=e.currenthealth) e.currenthealth=0;
                else e.currenthealth-=sdame;
                thelastdamestring = Integer.toString(sdame);
                thelastdamestring = "-"+thelastdamestring;
            }
            fired=true;
            System.out.println("enemy hp:"+e.currenthealth+"/"+e.health);
        }
        makecolor1+=0.3;
        if(System.currentTimeMillis()-thelastheroattacktime<1500*0.5/attackspeed) {
            g.setFont(Font.font(null, FontWeight.MEDIUM, 12));
            g.fillText(thelastdamestring,e.x-e.xCamera+e.width/2-35,e.y-e.yCamera-e.height/2+10-makecolor1);
        } else makecolor1=0.1;
    }
    public void collideattack(Hero h,GraphicsContext g){
        double exr= (x+width)/2;
        double eyr= (y+height)/2;
        double xr=(h.updatedX+h.getWidth())/2;
        double yr=(h.updatedY+h.getHeight())/2;
        if(!enemyattacked)
        {
            if (((int) Math.sqrt(Math.pow((exr-xr),2)+Math.pow((eyr-yr),2))<enemyattackrange)&&h.currenthealth>0) {
                thelastenemyattacktime = System.currentTimeMillis();
                isattack=true;
                int sdame;
                if (dame < h.def && magicaldame < h.magicaldef) {
                    sdame = 1;
                } else if (dame < h.def) {
                    sdame = (magicaldame - h.magicaldef);
                } else if (magicaldame < h.magicaldame) {
                    sdame = (dame - h.def);
                } else {
                    sdame = (magicaldame - h.magicaldef) + (dame - h.def);
                }
                if (sdame >=h.currenthealth) h.currenthealth=0;
                else h.currenthealth-=sdame;
                thelastdamestring = Integer.toString(sdame);
                thelastdamestring = "-" +thelastdamestring;
                enemyattacked = true;
                System.out.println("hero hp:" + h.currenthealth + "/" + h.health);
            }
            else isattack= false;
        }
       if ((System.currentTimeMillis()-thelastenemyattacktime)>2000) {enemyattacked=false;}
        makecolor2+=0.3;
        if(System.currentTimeMillis()-thelastenemyattacktime<1500*0.5/attackspeed) {
            g.setFont(Font.font(null, FontWeight.MEDIUM, 14));
            g.setFill(Color.web("#ed0707"));
            g.fillText(thelastdamestring,h.x-h.xCamera+h.width/2-30,h.y-h.yCamera-h.height/2-makecolor2);
        } else makecolor2=0.1;
    }

    public TileMap getTileMap() {
        return tileMap;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getCwidth() {
        return cwidth;
    }

    public int getCheight() {
        return cheight;
    }

    public boolean isIsattack() {
        return isattack;
    }

    public boolean isFire(){
        return isfire;
    }
    public boolean isFired(){
        return fired;
    }
    public void setIsfire(boolean isfire){
        this.isfire = isfire;
    }
    public void setFired(boolean fired){
        this.fired = fired;
    }

    public boolean isAttacked() {
        return attacked;
    }

    public void setAttacked(boolean attacked) {
        this.attacked = attacked;
    }

    public void setIsattack(boolean isattack) {
        this.isattack = isattack;
    }

    public double getUpdatedX() {
        return updatedX;
    }

    public double getUpdatedY() {
        return updatedY;
    }

    public int getAttackrange() {
        return attackrange;
    }

    public double getCurrenthealth() {
        return currenthealth;
    }
    public long getThelastheroattacktime() {
        return thelastheroattacktime;
    }

    public void setThelastheroattacktime(long thelastheroattacktime) {
        this.thelastheroattacktime = thelastheroattacktime;
    }

    public void changeMode()
    {
        health+=1000;
        dame+=200;
        veclocity+=1.0;
    }
}
