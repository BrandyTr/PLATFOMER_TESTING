package levels;

import Main.Game;
import gamestates.Gamestate;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LevelManager {
    private Game game;
    private BufferedImage[] levelSprite;
    private ArrayList<Level> levels;
    private int lvlIndex = 0;


    public LevelManager(Game game) {
        this.game = game;
        //levelSprite = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        importOutsideSprites();
        levels = new ArrayList<>();
        buildAllLevels();
    }
    public void loadNextLevel() {
        lvlIndex++;
        if(lvlIndex >= levels.size()) {
            //complete all levels => back to menu
            lvlIndex = 0;
            System.out.println("No more levels! Game completed!");
            Gamestate.state = Gamestate.MENU;
        }

        Level newLevel = levels.get(lvlIndex);
        game.getPlaying().getEnemyManager().loadEnemies(newLevel);
        game.getPlaying().getPlayer().loadlvlData(newLevel.getLevelData());
        game.getPlaying().seMaxLvlOffset(newLevel.getLvlOffset());
        game.getPlaying().getObjectManager().loadObjects(newLevel);

    }

    private void buildAllLevels() {
        BufferedImage[] allLevels = LoadSave.GetAllLevels();
        for (BufferedImage img : allLevels) {
            levels.add(new Level(img));
        }
    }

    private void importOutsideSprites() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[200]; //W: 20; H: 10 => 20 x 10 = 200
        //can use array BufferedImage [][] => easier
        for (int j = 0; j < 10; j++) { // j: row
            for (int i = 0; i < 20; i++) {
                int index = j * 20 + i;
                //if row j = 0 => start at row 2
                levelSprite[index] = img.getSubimage(i * 16, j * 16, 16, 16);
            }
        }
    }

    public void draw(Graphics g, int lvlOffset) {

        for (int j = 0; j < Game.TILES_IN_HEIGHT; j++) {
            for (int i = 0; i < levels.get(lvlIndex).getLevelData()[0].length; i++) {
                int index = levels.get(lvlIndex).getSpriteIndex(i, j);
                g.drawImage(levelSprite[index], Game.TILES_SIZE * i - lvlOffset, Game.TILES_SIZE * j, Game.TILES_SIZE, Game.TILES_SIZE, null);
            }
        }
    }

    public void update() {
    }
    public Level getCurrentLevel(){
        return levels.get(lvlIndex);
    }
    public int getAmountOfLevels() {
        return levels.size();
    }
}
