package Map;

import Entity.*;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import HUD.MainGameWindow.*;
import java.io.*;


public class TileMap {
    public final int GAMEPANEL_SIZE=720;

    //
    ArrowDown arrow1 =new ArrowDown(this);
    ArrowDown arrow2 =new ArrowDown(this);
    ArrowDown arrow3 =new ArrowDown(this);
    ArrowDown arrow4 =new ArrowDown(this);
    //position
    private double x;
    private double y;
    //bounds
    private int xmin;
    private int ymin;
    private int ymax;
    private int xmax;

    private double tween;

    //map
    private int [][] map;
    private int numRows;
    private int numCols;
    private int width;
    private int height;

    //spawn point
    public int spawnX;
    public int spawnY;

    //tileset
    private Image tileset;
    private int numTileHorizontal;
    private int numTileVertical;
    private Tile[][] tiles;

    //drawing
    private int startRow;
    private int startCol;
    private int rowToDraw;
    private int colToDraw;

        public TileMap() throws FileNotFoundException {

     rowToDraw=GAMEPANEL_SIZE /32+2;
     colToDraw=GAMEPANEL_SIZE/32+2;

    }
    public void loadTile(String s)
    {
        try{
            tileset=new Image(new FileInputStream(s));
            numTileHorizontal=(int)tileset.getWidth()/32;
            numTileVertical=(int)tileset.getHeight()/32;
            tiles=new Tile[numTileVertical][numTileHorizontal];
            Image subPixel;
            for(int row=0;row<numTileVertical;row++)
            {
                for(int col=0;col<numTileHorizontal;col++)
                {
                    subPixel=new WritableImage(tileset.getPixelReader(),32*col,
                            32*row,
                            32,32);
                    if(row==0&&col==0)
                    {
                        tiles[row][col]=new Tile(subPixel,Tile.NORMAL);
                    }
                    else {
                        tiles[row][col]=new Tile(subPixel,Tile.BLOCKED);
                    }

                }
            }


        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private int layer2[][];//extra layer for decorations
    private int layer3[][];

    public void loadMap(String s)
    {

        try{
            InputStream in=new FileInputStream(s);
            BufferedReader br=new BufferedReader(new InputStreamReader(in));
            numCols=Integer.parseInt(br.readLine());
            numRows=Integer.parseInt(br.readLine());
            spawnX=Integer.parseInt(br.readLine());
            spawnY=Integer.parseInt(br.readLine());
            width = numCols * 32;
            height = numRows * 32;
            xmin = 0;// size cua cancas la GAMEPANEL_SIZE,GAMEPANEL_SIZE
            xmax = width-GAMEPANEL_SIZE;
            ymin = 0;
            ymax = height-GAMEPANEL_SIZE;

            map=new int[numRows][numCols];
            //layer2
            layer2=new int[numRows][numCols];
            //layer3
            layer3=new int[numRows][numCols];

            width=numCols*32;
            height=numRows*32;

            //basic layer for movement
            for(int row =0 ;row<numRows ;row++)
            {
                String line=br.readLine();
                String[] tokens=line.split(",");
                for(int col=0;col<numCols;col++)
                {
                    map[row][col]=Integer.parseInt(tokens[col]);
                }
            }
            //layer 2
            for(int row =0 ;row<numRows ;row++)
            {
                String line=br.readLine();
                String[] tokens=line.split(",");
                for(int col=0;col<numCols;col++)
                {
                    layer2[row][col]=Integer.parseInt(tokens[col]);
                }
            }
            //layer 3
            for(int row =0 ;row<numRows ;row++)
           {
                String line=br.readLine();
                String[] tokens=line.split(",");
                for(int col=0;col<numCols;col++)
                {
                   layer3[row][col]=Integer.parseInt(tokens[col]);
                }
            }


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public int getType(int row,int col)
    {
        if(row<0||col<0||col>39||row>29)
        {
            return Tile.BLOCKED;
        }

        //qui doi vi tri tuong ung
        int tileIndex=layer2[row][col];//va cham layer 2
        if(tileIndex==0)
        {
            return Tile.NORMAL;
        }

        int r = (tileIndex-1) / numTileHorizontal;

        int c = (tileIndex-1) % numTileHorizontal;


        return tiles[r][c].getType();
    }

    public void setPosition(double x,double y)
    {

        this.x=x;
        this.y=y;

        fixBounds();
        startCol=(int)this.x/32;
        startRow=(int)this.y/32;
    }
    private void fixBounds() {
        if(x < xmin) x = xmin;
        if(y < ymin) y = ymin;
        if(x > xmax) x = xmax;
        if(y > ymax) y = ymax;
    }


    public void drawMap(GraphicsContext graphicsContext) {
        for (
                int row = startRow;
                row < startRow + rowToDraw;
                row++) {

            if (row >= numRows) break;

            for (
                    int col = startCol;
                    col < startCol + colToDraw;
                    col++) {

                if (col >= numCols) break;

                if (map[row][col] == 0) continue;

                int rc = map[row][col];
                if(rc-1<0) continue;
                int r = (rc - 1) / numTileHorizontal;
                int c = (rc - 1) % numTileHorizontal;

                graphicsContext.drawImage(
                        tiles[r][c].getImage(),

                        -x+col*32,
                        -y + row * 32,
                        32, 32
                );
            }

        }
    }
    public void drawLayer2(GraphicsContext graphicsContext)
    {
        for (
                int row = startRow;
                row < startRow + rowToDraw;
                row++) {

            if (row >= numRows) break;

            for (
                    int col = startCol;
                    col < startCol + colToDraw;
                    col++) {

                if (col >= numCols) break;

                if (layer2[row][col] == 0) continue;

                int rc = layer2[row][col];
                if(rc-1<0) continue;
                int r = (rc - 1) / numTileHorizontal;
                int c = (rc - 1) % numTileHorizontal;

                graphicsContext.drawImage(
                        tiles[r][c].getImage(),

                        -x+col*32,
                        -y + row * 32,
                        32, 32
                );
            }

        }
    }
    public void drawLayer3(GraphicsContext graphicsContext)
    {
        for (
                int row = startRow;
                row < startRow + rowToDraw;
                row++) {

            if (row >= numRows) break;

            for (
                    int col = startCol;
                    col < startCol + colToDraw;
                    col++) {

                if (col >= numCols) break;

                if (map[row][col] == 0) continue;

                int rc = layer3[row][col];
                if(rc-1<0) continue;
                int r = (rc - 1) / numTileHorizontal;
                int c = (rc - 1) % numTileHorizontal;

                graphicsContext.drawImage(
                        tiles[r][c].getImage(),

                        -x+col*32,
                        -y + row * 32,
                        32, 32
                );
            }

        }
    }


    public double getX()
    {
        return x;

    }
    public double getY()
    {
        return y;
    }

    public ArrowDown getArrow1() {
        return arrow1;
    }
    public ArrowDown getArrow2() { return arrow2;
    }
    public ArrowDown getArrow3() {
        return arrow3;
    }
    public ArrowDown getArrow4() {
        return arrow4;
    }
}
