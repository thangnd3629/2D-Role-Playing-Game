package HUD;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class indexBar {
    //Health &Mana Bar
   Image hpbar = new Image(new FileInputStream("src\\HUD\\indexbar\\hp.png"));
    Image mpbar = new Image(new FileInputStream("src\\HUD\\indexbar\\mp.png"));
    private int hpx =535,mpx=535,
                hpy =68,mpy=77;
    private int hpwidth,hpheight=7;
    private int mpwidth,mpheight=7;
    private String hpstring="100";
    private String mpstring="100";
    Image bar = new Image(new FileInputStream("src\\HUD\\indexbar\\health_bar.png"));


    public indexBar() throws FileNotFoundException {

    }
    public void update(){

    }
    public void render(GraphicsContext g){
        g.drawImage(hpbar,hpx,hpy,hpwidth,hpheight);
        g.drawImage(mpbar,mpx,mpy,mpwidth,mpheight);
        g.drawImage(bar,440,10,300,100);
        g.setFont(Font.font(null, FontWeight.MEDIUM, 10));
        g.setFill(Color.web("#ed0707"));
        g.fillText(hpstring,670,45);
        g.setFont(Font.font(null, FontWeight.MEDIUM, 10));
        g.setFill(Color.web("#035efc"));
        g.fillText(mpstring,670,60);

    }

    public void setHpwidth(int hpwidth) {
        this.hpwidth = hpwidth;
    }

    public void setHpheight(int hpheight) {
        this.hpheight = hpheight;
    }

    public void setMpwidth(int mpwidth) {
        this.mpwidth = mpwidth;
    }

    public void setMpheight(int mpheight) {
        this.mpheight = mpheight;
    }

    public void setHpstring(String hpstring) {
        this.hpstring = hpstring;
    }

    public void setMpstring(String mpstring) {
        this.mpstring = mpstring;
    }
}
