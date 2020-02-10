package GameObjects;

import java.awt.*;

public class HUD {

    private int score;



    public HUD() {
        score = 0;
    }

    public void draw(Graphics g, Component c) {
        g.setFont(new Font("Courier New", Font.BOLD, 15));
        g.setColor(Color.RED);
        g.drawString("Score: " + score, 500, 450);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score += score;
    }
}
