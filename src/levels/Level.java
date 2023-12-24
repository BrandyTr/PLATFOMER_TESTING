package levels;

import Main.Game;
import entities.Fox;
import entities.Snail;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.HelpMethods.GetLevelData;
import static utilz.HelpMethods.GetFoxes;
import static utilz.HelpMethods.GetSnails;



public class Level {
    private BufferedImage img;
    private int[][] lvlData;
    //Array for enemies
    private ArrayList<Fox> foxes;
    private ArrayList<Snail> snails;
    //Size of level => width is flexible & height is fixed
    private int lvlTilesWide;
    private int maxTilesOffset;
    private int maxLvlOffsetX;


    public Level(BufferedImage img) {
        this.img = img;
        creatLevelData();
        creatEnemies();
        calcLvlOffsets();
    }

    private void calcLvlOffsets() {
        lvlTilesWide = img.getWidth();
        maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
        maxLvlOffsetX = Game.TILES_SIZE;
    }

    private void creatEnemies() {
        foxes = GetFoxes(img);
        snails = GetSnails(img);
    }

    private void creatLevelData() {

    }

    public int getSpriteIndex(int x, int y) {
        return lvlData[y][x]; //x, y: positions
    }

    public int[][]getLevelData(){
        return lvlData;
    }

    public int getLvlOffset() {
        return maxLvlOffsetX;
    }
    public ArrayList<Fox> getFoxes() {
        return foxes;
    }
    public ArrayList<Snail> getSnails() {
        return snails;
    }
}
