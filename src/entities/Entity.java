package entities;

import Main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.Graphics;

public abstract class Entity {
    protected float x,y;
    protected int width, height;
    protected int aniTick, aniIndex;
    protected int state;
    protected float airSpeed;
    protected boolean inAir= false;
    protected int maxHealth = 100;
    protected Rectangle2D.Float attackBox;

    protected int currentHealth = maxHealth;
    protected float walkSpeed;



    protected Rectangle2D.Float hitbox;
    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    protected void drawAttackBox(Graphics g, int xLvlOffset){
        g.setColor(Color.red);
        g.drawRect((int)(attackBox.x - xLvlOffset), (int)attackBox.y, (int)attackBox.width, (int)attackBox.height);
    }

    protected void drawHitbox(Graphics g, int xLvlOffset){
        g.setColor(Color.pink);
        g.drawRect((int) hitbox.x - xLvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);

    }

    protected void initHitbox( int width, int height) {
        hitbox=new Rectangle2D.Float(x, y, (int) (width * Game.SCALE), (int) (height * Game.SCALE));
    }
    //  protected void updateHitbox(){
    //    hitbox.x = (int) x;
    //  hitbox.y= (int) y;
    //}
    public Rectangle2D.Float getHitbox(){
        return hitbox;
    }

    public int getEmenyState(){
        return state;
    }
    public int getAniIndex(){
        return aniIndex;
    }
}
