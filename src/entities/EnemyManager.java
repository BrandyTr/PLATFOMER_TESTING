package entities;

import Main.Game;
import gamestates.Playing;
import levels.Level;
import utilz.LoadSave;
import static utilz.Constants.EnemyConstant.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class EnemyManager {

    private Playing playing;
    private BufferedImage[][] foxArr;
    private ArrayList<Fox> foxes = new ArrayList<>();

    public EnemyManager(Playing playing){
        this.playing = playing;
        loadEnemyImgs();
    }

    public void loadEnemies(Level level) {
        foxes = level.getFoxes();
    }

    public void update(int[][] lvlData, Player player){
        for(Fox f : foxes)
            if(f. isActive())
                f.update(lvlData, player);

    }

    public void draw(Graphics g, int xLvlOffset){
        drawFoxes(g, xLvlOffset);
    }

    private void drawFoxes(Graphics g, int xLvlOffset) {
        for (Fox f : foxes)
            if(f. isActive()) {
            g.drawImage(foxArr[f.getEmenyState()][f.getAniIndex()], (int) (f.getHitbox().x - xLvlOffset -(8 * Game.SCALE)) + f.flipX(), (int) (f.getHitbox().y - (12*Game.SCALE)), FOX_WIDTH + f.flipW(), FOX_HEIGHT, null);
            f.drawHitbox(g, xLvlOffset);
            f.drawAttackBox(g, xLvlOffset);
        }
    }


    public void checkEnemyHit(Rectangle2D.Float attackBox){
        for(Fox f : foxes)
            if(attackBox.intersects(f.getHitbox())){
                f.hurt(20);
                return;
            }
    }

    private void loadEnemyImgs() {
        foxArr = new BufferedImage[3][6];
        BufferedImage temp1 = LoadSave.GetSpriteAtlas(LoadSave.FOX_ENEMY);
        for(int j = 0; j < foxArr.length; j++)
            for(int i = 0; i < foxArr[j].length; i++)
                foxArr[j][i] = temp1.getSubimage(i * FOX_WIDTH_DEFAULT, j * FOX_HEIGHT_DEFAULT, FOX_WIDTH_DEFAULT, FOX_HEIGHT_DEFAULT);
    }

    public void resetAllEnemies(){
        for(Fox c : foxes)
            c.resetEnemy();
    }
}
