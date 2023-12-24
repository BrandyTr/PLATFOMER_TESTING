package entities;

import Main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.Directions.RIGHT;
import static utilz.Constants.EnemyConstant.*;
import static utilz.Constants.PlayerConstants.RUNNING;
import static utilz.HelpMethods.*;

public class Snail extends Enemy{

   // private Rectangle2D.Float attackBox;


    public Snail(float x, float y){
        super(x, y, SNAIL_WIDTH, SNAIL_HEIGHT, SNAIL);
//        initHitbox(x, y, (int)(28 * Game.SCALE), (int)(22 * Game.SCALE));
//        initAttackBox();
    }

//    private void initAttackBox() {
//        attackBox = new Rectangle2D.Float(x, y, hitbox.width, hitbox.height);
//    }
//
//    public void update(int[][] lvlData, Player player) {
//        updateMove(lvlData, player);
//        updateAnimationTick();
//        updateAttackBox();
//    }
//
//    public void updateAttackBox(){
//        attackBox.x = hitbox.x;
//        attackBox.y = hitbox.y;
//    }
//
//    private void updateMove(int[][] lvlData, Player player){
//        if(firstUpdate)
//            firstUpdateCheck(lvlData);
//
//        if(inAir)
//           updateInAir(lvlData);
//        else{
//            switch(enemyState) {
//                case S_IDLE:
//                    newState(S_UNIDLE);
//                case S_UNIDLE:
//                    newState(RUNNING);
//                case S_RUNNING:
//                    if (canSeePlayer(lvlData, player))
//                        turnTowardsPlayer(player);
//                    checkEnemyHit(attackBox, player);
//                    move(lvlData);
//                    break;
//            }
//        }
//    }
//
//    public void drawAttackBox(Graphics g, int xLvlOffset){
//        g.setColor(Color.red);
//        g.drawRect((int)(attackBox.x - xLvlOffset), (int)attackBox.y, (int)attackBox.width, (int)attackBox.height);
//    }
//
//    public int flipX(){
//        if(walkDir == RIGHT)
//            return 0;
//        else
//            return width;
//    }
//    public int flipW(){
//        if(walkDir == RIGHT)
//            return 1;
//        else
//            return -1;
//    }

}
