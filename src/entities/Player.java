package entities;

import Main.Game;
import utilz.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utilz.Constants.Directions.*;
import static utilz.Constants.Directions.DOWN;
import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.*;

public class Player extends Entity {
    private BufferedImage[][] characBoy;
    private int aniTick, aniIndex, aniSpeech = 25; //lower speech faster animation
    private int playerAction = ATTACK;
    private boolean moving = false, attacking = false;
    private boolean left, up, right, down,jump;
    private float playerSpeech = 2.0f;

    private int[][] lvlData;
    private float xDrawOffset = 7 * Game.SCALE;
    private float yDrawOffSet= 9 * Game.SCALE;
    private float airSpeed=0f;
    private float gravity= 0.04f* Game.SCALE;
    private float jumpSpeed= -2.25f* Game.SCALE;
    private float fallSpeedAfterCollision= 0.5f * Game.SCALE;
    private boolean inAir= false;


    public Player(float x, float y, int width, int height) {
        super(x,y, width, height);
        loadAnimations();
        initHitbox(x,y,15*Game.SCALE, 9*Game.SCALE);

    }


    public void update() {
        updatePos(); //if running true => setAnimations

        updateAnimationTick();
        setAnimations();
    }

    public void render(Graphics g) {
        g.drawImage(characBoy[playerAction][aniIndex], (int)(hitbox.x - xDrawOffset), (int)(hitbox.y - yDrawOffSet), width, height, null);
        drawHitbox(g);
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
        if(inAir){
            if(airSpeed<0)
                playerAction = JUMP;
            else
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
        if(jump)
            jump();
        if (!left && !right && !inAir)
            return;

        float xSpeed = 0;

        if (left)
            xSpeed -= playerSpeech;
        if (right)
            xSpeed += playerSpeech;
        if(!inAir)
            if(!IsEntityOnFloor(hitbox, lvlData)){
                inAir = true;

            }


        if(inAir){
            if(CanMoveHere(hitbox.x, hitbox.y+ airSpeed, hitbox.width, hitbox.height, lvlData)){
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            }
            else{
                hitbox.y= GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
                if(airSpeed>0)
                    resetInAir();
                else
                    airSpeed= fallSpeedAfterCollision;
                updateXPos(xSpeed);
            }

        }else
            updateXPos(xSpeed);
        moving=true;


    }

    private void jump() {
        if (inAir)
            return;
        inAir=true;
        airSpeed= jumpSpeed;
    }

    private void resetInAir() {
        inAir= false;
        airSpeed=0;
    }

    private void updateXPos(float xSpeed) {
        if(CanMoveHere(hitbox.x +xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)){
            hitbox.x+=xSpeed;
        }
        else{
            hitbox.x= GetEntityXPosNextToWall(hitbox, xSpeed);
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

    public void loadlvlData(int[][] lvlData){
        this.lvlData= lvlData;
        if(!IsEntityOnFloor(hitbox, lvlData))
            inAir= true;
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

    public void setJump(boolean jump) {
        this.jump=jump;
    }
}
