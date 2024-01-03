package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import Main.Game;
import ui.MenuButton;
import utilz.LoadSave;

public class Menu extends State implements Statemethods {

    private MenuButton[] buttons = new MenuButton[3];
    private BufferedImage backgroundImg, backgroundImgSky;
    private int menuX, menuY, menuWidth, menuHeight;

    public Menu(Game game) {
        super(game);
        loadButtons();
        loadBackground();

        backgroundImgSky = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);

    }

    private void loadBackground() { //size and position background
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
//        menuWidth = (int) (backgroundImg.getWidth() * Game.SCALE);
//        menuHeight = (int) (backgroundImg.getHeight() * Game.SCALE);
//        menuX = Game.GAME_WIDTH / 2 - menuWidth / 2
//        menuY = (int) (45 * Game.SCALE);
        menuWidth = (int) (backgroundImg.getWidth() * 2f);
        menuHeight = (int) (backgroundImg.getHeight() * 2f);
        menuX = Game.GAME_WIDTH / 2 - menuWidth / 2 - 30;
        menuY = (int) (30 * Game.SCALE);

    }

    private void loadButtons() { //Distance between buttons
        buttons[0] = new MenuButton(Game.GAME_WIDTH / 2, (int) (60 * Game.SCALE), 0, Gamestate.PLAYING); //position x in middle, position y * game scale
        buttons[1] = new MenuButton(Game.GAME_WIDTH / 2, (int) (90 * Game.SCALE), 1, Gamestate.OPTIONS);
        buttons[2] = new MenuButton(Game.GAME_WIDTH / 2, (int) (120 * Game.SCALE), 2, Gamestate.QUIT);
    }

    @Override
    public void update() {
        for (MenuButton mb : buttons)
            mb.update();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImgSky, 0,0, (int) ((int) Game.GAME_WIDTH * 1.2), (int) ((int) Game.GAME_HEIGHT * 1.2), null);
        //scale to fit the bg screen

        g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight, null);

        for (MenuButton mb : buttons)
            mb.draw(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (MenuButton mb : buttons) {
            if (isIn(e, mb)) {
                mb.setMousePressed(true);
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) { //if mouse pressed, switch to gamestate
        for (MenuButton mb : buttons) {
            if (isIn(e, mb)) {
                if (mb.isMousePressed()) {
                    mb.applyGamestate();
                }
                if (mb.getState() == Gamestate.PLAYING) {
                    game.getAudioPlayer().setLevelSong(game.getPlaying().getLevelManager().getLvlIndex());
                }
                break;
            }
        }

        resetButtons();

    }

    private void resetButtons() {
        for (MenuButton mb : buttons)
            mb.resetBools();

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (MenuButton mb : buttons)
            mb.setMouseOver(false);

        for (MenuButton mb : buttons)
            if (isIn(e, mb)) {
                mb.setMouseOver(true);
                break;
            }

    }

    @Override
    public void keyPressed(KeyEvent e) { //press enter switch to gamestate
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            Gamestate.state = Gamestate.PLAYING;

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

}