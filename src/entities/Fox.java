package entities;

import Main.Game;

import static utilz.Constants.EnemyConstant.*;

public class Fox extends Enemy{
    public Fox (float x, float y){
        super (x, y, FOX_WIDTH, FOX_HEIGHT, FOX);
        initHitbox(x, y, (int)(19 * Game.SCALE), (int)(22 * Game.SCALE));
    }
}
