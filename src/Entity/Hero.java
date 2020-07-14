package Entity;

import HUD.indexBar;
import ImageProcesser.Assets;
import ImageProcesser.SpriteSheet;
import Inventory.Inventory;
import Map.TileMap;

import accesories.HeroClass;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Hero extends MapObject  {
    //hero attributes
    private HeroClass heroClass;

    private final int DAME_DEFAULT=100,
                      DEF_DEFAULT=5,CRIT_DEFAULT=5,
                      MAGICALDAME_DEFAULT=0, MAGICALDEF_DEFAULT=0;
    private final double HEALTH_DEFAULT=100,MANA_DEFAULT=100,
            ATTACKSPEED_DEFAULT=2 //attackspeed
            , HPHEALINGPERSEC_DEFAULT=0.05,
                         MPHEALINGPERSEC_DEFAULT=0.1,SPEEDUP_DEFAULT=0,
                         POWERUP_DEFAULT=0;
    private double mana=MANA_DEFAULT;

    private double previoushealth,previousmana;
    private int previousdame,previousdef,previouscrit,previousmagicaldame,
            previousmagicaldef;
    private double previousattackspeed,previoushphealingpersec,previousmphealingpersec,previousspeedup,previouspowerup;
    private double currentmana=mana;

    private int level=1;
    private double hphealingpersec=HPHEALINGPERSEC_DEFAULT;
    private double mphealingpersec=MPHEALINGPERSEC_DEFAULT;
    private double speedup=SPEEDUP_DEFAULT;
    private double powerup=POWERUP_DEFAULT;
    private boolean dead =false;
    //
    private int attackrange=50;
    //indexbar
    indexBar indexbar = new indexBar();
    //inventory
    Inventory inven = new Inventory();
    //hero animation

    private ArrayList<Image[]> sprites=new ArrayList<>(); // consists of moving frame, attaking frame , idle frame ....
    public Image bullet = new Image(new FileInputStream("src/Entity/image/laze2.1.png"));
    SpriteSheet bulletsprite = new SpriteSheet(bullet);
    public Image bulletright = bulletsprite.crop(913, 433, 1904, 816);
    public Image bulletup = bulletsprite.crop(3289, 417, 696, 1928);
    public Image bulletdown = bulletsprite.crop(9, 409, 704, 1912);
    public Image bulletleft = bulletsprite.crop(905,1289,1924,768);
    //action
    private static final int IDLE=0;
    private static final int WALKING=1;
    private static final int ATTACKING=2;
    private static final int FIRE = 3;
    //effect
    private long thelastheroattacktime;
    private String thelastdamestring;
    private double makecolor=0.2;
    public Hero(TileMap tileMap) throws FileNotFoundException {
        super(tileMap);
        up=true;//khoi tao bat ky
        down=false;
        right=true;
        left=false;

        health=HEALTH_DEFAULT;
        currenthealth=health;
        dame=DAME_DEFAULT;
        def=DEF_DEFAULT;
        crit=CRIT_DEFAULT;
        magicaldame=MAGICALDAME_DEFAULT;
        magicaldef=MAGICALDEF_DEFAULT;
        attackspeed=ATTACKSPEED_DEFAULT;

        width=40;
        height=40;
        cheight=28;
        cwidth=23;

        veclocity=0.3;
        maxVec=2.0;

        faceRight=true;
        faceDown=true;
        Image[] movingHero=new Image[6];
        try{
            for(int i=3;i<9;i++)
            {
                Image origin=new Image(new FileInputStream("src/Entity/hero/heroright"+i+".png"));

                movingHero[i-3]=new WritableImage(origin.getPixelReader(),0,0,32,32);

            }
            sprites.add(movingHero);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        Image[] idleHero=new Image[1];
        try{

                Image origin=new Image(new FileInputStream("src/Entity/hero/idlehero.png"));
                idleHero[0]=new WritableImage(origin.getPixelReader(),0,0,32,32);

            sprites.add(idleHero);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        Image[] attackHero=new Image[5];
        try{
            for(int i=1;i<6;i++)
            {
                Image origin=new Image(new FileInputStream("src/Entity/hero/heroattack/heroattackright"+i+".png"));
                attackHero[i-1]=new WritableImage(origin.getPixelReader(),0,0,32,32);
            }
            sprites.add(attackHero);

        }catch (Exception e)
        {
            e.printStackTrace();
        }

        Image[] movingdownHero=new Image[5];
        try{
            for(int i=1;i<6;i++)
            {
                Image origin=new Image(new FileInputStream("src/Entity/hero/herodown"+i+".png"));
                movingdownHero[i-1]=new WritableImage(origin.getPixelReader(),0,0,32,32);
            }
            sprites.add(movingdownHero);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        Image[] movingupHero=new Image[4];
        try{
            for(int i=1;i<5;i++)
            {
                Image origin=new Image(new FileInputStream("src/Entity/hero/heroup"+i+".png"));
                movingupHero[i-1]=new WritableImage(origin.getPixelReader(),0,0,32,32);
            }
            sprites.add(movingupHero);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        sprites.add(Assets.herofireright);
        sprites.add(Assets.herofireup);
        sprites.add(Assets.herofiredown);

        animation=new Animation();
        currentAction=IDLE;
        animation.setFrames(idleHero);
        animation.setDuration(0.2);

    }
    private boolean oldUp=true;
    private boolean oldRight=true;




    protected boolean bothRightLeftPressed=false;
    protected boolean bothUpDownPressed=false;
    public void updateXY()
    {

        //leftright confliction
        if(leftButtonPressed&&rightButtonPressed)//fixed
        {

            if(bothRightLeftPressed==false) {
                bothRightLeftPressed=true;
                if (right) {
                    right = false;
                    left = true;
                } else {
                    right = true;
                    left = false;
                }
            }
        }
        else if(leftButtonPressed)
        {

            bothRightLeftPressed=false;
            left=true;
            right=false;
        }
        else if(rightButtonPressed)
        {
            bothRightLeftPressed=false;
            right=true;
            left=false;
        }
        else {
            bothRightLeftPressed=false;
            left=false;
            right=false;
        }


        if(left)
        {

            dx -= veclocity;
            if(dx < -maxVec) {
                dx = -maxVec;
            }
        }
        else if(right)
        {

            dx+=veclocity;
            if(dx>maxVec)
            {
                dx=maxVec;
            }
        }
        else if(!left&&!right){

            dx=0;
        }

        //updown confliction
        if(upButtonPressed&&downButtonPressed)//fixed
        {

            if(bothUpDownPressed==false) {
                bothUpDownPressed=true;
                if (up) {
                    up = false;
                    down=true;
                } else {
                    up = true;
                    down = false;
                }
            }
        }
        else if(upButtonPressed)
        {

            bothUpDownPressed=false;
            up=true;
            down=false;
        }
        else if(downButtonPressed)
        {
            bothUpDownPressed=false;
            down=true;
            up=false;
        }
        else {
            bothUpDownPressed=false;
            up=false;
            down=false;
        }


        if(up)
        {

            dy -= veclocity;
            if(dy < -maxVec) {
                dy = -maxVec;
            }
        }
        else if(down)
        {

            dy+=veclocity;
            if(dy>maxVec)
            {
                dy=maxVec;
            }
        }
        else {

            dy=0;
        }


    }




    public void returnpreviousWeaponIndex(){
        this.dame=previousdame;
        this.crit=previouscrit;
        this.magicaldame=previousmagicaldame;
        this.attackspeed=previousattackspeed;
    }
    public void returndefaultWeaponIndex(){
        this.dame=DAME_DEFAULT;
        this.crit=CRIT_DEFAULT;
        this.magicaldame=DAME_DEFAULT;
        this.attackspeed=ATTACKSPEED_DEFAULT;
    }
    public void returnpreviousCapIndex(){
        this.health=previoushealth;
        this.def=previousdef;
        this.magicaldef=previousmagicaldef;
    }
    public void returndefaultCapIndex(){
        this.health=HEALTH_DEFAULT;
        this.def=DEF_DEFAULT;
        this.magicaldef=MAGICALDEF_DEFAULT;
    }
    public void returnpreviousBootIndex(){
        this.speedup=previousspeedup;
        this.powerup=previouspowerup;
        maxVec=2.0;
    }
    public void returndefaultBootIndex(){
        this.speedup=SPEEDUP_DEFAULT;
        this.powerup=POWERUP_DEFAULT;
    }
    public void returnpreviousArmorIndex(){
        this.health=previoushealth;
        this.def=previousdef;
        this.magicaldef=previousmagicaldef;
    }
    public void returndefaultArmorIndex(){
        this.health=HEALTH_DEFAULT;
        this.def=DEF_DEFAULT;
        this.magicaldef=MAGICALDEF_DEFAULT;
    }

    private boolean prevUp=false;
    private boolean prevDown=false;
    private boolean prevRight=false;
    private boolean prevLeft=false;
    public void update()
    {
        //update inventory
        for (int i=0;i<inven.getInventoryItems().size();i++){
            if(inven.getInventoryItems().get(i).isEquipped()&&!inven.getInventoryItems().get(i).isAddedindex()){
            if (inven.getInventoryItems().get(i).getType().compareTo("Weapon")==0){
                previousdame=this.dame;
                previouscrit=this.crit;
                previousmagicaldame=this.magicaldame;
                previousattackspeed=this.attackspeed;
                this.dame+=inven.getInventoryItems().get(i).getDame();
                this.crit+=inven.getInventoryItems().get(i).getCrit();
                this.magicaldame+=inven.getInventoryItems().get(i).getMagicaldame();
                this.attackspeed+=inven.getInventoryItems().get(i).getAttackspeed();
            }
            else if (inven.getInventoryItems().get(i).getType().compareTo("Cap")==0){
                previoushealth=this.health;
                previousdef=this.def;
                previousmagicaldef=this.magicaldef;
                this.health+=inven.getInventoryItems().get(i).getHealth();
                this.currenthealth+=inven.getInventoryItems().get(i).getHealth();
                this.def+=inven.getInventoryItems().get(i).getDef();
                this.magicaldef+=inven.getInventoryItems().get(i).getMagicaldef();
            }
            else if (inven.getInventoryItems().get(i).getType().compareTo("Boot")==0){
                previousspeedup=this.speedup;
                previouspowerup=this.powerup;
                this.speedup+=inven.getInventoryItems().get(i).getSpeedup();
                this.powerup+=inven.getInventoryItems().get(i).getPowerup();
                maxVec+=speedup;
            }
            else if (inven.getInventoryItems().get(i).getType().compareTo("Armor")==0){
                previoushealth=this.health;
                previousdef=this.def;
                previousmagicaldef=this.magicaldef;
                this.health+=inven.getInventoryItems().get(i).getHealth();
                this.currenthealth+=inven.getInventoryItems().get(i).getHealth();
                this.def+=inven.getInventoryItems().get(i).getDef();
                this.magicaldef+=inven.getInventoryItems().get(i).getMagicaldef();
            }
            inven.getInventoryItems().get(i).setAddedindex(true);
            }
        }
        //update indexbar
        indexbar.setHpwidth( 170*(int) currenthealth/ (int) health);
        indexbar.setMpwidth( 170*(int) currentmana/(int) mana);
        indexbar.setHpstring(Integer.toString((int) currenthealth).concat("/").concat(Integer.toString((int) health)));
        indexbar.setMpstring(Integer.toString((int) currentmana).concat("/").concat(Integer.toString((int) mana)));
        //update position
        updateXY();
        checkTileMapCollision();//and update constraint X Y stimultaneously// real X, real Y


        setPosition(updatedX, updatedY); //set Constrain to x,y //real movement of Hero

        //update state
        if(currenthealth<=0) {
            dead=true;
//            AudioPlayer audioPlayer=new AudioPlayer("src/Audio/DeathSound.wav");
//            audioPlayer.play();
        }

        if(down){
            if(currentAction!=WALKING||prevUp||prevRight||prevLeft)
            {
                currentAction=WALKING;
                animation.setFrames(sprites.get(3));

                animation.setDuration(0.2);
                prevDown=true;
                prevUp=false;
            }
        }
        else if(up){
            if(currentAction!=WALKING||prevDown||prevRight||prevLeft)
            {
                currentAction=WALKING;
                animation.setFrames(sprites.get(4));

                animation.setDuration(0.2);
                prevUp=true;
                prevDown=false;
            }
        }
        else {
            prevUp=false;
            prevDown=false;
        }
        if(left||right)
        {
            if(!prevLeft&&!prevRight||up||down)
            {

                currentAction=WALKING;
                animation.setFrames(sprites.get(0));

                if(left)
                {
                    prevLeft=true;
                    prevRight=false;
                }
                else {
                    prevRight=true;
                    prevLeft=false;
                }
                animation.setDuration(0.2);
            }

        }
        else {
            prevLeft=false;
            prevRight=false;
        }
        if(!up&&!down&&!left&&!right){
            if (currentAction != IDLE) {
                currentAction = IDLE;
                animation.setFrames(sprites.get(1));
                animation.setDuration(0.5);

            }
        }
        //attack
        if(isIsattack())
            if(currentAction!=ATTACKING){
                currentAction=ATTACKING;
                animation.setFrames(sprites.get(2));
                animation.setDuration(0.5);
            }
        if(isFire())
            if(currentAction != FIRE){
                if(right){
                    currentAction = FIRE;
                    animation.setFrames(sprites.get(5));
                    animation.setDuration(0.1);
                }
                else if(left){
                    currentAction = FIRE;
                    animation.setFrames(sprites.get(5));
                    animation.setDuration(0.1);
                }
                else if(up){
                    currentAction = FIRE;
                    animation.setFrames(sprites.get(6));
                    animation.setDuration(0.1);
                }
                else if(down){
                    currentAction = FIRE;
                    animation.setFrames(sprites.get(7));
                    animation.setDuration(0.1);
                }
                else{
                    currentAction = FIRE;
                    animation.setFrames(sprites.get(7));
                    animation.setDuration(0.1);
                }
            }
        animation.updateFrame();
        //set face direction
        if(right) faceRight=true;
        if(left) faceRight=false;
        if(up) faceDown =false;
        if(down) faceDown=true;


    }

    public void draw(GraphicsContext g)
    {
        setLocalMapPosition();
        super.draw(g);
        for (int i=0;i<inven.getInventoryItems().size();i++) {
            if (inven.getInventoryItems().get(i).isEquipped()) {
                if (inven.getInventoryItems().get(i).getType().compareTo("Weapon")==0){
                    g.drawImage(inven.getInventoryItems().get(i).getTexture(),542,38,25,23);
                }
                else if (inven.getInventoryItems().get(i).getType().compareTo("Cap")==0){
                    g.drawImage(inven.getInventoryItems().get(i).getTexture(),569,37,24,24);
                }
                else if (inven.getInventoryItems().get(i).getType().compareTo("Armor")==0){
                    g.drawImage(inven.getInventoryItems().get(i).getTexture(),597,37,24,26);
                }
                else if (inven.getInventoryItems().get(i).getType().compareTo("Boot")==0){
                    g.drawImage(inven.getInventoryItems().get(i).getTexture(),623,38,24,24);
                }
            }
        }
        if(isFire()){
            if(currentmana>10){
                if(right){
                    g.drawImage(bulletright, x-xCamera+10, y-yCamera-35, 60*2, 25*2);
                    currentmana -= 10;
                }
                else if(left){
                    g.drawImage(bulletleft, x-xCamera-110, y-yCamera-30, 60*2, 25*2);
                    currentmana -= 10;
                }
                else if(up){
                    g.drawImage(bulletup, x-xCamera-30, y-yCamera-140, 25*2, 60*2);
                    currentmana -= 10;
                }
                else if(down){
                    g.drawImage(bulletdown, x-xCamera-25, y-yCamera+8, 25*2, 60*2);
                    currentmana -= 10;
                }
                else {
                    g.drawImage(bulletdown, x-xCamera-25, y-yCamera+8, 25*2, 60*2);
                    currentmana -= 10;
                }
            }
        }

    }
    public double getx()
    {
        return x;

    }
    public double gety()

    {
        return y;
    }
    public double getConstrantX()
    {
        return updatedX;
    }
    public double getConStrantY()
    {
        return updatedY;
    }
    public double getdx()
    {
        return dx;
    }
    public boolean getRight()
    {
        return right;
    }
    public void drawRec(AnchorPane anchorPane)
    {

    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public Inventory getInven() {
        return inven;
    }

    public int getDame() {
        return dame;
    }

    public indexBar getIndexbar() {
        return indexbar;
    }

    public double getCurrenthealth() {
        return currenthealth;
    }

    public void setCurrenthealth(double currenthealth) {
        this.currenthealth = currenthealth;
    }
    public boolean isleftteleport(){
        if ((0<x && x<30)&&(465<y && y<495)) return true;
        else return false;
    }
    public boolean isupteleport(){
        if ((625<x && x<655)&&(0<y && y<30)) return true;
        else return false;
    }
    public boolean isrightteleport(){
        if ((1255<x&&x<1285)&&(465<y&&y<495)) return true;
        else return false;
    }
    public boolean isdownteleport(){
        if((625<x&&x<655)&&(935<y&&y<965)) return true;
        else return false;
    }

    public double getHphealingpersec() {
        return hphealingpersec;
    }

    public void setHphealingpersec(double hphealingpersec) {
        this.hphealingpersec = hphealingpersec;
    }

    public double getMphealingpersec() {
        return mphealingpersec;
    }

    public void setMphealingpersec(double mphealingpersec) {
        this.mphealingpersec = mphealingpersec;
    }

    public double getHealth() {
        return health;
    }

    public double getMana() {
        return mana;
    }

    public double getCurrentmana() {
        return currentmana;
    }

    public void setCurrentmana(double currentmana) {
        this.currentmana = currentmana;
    }


}
