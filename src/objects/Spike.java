package objects;
import Main.Game;

public class Spike extends GameObject{
    public Spike(int x,int y, int objType){
        super(x,y,objType);
        initHitbox(14,10);
        xDrawOffset=0;
        yDrawOffset=(int)(Game.SCALE*6);
        hitbox.y+= yDrawOffset;
    }
}
