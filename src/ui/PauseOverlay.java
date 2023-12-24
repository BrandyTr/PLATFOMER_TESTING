package ui;

import Main.Game;
import gamestates.Gamestate;
import gamestates.Playing;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import static utilz.Constants.UI.PauseButtons.*;
import static utilz.Constants.UI.URMButtons.*;
import static utilz.Constants.UI.VolumeButtons.*;

public class PauseOverlay {

    private Playing playing;
    private BufferedImage backgroundImg;
    private int bgX, bgY, bgW, bgH;
    private SoundButton musicButton, sfxButton;
    private UrmButton menuB, replayB, unpauseB;
    private VolumeButton volumeButton;

    public PauseOverlay(Playing playing) {
        this.playing = playing;
        loadBackground();
        createSoundButtons();
        createUrmButtons();
        createVolumeButton();
    }

    private void createVolumeButton() {
        int vX = (int) (300*2f + 90);
        int vY = (int) (300*2f - 80);
        volumeButton = new VolumeButton(vX, vY, SLIDER_WIDTH, VOLUME_HEIGHT);
    }

    private void createUrmButtons() {
//        int menuX = (int) (155 * Game.SCALE);
//        int replayX = (int) (160 * Game.SCALE);
//        int unpausedX = (int) (200 * Game.SCALE);
//        int bY = (int) (150 * Game.SCALE);
        int menuX = (int) (355 * 2f);
        int replayX = (int) (405 * 2f);
        int unpausedX = (int) (455 * 2f);
        int bY = (int) (300 * 2f);

        menuB = new UrmButton(menuX, bY, URM_SIZE, URM_SIZE,2);
        replayB = new UrmButton(replayX, bY, URM_SIZE, URM_SIZE,1);
        unpauseB = new UrmButton(unpausedX, bY, URM_SIZE, URM_SIZE,0);


    }

    private void createSoundButtons() {

        // Create sound buttons (postion)
        int soundX = (int) (450 * 2f);
        int musicY = (int) (165 * 1.8f);
        int sfxY = (int) (205 * 1.8f);
        musicButton = new SoundButton(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
        sfxButton = new SoundButton(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);

    }

    private void loadBackground() { //size of pause background
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PAUSE_BACKGROUND);
//        bgW = (int)(backgroundImg.getWidth() * Game.SCALE);
//        bgH = (int)(backgroundImg.getHeight() * Game.SCALE);
        bgW = (int)(backgroundImg.getWidth() * 1.6f);
        bgH = (int)(backgroundImg.getHeight() * 1.6f);
        bgX = Game.GAME_WIDTH / 2 - bgW / 2; //center - width/2
        bgY = (int) (25 * Game.SCALE); //test, if we want it up(decrease num), down(increase num)
    }

    public void update() {
        musicButton.update();
        sfxButton.update();

        menuB.update();
        replayB.update();
        unpauseB.update();

        volumeButton.update();

    }
    public void draw(Graphics g) {
        //Background
        g.drawImage(backgroundImg, bgX, bgY, bgW, bgH, null);

        //Sound buttons
        musicButton.draw(g);
        sfxButton.draw(g);

        //Urm
        menuB.draw(g);
        replayB.draw(g);
        unpauseB.draw(g);

        //Volume slider
        volumeButton.draw(g);

    }

    public void mouseDragged(MouseEvent e) {
        if(volumeButton.isMousePressed()) {
            volumeButton.changeX(e.getX());
        }
    }

    public void mousePressed(MouseEvent e) {
        if (isIn(e,musicButton))
            musicButton.setMousePressed(true);
        else if (isIn(e,sfxButton))
            sfxButton.setMousePressed(true);
        else if (isIn(e, menuB))
            menuB.setMousePressed(true);
        else if (isIn(e, replayB))
            replayB.setMousePressed(true);
        else if (isIn(e, unpauseB))
            unpauseB.setMousePressed(true);
        else if (isIn(e, volumeButton))
            volumeButton.setMousePressed(true);
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(e,musicButton)) {
            if (musicButton.isMousePressed()) {
                musicButton.setMuted(!musicButton.isMuted());
            }
        }
        else if (isIn(e,sfxButton)) {
            if (sfxButton.isMousePressed())
                sfxButton.setMuted(!sfxButton.isMuted());
        } else if (isIn(e,menuB)) {
            if (menuB.isMousePressed()) {
                Gamestate.state = Gamestate.MENU;
                playing.unPauseGame();
            }
        } else if (isIn(e,replayB)) {
            if (replayB.isMousePressed()) {
                playing.resetAll();
                playing.unPauseGame();
            }
        } else if (isIn(e,unpauseB)) {
            if (unpauseB.isMousePressed())
                playing.unPauseGame();
    }

        musicButton.resetBools();
        sfxButton.resetBools();
        menuB.resetBools();
        replayB.resetBools();
        unpauseB.resetBools();
        volumeButton.resetBools();
    }

    public void mouseMoved(MouseEvent e) {
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);
        menuB.setMouseOver(false);
        replayB.setMouseOver(false);
        unpauseB.setMouseOver(false);
        volumeButton.setMouseOver(false);

        if (isIn(e,musicButton))
            musicButton.setMouseOver(true);
        else if (isIn(e,sfxButton))
            sfxButton.setMouseOver(true);
        else if (isIn(e,menuB))
            menuB.setMouseOver(true);
        else if (isIn(e,replayB))
            replayB.setMouseOver(true);
        else if (isIn(e,unpauseB))
            unpauseB.setMouseOver(true);
        else if (isIn(e,volumeButton))
            volumeButton.setMouseOver(true);
    }

    private boolean isIn(MouseEvent e, PauseButton b) {
        return (b.getBounds().contains(e.getX(), e.getY()));
    }
}
