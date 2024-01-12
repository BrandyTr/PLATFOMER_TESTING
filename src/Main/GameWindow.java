package Main;

import javax.swing.*;

public class GameWindow {
    private JFrame jframe;
    public GameWindow(GamePanel gamePanel) {
        jframe = new JFrame();

        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(gamePanel);
        jframe.setTitle("Dreamy Land");

        jframe.setResizable(false);
        jframe.pack(); //fit the size to the preferred size
        jframe.setLocationRelativeTo(null); //set location at center screen
        jframe.setVisible(true);
    }
}
