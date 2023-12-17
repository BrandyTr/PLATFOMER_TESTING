package entities;

import utilz.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utilz.Constants.Directions.*;
import static utilz.Constants.Directions.DOWN;
import static utilz.Constants.PlayerConstants.*;

public class Player extends Entity {
    private BufferedImage[][] characBoy;
    private int aniTick, aniIndex, aniSpeech = 20; //lower speech faster animation
    private int playerAction = ATTACK;
    private boolean moving = false, attacking = false;
    private boolean left, up, right, down;
    private float playerSpeech = 2.0f;

    public Player(float x, float y, int width, int height) {
        super(x,y, width, height);
        loadAnimations();
    }
    public void update() {
        updatePos(); //if running true => setAnimations
        updateAnimationTick();
        setAnimations();
    }

    public void render(Graphics g) {
        g.drawImage(characBoy[playerAction][aniIndex], (int) x, (int) y, width, height, null);
    }
    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeech) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(playerAction)) {
                aniIndex = 0;
                attacking = false;
            }
        }
    }

    public void setAnimations() {

        int startAni = playerAction;

        if(moving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }
        if (attacking) {
            playerAction = ATTACK;
        }
        if (startAni != playerAction ) {
            resetAniTick();
        }
    }
    private void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;
    }
    public void updatePos() {
        moving = false;

        if(left && !right) {
            x -= playerSpeech;
            moving = true;
        } else if (right && !left){
            x += playerSpeech;
            moving = true;
        }

        if (up && !down) {
            y -= playerSpeech;
            moving = true;
        } else if (down && !up) {
            y += playerSpeech;
            moving = true;
        }
    }
    private void loadAnimations() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);

        characBoy = new BufferedImage[5][6];
        for (int j = 0; j < characBoy.length; j++ ) {
            for (int i = 0; i < characBoy[j].length; i++) {
                characBoy[j][i] = img.getSubimage(i * 64, j * 40, 64, 40);
                //row 1: x = i, y = 0; image 64x40
            }
        }
    }
    public void resetDirBooleans() {
        left = false;
        right = false;
        up = false;
        down = false;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }
}
