package Main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static Main.Game.GAME_HEIGHT;
import static Main.Game.GAME_WIDTH;
import static utilz.Constants.Directions.*;
import static utilz.Constants.PlayerConstants.*;

public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    private Game game;


    public GamePanel(Game game) {
        mouseInputs = new MouseInputs(this);
        this.game = game;

        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    /*private void importImage() {
        boymove = new BufferedImage[6];
        try {
            boymove[0]= ImageIO.read(getClass().getResourceAsStream("/Charac/boymove0.png"));
            boymove[1]= ImageIO.read(getClass().getResourceAsStream("/Charac/boymove1.png"));
            boymove[2]= ImageIO.read(getClass().getResourceAsStream("/Charac/boymove2.png"));
            boymove[3]= ImageIO.read(getClass().getResourceAsStream("/Charac/boymove3.png"));
            boymove[4]= ImageIO.read(getClass().getResourceAsStream("/Charac/boymove4.png"));
            boymove[5]= ImageIO.read(getClass().getResourceAsStream("/Charac/boymove5.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT); //can resize it => size image 32 x 32
        setPreferredSize(size);
        System.out.println("size: " + GAME_WIDTH + "x" + GAME_HEIGHT);
    }
    public void updateGame() {
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        game.render(g);

    }
    public Game getGame() {
        return game;
    }
}
