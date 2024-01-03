package ui;

import Main.Game;
import gamestates.Gamestate;
import gamestates.Playing;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import static utilz.Constants.UI.URMButtons.*;

public class PauseOverlay {

    private Playing playing;
    private BufferedImage backgroundImg;
    private int bgX, bgY, bgW, bgH;
    private UrmButton menuB, replayB, unpauseB;
    private AudioOptions audioOptions;

    public PauseOverlay(Playing playing) {
        this.playing = playing;
        loadBackground();
        audioOptions = playing.getGame().getAudioOptions();
        createUrmButtons();
    }

    private void createUrmButtons() {

        int menuX = (int) (355 * 2f - 36);
        int replayX = (int) (405 * 2f - 21);
        int unpausedX = (int) (455 * 2f - 6);
        int bY = (int) (315 * 2f + 10);

        menuB = new UrmButton(menuX, bY, URM_SIZE, URM_SIZE,2);
        replayB = new UrmButton(replayX, bY, URM_SIZE, URM_SIZE,1);
        unpauseB = new UrmButton(unpausedX, bY, URM_SIZE, URM_SIZE,0);
    }

    private void loadBackground() { //size of pause background
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PAUSE_BACKGROUND);

        bgW = (int)(backgroundImg.getWidth() * 8f);
        bgH = (int)(backgroundImg.getHeight() * 8f);
        bgX = Game.GAME_WIDTH / 2 - bgW / 2; //center - width/2
        bgY = (int) (25 * Game.SCALE); //test, if we want it up(decrease num), down(increase num)
    }

    public void update() {

        menuB.update();
        replayB.update();
        unpauseB.update();
        audioOptions.update();
    }
    public void draw(Graphics g) {
        //Background
        g.drawImage(backgroundImg, bgX, bgY, bgW, bgH, null);

        //Urm
        menuB.draw(g);
        replayB.draw(g);
        unpauseB.draw(g);
        audioOptions.draw(g);
    }

    public void mouseDragged(MouseEvent e) {
        audioOptions.mouseDragged(e);
    }

    public void mousePressed(MouseEvent e) {
        if (isIn(e, menuB)){
            menuB.setMousePressed(true);}
        else if (isIn(e, replayB)){
            replayB.setMousePressed(true);}
        else if (isIn(e, unpauseB)){
            unpauseB.setMousePressed(true);}
        else{
            audioOptions.mousePressed(e);}
    }

    public void mouseReleased(MouseEvent e) {
        if (menuB.isMousePressed()) {
            playing.setGamestate(Gamestate.MENU);
            playing.unPauseGame();
        } else if (isIn(e, replayB)) {
            if (replayB.isMousePressed()) {
                playing.resetAll();
                playing.unPauseGame();
            }
        } else if (isIn(e, unpauseB)) {
            if (unpauseB.isMousePressed())
                playing.unPauseGame();
        } else
            audioOptions.mouseReleased(e);

        menuB.resetBools();
        replayB.resetBools();
        unpauseB.resetBools();
    }

    public void mouseMoved(MouseEvent e) {
        menuB.setMouseOver(false);
        replayB.setMouseOver(false);
        unpauseB.setMouseOver(false);

        if (isIn(e, menuB))
            menuB.setMouseOver(true);
        else if (isIn(e, replayB))
            replayB.setMouseOver(true);
        else if (isIn(e, unpauseB))
            unpauseB.setMouseOver(true);

        else
            audioOptions.mouseMoved(e);
    }

    private boolean isIn(MouseEvent e, PauseButton b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }
}
