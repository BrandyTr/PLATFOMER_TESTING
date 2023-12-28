package entities;

import Main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utilz.Constants.Directions.*;
import static utilz.Constants.EnemyConstant.*;
import static utilz.HelpMethods.*;

public class Fox extends Enemy{

    //AttackBox
    //private Rectangle2D.Float attackBox;
//    private int attackBoxOffsetX;

    public Fox (float x, float y){
        super (x, y, FOX_WIDTH, FOX_HEIGHT, FOX);
        initHitbox(19, 22);
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, hitbox.width, hitbox.height);
        //attackBoxOffsetX = (int)(Game.SCALE * 30)
    }

    public void update(int[][] lvlData, Player player){
        updateMove(lvlData, player);
        updateAnimationTick();
        updateAttackBox();
    }

    public void updateAttackBox(){
        attackBox.x = hitbox.x;
        attackBox.y = hitbox.y;
    }

    private void updateMove(int[][] lvlData, Player player){
        if(firstUpdate)
            firstUpdateCheck(lvlData);
        if(inAir)
            updateInAir(lvlData);
        else {
            switch(state){
                case F_IDLE:
                    newState(F_RUNNING);
                    break;
                case F_RUNNING:
                    if(canSeePlayer(lvlData, player))
                        turnTowardsPlayer(player);
//                    if(isPlayerCloseForAttack(player))
//                        newState(ATTACK);
                    move(lvlData);
                    checkEnemyHit(attackBox, player);
                    break;
            }
        }
    }


    public int flipX(){
       if(walkDir == RIGHT)
           return 0;
       else
           return 146;
    }
    public int flipW(){
        if(walkDir == RIGHT)
            return 1;
        else
            return -1;
    }
}
