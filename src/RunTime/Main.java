package RunTime;

import javax.swing.*;
import javax.swing.JButton;

public class Main {
    public static void main(String[] args){
        JFrame frame = new JFrame("Super Rainbow Reef");


        JFrame startScreen = new JFrame();
        JButton start = new JButton("Press spacebar to play!");


        start.addActionListener(listener ->{
            startScreen.setVisible(false);
            startScreen.dispose();
            frame.setVisible(true);
        });
        BlockBreakerPanel panel = new BlockBreakerPanel(frame, startScreen);


        startScreen.getContentPane().add(start);
        startScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startScreen.setVisible(true); //shows the frame
        startScreen.setSize(640, 480);
        startScreen.setResizable(false);

        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(false); //shows the frame
        frame.setSize(640, 480);
        frame.setResizable(false);

    }



}
