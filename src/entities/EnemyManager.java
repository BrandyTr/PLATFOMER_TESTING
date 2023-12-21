package entities;

import gamestates.Playing;
import utilz.LoadSave;
import static utilz.Constants.EnemyConstant.*;

import java.awt.*;
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
        for(Fox c : foxes)
            g.drawImage(foxArr[c.getEmenyState()][c.getAniIndex()], (int)c.getHitbox().x - xLvlOffset, (int)c.getHitbox().y, FOX_WIDTH, FOX_HEIGHT, null);
    }

    private void drawSnails(Graphics g, int xLvlOffset) {
        for(Snail c : snails)
            g.drawImage(snailArr[c.getEmenyState()][c.getAniIndex()], (int)c.getHitbox().x - xLvlOffset, (int)c.getHitbox().y, SNAIL_WIDTH, SNAIL_HEIGHT, null);
    }

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
}
