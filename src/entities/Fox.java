package entities;

import static utilz.Constants.EnemyConstant.*;

public class Fox extends Enemy{
    public Fox (float x, float y){
        super (x, y, FOX_WIDTH, FOX_HEIGHT, FOX);
    }
}
