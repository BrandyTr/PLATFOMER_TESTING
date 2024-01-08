package objects;
import Main.Game;

public class Spike extends GameObject{
    public Spike(int x,int y, int objType){
        super(x,y,objType);
        initHitbox(16,16);
        xDrawOffset=0;
        yDrawOffset=(int)(Game.SCALE*4);
        hitbox.y+= yDrawOffset;
    }
}
