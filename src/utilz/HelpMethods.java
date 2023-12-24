package utilz;



import java.awt.geom.Rectangle2D;
import Main.Game;

public class HelpMethods {
    public static boolean CanMoveHere(float x, float y, float width, float height, int [][]lvlData){
        if(!IsSolid(x,y,lvlData)) {
            if (!IsSolid(x + width, y + height, lvlData)) {
                if (!IsSolid(x + width, y, lvlData)) {
                    if (!IsSolid(x, y + height, lvlData)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean IsSolid(float x, float y, int [][] lvlData) {
        int maxWidth = lvlData[0].length * Game.TILES_SIZE;

        if (x < 0 || x >= maxWidth)
            return true;
        if (y < 0 || y >= Game.GAME_HEIGHT)
            return true;

        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;

        int value = lvlData[(int) yIndex][(int) xIndex];

        if (value >= 48 || value < 0 || value != 11)
            return true;
        return false;
    }

    public static boolean IsTileSolid(int xTile, int yTile, int[][] lvlData){
        int value =lvlData[(int)yTile][(int)xTile];
        if (value >= 200 || value < 0 || value != 19 && value != 26 && value != 16 && value != 38) {
            return true;
        }
        return false;
        //if = 19 => return false because transparent & it not solid
    }

    public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed){
        int currentTile=(int)(hitbox.x / Game.TILES_SIZE);
        if(xSpeed > 0){
            //Right
            int tileXPos= currentTile * Game.TILES_SIZE;
            int xOffset= (int)(Game.TILES_SIZE- hitbox.width);
            return tileXPos + xOffset + 1  ; //so it not overlapping

        } else {
            //Left
            return currentTile * Game.TILES_SIZE;
        }



    }

    public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed){
        int currentTile=(int)(hitbox.y/ Game.TILES_SIZE);
        if(airSpeed >0){
            //Going down (Falling - touch floor)
            int tileYPos = currentTile * Game.TILES_SIZE;
            int yOffset= (int)(2 * Game.TILES_SIZE - hitbox.height);
            return tileYPos + yOffset - 1;
        } else {
            //Jumping
            return currentTile * Game.TILES_SIZE;

        }
    }

    public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int [][] lvlData){
        //Check the pixel below bottom-left & bottom right
        if(!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData)) {
            if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
                return false;
        }
        return true;
    }


}
