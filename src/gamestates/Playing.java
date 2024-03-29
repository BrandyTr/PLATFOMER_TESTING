package gamestates;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import Main.Game;
import objects.ObjectManager;
import ui.GameOverOverlay;
import ui.LevelCompletedOverlay;
import ui.PauseOverlay;
import utilz.LoadSave;

public class Playing extends State implements Statemethods {
    private Player player;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private ObjectManager objectManager;
    private PauseOverlay pauseOverlay;
    private GameOverOverlay gameOverOverlay;
    private LevelCompletedOverlay levelCompletedOverlay;
    private boolean playerDying;
    private boolean paused = false;


    //extent the Map
    private int xLvlOffset;
    private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
    private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
    /*private int lvlTilesWide = LoadSave.GetLevelData()[0].length;
    private int maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
    private int maxLvlOffsetX = maxTilesOffset * Game.TILES_SIZE;*/
    private int maxLvlOffsetX;
    private BufferedImage backgroundImg;
    private boolean gameOver;
    private boolean lvlCompleted;

    public Playing(Game game) {
        super(game);
        initClasses();

        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG);

        calcLvlOffset();
        loadStartLevel();
    }

    public void loadNextLevel() {

        levelManager.loadNextLevel();
        resetAll();
//        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
    }

    private void loadStartLevel() {
        enemyManager.loadEnemies(levelManager.getCurrentLevel());
        objectManager.loadObjects(levelManager.getCurrentLevel());
    }

    private void calcLvlOffset() {
        maxLvlOffsetX = levelManager.getCurrentLevel().getLvlOffset();
    }

    private void initClasses() {
        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this);
        objectManager = new ObjectManager(this);


        player = new Player(200, 200, (int) (64 * Game.SCALE), (int) (40 * Game.SCALE), this);
        player.loadlvlData(levelManager.getCurrentLevel().getLevelData());

        pauseOverlay = new PauseOverlay(this);
        gameOverOverlay = new GameOverOverlay(this);
        levelCompletedOverlay = new LevelCompletedOverlay(this);

    }

    @Override
    public void update() {
        if(paused) {
            pauseOverlay.update();
        } else if (lvlCompleted) {
            levelCompletedOverlay.update();
        } else if(gameOver) {
            gameOverOverlay.update();
        } else if (playerDying) {
            player.update();
        } else {
            levelManager.update();
            objectManager.update(levelManager.getCurrentLevel().getLevelData(),player);
            player.update();
            enemyManager.update(levelManager.getCurrentLevel().getLevelData(), player);
            checkCloseToBorder();
        }
    }

    private void checkCloseToBorder() {
        int playerX = (int) player.getHitbox().x;
        int diff = playerX - xLvlOffset;

        if (diff > rightBorder) {
            xLvlOffset += diff - rightBorder;
            /* Player at 85; offset = 0 => diff = 85 - 0 = 85
			rightBorder = 80 => 85 > 80 => offset += 85 - 80
			<=> offset += 5*/
        } else if (diff < leftBorder) {
            xLvlOffset += diff - leftBorder;
        }

        if (xLvlOffset > maxLvlOffsetX) {
            xLvlOffset = maxLvlOffsetX;
        } else if (xLvlOffset < 0) {
            xLvlOffset = 0;
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, backgroundImg.getWidth() * 5, backgroundImg.getHeight() * 5, null);

        levelManager.draw(g, xLvlOffset);
        player.render(g, xLvlOffset);
        enemyManager.draw(g, xLvlOffset);
        //player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
        objectManager.draw(g, xLvlOffset);

        if(paused) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0,0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pauseOverlay.draw(g);
        } else if(gameOver) {
            gameOverOverlay.draw(g);
        } else if (lvlCompleted) {
            levelCompletedOverlay.draw(g);
        }
    }

    public void resetAll() {
        //reset player, enemy, lvl etc
        gameOver = false;
        paused = false;
        lvlCompleted = false;
        playerDying = false;
        player.resetAll();
        enemyManager.resetAllEnemies();
        objectManager.resetAllObjects();
    }

    public void setGameOver(boolean gameOver){
        this.gameOver = gameOver;
    }

    public void checkObjectHit(Rectangle2D.Float attackBox) {
        objectManager.checkObjectHit(attackBox);
    }


    public void checkEnemyHit(Rectangle2D.Float attackBox){
        enemyManager.checkEnemyHit(attackBox);
    }
    public void checkPotionTouched(Rectangle2D.Float hitbox){
        objectManager.checkObjectTouched(hitbox);
    }

    public void checkSpikesTouched(Player p){
        objectManager.checkSpikesTouched(p);
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(gameOver)
            gameOverOverlay.keyPressed(e);
        else
            switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                player.setLeft(true);
                break;
            case KeyEvent.VK_RIGHT:
                player.setRight(true);
                break;
            case KeyEvent.VK_UP:
                player.setJump(true);
                break;
            case KeyEvent.VK_SPACE:
                player.setAttacking(true);
                break;
                case KeyEvent.VK_ESCAPE:
                paused = !paused;
                break;
                case KeyEvent.VK_X:
                    player.powerAttack();
                    break;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(!gameOver)
            switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                player.setLeft(false);
                break;
            case KeyEvent.VK_RIGHT:
                player.setRight(false);
                break;
            case KeyEvent.VK_UP:
                player.setJump(false);
                break;
            case KeyEvent.VK_SPACE:
                player.setAttacking(true);
                break;
        }

    }

    public void mouseDragged(MouseEvent e) {
        if(!gameOver)
            if(paused)
                pauseOverlay.mouseDragged(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(!gameOver) {
            if (paused) {
                pauseOverlay.mousePressed(e);
            } else if (lvlCompleted) {
                levelCompletedOverlay.mousePressed(e);
            }
        } else
            gameOverOverlay.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(!gameOver) {
            if (paused) {
                pauseOverlay.mouseReleased(e);
            } else if (lvlCompleted) {
                levelCompletedOverlay.mouseReleased(e);
            }
        } else
            gameOverOverlay.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(!gameOver) {
            if (paused) {
                pauseOverlay.mouseMoved(e);
            } else if (lvlCompleted) {
                levelCompletedOverlay.mouseMoved(e);
            }
        } else
            gameOverOverlay.mouseMoved(e);
    }

    public void setLevelCompleted(boolean levelCompleted) {
        this.lvlCompleted = levelCompleted;
        if(levelCompleted) {
            game.getAudioPlayer().lvlCompleted();
        }
    }


    public void seMaxLvlOffset(int lvlOffset) {
        this.maxLvlOffsetX = lvlOffset;
    }

    public void unPauseGame() {
        paused = false;
    }

    public void windowFocusLost() {
        player.resetDirBooleans();
    }

    public Player getPlayer() {
        return player;
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public ObjectManager getObjectManager(){
        return objectManager;
    }

    public LevelManager getLevelManager(){
        return levelManager;
    }

    public void setPlayerDying(boolean playerDying) {
        this.playerDying = playerDying;
    }
}