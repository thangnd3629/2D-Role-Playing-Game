package ImageProcesser;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Assets {

    public static Image itemsheet;
    public static Image kiemcui,kiemhong;
    public static Image muphuthuy;
    public static Image giaycodong;
    public static Image giapgai,giaplietsi;
    public static Image movingarrow;
    public static Image hero,hero1,enemy2s,enemy3s,enemy4s,enemy5s,
                        boss, skillboss1,skillboss2,bullethero,bulletenemy;
    public static Image[] arrow = new Image[5];
    public static Image[] enemy3up =new Image[4];
    public static Image[] enemy3down =new Image[4];
    public static Image[] enemy3right =new Image[4];
    public static Image[] enemy3idle =new Image[1];
    public static Image[] idlehero = new Image[1];
    public static Image[] idleboss = new Image[1];
    public static Image[] idleevilflower = new Image[1];
    public static Image[] idleeviltank = new Image[1];
    public static Image[] idlelava = new Image[1];
    public static Image[] idleevilfly = new Image[1];
    public static Image[] runright = new Image[6];
    public static Image[] runup = new Image[6];
    public static Image[] rundown = new Image[6];
    public static Image[] punchright = new Image[2];
    public static Image[] punchup = new Image[2];
    public static Image[] punchdown = new Image[2];
    public static Image[] fireright = new Image[2];
    public static Image[] fireup = new Image[2];
    public static Image[] firedown = new Image[2];
    public static Image[] bossslideright = new Image[16];
    public static Image[] bosssliderup = new Image[16];
    public static Image[] bosssliderdown = new Image[16];
    public static Image[] bossulti = new Image[11];
    public static Image[] evilflowerleft = new Image[5];
    public static Image[] evilflowerup = new Image[5];
    public static Image[] evilflowerdown = new Image[5];
    public static Image[] eviltankrunright = new Image[4];
    public static Image[] eviltankrunup = new Image[4];
    public static Image[] eviltankrundown = new Image[4];
    public static Image[] eviltankfightright = new Image[5];
    public static Image[] eviltankfightup = new Image[5];
    public static Image[] eviltankfightdown = new Image[5];
    public static Image[] lavarunandfight = new Image[6];
    public static Image[] evilflyrunright = new Image[3];
    public static Image[] evilflyrunup = new Image[3];
    public static Image[] evilflyrundown = new Image[3];
    public static Image[] evilflyfightright = new Image[2];
    public static Image[] evilflyfightup = new Image[2];
    public static Image[] evilflyfightdown = new Image[2];
    public static Image[] bossskillfirst = new Image[10];
    public static Image[] bossskillsecond = new Image[8];
    public static Image[] bulletheroright = new Image[3];
    public static Image[] bulletheroup = new Image[3];
    public static Image[] bulletherodown = new Image[3];
    public static Image[] bulletevilflowerright = new Image[1];
    public static Image[] bulletevilflowerup = new Image[1];
    public static Image[] bulletevilflowerdown = new Image[1];

    public static Image[] herofireright = new Image[5];
    public static Image[] herofireup = new Image[5];
    public static Image[] herofiredown = new Image[5];
    public static Image[] HuanRose = new Image[1];
    public static Image huanrose;
    public static Image[] HuanRosechatbox = new Image[1];
    public static Image huanrosechatbox;


    public static void init(){
        {
            try {
                itemsheet = new Image (new FileInputStream("src\\Inventory\\res\\itemsheet.png"));
                movingarrow=new Image (new FileInputStream("src\\Entity\\arrow\\hi.png"));
                enemy3s= new Image(new FileInputStream("src\\Entity\\image\\enemy3.png"));
                enemy2s = new Image(new FileInputStream("src\\Entity\\image\\enemy2.1.png"));
                enemy4s = new Image(new FileInputStream("src\\Entity\\image\\enemy4.png"));
                enemy5s = new Image(new FileInputStream("src\\Entity\\image\\enemy5.png"));
                hero = new Image(new FileInputStream("src\\Entity\\image\\hero.png"));
                boss = new Image(new FileInputStream("src\\Entity\\image\\boss.png"));
                bulletenemy = new Image(new FileInputStream("src\\Entity\\image\\skill1.png"));
                skillboss1 = new Image(new FileInputStream("src\\Entity\\image\\skillboss.png"));
                skillboss2 = new Image(new FileInputStream("src\\Entity\\image\\skillboss.png"));
                bullethero = new Image(new FileInputStream("src\\Entity\\image\\laze1.png"));

                hero1 = new Image(new FileInputStream("src\\accesories\\resources\\PC Computer - CrossCode - Lea Kunoichi.png"));
                huanrose = new Image(new FileInputStream("src\\Entity\\NPC\\huanrose.png"));
                huanrosechatbox= new Image (new FileInputStream("src\\Entity\\NPC\\huanchatbox.png"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        //tao sheet moi de crop anh
        SpriteSheet s = new SpriteSheet(itemsheet);
        kiemhong=s.crop(1,0);
        kiemcui =s.crop(2,0);
        muphuthuy=s.crop(7,14);
        giaycodong=s.crop(7,20);
        giapgai=s.crop(6,8);
        giaplietsi=s.crop(6,9);
        //NPC
        SpriteSheet huanrosesprite =new SpriteSheet(huanrose);
        HuanRose[0]=huanrosesprite.crop(0,0,48,48);
        SpriteSheet huanrosechatboxsprite = new SpriteSheet(huanrosechatbox);
        HuanRosechatbox[0]= huanrosechatboxsprite.crop(0,0,2083,2083);
        //arrow
        SpriteSheet arrowsprite = new SpriteSheet(movingarrow);
        for(int i=0;i<5;i++) {
            arrow[i] = arrowsprite.crop(13 + 55 * i, 79, 55, 112);
//            try{
//                arrow[i]=new Image(new FileInputStream("src/Entity/arrow/output-onlinepngtools ("+i+").png"),80,80,true,true);
//            }catch (FileNotFoundException e)
//            {
//                e.printStackTrace();
//            }


        }
        //hero
        SpriteSheet herosprite = new SpriteSheet(hero);
        runright[0] = herosprite.crop(100, 252, 96, 124);
        runright[1] = herosprite.crop(309, 252, 103, 124);
        runright[2] = herosprite.crop(416, 252, 92, 124);
        runright[3] = herosprite.crop(508, 252, 100, 124);
        runright[4] = herosprite.crop(608, 252, 108, 124);
        runright[5] = herosprite.crop(716, 252, 101, 124);
        runup[0] = herosprite.crop(100, 0, 100, 124);
        runup[1] = herosprite.crop(310, 0, 94, 124);
        runup[2] = herosprite.crop(404, 0, 106, 124);
        runup[3] = herosprite.crop(510, 0, 98, 124);
        runup[4] = herosprite.crop(608, 0, 107, 124);
        runup[5] = herosprite.crop(715, 0, 100, 124);
        rundown[0] = herosprite.crop(104, 504, 98, 126);
        rundown[1] = herosprite.crop(302, 504, 88, 126);
        rundown[2] = herosprite.crop(390, 504, 109, 126);
        rundown[3] = herosprite.crop(508, 252, 100, 124);
        rundown[4] = herosprite.crop(608, 252, 108, 124);
        rundown[5] = herosprite.crop(716, 252, 101, 124);
        punchright[0] = herosprite.crop(1136, 505, 103, 128);
        punchright[1] = herosprite.crop(1448, 505, 98, 128);
        punchup[0] = herosprite.crop(1120, 380, 108, 123);
        punchup[1] = herosprite.crop(1446, 380, 101, 123);
        punchdown[0] = herosprite.crop(1144, 642, 100, 120);
        punchdown[1] = herosprite.crop(1435, 642, 100, 120);
        fireright[0] = herosprite.crop(303, 1017, 104, 116);
        fireright[1] = herosprite.crop(115, 1007, 86, 126);
        fireup[0] = herosprite.crop(313, 627, 100, 130);
        fireup[1] = herosprite.crop(5, 1005, 106, 130);
        firedown[0] = herosprite.crop(307, 1133, 85, 120);
        firedown[1] = herosprite.crop(1, 1133, 86, 130);
        idleboss[0] = herosprite.crop(0, 589, 200, 272);
        idlehero[0] = herosprite.crop(104, 504, 98, 126);
        //hero1
        SpriteSheet hero1sprite = new SpriteSheet(hero1);
        herofireright[0] = hero1sprite.crop(194,386,25,32);
        herofireright[1] = hero1sprite.crop(228,386,32,35);
        herofireright[2] = hero1sprite.crop(260,386,33,35);
        herofireright[3] = hero1sprite.crop(293,386,32,35);
        herofireright[4] = hero1sprite.crop(325,386,32,35);
        herofireup[0] = hero1sprite.crop(197,328,32,25);
        herofireup[1] = hero1sprite.crop(223,324,37,33);
        herofireup[2] = hero1sprite.crop(261,324,30,33);
        herofireup[3] = hero1sprite.crop(292,324,30,33);
        herofireup[4] = hero1sprite.crop(325,324,30,33);
        herofiredown[0] = hero1sprite.crop(194,450,33,33);
        herofiredown[1] = hero1sprite.crop(229,451,30,33);
        herofiredown[2] = hero1sprite.crop(259,450,30,33);
        herofiredown[3] = hero1sprite.crop(291,451,30,33);
        herofiredown[4] = hero1sprite.crop(323,451,30,33);
        //boss
        SpriteSheet bosssprite = new SpriteSheet(boss);
        idleboss[0] = bosssprite.crop(0,589, 200, 272);
        bossslideright[0] = bosssprite.crop(30, 300, 150, 279);
        bossslideright[1] = bosssprite.crop(230, 300, 183, 279);
        bossslideright[2] = bosssprite.crop(437, 300, 188, 279);
        bossslideright[3] = bosssprite.crop(659, 300, 162, 279);
        bossslideright[4] = bosssprite.crop(855, 300, 184, 279);
        bossslideright[5] = bosssprite.crop(1075, 300, 172, 279);
        bossslideright[6] = bosssprite.crop(1285, 300, 160, 279);
        bossslideright[7] = bosssprite.crop(1487, 300, 172, 279);
        bossslideright[8] = bosssprite.crop(1689, 300, 1873, 279);
        bossslideright[9] = bosssprite.crop(1913, 300, 182, 279);
        bossslideright[10] = bosssprite.crop(2139, 300, 180, 279);
        bossslideright[11] = bosssprite.crop(2335, 300, 198, 279);
        bossslideright[12] = bosssprite.crop(2549, 300, 186, 279);
        bossslideright[13] = bosssprite.crop(2739, 300, 286, 279);
        bossslideright[14] = bosssprite.crop(2739, 300, 288, 279);
        bossslideright[15] = bosssprite.crop(2739, 300, 288, 279);
        bosssliderup[0] = bosssprite.crop(13, 0, 200, 289);
        bosssliderup[1] = bosssprite.crop(215, 0, 200, 289);
        bosssliderup[2] = bosssprite.crop(145, 0, 212, 289);
        bosssliderup[3] = bosssprite.crop(641, 0, 204, 289);
        bosssliderup[4] = bosssprite.crop(869, 0, 162, 289);
        bosssliderup[5] = bosssprite.crop(1075, 0, 164, 289);
        bosssliderup[6] = bosssprite.crop(1285, 0, 178, 289);
        bosssliderup[7] = bosssprite.crop(1491, 0, 178, 289);
        bosssliderup[8] = bosssprite.crop(1697, 0, 188, 289);
        bosssliderup[9] = bosssprite.crop(1909, 0, 170, 289);
        bosssliderup[10] = bosssprite.crop(2123, 0, 170, 289);
        bosssliderup[11] = bosssprite.crop(2329, 0, 186, 289);
        bosssliderup[12] = bosssprite.crop(2551, 0, 178, 289);
        bosssliderup[13] = bosssprite.crop(2765, 0, 182, 289);
        bosssliderup[14] = bosssprite.crop(2765, 0, 182, 289);
        bosssliderup[15] = bosssprite.crop(2765, 0, 182, 289);
        bosssliderdown[0] = bosssprite.crop(0,589, 200, 272);
        bosssliderdown[1] = bosssprite.crop(203,589, 206, 272);
        bosssliderdown[2] = bosssprite.crop(409,589, 216, 272);
        bosssliderdown[3] = bosssprite.crop(627,589, 202, 272);
        bosssliderdown[4] = bosssprite.crop(853,589, 174, 272);
        bosssliderdown[5] = bosssprite.crop(1069,589, 166, 272);
        bosssliderdown[6] = bosssprite.crop(1287,589, 152, 272);
        bosssliderdown[7] = bosssprite.crop(1481,589, 178, 272);
        bosssliderdown[8] = bosssprite.crop(1679,589, 200, 272);
        bosssliderdown[9] = bosssprite.crop(1901,589, 190, 272);
        bosssliderdown[10] = bosssprite.crop(2113,589, 190, 272);
        bosssliderdown[11] = bosssprite.crop(2321,589, 196, 272);
        bosssliderdown[12] = bosssprite.crop(2537,589, 184, 272);
        bosssliderdown[13] = bosssprite.crop(2743,589, 182, 272);
        bosssliderdown[14] = bosssprite.crop(2743,589, 182, 272);
        bosssliderdown[15] = bosssprite.crop(2743,589, 182, 272);
        bossulti[0] = bosssprite.crop(0, 869, 185, 294);
        bossulti[1] = bosssprite.crop(217, 869, 185, 294);
        bossulti[2] = bosssprite.crop(435, 869, 166, 294);
        bossulti[3] = bosssprite.crop(627, 869, 200, 294);
        bossulti[4] = bosssprite.crop(849, 869, 188, 294);
        bossulti[5] = bosssprite.crop(1063, 869, 185, 294);
        bossulti[6] = bosssprite.crop(1261, 869, 222, 294);
        bossulti[7] = bosssprite.crop(1483, 869, 192, 294);
        bossulti[8] = bosssprite.crop(1691, 869, 192, 294);
        bossulti[9] = bosssprite.crop(1901, 869, 190, 294);
        bossulti[10] = bosssprite.crop(2115, 869, 178, 294);

        //enemy2
        SpriteSheet enemy2sprite = new SpriteSheet(enemy2s);
        idleevilflower[0] = enemy2sprite.crop(647, 166, 119, 158);
        evilflowerleft[0] = enemy2sprite.crop(647, 166, 119, 158);
        evilflowerleft[1] = enemy2sprite.crop(496, 166, 152, 158);
        evilflowerleft[2] = enemy2sprite.crop(337, 166, 160, 158);
        evilflowerleft[3] = enemy2sprite.crop(169, 166, 167, 158);
        evilflowerleft[4] = enemy2sprite.crop(14, 166, 119, 158);
        evilflowerup[0] = enemy2sprite.crop(652, 0, 120, 167);
        evilflowerup[1] = enemy2sprite.crop(525, 0, 120, 167);
        evilflowerup[2] = enemy2sprite.crop(361, 0, 137, 167);
        evilflowerup[3] = enemy2sprite.crop(200, 0, 136, 167);
        evilflowerup[4] = enemy2sprite.crop(23, 0, 102, 167);
        evilflowerdown[0] = enemy2sprite.crop(672, 325, 104, 165);
        evilflowerdown[1] = enemy2sprite.crop(521, 325, 120, 165);
        evilflowerdown[3] = enemy2sprite.crop(202, 325, 126, 165);
        evilflowerdown[4] = enemy2sprite.crop(8, 325, 120, 165);
        evilflowerdown[2] = enemy2sprite.crop(365, 325, 130, 165);

        //enemy3
        SpriteSheet enemy3sprite = new SpriteSheet(enemy3s);
        idleeviltank[0] = enemy3sprite.crop(11, 102, 80, 70);
        eviltankrunright[0] = enemy3sprite.crop(355, 100, 88, 72);
        eviltankrunright[1] = enemy3sprite.crop(444, 100, 88, 72);
        eviltankrunright[2] = enemy3sprite.crop(535, 100, 88, 72);
        eviltankrunright[3] = enemy3sprite.crop(621, 100, 88, 72);
        eviltankrunup[0] = enemy3sprite.crop(368, 1, 63, 72);
        eviltankrunup[1] = enemy3sprite.crop(455, 1, 66, 72);
        eviltankrunup[2] = enemy3sprite.crop(544, 1, 63, 72);
        eviltankrunup[3] = enemy3sprite.crop(629, 1, 70, 72);
        eviltankrundown[0] = enemy3sprite.crop(368, 191, 63, 70);
        eviltankrundown[1] = enemy3sprite.crop(454, 191, 67, 70);
        eviltankrundown[2] = enemy3sprite.crop(541, 191, 65, 70);
        eviltankrundown[3] = enemy3sprite.crop(628, 191, 68, 70);
        eviltankfightright[0] = enemy3sprite.crop(717, 83, 80, 85);
        eviltankfightright[1] = enemy3sprite.crop(806, 83, 82, 85);
        eviltankfightright[2] = enemy3sprite.crop(895, 83, 80, 85);
        eviltankfightright[3] = enemy3sprite.crop(982, 83, 80, 85);
        eviltankfightright[4] = enemy3sprite.crop(1073, 83, 69, 85);
        eviltankfightup[0] = enemy3sprite.crop(721, 0, 66, 80);
        eviltankfightup[1] = enemy3sprite.crop(804, 0, 73, 80);
        eviltankfightup[2] = enemy3sprite.crop(892, 0, 66, 80);
        eviltankfightup[3] = enemy3sprite.crop(981, 0, 66, 80);
        eviltankfightup[4] = enemy3sprite.crop(1074, 0, 67, 80);
        eviltankfightdown[0] = enemy3sprite.crop(719, 174, 66, 89);
        eviltankfightdown[1] = enemy3sprite.crop(803, 174, 68, 89);
        eviltankfightdown[2] = enemy3sprite.crop(890, 174, 76, 89);
        eviltankfightdown[3] = enemy3sprite.crop(983, 174, 68, 89);
        eviltankfightdown[4] = enemy3sprite.crop(1071, 174, 66, 89);

        //enemy4
        SpriteSheet enemy4sprite = new SpriteSheet(enemy4s);
        idleevilfly[0] = enemy4sprite.crop(647, 2, 66, 76);
        evilflyrunright[0] = enemy4sprite.crop(7, 75, 57, 83);
        evilflyrunright[1] = enemy4sprite.crop(86, 75, 60, 83);
        evilflyrunright[2] = enemy4sprite.crop(164, 75, 61, 83);
        evilflyrunup[0] = enemy4sprite.crop(2, 75, 83, 77);
        evilflyrunup[1] = enemy4sprite.crop(89, 75, 62, 77);
        evilflyrunup[2] = enemy4sprite.crop(156, 75, 83, 77);
        evilflyrundown[0] = enemy4sprite.crop(0 ,156, 77, 87);
        evilflyrundown[1] = enemy4sprite.crop(77 ,156, 72, 87);
        evilflyrundown[2] = enemy4sprite.crop(154 ,156, 81, 87);
        evilflyfightright[0] = enemy4sprite.crop(483, 90, 60, 67);
        evilflyfightright[1] = enemy4sprite.crop(555, 90, 81, 67);
        evilflyfightup[0] = enemy4sprite.crop(482, 5, 65, 73);
        evilflyfightup[1] = enemy4sprite.crop(553, 5, 85, 73);
        evilflyfightdown[0] = enemy4sprite.crop(481, 166, 75, 72);
        evilflyfightdown[1] = enemy4sprite.crop(556, 166, 80, 72);

        //enemy5
        SpriteSheet enemy5sprite = new SpriteSheet(enemy5s);
        idlelava[0] = enemy5sprite.crop(237, 473, 218, 226);
        lavarunandfight[0] = enemy5sprite.crop(935, 1418, 240, 248);
        lavarunandfight[1] = enemy5sprite.crop(689, 1418, 238, 248);
        lavarunandfight[2] = enemy5sprite.crop(1177, 1418, 256, 248);
        lavarunandfight[3] = enemy5sprite.crop(1445, 1418, 186, 248);
        lavarunandfight[4] = enemy5sprite.crop(1651, 1418, 244, 248);
        lavarunandfight[5] = enemy5sprite.crop(1895, 1418, 258, 248);

        //bossskillfirst
        SpriteSheet bossskill1 = new SpriteSheet(skillboss1);
        bossskillfirst[0] = bossskill1.crop(5, 481, 204, 336);
        bossskillfirst[1] = bossskill1.crop(245, 481, 292, 336);
        bossskillfirst[2] = bossskill1.crop(541, 481, 292, 336);
        bossskillfirst[3] = bossskill1.crop(833, 481, 280, 336);
        bossskillfirst[4] = bossskill1.crop(1113, 481, 296, 336);
        bossskillfirst[5] = bossskill1.crop(0, 809, 296, 276);
        bossskillfirst[6] = bossskill1.crop(317, 809, 200, 276);
        bossskillfirst[7] = bossskill1.crop(609, 809, 168, 276);
        bossskillfirst[9] = bossskill1.crop(1225, 809, 64, 276);
        bossskillfirst[8] = bossskill1.crop(929, 809, 108, 276);

        //bossskillsecond
        SpriteSheet bossskill2 = new SpriteSheet(skillboss2);
        bossskillsecond[0] = bossskill2.crop(13, 1437, 444, 860);
        bossskillsecond[1] = bossskill2.crop(469, 1437, 496, 860);
        bossskillsecond[2] = bossskill2.crop(965, 1437, 508, 860);
        bossskillsecond[3] = bossskill2.crop(1473, 1437, 488, 860);
        bossskillsecond[4] = bossskill2.crop(2041, 1437, 416, 860);
        bossskillsecond[5] = bossskill2.crop(2561, 1437, 376, 860);
        bossskillsecond[7] = bossskill2.crop(3649, 1437, 252, 860);
        bossskillsecond[6] = bossskill2.crop(3093, 1437, 320, 860);

        //bullethero
        SpriteSheet bulletheroes = new SpriteSheet(bullethero);
        bulletheroright[0] = bulletheroes.crop(46, 60, 541, 55);
        bulletheroright[1] = bulletheroes.crop(46, 60, 541, 55);
        bulletheroright[2] = bulletheroes.crop(46, 60, 541, 55);
        bulletheroup[0] = bulletheroes.crop(47, 201, 55, 541);
        bulletheroup[1] = bulletheroes.crop(47, 201, 55, 541);
        bulletheroup[2] = bulletheroes.crop(47, 201, 55, 541);
        bulletherodown[0] = bulletheroes.crop(47, 201, 55, 541);
        bulletherodown[1] = bulletheroes.crop(47, 201, 55, 541);
        bulletherodown[2] = bulletheroes.crop(47, 201, 55, 541);

        //bulletenemy
        SpriteSheet bulletenemies = new SpriteSheet(bulletenemy);
        bulletevilflowerright[0] = bulletenemies.crop(581, 641, 240, 160);
        bulletevilflowerup[0] = bulletenemies.crop(669, 401, 144, 144);
        bulletevilflowerdown[0] = bulletenemies.crop(617, 1069, 152, 124);

    }
}
