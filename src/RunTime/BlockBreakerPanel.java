package RunTime;

import GameObjects.*;
import GameObjects.BlocksPopKatch.Ball2;
import GameObjects.BlocksPopKatch.Block;
import GameObjects.PowerUp.DoubleBallPowerUp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.image.BufferedImage; //
import java.util.ArrayList;

public class BlockBreakerPanel extends JPanel implements KeyListener {
    private ArrayList<Block> blocks;
    private DoubleBallPowerUp powerUpBlock;
    private ArrayList<Wall> walls;
    private Block ball, paddle;
    private Ball2 ball2;
    private BigLeg bigleg;
    private BufferedImage background, winner;
    private HUD hud = new HUD();
    private boolean level2 = false, isWin = false, movingBigLeg;


    Thread thread;
    JFrame mainFrame, startScreen;


    public BlockBreakerPanel(JFrame frame, JFrame startScreen){

        this.mainFrame = frame;
        this.startScreen = startScreen;

        init();;
        reset();
        thread = new Thread(() -> {
            while (true) {
                update();
                try {
                    Thread.sleep(12);
                } catch (InterruptedException err) {
                    err.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public void init(){
        BufferedImageLoader load = new BufferedImageLoader();
        background = load.loadImage("Background1.bmp");
        winner = load.loadImage("Congratulation.gif");
        blocks = new ArrayList<Block>();
        walls = new ArrayList<Wall>();
        ball = new Block(305,350,30,25,"pop.png");
        ball2 = new Ball2(305,300,30,25,"pop.png");
        paddle = new Block(280, 400, 120, 30, "katch.png");
        powerUpBlock = new DoubleBallPowerUp(20, 147, 45,20,"Block_split.gif");
    }
    public void reset(){
        movingBigLeg = false;
        bigleg = new BigLeg(320,45,45,37,"bigleg.png");
        //walls
        for(int i = 0; i < 23; i++) //vertical walls (left)
            walls.add(new Wall(0, (i*20), 20,20,"Wall.gif"));
        for(int i = 0; i < 32; i++) //horizontal walls
            walls.add(new Wall((i*20), 0, 20,20,"Wall.gif"));
        for(int i = 0; i < 23; i++) //vertical walls (right)
            walls.add(new Wall(620, (i*20), 20,20,"Wall.gif"));
        //left blocks
        for(int i = 0; i < 7; i++)
            blocks.add(new Block((i * 46 + 20), 21, 45,20,"Block7.gif"));
        for(int i = 0; i < 6; i++)
            blocks.add(new Block((i * 46 + 20), 42, 45, 20, "Block6.gif"));
        for(int i = 0; i < 6; i++)
            blocks.add(new Block((i * 46 + 20), 63, 45,20,"Block5.gif"));
        for(int i = 0; i < 7; i++)
            blocks.add(new Block((i * 46 + 20), 84, 45,20,"Block4.gif"));
        for(int i = 0; i < 7; i++)
            blocks.add(new Block((i * 46 + 20), 105, 45,20,"Block3.gif"));
        for(int i = 0; i < 7; i++)
            blocks.add(new Block((i * 46 + 20), 126, 45,20,"Block2.gif"));
        for(int i = 1; i < 7; i++)
            blocks.add(new Block((i * 46 + 20), 147, 45,20,"Block1.gif"));

        //right blocks
        for(int i = 7; i < 13; i++)
            blocks.add(new Block((i * 46 + 20) + 3, 21, 45,20,"Block1.gif"));
        for(int i = 8; i < 13; i++)
            blocks.add(new Block((i * 46 + 20) +3, 42, 45,20,"Block2.gif"));
        for(int i = 8; i < 13; i++)
            blocks.add(new Block((i * 46 + 20) + 3, 63, 45,20,"Block3.gif"));
        for(int i = 7; i < 13; i++)
            blocks.add(new Block((i * 46 + 20) + 3, 84, 45,20,"Block4.gif"));
        for(int i = 7; i < 13; i++)
            blocks.add(new Block((i * 46 + 20) + 3, 105, 45,20,"Block5.gif"));
        for(int i = 7; i < 13; i++)
            blocks.add(new Block((i * 46 + 20) + 3, 126, 45,20,"Block6.gif"));
        for(int i = 7; i < 13; i++)
            blocks.add(new Block((i * 46 + 20) + 3, 147, 45,20,"Block7.gif"));


        addKeyListener(this);
        setFocusable(true); //focuses on component

    }




    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g);
        g2d.drawImage(background, 0, 0, null);
        if(isWin){
            g2d.drawImage(winner,0,0,null);
        }
        for (Block block : blocks) {
            block.draw(g, this);
        }
       /*blocks.forEach(block -> {
           block.draw(g, this);
               });*/
        for (Wall wall : walls) {
            wall.draw(g, this);
        }
       /*walls.forEach(wall -> {
           wall.draw(g, this);
       });*/

        hud.draw(g, this);
        ball.draw(g, this);
        paddle.draw(g, this);
        bigleg.draw(g, this);
        powerUpBlock.draw(g,this);
        if(powerUpBlock.destroyed){
            ball2.draw(g,this);
        }
        g.dispose();
        g2d.dispose();
    }
    private void checkOutOfBounds(){
        if(!(powerUpBlock.destroyed)) {
            if (ball.y > getHeight()) {
                thread = null;
                init();
                reset();
                mainFrame.setVisible(false);
                startScreen.setVisible(true);
            }
        }
        if(powerUpBlock.destroyed) {
            if (ball.y > getHeight()) {
                ball.destroyed = true;
            }
            if (ball2.y > getHeight()) {
                ball2.destroyed = true;
            }
            if (ball2.destroyed && ball.destroyed) {
                thread = null;
                init();
                reset();
                mainFrame.setVisible(false);
                startScreen.setVisible(true);
                ball2.destroyed = false;
            }
        }
    }
    public void update(){
        checkCollisions();

        checkOutOfBounds();
        if (ball.y > getHeight() && ball2.y > getHeight()) {
            thread = null;
            init();
            reset();
            mainFrame.setVisible(false);
            startScreen.setVisible(true);
        }
        /*for(Wall wall : walls){
            if(ball.intersects(wall)){
                ball.moveX *= -1;
            }
        }*/

//        for(Block block : blocks){
//            if(ball.getBounds().intersects(block.getBounds()) && !block.destroyed && ball.moveY < 0){
//                block.destroyed = true;
//                ball.moveY *= -1;
//            }
//        }



       /*walls.forEach(wall ->{
           if(ball.intersects(wall)){
               ball.moveY *= -1;
           }
       });*/
       /*blocks.forEach(block ->{
           if(ball.intersects(block) && !block.destroyed){
               ball.moveX *= -1;
               block.destroyed = true;
           }
       });*/
        repaint();



    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && paddle.x < (getWidth() - paddle.width) - 20) {
            paddle.x += 6;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT && paddle.x > 20) {
            paddle.x -= 6;
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    private void checkCollisions(){
        ball.x += ball.moveX;
        ball.y += ball.moveY;
        if(powerUpBlock.destroyed){
            ball2.x += ball2.moveX;
            ball2.y += ball2.moveY;
        }
        if(level2 == true && movingBigLeg == true){
            bigleg.x += bigleg.moveX;
            if((bigleg.x > getWidth() - 45 || bigleg.x < 0)){
                bigleg.moveX *= -1;
            }
        }
        if(ball.intersects(powerUpBlock)){
            powerUpBlock.destroyed = true;
        }
        //ball doesnt go through wall
        if(ball.x > (getWidth() - 45) || ball.x < 20) {
            ball.moveX *= -1;
        }
        if(ball2.x > (getWidth() - 45) ||ball2.x < 20) {
            ball2.moveX *= -1;
        }
//        ball doesnt go through top wall and paddle
        if(ball.y < 20 || ball.intersects(paddle)){
            ball.moveY *= -1;
        }
        if(ball2.y < 20 || ball2.intersects(paddle)){
            ball2.moveY *= -1;
        }
        for(int i = 0; i < blocks.size(); i++) {
            if(ball.intersects(blocks.get(i)) && !(blocks.get(i).destroyed)) {
                ball.moveY *= -1;
                blocks.remove(i);
                hud.setScore(20);
//                blocks.get(i).destroyed = true;
            }
        }
        for(int i = 0; i < blocks.size(); i++) {
            if (ball2.intersects(blocks.get(i)) && !(blocks.get(i).destroyed)) {
                ball2.moveY *= -1;
                blocks.remove(i);
                hud.setScore(20);
            }
        }
        if(ball.intersects(bigleg) || ball2.intersects(bigleg)){

            if(level2 == true){
                thread = null;
                init();
                isWin = true;
            }
            if(level2 == false){
                init();
                level2();
            }

        }


    }
    private void level2(){
        movingBigLeg = true;
        level2 = true;
        bigleg = new BigLeg(320,45,45,37,"bigleg.png");


        //walls
        for(int i = 0; i < 23; i++) //vertical walls (left)
            walls.add(new Wall(0, (i*20), 20,20,"Wall.gif"));
        for(int i = 0; i < 32; i++) //horizontal walls
            walls.add(new Wall((i*20), 0, 20,20,"Wall.gif"));
        for(int i = 0; i < 23; i++) //vertical walls (right)
            walls.add(new Wall(620, (i*20), 20,20,"Wall.gif"));
        //left blocks
        for(int i = 0; i < 7; i++)
            blocks.add(new Block((i * 46 + 20), 84, 45,20,"Block4.gif"));
        for(int i = 0; i < 7; i++)
            blocks.add(new Block((i * 46 + 20), 105, 45,20,"Block3.gif"));
        for(int i = 0; i < 7; i++)
            blocks.add(new Block((i * 46 + 20), 126, 45,20,"Block2.gif"));
        for(int i = 1; i < 7; i++)
            blocks.add(new Block((i * 46 + 20), 147, 45,20,"Block1.gif"));


//        //right blocks
        for(int i = 7; i < 13; i++)
            blocks.add(new Block((i * 46 + 20) + 3, 84, 45,20,"Block4.gif"));
        for(int i = 7; i < 13; i++)
            blocks.add(new Block((i * 46 + 20) + 3, 105, 45,20,"Block5.gif"));
        for(int i = 7; i < 13; i++)
            blocks.add(new Block((i * 46 + 20) + 3, 126, 45,20,"Block6.gif"));
        for(int i = 7; i < 13; i++)
            blocks.add(new Block((i * 46 + 20) + 3, 147, 45,20,"Block7.gif"));

        addKeyListener(this);
        setFocusable(true); //focuses on component
    }
}
