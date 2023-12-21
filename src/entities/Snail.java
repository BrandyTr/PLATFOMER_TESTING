package entities;

import static utilz.Constants.EnemyConstant.*;

public class Snail extends Enemy{
    public Snail(float x, float y){
        super(x, y, SNAIL_WIDTH, SNAIL_HEIGHT, SNAIL);
    }
}
