package GameObjects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Wall extends Rectangle {

    private Image pic;


    //x = x-coordinate, y = y-coordinate, h = height, w = width, s = location of image
    public Wall(int x, int y, int w, int h, String s) {
        this.x = x;
        this.y = y;

        this.width = w;
        this.height = h;

        try {
            pic = ImageIO.read(new File("resources/" + s));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g, Component c) {
        g.drawImage(pic, x, y, width, height, c);
    }
}
