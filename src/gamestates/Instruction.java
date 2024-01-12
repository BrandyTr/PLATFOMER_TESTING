package gamestates;

import Main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Instruction {
    private BufferedImage backgroundImg;
    private boolean visible;

    public Instruction() {
        loadBackground();
        visible = false;
    }

    private void loadBackground() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.INSTRUCTION_BACKGROUND);
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public void draw(Graphics g) {
        if (visible && backgroundImg != null) {
            // Draw the instruction background
            g.drawImage(backgroundImg, 300, 230, Game.GAME_WIDTH - 600, Game.GAME_HEIGHT - 250, null);
        }
    }
}
