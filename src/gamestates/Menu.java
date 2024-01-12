package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import Main.Game;
import ui.InstructionButton;
import ui.MenuButton;
import ui.PauseButton;
import utilz.LoadSave;

import static utilz.Constants.UI.IntructionButtons.*;

public class Menu extends State implements Statemethods {

    private MenuButton[] buttons = new MenuButton[3];
    private BufferedImage backgroundImg, backgroundImgSky, gameTitle;
    private int menuX, menuY, menuWidth, menuHeight, nameX, nameY, nameW, nameH;
    private InstructionButton instructionButton;
    private Instruction instruction;

    public Menu(Game game) {
        super(game);
        loadButtons();
        loadBackground();

        createIntructionButton();
        instruction = new Instruction();
        backgroundImgSky = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);
    }

    private void createIntructionButton() {
        int instructionX = (int) (580 * 2f);
        int instructionY = (int) (295 * 2f);

        instructionButton = new InstructionButton(instructionX, instructionY, INSTRUCTION_WIDTH, INSTRUCTION_HEIGHT, 2);
    }

    private void loadBackground() { //size and position background
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
        gameTitle = LoadSave.GetSpriteAtlas(LoadSave.GAME_TITLE);

        menuWidth = (int) (backgroundImg.getWidth() * 1.7f);
        menuHeight = (int) (backgroundImg.getHeight() * 1.7f);
        menuX = Game.GAME_WIDTH / 2 - menuWidth / 2 ;
        menuY = (int) (100 * 3f);

        nameH = (int) (gameTitle.getHeight() * 3.2f);
        nameW = (int) (gameTitle.getWidth() * 3.2f);
        nameX = Game.GAME_WIDTH / 2 - nameW / 2;
        nameY = (int) (100 * 1.3f);

    }

    private void loadButtons() { //Distance between buttons
        buttons[0] = new MenuButton(Game.GAME_WIDTH / 2 + 27, (int) (90 * 3f), 0, Gamestate.PLAYING); //position x in middle, position y * game scale
        buttons[1] = new MenuButton(Game.GAME_WIDTH / 2 + 27, (int) (120 * 3f), 1, Gamestate.OPTIONS);
        buttons[2] = new MenuButton(Game.GAME_WIDTH / 2 + 27, (int) (150 * 3f), 2, Gamestate.QUIT);
    }

    @Override
    public void update() {
        for (MenuButton mb : buttons)
            mb.update();

        instructionButton.update();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImgSky, 0,0, (int) ((int) Game.GAME_WIDTH * 1.2), (int) ((int) Game.GAME_HEIGHT * 1.2), null);
        //scale to fit the bg screen

        g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight, null);

        g.drawImage(gameTitle, nameX, nameY, nameW, nameH ,null);

        for (MenuButton mb : buttons)
            mb.draw(g);

        instructionButton.draw(g);
        instruction.draw(g);
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

        if (isIn(e, instructionButton))
            instructionButton.setMousePressed(true);

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

        if (isIn(e, instructionButton)) {
            // Toggle the visibility of the instruction screen
            instruction.setVisible(!instruction.isVisible());
            instructionButton.setMousePressed(false); // Reset the button state
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

        if (isIn(e, instructionButton)) {
            instructionButton.setMouseOver(true);
        } else {
            instructionButton.setMouseOver(false);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) { //press enter switch to gamestate
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            Gamestate.state = Gamestate.PLAYING;
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            instruction.setVisible(false);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }
    private boolean isIn(MouseEvent e, PauseButton b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

}