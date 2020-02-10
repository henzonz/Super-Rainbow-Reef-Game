package GameObjects.BlocksPopKatch;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Block extends Rectangle {

    private Image pic;
    public boolean destroyed;  //block gets destroyed

    public int moveX, moveY;



    //x = x-coordinate, y = y-coordinate, h = height, w = width, s = location of image
    public Block(int x, int y,int w, int h, String s){
        this.x = x;
        this.y = y;


        moveY = -3;
        moveX = 3;

        this.width = w;
        this.height = h;
        try {
            pic = ImageIO.read(new File("resources/"+s));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void draw(Graphics g, Component c){
        if(!destroyed){
            g.drawImage(pic,x,y,width,height,c);
        }
    }
    public int getCoordinate (int x, int y){
        return x & y;
    }

}
