package HUD;


import Entity.*;
import Entity.NPC.ChatBoxHuAn;
import Entity.NPC.HuanRose;
import Inventory.res.ItemManager;
import Map.TileMap;
import accesories.HeroClass;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MainGameWindow {
    protected final int GAMEPANEL_SIZE=720;
    private int currentmap=0;
    private boolean havecap=false,havearmor=false;
    private boolean tick=false;

    public long[] timeToAttack= new long[10];

    private Stage gameStage;
    private Scene gameScece;
    private AnchorPane gamePane;
    private Canvas gameCanvas;
    

    //currentmap
    private boolean isloaded=false;
    //item manager
    private ItemManager itemManager= new ItemManager();
    //inventory

    private boolean inventory=false;
    //Map
    TileMap sp = new TileMap();
    //AudioPlayer[] mapsound = new AudioPlayer[11];
    //player
    Hero savior = new Hero(sp);
    //time attack animation
    private long t;
    //NPC
    HuanRose huanrose = new HuanRose(sp);
    ChatBoxHuAn chatBoxHuAn = new ChatBoxHuAn(sp);
    private boolean npcsoundplayed =false;


    //Enemy
    Enemy2[][] enemy2 =new Enemy2[11][10];
    public void createarrayenemy2(){
        for(int i=0;i<11;i++)
            for (int j=0;j<10;j++) {
                enemy2[i][j] = new Enemy2(sp);
                if(hardMode==true)
                {
                    enemy2[i][j].changeMode();
                }
            }
    }
    Enemy3[][] enemy3=new Enemy3[11][15];
    public void createarrayenemy3(){
            for(int i=0;i<11;i++)
                for (int j=0;j<15;j++)
                {
                    enemy3[i][j]=new Enemy3(sp,15,savior);
                    if(hardMode==true)
                    {
                        enemy3[i][j].changeMode();
                    }
                }
    }
    Enemy4[][] enemy4 = new Enemy4[11][20];
    public void createarrayenemy4(){
        for(int i=0;i<11;i++)
            for (int j=0;j<20;j++)
            {
                enemy4[i][j]=new Enemy4(sp, 15, savior);
                if(hardMode==true)
                {
                    enemy4[i][j].changeMode();
                }

            }
    }
    Enemy5[][] enemy5 = new Enemy5[11][15];
    public void createarrayenemy5() {
        for (int i = 0; i < 11; i++)
            for (int j = 0; j < 15; j++)
            {
                enemy5[i][j] = new Enemy5(sp, 15, savior);
                if(hardMode==true)
                {
                    enemy5[i][j].changeMode();
                }
            }
    }

    FinallBoss[][] finallBoss = new FinallBoss[11][1];
    public void createarrayfinallboss(){
        for(int i=0; i<11; i++){
            finallBoss[i][0] = new FinallBoss(sp, 15, savior);
        }
    }
    BulletEnemy[][] bulletEnemy1 = new BulletEnemy[11][10];
    public void createarraybulletenemy(){
        for(int i=0;i<11;i++)
            for (int j=0;j<10;j++)
            bulletEnemy1[i][j]=new BulletEnemy(sp);
    }
    BulletEnemy bulletEnemy2 = new BulletEnemy(sp);
    ItemDrop[][] itemdrop = new ItemDrop[11][20];
    private boolean[][] checkitemdropenemy=new boolean[11][20];
    public void createcheckitemdropenemy(){
        for(int i=0;i<11;i++)
            for (int j=0;j<10;j++)
                checkitemdropenemy[i][j]=false;
    }


    

    //    private Stage menuStage

    //constructor

    // Timeline
    private Timeline gameLoop = new Timeline();
    private boolean pause=false;

    public MainGameWindow()  throws FileNotFoundException {
        initStage();
        keyListener();
        createOptionButton();
        gameStage.setResizable(false);
    }
        
    public void keyListener() {
        gameScece.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.E) {
                    inventory=!inventory;
                    savior.getInven().setActive(inventory);
                }
                if(!inventory)
                {
                    if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.D){
                        savior.setRightButtonPressed(true);
                    }
                    if (keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.A) {
                        savior.setLeftButtonPressed(true);
                    }
                    if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W) {
                        savior.setUpButtonPressed(true);
                    }
                    if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S) {
                        savior.setDownButtonPressed(true);

                    }
                    if (keyEvent.getCode() == KeyCode.ENTER) {savior.setIsattack(true);
                    }
                    if (keyEvent.getCode() == KeyCode.SPACE) {
                        savior.setIsfire(true);
                    }
                }
                else {
                    if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.D) {
                        if (savior.getInven().getSelectedItem()!=35)
                            savior.getInven().setSelectedItem(savior.getInven().getSelectedItem()+1);
                        else savior.getInven().setSelectedItem(0);
                    }
                    else if (keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.A ) {
                        if (savior.getInven().getSelectedItem()!=0)
                            savior.getInven().setSelectedItem(savior.getInven().getSelectedItem()-1);
                        else savior.getInven().setSelectedItem(35);
                    }
                    else if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W ) {
                        if(savior.getInven().getSelectedItem()>=9)
                            savior.getInven().setSelectedItem(savior.getInven().getSelectedItem()-9);
                    }
                    else if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S) {
                        if(savior.getInven().getSelectedItem()<=26)
                        savior.getInven().setSelectedItem(savior.getInven().getSelectedItem()+9);
                    }
                    else if(keyEvent.getCode()==KeyCode.ENTER)
                        if(inventory){
                        if(savior.getInven().getSelectedItem()<savior.getInven().getInventoryItems().size()) {
                            for(int i=0;i<savior.getInven().getInventoryItems().size();i++){
                                if(savior.getInven().getInventoryItems().get(i).getType().compareTo( savior.getInven().selectedItem().getType())==0 &&
                                savior.getInven().getInventoryItems().get(i).isEquipped()==true && i!=savior.getInven().getSelectedItem()){
                                    tick=true;
                                    savior.getInven().getInventoryItems().get(i).setEquipped(!savior.getInven().getInventoryItems().get(i).isEquipped());
                                    savior.getInven().getInventoryItems().get(i).setAddedindex(false);
                                    if(savior.getInven().getInventoryItems().get(i).getType().compareTo("Weapon")==0) savior.returnpreviousWeaponIndex();
                                    if(savior.getInven().getInventoryItems().get(i).getType().compareTo("Cap")==0) savior.returnpreviousCapIndex();
                                    if(savior.getInven().getInventoryItems().get(i).getType().compareTo("Boot")==0) savior.returnpreviousBootIndex();
                                    if(savior.getInven().getInventoryItems().get(i).getType().compareTo("Armor")==0) savior.returnpreviousArmorIndex();
                                    System.out.println(savior.getInven().getInventoryItems().get(i).getName() +" is unequipped !");
                                }
                            }
                            for(int i=0;i<savior.getInven().getInventoryItems().size();i++){
                                if(savior.getInven().getInventoryItems().get(i).isEquipped()==true &&
                                        savior.getInven().getInventoryItems().get(i).getType().compareTo("Cap")==0) havecap=true;
                                if(savior.getInven().getInventoryItems().get(i).isEquipped()==true &&
                                        savior.getInven().getInventoryItems().get(i).getType().compareTo("Armor")==0) havearmor=true;
                            }
                            savior.getInven().selectedItem().setEquipped(!savior.getInven().selectedItem().isEquipped());
                            if(savior.getInven().getInventoryItems().get(savior.getInven().getSelectedItem()).getType().compareTo("Cap")==0
                            &&havecap&&!havearmor&&!tick) {
                                savior.getInven().getInventoryItems().get(savior.getInven().getSelectedItem()).setAddedindex(false);
                                savior.returndefaultCapIndex();
                                System.out.println(savior.getInven().getInventoryItems().get(savior.getInven().getSelectedItem()).getName()+" is unequipped !");}
                            else if (savior.getInven().getInventoryItems().get(savior.getInven().getSelectedItem()).getType().compareTo("Armor")==0
                                    &&!havecap&&havearmor&&!tick) {
                                savior.getInven().getInventoryItems().get(savior.getInven().getSelectedItem()).setAddedindex(false);
                                savior.returndefaultArmorIndex();
                                System.out.println(savior.getInven().getInventoryItems().get(savior.getInven().getSelectedItem()).getName()+" is unequipped !");}
                            else if (savior.getInven().selectedItem().isEquipped() == true)
                                System.out.println(savior.getInven().getInventoryItems().get(savior.getInven().getSelectedItem()).getName()+" is equipped !");
                            else {
                                savior.getInven().getInventoryItems().get(savior.getInven().getSelectedItem()).setAddedindex(false);
                                if(savior.getInven().getInventoryItems().get(savior.getInven().getSelectedItem()).getType().compareTo("Weapon")==0) savior.returnpreviousWeaponIndex();
                                if(savior.getInven().getInventoryItems().get(savior.getInven().getSelectedItem()).getType().compareTo("Cap")==0) savior.returnpreviousCapIndex();
                                if(savior.getInven().getInventoryItems().get(savior.getInven().getSelectedItem()).getType().compareTo("Boot")==0) savior.returnpreviousBootIndex();
                                if(savior.getInven().getInventoryItems().get(savior.getInven().getSelectedItem()).getType().compareTo("Armor")==0) savior.returnpreviousArmorIndex();
                                System.out.println(savior.getInven().getInventoryItems().get(savior.getInven().getSelectedItem()).getName()+" is unequipped !");
                            }
                            havearmor=false;havecap=false;tick=false;
                        }
                    }
                    else {
                        // attack here

                        }
                }

            }
        });
        gameScece.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(!inventory)
                {
                    if (keyEvent.getCode() == KeyCode.RIGHT||keyEvent.getCode() == KeyCode.D) {
                        savior.setRightButtonPressed(false);
                    }
                    if (keyEvent.getCode() == KeyCode.LEFT||keyEvent.getCode() == KeyCode.A) {
                        savior.setLeftButtonPressed(false);
                        System.out.println(savior.getRight());

                    }
                    if (keyEvent.getCode() == KeyCode.UP||keyEvent.getCode() == KeyCode.W) {
                        savior.setUpButtonPressed(false);;
                    }
                    if (keyEvent.getCode() == KeyCode.DOWN||keyEvent.getCode() == KeyCode.S) {
                        savior.setDownButtonPressed(false);
                    }
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    savior.setIsattack(false);
                }
                    if(keyEvent.getCode() == KeyCode.SPACE){
                        savior.setIsfire(false);
                        savior.setFired(false);
                    }
                }
            }
        });
    }

    private void initStage() {
        gamePane = new AnchorPane();
        gameCanvas = new Canvas(GAMEPANEL_SIZE, GAMEPANEL_SIZE);
        gamePane.getChildren().add(gameCanvas);
        gameScece = new Scene(gamePane, GAMEPANEL_SIZE, GAMEPANEL_SIZE);
        gameStage = new Stage();
        gameStage.setScene(gameScece);

    }

    public void createNewGame(HeroClass heroClass) throws FileNotFoundException {
        gameStage.show();
        gameloop();

    }

    public Stage getGameStage() {
        return gameStage;
    }



    public void gameloop() throws FileNotFoundException {

        //spawn;

            sp.loadTile("src/Map/tileshet.png");
            createarrayenemy2();
            createarraybulletenemy();
            createarrayenemy3();
            createcheckitemdropenemy();
            createarrayenemy4();
            createarrayenemy5();
            createarrayfinallboss();
            for(int i=0;i<10;i++){timeToAttack[i]=System.currentTimeMillis();}


            KeyFrame kf = new KeyFrame(
                    Duration.seconds(0.017),
                    actionEvent -> {
                        checkcurrentMap();
                        gameCanvas.getGraphicsContext2D().clearRect(0, 0, GAMEPANEL_SIZE, GAMEPANEL_SIZE);
                        savior.update();
                        for (int i = 0; i < 15; i++) {
                            if (!enemy3[currentmap][i].isIsdead()) {
                                if (enemy3[currentmap][i].heroInRange()) {
                                    enemy3[currentmap][i].updatePath();
                                    enemy3[currentmap][i].detectHero();
                                }
                            }
                        }
                        for (int i = 0; i < 20; i++) {
                            if (!enemy4[currentmap][i].isIsdead()) {
                                if (enemy4[currentmap][i].heroInRange()) {
                                    enemy4[currentmap][i].updatePath();
                                    enemy4[currentmap][i].detectHero();
                                }
                            }
                        }
                        for (int i = 0; i < 15; i++) {
                            if (!enemy5[currentmap][i].isIsdead()) {
                                if (enemy5[currentmap][i].heroInRange()) {
                                    enemy5[currentmap][i].updatePath();
                                    enemy5[currentmap][i].detectHero();
                                }
                            }
                        }
                        for (int i = 0; i < 10; i++) {
                            enemy2[currentmap][i].update(savior);
                        }
                        if (!finallBoss[10][0].isIsdead()) {
                            if (finallBoss[10][0].heroInRange()) {
                                finallBoss[10][0].updatePath();
                                finallBoss[10][0].detectHero();
                            }
                        }

                        sp.setPosition(savior.getUpdatedX() - GAMEPANEL_SIZE / 2, savior.getUpdatedY() - GAMEPANEL_SIZE / 2);
                        sp.drawMap(gameCanvas.getGraphicsContext2D());
                        sp.drawLayer2(gameCanvas.getGraphicsContext2D());
                        sp.drawLayer3(gameCanvas.getGraphicsContext2D());
                        if (savior.getUpdatedY() > huanrose.getY()) {
                            if (currentmap == 0)
                                huanrose.draw(gameCanvas.getGraphicsContext2D());
                            savior.draw(gameCanvas.getGraphicsContext2D());
                        } else {
                            savior.draw(gameCanvas.getGraphicsContext2D());
                            if (currentmap == 0)
                                huanrose.draw(gameCanvas.getGraphicsContext2D());
                        }

                        //enemy3
                        for (int i = 0; i < 15; i++) {
                            if (enemy3[currentmap][i].isIsdead()) {
                                if (!checkitemdropenemy[currentmap][i]) {
                                    itemdrop[currentmap][i] = new ItemDrop(sp);
                                    checkitemdropenemy[currentmap][i] = !checkitemdropenemy[currentmap][i];
                                    itemdrop[currentmap][i].setPosition(enemy3[currentmap][i].getX(), enemy3[currentmap][i].getY());
                                }
                                if (itemdrop[currentmap][i] != null) {
                                    if (!itemdrop[currentmap][i].getI().isPickedUp())
                                        itemdrop[currentmap][i].draw(gameCanvas.getGraphicsContext2D());
                                    if (itemdrop[currentmap][i].getI().isPickedUp() == false && ((savior.getUpdatedX() > itemdrop[currentmap][i].getX() && savior.getUpdatedX() < (itemdrop[currentmap][i].getX() + itemdrop[currentmap][i].getCwidth())
                                            && savior.getUpdatedY() > itemdrop[currentmap][i].getY() && savior.getUpdatedY() < (itemdrop[currentmap][i].getY() + itemdrop[currentmap][i].getCheight()))
                                            || ((savior.getUpdatedX() + savior.getCwidth()) > itemdrop[currentmap][i].getX() && (savior.getUpdatedX() + savior.getCwidth()) < (itemdrop[currentmap][i].getX() + itemdrop[currentmap][i].getCwidth())
                                            && savior.getUpdatedY() > itemdrop[currentmap][i].getY() && savior.getUpdatedY() < (itemdrop[currentmap][i].getY() + itemdrop[currentmap][i].getCheight()))
                                            || (savior.getUpdatedX() > itemdrop[currentmap][i].getX() && savior.getUpdatedX() < (itemdrop[currentmap][i].getX() + itemdrop[currentmap][i].getCwidth())
                                            && (savior.getUpdatedY() + savior.getCheight()) > itemdrop[currentmap][i].getY() && (savior.getUpdatedY() + savior.getCheight()) < (itemdrop[currentmap][i].getY() + itemdrop[currentmap][i].getCheight()))
                                            || ((savior.getUpdatedX() + savior.getCwidth()) > itemdrop[currentmap][i].getX() && (savior.getUpdatedX() + savior.getCwidth()) < (itemdrop[currentmap][i].getX() + itemdrop[currentmap][i].getCwidth())
                                            && (savior.getUpdatedY() + savior.getCheight()) > itemdrop[currentmap][i].getY() && (savior.getUpdatedY() + savior.getCheight()) < (itemdrop[currentmap][i].getY() + itemdrop[currentmap][i].getCheight())))) {
                                        itemdrop[currentmap][i].getI().setPickedUp(true);
                                        savior.getInven().addItem(itemdrop[currentmap][i].getI());
                                        checkitemdropenemy[currentmap][i] = true;
                                        itemdrop[currentmap][i].setPosition(0, 0);

                                    }
                                }
                            } else {
                                enemy3[currentmap][i].draw(gameCanvas.getGraphicsContext2D());
                                if (!enemy3[currentmap][i].isIsdead()) {
                                    if (enemy3[currentmap][i].heroInRange()) {
                                        enemy3[currentmap][i].updatePath();
                                        enemy3[currentmap][i].detectHero();
                                    }
                                }
                            }
                        }
                        //enemy4
                        for (int i = 0; i < 20; i++) {
                            if (enemy4[currentmap][i].isIsdead()) {
                                if (!checkitemdropenemy[currentmap][i]) {
                                    itemdrop[currentmap][i] = new ItemDrop(sp);
                                    checkitemdropenemy[currentmap][i] = !checkitemdropenemy[currentmap][i];
                                    itemdrop[currentmap][i].setPosition(enemy4[currentmap][i].getX(), enemy4[currentmap][i].getY());
                                }
                                if (itemdrop[currentmap][i] != null) {
                                    if (!itemdrop[currentmap][i].getI().isPickedUp())
                                        itemdrop[currentmap][i].draw(gameCanvas.getGraphicsContext2D());
                                    if (itemdrop[currentmap][i].getI().isPickedUp() == false && ((savior.getUpdatedX() > itemdrop[currentmap][i].getX() && savior.getUpdatedX() < (itemdrop[currentmap][i].getX() + itemdrop[currentmap][i].getCwidth())
                                            && savior.getUpdatedY() > itemdrop[currentmap][i].getY() && savior.getUpdatedY() < (itemdrop[currentmap][i].getY() + itemdrop[currentmap][i].getCheight()))
                                            || ((savior.getUpdatedX() + savior.getCwidth()) > itemdrop[currentmap][i].getX() && (savior.getUpdatedX() + savior.getCwidth()) < (itemdrop[currentmap][i].getX() + itemdrop[currentmap][i].getCwidth())
                                            && savior.getUpdatedY() > itemdrop[currentmap][i].getY() && savior.getUpdatedY() < (itemdrop[currentmap][i].getY() + itemdrop[currentmap][i].getCheight()))
                                            || (savior.getUpdatedX() > itemdrop[currentmap][i].getX() && savior.getUpdatedX() < (itemdrop[currentmap][i].getX() + itemdrop[currentmap][i].getCwidth())
                                            && (savior.getUpdatedY() + savior.getCheight()) > itemdrop[currentmap][i].getY() && (savior.getUpdatedY() + savior.getCheight()) < (itemdrop[currentmap][i].getY() + itemdrop[currentmap][i].getCheight()))
                                            || ((savior.getUpdatedX() + savior.getCwidth()) > itemdrop[currentmap][i].getX() && (savior.getUpdatedX() + savior.getCwidth()) < (itemdrop[currentmap][i].getX() + itemdrop[currentmap][i].getCwidth())
                                            && (savior.getUpdatedY() + savior.getCheight()) > itemdrop[currentmap][i].getY() && (savior.getUpdatedY() + savior.getCheight()) < (itemdrop[currentmap][i].getY() + itemdrop[currentmap][i].getCheight())))) {
                                        itemdrop[currentmap][i].getI().setPickedUp(true);
                                        savior.getInven().addItem(itemdrop[currentmap][i].getI());
                                        checkitemdropenemy[currentmap][1 + i] = true;
                                        itemdrop[currentmap][i].setPosition(0, 0);
                                    }
                                }
                            } else {
                                enemy4[currentmap][i].draw(gameCanvas.getGraphicsContext2D());
                                if (!enemy4[currentmap][i].isIsdead()) {
                                    if (enemy4[currentmap][i].heroInRange()) {
                                        enemy4[currentmap][i].updatePath();
                                        enemy4[currentmap][i].detectHero();
                                    }
                                }
                            }
                        }
                        //enemy2
                        for (int i = 0; i < 10; i++) {
                            if (enemy2[currentmap][i].isIsdead()) {
                                if (!checkitemdropenemy[currentmap][1 + i]) {
                                    itemdrop[currentmap][1 + i] = new ItemDrop(sp);
                                    checkitemdropenemy[currentmap][1 + i] = !checkitemdropenemy[currentmap][1 + i];
                                    itemdrop[currentmap][1 + i].setPosition(enemy2[currentmap][i].getX(), enemy2[currentmap][i].getY());
                                }
                                if (itemdrop[currentmap][1 + i] != null) {
                                    if (!itemdrop[currentmap][1 + i].getI().isPickedUp())
                                        itemdrop[currentmap][1 + i].draw(gameCanvas.getGraphicsContext2D());
                                    if (itemdrop[currentmap][1 + i].getI().isPickedUp() == false && ((savior.getUpdatedX() > itemdrop[currentmap][1 + i].getX() && savior.getUpdatedX() < (itemdrop[currentmap][1 + i].getX() + itemdrop[currentmap][1 + i].getCwidth())
                                            && savior.getUpdatedY() > itemdrop[currentmap][1 + i].getY() && savior.getUpdatedY() < (itemdrop[currentmap][1 + i].getY() + itemdrop[currentmap][1 + i].getCheight()))
                                            || ((savior.getUpdatedX() + savior.getCwidth()) > itemdrop[currentmap][1 + i].getX() && (savior.getUpdatedX() + savior.getCwidth()) < (itemdrop[currentmap][1 + i].getX() + itemdrop[currentmap][1 + i].getCwidth())
                                            && savior.getUpdatedY() > itemdrop[currentmap][1 + i].getY() && savior.getUpdatedY() < (itemdrop[currentmap][1 + i].getY() + itemdrop[currentmap][1 + i].getCheight()))
                                            || (savior.getUpdatedX() > itemdrop[currentmap][1 + i].getX() && savior.getUpdatedX() < (itemdrop[currentmap][1 + i].getX() + itemdrop[currentmap][1 + i].getCwidth())
                                            && (savior.getUpdatedY() + savior.getCheight()) > itemdrop[currentmap][1 + i].getY() && (savior.getUpdatedY() + savior.getCheight()) < (itemdrop[currentmap][1 + i].getY() + itemdrop[currentmap][1 + i].getCheight()))
                                            || ((savior.getUpdatedX() + savior.getCwidth()) > itemdrop[currentmap][1 + i].getX() && (savior.getUpdatedX() + savior.getCwidth()) < (itemdrop[currentmap][1 + i].getX() + itemdrop[currentmap][1 + i].getCwidth())
                                            && (savior.getUpdatedY() + savior.getCheight()) > itemdrop[currentmap][1 + i].getY() && (savior.getUpdatedY() + savior.getCheight()) < (itemdrop[currentmap][1 + i].getY() + itemdrop[currentmap][1 + i].getCheight())))) {
                                        itemdrop[currentmap][1 + i].getI().setPickedUp(true);
                                        savior.getInven().addItem(itemdrop[currentmap][1 + i].getI());
                                        checkitemdropenemy[currentmap][1 + i] = true;
                                        itemdrop[currentmap][1 + i].setPosition(0, 0);
                                    }
                                }
                            } else {
                                enemy2[currentmap][i].draw(gameCanvas.getGraphicsContext2D());
                            }

                            //something
                            if (!savior.isDead()) {
                                if (!enemy2[currentmap][i].isIsdead()) {
                                    if (Math.sqrt(Math.pow((savior.getUpdatedX() - enemy2[currentmap][i].getX()), 2) + Math.pow(savior.getUpdatedY() - enemy2[currentmap][i].getY(), 2)) <= 150) {
                                        if (!bulletEnemy1[currentmap][i].isIsdead()) {
                                            bulletEnemy1[currentmap][i].update(savior, enemy2[currentmap][i], gameCanvas.getGraphicsContext2D());
                                            bulletEnemy1[currentmap][i].draw(gameCanvas.getGraphicsContext2D());
                                        } else if (bulletEnemy1[currentmap][i].isIsdead()) {
                                            if (((System.currentTimeMillis() - timeToAttack[i]) > 2100)) {
                                                timeToAttack[i] = System.currentTimeMillis();
                                                bulletEnemy1[currentmap][i].setPosition(enemy2[currentmap][i].getX(), enemy2[currentmap][i].getY());
                                                bulletEnemy1[currentmap][i].setIsDead(false);
                                            }
                                        }
                                    } else
                                        bulletEnemy1[currentmap][i].setPosition(enemy2[currentmap][i].getX(), enemy2[currentmap][i].getY());
                                } else bulletEnemy1[currentmap][i].setIsDead(true);
                            }
                        }
                        //enemy5
                        //if (enemy5[currentmap][0].getX()!=0)
                        for (int i = 0; i < 15; i++) {
                            if (enemy5[currentmap][i].isIsdead()) {
                                if (!checkitemdropenemy[currentmap][i]) {
                                    itemdrop[currentmap][i] = new ItemDrop(sp);
                                    checkitemdropenemy[currentmap][i] = !checkitemdropenemy[currentmap][i];
                                    itemdrop[currentmap][i].setPosition(enemy5[currentmap][i].getX(), enemy5[currentmap][i].getY());
                                }
                                if (itemdrop[currentmap][i] != null) {
                                    if (!itemdrop[currentmap][i].getI().isPickedUp())
                                        itemdrop[currentmap][i].draw(gameCanvas.getGraphicsContext2D());
                                    if (itemdrop[currentmap][i].getI().isPickedUp() == false && ((savior.getUpdatedX() > itemdrop[currentmap][i].getX() && savior.getUpdatedX() < (itemdrop[currentmap][i].getX() + itemdrop[currentmap][i].getCwidth())
                                            && savior.getUpdatedY() > itemdrop[currentmap][i].getY() && savior.getUpdatedY() < (itemdrop[currentmap][i].getY() + itemdrop[currentmap][i].getCheight()))
                                            || ((savior.getUpdatedX() + savior.getCwidth()) > itemdrop[currentmap][i].getX() && (savior.getUpdatedX() + savior.getCwidth()) < (itemdrop[currentmap][i].getX() + itemdrop[currentmap][i].getCwidth())
                                            && savior.getUpdatedY() > itemdrop[currentmap][i].getY() && savior.getUpdatedY() < (itemdrop[currentmap][i].getY() + itemdrop[currentmap][i].getCheight()))
                                            || (savior.getUpdatedX() > itemdrop[currentmap][i].getX() && savior.getUpdatedX() < (itemdrop[currentmap][i].getX() + itemdrop[currentmap][i].getCwidth())
                                            && (savior.getUpdatedY() + savior.getCheight()) > itemdrop[currentmap][i].getY() && (savior.getUpdatedY() + savior.getCheight()) < (itemdrop[currentmap][i].getY() + itemdrop[currentmap][i].getCheight()))
                                            || ((savior.getUpdatedX() + savior.getCwidth()) > itemdrop[currentmap][i].getX() && (savior.getUpdatedX() + savior.getCwidth()) < (itemdrop[currentmap][i].getX() + itemdrop[currentmap][i].getCwidth())
                                            && (savior.getUpdatedY() + savior.getCheight()) > itemdrop[currentmap][i].getY() && (savior.getUpdatedY() + savior.getCheight()) < (itemdrop[currentmap][i].getY() + itemdrop[currentmap][i].getCheight())))) {
                                        itemdrop[currentmap][i].getI().setPickedUp(true);
                                        savior.getInven().addItem(itemdrop[currentmap][i].getI());
                                        checkitemdropenemy[currentmap][1 + i] = true;
                                        itemdrop[currentmap][i].setPosition(0, 0);
                                    }
                                }
                            } else {
                                enemy5[currentmap][i].draw(gameCanvas.getGraphicsContext2D());
                                if (!enemy5[currentmap][i].isIsdead()) {
                                    if (enemy5[currentmap][i].heroInRange()) {
                                        enemy5[currentmap][i].updatePath();
                                        enemy5[currentmap][i].detectHero();
                                    }
                                }
                            }
                        }
                        //finallboss
                        if (finallBoss[10][0].isIsdead()) {
                            if (!checkitemdropenemy[10][0]) {
                                itemdrop[10][0] = new ItemDrop(sp);
                                checkitemdropenemy[10][0] = !checkitemdropenemy[10][0];
                                itemdrop[10][0].setPosition(finallBoss[10][0].getX(), finallBoss[10][0].getY());
                            }
                            if (itemdrop[10][0] != null) {
                                if (!itemdrop[10][0].getI().isPickedUp())
                                    itemdrop[10][0].draw(gameCanvas.getGraphicsContext2D());
                                if (itemdrop[10][0].getI().isPickedUp() == false && ((savior.getUpdatedX() > itemdrop[10][0].getX() && savior.getUpdatedX() < (itemdrop[10][0].getX() + itemdrop[10][0].getCwidth())
                                        && savior.getUpdatedY() > itemdrop[10][0].getY() && savior.getUpdatedY() < (itemdrop[10][0].getY() + itemdrop[10][0].getCheight()))
                                        || ((savior.getUpdatedX() + savior.getCwidth()) > itemdrop[10][0].getX() && (savior.getUpdatedX() + savior.getCwidth()) < (itemdrop[10][0].getX() + itemdrop[10][0].getCwidth())
                                        && savior.getUpdatedY() > itemdrop[10][0].getY() && savior.getUpdatedY() < (itemdrop[10][0].getY() + itemdrop[10][0].getCheight()))
                                        || (savior.getUpdatedX() > itemdrop[10][0].getX() && savior.getUpdatedX() < (itemdrop[10][0].getX() + itemdrop[10][0].getCwidth())
                                        && (savior.getUpdatedY() + savior.getCheight()) > itemdrop[10][0].getY() && (savior.getUpdatedY() + savior.getCheight()) < (itemdrop[10][0].getY() + itemdrop[10][0].getCheight()))
                                        || ((savior.getUpdatedX() + savior.getCwidth()) > itemdrop[10][0].getX() && (savior.getUpdatedX() + savior.getCwidth()) < (itemdrop[10][0].getX() + itemdrop[10][0].getCwidth())
                                        && (savior.getUpdatedY() + savior.getCheight()) > itemdrop[10][0].getY() && (savior.getUpdatedY() + savior.getCheight()) < (itemdrop[10][0].getY() + itemdrop[10][0].getCheight())))) {
                                    itemdrop[10][0].getI().setPickedUp(true);
                                    savior.getInven().addItem(itemdrop[10][0].getI());
                                    //checkitemdropenemy[10][0+1] = true;
                                    itemdrop[10][0].setPosition(0, 0);
                                }
                            }
                        } else {
                            finallBoss[10][0].draw(gameCanvas.getGraphicsContext2D());
                            if (!finallBoss[10][0].isIsdead()) {
                                if (finallBoss[10][0].heroInRange()) {
                                    finallBoss[10][0].updatePath();
                                    finallBoss[10][0].detectHero();
                                }
                            }
                        }

                        //arrow
                        drawArrow(gameCanvas.getGraphicsContext2D());
                        //

                        if (currentmap == 0 && huanrose.check(savior)) {
                            chatBoxHuAn.draw(gameCanvas.getGraphicsContext2D());

                        }

                        //render inventory
                        savior.getInven().render(gameCanvas.getGraphicsContext2D());
                        //render indexbar
                        savior.getIndexbar().render(gameCanvas.getGraphicsContext2D());

                        //healing hp,mp
                        if (savior.getCurrenthealth() < savior.getHealth())
                            savior.setCurrenthealth(savior.getCurrenthealth() + savior.getHphealingpersec());
                        if (savior.getCurrenthealth() > savior.getHealth()) savior.setCurrenthealth(savior.getHealth());
                        //mp
                        if (savior.getCurrentmana() < savior.getMana())
                            savior.setCurrentmana(savior.getCurrentmana() + savior.getMphealingpersec());
                        if (savior.getCurrentmana() > savior.getMana()) savior.setCurrentmana(savior.getMana());

                        //check attack
                        for (int i = 0; i < 10; i++)
                            if (!enemy2[currentmap][i].isIsdead()) {
                                if (((int) Math.sqrt(Math.pow(((enemy2[currentmap][i].getUpdatedX() + enemy2[currentmap][i].getWidth()) / 2 - (savior.getUpdatedX() + savior.getWidth()) / 2), 2)
                                        + Math.pow(((enemy2[currentmap][i].getUpdatedY() + enemy2[currentmap][i].getHeight()) / 2 - (savior.getUpdatedY() + savior.getHeight()) / 2), 2)) < savior.getAttackrange()) && enemy2[currentmap][i].getCurrenthealth() > 0) {
                                    savior.attack(enemy2[currentmap][i], gameCanvas.getGraphicsContext2D());
                                    savior.fire(savior, enemy2[currentmap][i], gameCanvas.getGraphicsContext2D());
                                }
                            }
                        for (int i = 0; i < 15; i++) {
                            if (!enemy3[currentmap][i].isIsdead()) {
                                if (((int) Math.sqrt(Math.pow(((enemy3[currentmap][i].getUpdatedX() + enemy3[currentmap][i].getWidth()) / 2 - (savior.getUpdatedX() + savior.getWidth()) / 2), 2)
                                        + Math.pow(((enemy3[currentmap][i].getUpdatedY() + enemy3[currentmap][i].getHeight()) / 2 - (savior.getUpdatedY() + savior.getHeight()) / 2), 2)) < savior.getAttackrange()) && enemy3[currentmap][i].getCurrenthealth() > 0) {
                                    savior.attack(enemy3[currentmap][i], gameCanvas.getGraphicsContext2D());
                                    savior.fire(savior, enemy3[currentmap][i], gameCanvas.getGraphicsContext2D());
                                }
                                enemy3[currentmap][i].collideattack(savior, gameCanvas.getGraphicsContext2D());
                            }
                        }
                        for (int i = 0; i < 20; i++){
                            if (!enemy4[currentmap][i].isIsdead()) {
                                if (((int) Math.sqrt(Math.pow(((enemy4[currentmap][i].getUpdatedX() + enemy4[currentmap][i].getWidth()) / 2 - (savior.getUpdatedX() + savior.getWidth()) / 2), 2)
                                        + Math.pow(((enemy4[currentmap][i].getUpdatedY() + enemy4[currentmap][i].getHeight()) / 2 - (savior.getUpdatedY() + savior.getHeight()) / 2), 2)) < savior.getAttackrange()) && enemy4[currentmap][i].getCurrenthealth() > 0) {
                                    savior.attack(enemy4[currentmap][i], gameCanvas.getGraphicsContext2D());
                                    savior.fire(savior, enemy4[currentmap][i], gameCanvas.getGraphicsContext2D());
                                }
                                enemy4[currentmap][i].collideattack(savior, gameCanvas.getGraphicsContext2D());
                            }
                        }
                        for(int i=0;i<15;i++)
                        if (!enemy5[currentmap][i].isIsdead()){
                            if (((int) Math.sqrt(Math.pow(((enemy5[currentmap][i].getUpdatedX()+enemy5[currentmap][i].getWidth())/2-(savior.getUpdatedX()+savior.getWidth())/2),2)
                                    +Math.pow(((enemy5[currentmap][i].getUpdatedY()+enemy5[currentmap][i].getHeight())/2-(savior.getUpdatedY()+savior.getHeight())/2),2))<savior.getAttackrange())&&enemy5[currentmap][i].getCurrenthealth()>0)
                            {
                                savior.attack(enemy5[currentmap][i],gameCanvas.getGraphicsContext2D());
                                savior.fire(savior, enemy5[currentmap][i],gameCanvas.getGraphicsContext2D());
                            }
                            enemy5[currentmap][i].collideattack(savior,gameCanvas.getGraphicsContext2D());
                        }
                        if(!finallBoss[10][0].isIsdead()){
                            if (((int) Math.sqrt(Math.pow(((finallBoss[10][0].getUpdatedX()+finallBoss[10][0].getWidth())/2-(savior.getUpdatedX()+savior.getWidth())/2),2)
                                    +Math.pow(((finallBoss[10][0].getUpdatedY()+finallBoss[10][0].getHeight())/2-(savior.getUpdatedY()+savior.getHeight())/2),2))<savior.getAttackrange())&&finallBoss[10][0].getCurrenthealth()>0)
                            {
                                savior.attack(finallBoss[10][0],gameCanvas.getGraphicsContext2D());
                                savior.fire(savior, finallBoss[10][0],gameCanvas.getGraphicsContext2D());
                            }
                            finallBoss[10][0].collideattack(savior,gameCanvas.getGraphicsContext2D());
                        }
                        if(System.currentTimeMillis()-savior.getThelastheroattacktime()>500) savior.setAttacked(false);
                    }
            );

        gameLoop.setCycleCount(Animation.INDEFINITE);
        gameLoop.getKeyFrames().add(kf);


        gameLoop.play();
    }

    public void createOptionButton()
    {


        Button optionButton=new Button();
        optionButton.setPrefWidth(30);
        optionButton.setPrefHeight(30);
        SettingScece settingScece=new SettingScece(new AnchorPane());
        gamePane.getChildren().addAll(settingScece);


        try{
            //option button
            Image image=new Image(new FileInputStream("src/HUD/Reso/option button.png"),30,30,true,true);
            BackgroundImage backgroundImage=new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,null);
            optionButton.setBackground(new Background(backgroundImage));
            optionButton.setPadding(Insets.EMPTY);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        gamePane.getChildren().add(optionButton);

        optionButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(pause==false)
                {
                    pause=true;
                    gameLoop.stop();
                    gameCanvas.setEffect(new BoxBlur());
                    settingScece.transition();
                }
                else {
                    pause=false;
                    gameLoop.play();
                    gameCanvas.setEffect(null);
                    settingScece.transition();
                }
                gamePane.requestFocus();
            }
        });

        gamePane.requestFocus();
        settingScece.getResumeButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                pause=false;
                settingScece.transition();
                gameCanvas.setEffect(null);
                gameLoop.play();
                gamePane.requestFocus();
            }
        });


    }
    public boolean isPause()
    {
        return pause;
    }

    public boolean isInventory() {
        return inventory;
    }

    public void setInventory(boolean inventory) {
        this.inventory = inventory;
    }
    public void checkcurrentMap(){
        if(savior.isDead()==true) {
            //mapsound[currentmap].stop();
            currentmap=0;
            savior.setDead(false);
            savior.setCurrenthealth(savior.getHealth());
            isloaded=false;
        }
        if(currentmap==0&&savior.isrightteleport()) {currentmap=1; isloaded=false;}
        else if(currentmap==1){
            if(savior.isleftteleport()) {currentmap=0;isloaded=false;}
            else if(savior.isupteleport()){currentmap=2;isloaded=false;}
            else if(savior.isdownteleport()){currentmap=3;isloaded=false;}
            else if(savior.isrightteleport()) {currentmap=5;isloaded=false;}
        }
        else if(currentmap==2){
            if(savior.isdownteleport()){currentmap=1;isloaded=false;}
        }
        else if(currentmap==3){
            if(savior.isupteleport()){currentmap=1;isloaded=false;}
            else if(savior.isdownteleport()){currentmap=4;isloaded=false;}
            else if(savior.isrightteleport()) {currentmap=7;isloaded=false;}
        }
        else if(currentmap==4){
            if(savior.isrightteleport()){currentmap=8;isloaded=false;}
            else if(savior.isupteleport()) {currentmap=3;isloaded=false;}
        }
        else if(currentmap==5){
            if(savior.isleftteleport()) {currentmap=1;isloaded=false;}
            else if(savior.isupteleport()){currentmap=6;isloaded=false;}
            else if(savior.isdownteleport()){currentmap=7;isloaded=false;}
            else if(savior.isrightteleport()) {currentmap=9;isloaded=false;}
        }
        else if(currentmap==6){
            if(savior.isdownteleport()){currentmap=5;isloaded=false;}
        }
        else if(currentmap==7){
            if(savior.isleftteleport()) {currentmap=3;isloaded=false;}
            else if(savior.isupteleport()){currentmap=5;isloaded=false;}
            else if(savior.isdownteleport()){currentmap=8;isloaded=false;}
            else if(savior.isrightteleport()) {currentmap=10;isloaded=false;}
        }
        else if(currentmap==8){
            if(savior.isleftteleport()) {currentmap=4;isloaded=false;}
            else if(savior.isupteleport()){currentmap=7;isloaded=false;}
        }
        else if(currentmap==9){
            if(savior.isleftteleport()) {currentmap=5;isloaded=false;}
            else if(savior.isdownteleport()){currentmap=10;isloaded=false;}
        }
        else if(currentmap==10){
            if(savior.isleftteleport()) {currentmap=7;isloaded=false;}
            else if(savior.isupteleport()){currentmap=9;isloaded=false;}
        }

        if (currentmap==0&&!isloaded) {
            //map
            sp.loadMap("src/Map/RPG/spawnDesert.tmx");
            sp.setPosition(0.0, 0.0);
            //map sound
            //mapsound[currentmap]= new AudioPlayer("src\\Audio\\map"+(currentmap+1)+"sound.wav");
            //for(int i=0;i<11;i++) if(mapsound[i]!=null) mapsound[i].stop();
            //mapsound[currentmap].mixingVolume(-30);
            //mapsound[currentmap].play();
            //npc
            huanrose.setPosition(900,450);
            chatBoxHuAn.setPosition(790,335);
            //savior.setPosition(sp.spawnX, sp.spawnY);
            savior.setPosition(800,500);
            enemy2[currentmap][0].setPosition(30, 420);
            enemy2[currentmap][1].setPosition(500,550);
            enemy2[currentmap][2].setPosition(550,900);
            enemy2[currentmap][3].setPosition(1200,700);
            enemy2[currentmap][4].setPosition(1300,750);
            enemy2[currentmap][5].setPosition(1250,600);
            enemy2[currentmap][6].setPosition(1150,500);
            enemy2[currentmap][7].setPosition(1000,750);
            enemy2[currentmap][8].setPosition(500,750);
            enemy2[currentmap][9].setPosition(900,900);
            isloaded=true;
        }
        else if (currentmap==1&&!isloaded){
            //map
            sp.loadMap("src/Map/RPG/midDeasert.tmx");
            sp.setPosition(0.0, 0.0);
            //map sound
            //mapsound[currentmap]= new AudioPlayer("src\\Audio\\map"+(currentmap+1)+"sound.wav");
            //for(int i=0;i<11;i++) if(mapsound[i]!=null) mapsound[i].stop();
            //mapsound[currentmap].mixingVolume(-30);
            //mapsound[currentmap].play();
            //savior && enemy
            savior.setPosition(sp.spawnX, sp.spawnY);

            enemy5[currentmap][0].setPosition(30, 420);
            enemy5[currentmap][1].setPosition(500,550);
            enemy5[currentmap][2].setPosition(550,900);
            enemy5[currentmap][3].setPosition(1200,700);
            enemy5[currentmap][4].setPosition(1300,750);
            enemy5[currentmap][5].setPosition(1250,600);
            enemy5[currentmap][6].setPosition(1150,500);
            enemy5[currentmap][7].setPosition(1000,750);
            enemy5[currentmap][8].setPosition(500,750);
            enemy5[currentmap][9].setPosition(900,900);
            enemy5[currentmap][10].setPosition(100, 100);
            enemy5[currentmap][11].setPosition(200, 600);
            enemy5[currentmap][12].setPosition(800, 100);
            enemy5[currentmap][13].setPosition(300, 900);
            enemy5[currentmap][14].setPosition(100, 600);
            isloaded=true;
        }
        else if (currentmap==2&&!isloaded){
            //map
            sp.loadMap("src/Map/RPG/Temple.tmx");
            sp.setPosition(0.0, 0.0);
            //map sound
            //mapsound[currentmap]= new AudioPlayer("src\\Audio\\map"+(currentmap+1)+"sound.wav");
            //for(int i=0;i<11;i++) if(mapsound[i]!=null) mapsound[i].stop();
           // mapsound[currentmap].mixingVolume(-10);
           // mapsound[currentmap].play();
            //
            savior.setPosition(sp.spawnX, sp.spawnY);
            enemy3[currentmap][0].setPosition(50, 50);
            enemy3[currentmap][1].setPosition(100, 800);
            enemy3[currentmap][2].setPosition(400, 300);
            enemy3[currentmap][3].setPosition(900, 50);
            enemy3[currentmap][4].setPosition(140, 620);
            enemy3[currentmap][5].setPosition(300, 100);
            enemy3[currentmap][6].setPosition(400, 50);
            enemy3[currentmap][7].setPosition(500, 500);
            enemy3[currentmap][8].setPosition(200, 70);
            enemy3[currentmap][9].setPosition(130, 250);
            enemy3[currentmap][10].setPosition(600, 890);
            enemy3[currentmap][11].setPosition(700, 300);
            enemy3[currentmap][12].setPosition(500, 250);
            enemy3[currentmap][13].setPosition(120, 760);
            enemy3[currentmap][14].setPosition(500, 610);
            isloaded=true;
        }
        else if (currentmap==3&&!isloaded){
            //map
            sp.loadMap("src/Map/RPG/underDesert.tmx");
            sp.setPosition(0.0, 0.0);
            //map sound
            //mapsound[currentmap]= new AudioPlayer("src\\Audio\\map"+(currentmap+1)+"sound.wav");
            //for(int i=0;i<11;i++) if(mapsound[i]!=null) mapsound[i].stop();
            //mapsound[currentmap].mixingVolume(-10);
            //mapsound[currentmap].play();
            //
            savior.setPosition(sp.spawnX, sp.spawnY);
            enemy4[currentmap][0].setPosition(50, 50);
            enemy4[currentmap][1].setPosition(100, 800);
            enemy4[currentmap][2].setPosition(400, 300);
            enemy4[currentmap][3].setPosition(900, 50);
            enemy4[currentmap][4].setPosition(140, 620);
            enemy4[currentmap][5].setPosition(300, 100);
            enemy4[currentmap][6].setPosition(400, 50);
            enemy4[currentmap][7].setPosition(10, 500);
            enemy4[currentmap][8].setPosition(200, 70);
            enemy4[currentmap][9].setPosition(130, 250);
            enemy4[currentmap][10].setPosition(600, 890);
            enemy4[currentmap][11].setPosition(700, 300);
            enemy4[currentmap][12].setPosition(500, 250);
            enemy4[currentmap][13].setPosition(120, 760);
            enemy4[currentmap][14].setPosition(500, 610);
            enemy4[currentmap][15].setPosition(340, 610);
            enemy4[currentmap][16].setPosition(10, 610);
            enemy4[currentmap][17].setPosition(730, 420);
            enemy4[currentmap][18].setPosition(500, 800);
            enemy4[currentmap][19].setPosition(310, 400);

            isloaded=true;
        }
        else if (currentmap==4&&!isloaded){
            //map
            sp.loadMap("src/Map/RPG/Under2desert.tmx");
            sp.setPosition(0.0, 0.0);
            //map sound
           // mapsound[currentmap]= new AudioPlayer("src\\Audio\\map"+(currentmap+1)+"sound.wav");
           // for(int i=0;i<11;i++) if(mapsound[i]!=null) mapsound[i].stop();
           // mapsound[currentmap].mixingVolume(-10);
           // mapsound[currentmap].play();
            //
            savior.setPosition(sp.spawnX, sp.spawnY);
            enemy4[currentmap][0].setPosition(50, 50);
            enemy4[currentmap][1].setPosition(100, 800);
            enemy4[currentmap][2].setPosition(400, 300);
            enemy4[currentmap][3].setPosition(900, 50);
            enemy4[currentmap][4].setPosition(140, 620);
            enemy3[currentmap][3].setPosition(600, 50);
            enemy3[currentmap][4].setPosition(140, 900);
            enemy3[currentmap][5].setPosition(300, 100);
            enemy3[currentmap][6].setPosition(400, 50);
            enemy3[currentmap][7].setPosition(500, 500);
            enemy2[currentmap][1].setPosition(500,550);
            enemy2[currentmap][2].setPosition(550,900);
            enemy2[currentmap][3].setPosition(1200,700);
            isloaded=true;
        }
        else if (currentmap==5&&!isloaded){
            //map
            sp.loadMap("src/Map/RPG/Right1Desert.tmx");
            sp.setPosition(0.0, 0.0);
            //map sound
           // mapsound[currentmap]= new AudioPlayer("src\\Audio\\map"+(currentmap+1)+"sound.wav");
          //  for(int i=0;i<11;i++) if(mapsound[i]!=null) mapsound[i].stop();
           // mapsound[currentmap].mixingVolume(-10);
           // mapsound[currentmap].play();
            //
            savior.setPosition(sp.spawnX, sp.spawnY);
            enemy5[currentmap][0].setPosition(30, 420);
            enemy5[currentmap][1].setPosition(500,550);
            enemy5[currentmap][2].setPosition(550,900);
            enemy5[currentmap][3].setPosition(1200,700);
            enemy5[currentmap][4].setPosition(1300,750);
            enemy5[currentmap][5].setPosition(1250,600);
            enemy5[currentmap][6].setPosition(1150,500);
            enemy5[currentmap][7].setPosition(1000,750);
            enemy3[currentmap][10].setPosition(600, 890);
            enemy3[currentmap][11].setPosition(700, 300);
            enemy3[currentmap][12].setPosition(500, 250);
            enemy3[currentmap][13].setPosition(120, 760);
            enemy3[currentmap][14].setPosition(500, 610);

            isloaded=true;
        }
        else if (currentmap==6&&!isloaded){
            //map
            sp.loadMap("src/Map/RPG/aboveright1desert.tmx");
            sp.setPosition(0.0, 0.0);
            //map sound
         //   mapsound[currentmap]= new AudioPlayer("src\\Audio\\map"+(currentmap+1)+"sound.wav");
           // for(int i=0;i<11;i++) if(mapsound[i]!=null) mapsound[i].stop();
           // mapsound[currentmap].mixingVolume(-10);
           // mapsound[currentmap].play();
            //
            savior.setPosition(sp.spawnX, sp.spawnY);
            enemy2[currentmap][0].setPosition(30, 420);
            enemy2[currentmap][1].setPosition(500,550);
            enemy2[currentmap][2].setPosition(550,900);
            enemy2[currentmap][3].setPosition(1200,700);
            enemy2[currentmap][4].setPosition(1300,750);
            enemy2[currentmap][5].setPosition(1250,600);
            enemy2[currentmap][6].setPosition(1150,500);
            enemy2[currentmap][7].setPosition(1000,750);
            enemy2[currentmap][8].setPosition(500,750);
            enemy2[currentmap][9].setPosition(900,900);
            enemy3[currentmap][2].setPosition(400, 300);
            enemy3[currentmap][3].setPosition(900, 50);
            enemy3[currentmap][4].setPosition(140, 620);
            enemy3[currentmap][5].setPosition(300, 100);
            enemy3[currentmap][6].setPosition(400, 50);
            enemy3[currentmap][0].setPosition(50, 50);
            isloaded=true;
        }
        else if (currentmap==7&&!isloaded){
            //map
            sp.loadMap("src/Map/RPG/farm.tmx");
            sp.setPosition(0.0, 0.0);
            //map sound
            //mapsound[currentmap]= new AudioPlayer("src\\Audio\\map"+(currentmap+1)+"sound.wav");
            //for(int i=0;i<11;i++) if(mapsound[i]!=null) mapsound[i].stop();
            //mapsound[currentmap].mixingVolume(-10);
            //mapsound[currentmap].play();
            //
            savior.setPosition(sp.spawnX, sp.spawnY);
            enemy4[currentmap][0].setPosition(50, 50);
            enemy4[currentmap][1].setPosition(100, 800);
            enemy4[currentmap][2].setPosition(400, 300);
            enemy4[currentmap][3].setPosition(900, 50);
            enemy4[currentmap][4].setPosition(140, 620);
            enemy4[currentmap][5].setPosition(300, 100);
            enemy4[currentmap][6].setPosition(400, 50);
            enemy4[currentmap][7].setPosition(500, 500);
            enemy4[currentmap][8].setPosition(200, 70);
            enemy4[currentmap][9].setPosition(130, 250);
            enemy4[currentmap][10].setPosition(600, 890);
            enemy4[currentmap][11].setPosition(700, 300);
            enemy4[currentmap][12].setPosition(500, 250);
            enemy4[currentmap][13].setPosition(120, 760);
            enemy4[currentmap][14].setPosition(500, 610);
            enemy4[currentmap][15].setPosition(340, 610);
            enemy4[currentmap][16].setPosition(10, 610);
            enemy4[currentmap][17].setPosition(730, 420);
            enemy4[currentmap][18].setPosition(500, 800);
            enemy4[currentmap][19].setPosition(310, 400);
            enemy5[currentmap][0].setPosition(30, 420);
            enemy5[currentmap][1].setPosition(500,550);
            enemy5[currentmap][2].setPosition(550,900);
            enemy5[currentmap][3].setPosition(1200,700);
            enemy5[currentmap][4].setPosition(1300,750);
            enemy5[currentmap][5].setPosition(1250,600);
            enemy5[currentmap][6].setPosition(1150,500);
            isloaded=true;
        }
        else if (currentmap==8&&!isloaded){
            sp.loadMap("src/Map/RPG/underFarm.tmx");
            sp.setPosition(0.0, 0.0);
            //map sound
            //mapsound[currentmap]= new AudioPlayer("src\\Audio\\map"+(currentmap+1)+"sound.wav");
            //for(int i=0;i<11;i++) if(mapsound[i]!=null) mapsound[i].stop();
            //mapsound[currentmap].mixingVolume(-10);
            //mapsound[currentmap].play();
            //
            savior.setPosition(sp.spawnX, sp.spawnY);
            enemy3[currentmap][0].setPosition(50, 50);
            enemy3[currentmap][1].setPosition(100, 800);
            enemy3[currentmap][2].setPosition(400, 300);
            enemy3[currentmap][3].setPosition(900, 50);
            enemy3[currentmap][4].setPosition(140, 620);
            enemy3[currentmap][5].setPosition(300, 100);
            enemy3[currentmap][6].setPosition(400, 50);
            enemy3[currentmap][7].setPosition(500, 500);
            enemy2[currentmap][2].setPosition(550,900);
            enemy2[currentmap][3].setPosition(1200,700);
            enemy2[currentmap][4].setPosition(1300,750);
            enemy2[currentmap][5].setPosition(1250,600);
            enemy2[currentmap][6].setPosition(1150,500);
            enemy2[currentmap][7].setPosition(1000,750);
            isloaded=true;
        }
        else if (currentmap==9&&!isloaded){
            sp.loadMap("src/Map/RPG/Village.tmx");
            sp.setPosition(0.0, 0.0);
            //map sound
            //mapsound[currentmap]= new AudioPlayer("src\\Audio\\map"+(currentmap+1)+"sound.wav");
            //for(int i=0;i<11;i++) if(mapsound[i]!=null) mapsound[i].stop();
            //mapsound[currentmap].mixingVolume(-10);
            //mapsound[currentmap].play();
            //
            savior.setPosition(sp.spawnX, sp.spawnY);
            enemy5[currentmap][4].setPosition(1300,750);
            enemy5[currentmap][5].setPosition(1250,600);
            enemy5[currentmap][6].setPosition(1150,500);
            enemy5[currentmap][7].setPosition(1000,750);
            enemy5[currentmap][8].setPosition(500,750);
            enemy5[currentmap][9].setPosition(900,900);
            enemy3[currentmap][0].setPosition(50, 50);
            enemy3[currentmap][9].setPosition(130, 250);
            enemy3[currentmap][10].setPosition(600, 890);
            enemy3[currentmap][11].setPosition(700, 300);
            enemy3[currentmap][12].setPosition(500, 250);
            isloaded=true;
        }
        else if (currentmap==10&&!isloaded){
            //map
            sp.loadMap("src/Map/RPG/rightfarm.tmx");
            sp.setPosition(0.0, 0.0);
            //map sound
            //mapsound[currentmap]= new AudioPlayer("src\\Audio\\mapbosssound.wav");
           // for(int i=0;i<11;i++) if(mapsound[i]!=null) mapsound[i].stop();
            //mapsound[currentmap].mixingVolume(-10);
            //mapsound[currentmap].play();
            //
            savior.setPosition(sp.spawnX, sp.spawnY);
            enemy2[currentmap][3].setPosition(1200,700);
            enemy2[currentmap][4].setPosition(1300,750);
            enemy2[currentmap][5].setPosition(1250,600);
            enemy2[currentmap][6].setPosition(1150,500);
            enemy2[currentmap][7].setPosition(1000,750);
            enemy2[currentmap][8].setPosition(500,750);
            enemy4[currentmap][4].setPosition(140, 620);
            enemy4[currentmap][5].setPosition(300, 100);
            enemy4[currentmap][6].setPosition(400, 50);
            enemy4[currentmap][7].setPosition(500, 500);
            enemy4[currentmap][8].setPosition(200, 70);
            enemy3[currentmap][7].setPosition(500, 500);
            enemy3[currentmap][8].setPosition(200, 70);
            enemy3[currentmap][9].setPosition(130, 250);
            enemy3[currentmap][10].setPosition(600, 890);
            enemy5[currentmap][3].setPosition(120,600);
            enemy5[currentmap][4].setPosition(1300,1050);
            enemy5[currentmap][5].setPosition(1250,690);
            enemy5[currentmap][6].setPosition(1150,400);
            enemy5[currentmap][7].setPosition(1020,750);
            enemy5[currentmap][8].setPosition(540,750);
            finallBoss[10][0].setPosition(1100, 320);
            isloaded=true;
        }
    }
    public void drawArrow(GraphicsContext g){
        if(currentmap==0){
            //right
            sp.getArrow1().setPosition(1260,470);
            sp.getArrow1().draw(g);
            sp.getArrow1().update();
        }
        else if(currentmap==1){
            //right
            sp.getArrow1().setPosition(1260,470);
            sp.getArrow1().draw(g);
            sp.getArrow1().update();
            //left
            sp.getArrow2().setPosition(10,470);
            sp.getArrow2().draw(g);
            sp.getArrow2().update();
            //up
            sp.getArrow3().setPosition(630,10);
            sp.getArrow3().draw(g);
            sp.getArrow3().update();
            //down
            sp.getArrow4().setPosition(630,940);
            sp.getArrow4().draw(g);
            sp.getArrow4().update();
        }
        else if (currentmap==2){
            //down
            sp.getArrow4().setPosition(630,940);
            sp.getArrow4().draw(g);
            sp.getArrow4().update();
        }
        else if (currentmap==3){
            //right
            sp.getArrow1().setPosition(1260,470);
            sp.getArrow1().draw(g);
            sp.getArrow1().update();
            //up
            sp.getArrow3().setPosition(630,10);
            sp.getArrow3().draw(g);
            sp.getArrow3().update();
            //down
            sp.getArrow4().setPosition(630,940);
            sp.getArrow4().draw(g);
            sp.getArrow4().update();
        }
        else if (currentmap==4){
            //right
            sp.getArrow1().setPosition(1260,470);
            sp.getArrow1().draw(g);
            sp.getArrow1().update();
            //up
            sp.getArrow3().setPosition(630,10);
            sp.getArrow3().draw(g);
            sp.getArrow3().update();
        }
        else if(currentmap==5){
            //right
            sp.getArrow1().setPosition(1260,470);
            sp.getArrow1().draw(g);
            sp.getArrow1().update();
            //left
            sp.getArrow2().setPosition(10,470);
            sp.getArrow2().draw(g);
            sp.getArrow2().update();
            //up
            sp.getArrow3().setPosition(630,10);
            sp.getArrow3().draw(g);
            sp.getArrow3().update();
            //down
            sp.getArrow4().setPosition(630,940);
            sp.getArrow4().draw(g);
            sp.getArrow4().update();
        }
        else if(currentmap==6){
            //down
            sp.getArrow4().setPosition(630,940);
            sp.getArrow4().draw(g);
            sp.getArrow4().update();
        }
        else if(currentmap==7){
            //right
            sp.getArrow1().setPosition(1260,470);
            sp.getArrow1().draw(g);
            sp.getArrow1().update();
            //left
            sp.getArrow2().setPosition(10,470);
            sp.getArrow2().draw(g);
            sp.getArrow2().update();
            //up
            sp.getArrow3().setPosition(630,10);
            sp.getArrow3().draw(g);
            sp.getArrow3().update();
            //down
            sp.getArrow4().setPosition(630,940);
            sp.getArrow4().draw(g);
            sp.getArrow4().update();
        }
        else if(currentmap==8){
            //left
            sp.getArrow2().setPosition(10,470);
            sp.getArrow2().draw(g);
            sp.getArrow2().update();
            //up
            sp.getArrow3().setPosition(630,10);
            sp.getArrow3().draw(g);
            sp.getArrow3().update();
        }
        else if(currentmap==9){
            //left
            sp.getArrow2().setPosition(10,470);
            sp.getArrow2().draw(g);
            sp.getArrow2().update();
            //down
            sp.getArrow4().setPosition(630,940);
            sp.getArrow4().draw(g);
            sp.getArrow4().update();
        }
        else if (currentmap==10){
            //left
            sp.getArrow2().setPosition(10,470);
            sp.getArrow2().draw(g);
            sp.getArrow2().update();
            //up
            sp.getArrow3().setPosition(630,10);
            sp.getArrow3().draw(g);
            sp.getArrow3().update();
        }
    }
    private boolean hardMode=false;
    public void setHardMode(boolean mode)
    {
        hardMode=mode;
    }
}


