package ui;

import Main.Game;
import entities.Player;
import gamestates.Gamestate;
import gamestates.Playing;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import static utilz.Constants.UI.URMButtons.*;

public class LevelCompletedOverlay {
    private Playing playing;
    private UrmButton menu, next;
    private BufferedImage img;
    private int bgX, bgY, bgW, bgH;
    public LevelCompletedOverlay(Playing playing) {
        this.playing = playing;
        initImg();
        initButtons();
    }

    private void initButtons() { //fix position of URM BUTTONS
        //position X
        int menuX = (int) (250 * 2);
        int nextX = (int) (338 * 2);
        //position Y
        int y = (int) (210 * 2);

        next = new UrmButton(nextX, y, URM_SIZE, URM_SIZE, 0);
        menu = new UrmButton(menuX, y, URM_SIZE, URM_SIZE, 2);
    }

    private void initImg() {
        img = LoadSave.GetSpriteAtlas(LoadSave.COMPLETED_IMG);
        bgW = (int) (img.getWidth() * 2);
        bgH = (int) (img.getHeight() * 2);
        bgX = Game.GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (75 * 2);
    }

    public void draw(Graphics g) {
        g.drawImage(img, bgX, bgY, bgW, bgH, null);
        next.draw(g);
        menu.draw(g);
    }
    public void update() {
        next.update();
        menu.update();
    }
    public boolean isIn(UrmButton b, MouseEvent e) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

    public void mouseMoved(MouseEvent e) {
        next.setMouseOver(false);
        menu.setMouseOver(false);

        if(isIn(menu, e)) {
            menu.setMouseOver(true);
        } else if (isIn(next, e)) {
            next.setMouseOver(true);
        }
    }
    public void mouseReleased(MouseEvent e) {
        if(isIn(menu, e)) {
            if(menu.isMousePressed()){
                //System.out.println("Menu");
                playing.resetAll();
                playing.setGamestate(Gamestate.MENU);
            }
        } else if (isIn(next, e)) {
            if (next.isMousePressed()) {
               // System.out.println("Next");
                playing.loadNextLevel();
                playing.getGame().getAudioPlayer().setLevelSong(playing.getLevelManager().getLvlIndex());
            }
        }
        menu.resetBools();
        next.resetBools();
    }
    public void mousePressed(MouseEvent e) {
        if(isIn(menu, e)) {
            menu.setMousePressed(true);
        } else if (isIn(next, e)) {
            next.setMousePressed(true);
        }
    }

}
