package utilz;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

public class LoadSave {
    public static final String PLAYER_ATLAS = "Charac/player_boy.png";
    public static final String LEVEL_ATLAS = "Tiles/outside_tiles.png";
    //public static final String LEVEL_ONE_ATLAS = "Tiles/level_one_data.png";
    public static final String MENU_BUTTONS = "Menu/button_atlas.png";
    public static final String MENU_BACKGROUND = "Menu/menu_background_final.png";
    public static final String PAUSE_BACKGROUND = "Pause/pause_menu.png";
    public static final String SOUND_BUTTONS = "Pause/sound_button.png";
    public static final String URM_BUTTONS = "Pause/urm_buttons.png";
    public static final String VOLUME_BUTTONS = "Pause/volume_buttons.png";
    public static final String MENU_BACKGROUND_IMG = "Menu/background_menu.png";
    public static final String PLAYING_BG_IMG = "Tiles/background_PLAYING_SCREEN.png";
    public static final String FOX_ENEMY = "Enemy/enemy_fox.png";
    public static final String STATUS_BAR = "Charac/health_power_bar.png";
    public static final String COMPLETED_IMG = "Menu/completed_sprite.png";
    public static final String POTION_ATLAS = "Objects/potions_sprites.png";
    public static final String CONTAINER_ATLAS = "Objects/objects_sprites.png";
    public static final String TRAP_ATLAS = "Objects/trap_atlas.png";
    public static final String CANNON_ATLAS = "Objects/cannon_atlas.png";
    public static final String CANNON_BALL = "Objects/bullet.png";
    public static final String DEATH_SCREEN = "Death/death_screen.png";
    public static final String OPTIONS_MENU = "Option/options_background.png";
    public static final String INSTRUCTION_BUTTON = "Menu/instruction_button.png";
    public static final String INSTRUCTION_BACKGROUND = "Menu/instruction.png";
    public static final String GAME_TITLE = "Menu/game_name.png";

    public static BufferedImage GetSpriteAtlas(String filename) {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream("/" + filename);

        try {
            img = ImageIO.read(is);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }

    public static BufferedImage[] GetAllLevels() {
        URL url = LoadSave.class.getResource("/lvls");
        File file = null;

        try {
            file= new File(url.toURI());
            //url: check location, uri: the actual resource in this case
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        File[] files = file.listFiles();
        File[] fileSorted = new File[files.length];

        for(int i = 0; i < fileSorted.length; i++) {
            for(int j = 0; j < files.length; j++) {
                if(files[j].getName().equals((i + 1) + ".png")) {
                    fileSorted[i] = files[j];
                }
            }
        }

        BufferedImage[] imgs = new BufferedImage[fileSorted.length];

        for (int i = 0; i < imgs.length; i++) {
            try {
                imgs[i] = ImageIO.read(fileSorted[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return imgs;
    }
}
