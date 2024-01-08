package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import Main.Game;
import ui.AudioOptions;
import ui.PauseButton;
import ui.UrmButton;
import utilz.LoadSave;
import static utilz.Constants.UI.URMButtons.*;

public class GameOptions extends State implements Statemethods {

    private AudioOptions audioOptions;
    private BufferedImage backgroundImg, optionsBackgroundImg;
    private int bgX, bgY, bgW, bgH;
    private UrmButton menuB;

    public GameOptions(Game game) {
        super(game);
        loadImgs();
        loadButton();
        audioOptions = game.getAudioOptions();
    }

    private void loadButton() { //BUTTONS POS
        int menuX = (int) (300 * 2f - 5);
        int menuY = (int) (270 * 2f);

        menuB = new UrmButton(menuX, menuY, URM_SIZE, URM_SIZE, 2);
    }

    private void loadImgs() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG); //get menu background
        optionsBackgroundImg = LoadSave.GetSpriteAtlas(LoadSave.OPTIONS_MENU);
// SIZE OF OPTION BACKGROUND
        bgW = (int) (optionsBackgroundImg.getWidth() * 1.5f);
        bgH = (int) (optionsBackgroundImg.getHeight() * 1.5f);
        bgX = Game.GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (30 * 3f);
    }

    @Override
    public void update() {
        menuB.update();
        audioOptions.update();

    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.drawImage(optionsBackgroundImg, bgX, bgY, bgW, bgH, null);

        menuB.draw(g);
        audioOptions.draw(g);

    }

    public void mouseDragged(MouseEvent e) {
        audioOptions.mouseDragged(e);
    }


    @Override
    public void mousePressed(MouseEvent e) {
        if (isIn(e, menuB)) {
            menuB.setMousePressed(true);
        } else
            audioOptions.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isIn(e, menuB)) {
            if (menuB.isMousePressed())
                Gamestate.state = Gamestate.MENU;
        } else
            audioOptions.mouseReleased(e);

        menuB.resetBools();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        menuB.setMouseOver(false);

        if (isIn(e, menuB))
            menuB.setMouseOver(true);
        else
            audioOptions.mouseMoved(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) //PRESS ESC BACK TO MENU
            Gamestate.state = Gamestate.MENU;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    private boolean isIn(MouseEvent e, PauseButton b) {  // CHECK IF INSIDE THE BUTTONS
        return b.getBounds().contains(e.getX(), e.getY());
    }

}