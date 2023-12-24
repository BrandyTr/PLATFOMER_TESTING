package entities;

import Main.Game;
import gamestates.Playing;
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
    private BufferedImage[][] snailArr;
    private ArrayList<Snail> snails = new ArrayList<>();

    public EnemyManager(Playing playing){
        this.playing = playing;
        loadEnemyImgs();
        addEnemies();
    }

    private void addEnemies() {
        foxes = LoadSave.GetFoxes();
        snails = LoadSave.GetSnails();
        System.out.println("size of foxes: " + foxes.size());
        System.out.println("size of snails: " + snails.size());
    }

    public void update(){
        for(Fox c : foxes)
            c.update();
        for(Snail c : snails)
            c.update();
    }

    public void draw(Graphics g, int xLvlOffset){
        drawFoxes(g, xLvlOffset);
        drawSnails(g, xLvlOffset);
    }

    private void drawFoxes(Graphics g, int xLvlOffset) {
        for (Fox f : foxes)
//            if(f. isActive()) {
            g.drawImage(foxArr[f.getEnemyState()][f.getAniIndex()], (int) (f.getHitbox().x - xLvlOffset -(8 * Game.SCALE)), (int) (f.getHitbox().y - (12*Game.SCALE)), FOX_WIDTH , FOX_HEIGHT, null);
//            f.drawHitbox(g, xLvlOffset);
//            f.drawAttackBox(g, xLvlOffset);
//        }
    }

    private void drawSnails(Graphics g, int xLvlOffset) {
        for(Snail s : snails)
//            if(s. isActive()) {
            g.drawImage(snailArr[s.getEnemyState()][s.getAniIndex()], (int) (s.getHitbox().x - xLvlOffset - (10 * Game.SCALE)), (int) (s.getHitbox().y - (12 * Game.SCALE)), SNAIL_WIDTH, SNAIL_HEIGHT, null);
//            s.drawHitbox(g, xLvlOffset);
//            s.drawAttackBox(g, xLvlOffset);
//        }
    }

//    public void checkEnemyHit(Rectangle2D.Float attackBox){
//        for(Fox f : foxes)
//            if(attackBox.intersects(f.getHitbox())){
//                f.hurt(20);
//                return;
//            }
//        for(Snail s : snails)
//            if(attackBox.intersects(s.getHitbox())){
//                s.hurt(10);
//                return;
//            }
//    }

    private void loadEnemyImgs() {
        foxArr = new BufferedImage[3][6];
        BufferedImage temp1 = LoadSave.GetSpriteAtlas(LoadSave.FOX_ENEMY);
        for(int j = 0; j < foxArr.length; j++)
            for(int i = 0; i < foxArr[j].length; i++)
                foxArr[j][i] = temp1.getSubimage(i * FOX_WIDTH_DEFAULT, j * FOX_HEIGHT_DEFAULT, FOX_WIDTH_DEFAULT, FOX_HEIGHT_DEFAULT);

        snailArr = new BufferedImage[4][8];
        BufferedImage temp2 = LoadSave.GetSpriteAtlas(LoadSave.SNAIL_ENEMY);
        for(int j = 0; j < snailArr.length; j++)
            for(int i = 0; i < snailArr[j].length; i++)
                snailArr[j][i] = temp2.getSubimage(i * SNAIL_WIDTH_DEFAULT, j * SNAIL_HEIGHT_DEFAULT, SNAIL_WIDTH_DEFAULT, SNAIL_HEIGHT_DEFAULT);

    }

//    public void resetAllEnemies(){
//        for(Fox c : foxes)
//            c.resetEnemy();
//    }
}
