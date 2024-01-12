package ui;

import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Constants.UI.IntructionButtons.*;

public class InstructionButton extends PauseButton {
    private BufferedImage[] imgs;
    private int index;
    private boolean mouseOver, mousePressed;

    public InstructionButton (int x, int y, int width, int height, int rowIndex) {
        super(x, y, width, height);
        loadImgs();
    }

    private void loadImgs() {
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.INSTRUCTION_BUTTON);
        imgs = new BufferedImage[2];
        for(int i = 0; i < imgs.length; i++)
            imgs[i] = temp.getSubimage(i * INSTRUCTION_DEFAULT_WIDTH, 0, INSTRUCTION_DEFAULT_WIDTH, INSTRUCTION_DEFAULT_HEIGHT);
    }

    public void update() {
        index = 0;
        if (mouseOver)
            index = 1;
        if (mousePressed)
            index = 2;
    }

    public void draw(Graphics g) {
        g.drawImage(imgs[index], x, y, INSTRUCTION_WIDTH, INSTRUCTION_HEIGHT, null);
    }

    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }


}
