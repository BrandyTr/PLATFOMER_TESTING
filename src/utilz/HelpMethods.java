package utilz;



import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Main.Game;
import entities.Fox;

import static utilz.Constants.EnemyConstant.FOX;

public class HelpMethods {
    public static boolean CanMoveHere(float x, float y, float width, float height, int [][]lvlData){
        if(!IsSolid(x,y,lvlData))
            if (!IsSolid(x + width, y + height, lvlData))
                if (!IsSolid(x + width, y, lvlData))
                    if (!IsSolid(x, y + height, lvlData))
                        return true;
        return false;
    }

    private static boolean IsSolid(float x, float y, int [][] lvlData){
        int maxWidth = lvlData[0].length * Game.TILES_SIZE;

        if(x < 0 || x >= maxWidth)
            return true;
        if(y < 0 || y>= Game.GAME_HEIGHT)
            return true;

        float xIndex= x / Game.TILES_SIZE;
        float yIndex= y / Game.TILES_SIZE;

        return IsTileSolid((int)xIndex, (int)yIndex, lvlData);
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

    public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData){
        if(xSpeed > 0)
            return IsSolid(hitbox.x + hitbox.width + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
        else
            return IsSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
    }

    public static boolean IsAllTilesWalkable(int xStart, int xEnd, int y, int[][] lvlData){
        for(int i = 0; i < xEnd - xStart; i++) {
            if (IsTileSolid(xStart + i, y, lvlData))
                return false;
//            if (!IsTileSolid(xStart + i, y + 1, lvlData))
//                return false;
        }
        return true;
    }

    public static boolean IsSightClear(int[][] lvlData, Rectangle2D.Float firstHitBox, Rectangle2D.Float secondHitBox, int yTile){
        int firstXTile = (int)(firstHitBox.x / Game.TILES_SIZE);
        int secondXTile = (int)(secondHitBox.x / Game.TILES_SIZE);

        if(firstXTile > secondXTile)
            return IsAllTilesWalkable(secondXTile, firstXTile, yTile, lvlData);
            /*
            for(int i = 0; i < firstXTile - secondXTile; i++)
                if(IsTileSolid(secondXTile + i, yTile, lvlData))
                    return true;*/
        else
            return IsAllTilesWalkable(firstXTile, secondXTile, yTile, lvlData);
            /*
            for(int i = 0; i < secondXTile - firstXTile; i++)
                if(IsTileSolid(firstXTile + i, yTile, lvlData))
                    return true;*/

    }

    public static int[][] GetLevelData(BufferedImage img) {
        int[][] lvlData = new int[img.getHeight()][img.getWidth()];

        for(int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();
                if (value >= 200)
                    value = 0;
                lvlData[j][i] = value; //about 255 colors
            }
        return lvlData;
    }

    public static ArrayList<Fox> GetFoxes(BufferedImage img){
        ArrayList<Fox> list = new ArrayList<>();
        for(int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getGreen();
                if (value == FOX)
                    list.add(new Fox(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
            }
        return list;
    }
}
