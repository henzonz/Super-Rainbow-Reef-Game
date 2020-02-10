package GameObjects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BigLeg extends Rectangle {

    private Image pic;
    public boolean destroyed;
    public int moveX;




    //x = x-coordinate, y = y-coordinate, h = height, w = width, s = location of image
    public BigLeg(int x, int y,int w, int h, String s){
        this.x = x;
        this.y = y;
        moveX = 6;

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
}
