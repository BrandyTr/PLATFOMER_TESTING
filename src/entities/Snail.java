package entities;

import Main.Game;

import static utilz.Constants.EnemyConstant.*;

public class Snail extends Enemy{
    public Snail(float x, float y){
        super(x, y, SNAIL_WIDTH, SNAIL_HEIGHT, SNAIL);
        initHitbox(x, y, (int)(28 * Game.SCALE), (int)(22 * Game.SCALE));
    }
}
