package utilz;

import Main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadSave {
    public static final String PLAYER_ATLAS = "Charac/player_boy.png";
    public static final String LEVEL_ATLAS = "Tiles/outside_tiles.png";
    public static final String LEVEL_ONE_ATLAS = "Tiles/level_one_data.png";
    public static final String MENU_BUTTONS = "Menu/button_atlas.png";
    public static final String MENU_BACKGROUND = "Menu/menu_background.png";
    public static final String PAUSE_BACKGROUND = "Pause/pause_menu.png";
    public static final String SOUND_BUTTONS = "Pause/sound_button.png";
    public static final String URM_BUTTONS = "Pause/urm_buttons.png";
    public static final String VOLUME_BUTTONS = "Pause/volume_buttons.png";



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
    public static int[][] GetLevelData() {
        int[][] lvlData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
        BufferedImage img = GetSpriteAtlas(LEVEL_ONE_ATLAS);

        for(int j = 0; j < img.getHeight(); j++) {
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();
                if (value >= 200) {
                    value = 0;
                }
                lvlData[j][i] = value; //about 255 colors
            }
        }
        return lvlData;
    }
}
