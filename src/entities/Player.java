package entities;

import Main.Game;
import audio.AudioPlayer;
import gamestates.Playing;
import utilz.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utilz.Constants.ANI_SPEED;
import static utilz.Constants.Directions.*;
import static utilz.Constants.Directions.DOWN;
import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.*;

public class Player extends Entity {
    private BufferedImage[][] characBoy;
    private boolean moving = false, attacking = false;
    private boolean left, right, jump;
    private int[][] lvlData;
    // (0,0) -> (7,8)
    private float xDrawOffset = 7 * Game.SCALE;
    private float yDrawOffSet= 8 * Game.SCALE;
    //Jump, Gravity
    private float gravity = 0.04f * Game.SCALE; //lower gravity => higher jump
    private float jumpSpeed= -2.25f* Game.SCALE;
    private float fallSpeedAfterCollision= 0.5f * Game.SCALE; //in case player hit roof

    //StatusBarUI
    private BufferedImage statusBarImg;

    private int statusBarWidth = (int)(96 * Game.SCALE);
    private int statusBarHeight = (int)(29 * Game.SCALE);
    private int statusBarX = (int)(10 * Game.SCALE);
    private int statusBarY = (int)(10 * Game.SCALE);

    private int healthBarWidth = (int)(75 * Game.SCALE);
    private int healthBarHeight = (int)(2 * Game.SCALE);
    private int healthBarXStart = (int)(34 * Game.SCALE);
    private int healthBarYStart = (int)(14 * Game.SCALE);
//    private int maxHealth = 100;
//    private int currentHealth = maxHealth;
    private int healthWidth = healthBarWidth;

    //AttackBox
    private int flipX = 0;
    private int flipW = 1;
    private boolean attackChecked;

    private Playing playing;
    private int tileY=0;
    public Player(float x, float y, int width, int height, Playing playing) {
        super(x,y, width, height);
        this.playing = playing;
        this.state = IDLE;
        this.maxHealth = 100;
        this.currentHealth = maxHealth;
        this.walkSpeed = 0.5f * Game.SCALE;

        loadAnimations();
        initHitbox(15, 18 );
        initAttackBox();
    }

//    public void setSpawn(Point spawn) {
//        this.x = spawn.x;
//        this.y = spawn.y;
//        hitbox.x = x;
//        hitbox.y = y;
//    }
    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y,(int)(10 * Game.SCALE), (int)(10* Game.SCALE));
    }

    public void update() {

        updateHealthBar();

        if (currentHealth <= 0) {
            if(state != DEAD) { // health = 0 change state = dead
                state = DEAD;
                aniTick = 0; // reset animation tick
                aniIndex = 0;
                playing.setPlayerDying(true);

                playing.getGame().getAudioPlayer().playEffect(AudioPlayer.DIE);
            } else if (aniIndex == GetSpriteAmount(DEAD) - 1 && aniTick >= ANI_SPEED - 1) { //Check for the last "dead" sprites (Example: if there is 8 animations, it will count in order from 0 to 7). Check for the last animation tick.
                playing.setGameOver(true);

                playing.getGame().getAudioPlayer().stopSong();
                playing.getGame().getAudioPlayer().playEffect(AudioPlayer.GAMEOVER);
            } else {
                updateAnimationTick();
            }

            return;
        }

        updateAttackBox();
        updatePos(); //if running true => setAnimations

        if(moving){
            checkPotionTouched();
            checkSpikesTouched();
            tileY= (int) (hitbox.y/ Game.TILES_SIZE);
        }
        if(attacking)
            checkAttack();
        updateAnimationTick();
        setAnimations();
    }

    private void checkSpikesTouched() {
        playing.checkSpikesTouched(this);
    }

    private void checkPotionTouched() {
        playing.checkPotionTouched(hitbox);
    }

    private void checkAttack() {
        if(attackChecked || aniIndex != 1)
            return;
        attackChecked = true;
        playing.checkEnemyHit(attackBox);
        playing.checkObjectHit(attackBox);

        playing.getGame().getAudioPlayer().playAttackSound();
    }

    private void updateAttackBox() {
        if(right){
            attackBox.x = hitbox.x  + hitbox.width + (int)(Game.SCALE);
        }else if(left){
            attackBox.x = hitbox.x  - hitbox.width + (int)(Game.SCALE * 4);
        }
        attackBox.y = hitbox.y + (Game.SCALE * 8);
    }

    private void updateHealthBar() {
        healthWidth = (int)((currentHealth / (float)maxHealth) * healthBarWidth + Game.SCALE);
    }

    public void render(Graphics g, int lvlOffset) {
        g.drawImage(characBoy[state][aniIndex], (int)(hitbox.x - xDrawOffset) -lvlOffset + flipX, (int)(hitbox.y - yDrawOffSet), width * flipW, height, null);
        drawHitbox(g,lvlOffset);
        drawAttackbox(g, lvlOffset);
        drawUI(g);
    }

    private void drawAttackbox(Graphics g, int lvlOffset) {
        g.setColor(Color.red);
        g.drawRect((int)attackBox.x - lvlOffset, (int)attackBox.y, (int)attackBox.width, (int)attackBox.height);
    } //Clean code => need to remove after everything oke

    private void drawUI(Graphics g) {
        g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
        g.setColor(Color.red);
        g.fillRect((int)(healthBarXStart - (7 * Game.SCALE)), healthBarYStart + (int)(3 * Game.SCALE), healthWidth, healthBarHeight);
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= ANI_SPEED) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(state)) {
                aniIndex = 0;
                attacking = false;
                attackChecked = false;
            }
        }
    }

    public void setAnimations() {

        int startAni = state;
        //Moving
        if(moving) {
            state = RUNNING;
        } else {
            state = IDLE;
        }

        //Jumping
        if(inAir){
            if(airSpeed<0)
                state = JUMP;
            else
                state = FALLING;
        }

        //Attacking
        if (attacking) {
            state = ATTACK;
        }
        if (startAni != state ) {
            resetAniTick();
        }
    }
    private void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;
    }
    public void updatePos() {
        moving = false;

        if(jump) {
            jump();
        }

        //if (!left && !right && !inAir)
          //  return;

        //Pressed both <- & -> => player idle
        if(!inAir) {
            if((!left && !right) || (left && right)) {
                return;
            }
        }

        float xSpeed = 0;

        if (left) {
            xSpeed -= walkSpeed;
            //say moving is true if it can move somewhere
            flipX = 116;
            flipW = -1;
        }
        if (right) {
            xSpeed += walkSpeed;
            flipX = 0;
            flipW = 1;
        }
        if(!inAir)
            if(!IsEntityOnFloor(hitbox, lvlData)){
                inAir = true;

            }


        if(inAir){
            if(CanMoveHere(hitbox.x, hitbox.y+ airSpeed, hitbox.width, hitbox.height, lvlData)){
                hitbox.y += airSpeed;
                airSpeed += gravity;
                //gravity < 0 => slow down because going up
                //going down => gravity increase
                updateXPos(xSpeed);
            }
            else{
                hitbox.y= GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
                if(airSpeed>0) {
                    resetInAir();
                }
                else {
                    airSpeed = fallSpeedAfterCollision;
                }
                updateXPos(xSpeed);
            }
        }else { //if not in air => check only x position
            updateXPos(xSpeed);
        }
        moving=true;
    }

    private void jump() {
        if (inAir)
            return;

        playing.getGame().getAudioPlayer().playEffect(AudioPlayer.JUMP);
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
        } else{ //collision with something
            hitbox.x= GetEntityXPosNextToWall(hitbox, xSpeed);
        }
    }

    public void changeHealth(int value) {
        currentHealth += value;
        if(currentHealth <= 0){
            currentHealth = 0;
            //gameOver();
        }else if(currentHealth >= maxHealth)
            currentHealth = maxHealth;
    }

    public void kill(){
        currentHealth=0;
    }

    public void changePower(int value){
        System.out.println("Added power!");
    }


    private void loadAnimations() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);

        characBoy = new BufferedImage[6][6];
        for (int j = 0; j < characBoy.length; j++ ) {
            for (int i = 0; i < characBoy[j].length; i++) {
                characBoy[j][i] = img.getSubimage(i * 64, j * 40, 64, 40);
                //row 1: x = i, y = 0; image 64x40
            }
        }
        statusBarImg = LoadSave.GetSpriteAtlas(LoadSave.STATUS_BAR);
    }

    public void loadlvlData(int[][] lvlData){
        this.lvlData= lvlData;
        if(!IsEntityOnFloor(hitbox, lvlData))
            inAir= true;
    }
    public void resetDirBooleans() {
        left = false;
        right = false;

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

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public void resetAll() {
        resetDirBooleans();
        inAir = false;
        attacking = false;
        moving = false;
        state = IDLE;
        currentHealth = maxHealth;
        hitbox.x = x;
        hitbox.y = y;
        if(!IsEntityOnFloor(hitbox, lvlData))
            inAir= true;
    }

    public int getTileY(){
        return tileY;
    }
}
