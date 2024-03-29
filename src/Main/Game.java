package Main;

import audio.AudioPlayer;
import gamestates.GameOptions;
import gamestates.Gamestate;
import gamestates.Playing;
import java.awt.Graphics;
import gamestates.Menu;
import ui.AudioOptions;
import utilz.LoadSave;

import java.awt.*;


public class Game implements Runnable {
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200; //updates per second
    private Playing playing;
    private Menu menu;
    private GameOptions gameOptions;
    private AudioOptions audioOptions;
    private AudioPlayer audioPlayer;

    //Game Window
    public final static int TILES_DEFAULT_SIZE = 16;
    public final static float SCALE = 3f;
    public final static int TILES_IN_WIDTH = 26;
    public final static int TILES_IN_HEIGHT = 14;
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
    public Game() {
        initClasses();

        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();

        startGameLoop();
    }
    private void initClasses() {
        audioOptions = new AudioOptions(this);
        audioPlayer = new AudioPlayer();
        menu = new Menu(this);
        playing = new Playing(this);
        gameOptions = new GameOptions(this);
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {

        switch (Gamestate.state) {
            case MENU:
                menu.update();
                break;
            case PLAYING:
                playing.update();
                break;
            case OPTIONS:
                gameOptions.update();
                break;
            case QUIT:
            default:
                System.exit(0);
                break;
        }
    }
    public void render(Graphics g) {
            switch (Gamestate.state) {
                case MENU:
                    menu.draw(g);
                    break;
                case PLAYING:
                    playing.draw(g);
                    break;
                case OPTIONS:
                    gameOptions.draw(g);
                default:
                    break;
            }
    }
    @Override //GAME LOOP
    public void run() {
        double timePerFrame = 1000000000 / FPS_SET;
        double timePerUpdate = 1000000000 / UPS_SET;

        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while (true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            /* deltaU will be >= 1 when the duration since last update is
            equal or more than timePerUpdate*/
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if(deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if(deltaF >=1 ) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            /*if(now - lastFrame >= timePerFrame) {

                gamePanel.repaint();
                lastFrame = now;
                frames++;
            }*/
            //frames increment by 1 => repaint 1 frame until satisfy if condition
            if(System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }
    public void windowFocusLost() {
        if (Gamestate.state == Gamestate.PLAYING)
            playing.getPlayer().resetDirBooleans();
    }
    public Menu getMenu() {
        return menu;
    }

    public Playing getPlaying() {
        return playing;
    }

    public AudioOptions getAudioOptions() {
        return audioOptions;
    }
    public GameOptions getGameOptions() {
        return gameOptions;
    }

    public AudioPlayer getAudioPlayer() {
        return audioPlayer;
    }
}
