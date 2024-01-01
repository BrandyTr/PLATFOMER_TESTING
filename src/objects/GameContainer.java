package objects;

import Main.Game;

import static utilz.Constants.ObjectConstants.*;

public class GameContainer extends GameObject{
    public GameContainer(int x, int y, int objType) {
        super(x, y, objType);
        createHitBox();
    }

    private void createHitBox() {

        if(objType == BOX){
            initHitbox(19, 14);
            xDrawOffset = (int)(7 * Game.SCALE);
            yDrawOffset = (int)(12 * Game.SCALE);
            hitbox.y += yDrawOffset;
        }else {
            initHitbox(17, 19);
            xDrawOffset = (int)(8 * Game.SCALE);
            yDrawOffset = (int)(5 * Game.SCALE);
            hitbox.y += yDrawOffset + (int)(Game.SCALE * 2);
        }
        hitbox.x += xDrawOffset;
    }

    public void update(){
        if(doAnimation)
            updateAnimationTick();
    }
}
