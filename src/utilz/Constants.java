package utilz;

import Main.Game;

import static utilz.Constants.EnemyConstant.*;

public class Constants {

    public static class EnemyConstant{
        public static final int FOX = 0;
        public static final int F_IDLE = 0;
        public static final int F_RUNNING = 1;
        public static final int F_DEAD = 2;
        public static final int FOX_WIDTH_DEFAULT = 32;
        public static final int FOX_HEIGHT_DEFAULT = 32;
        public static final int FOX_WIDTH = (int)(FOX_WIDTH_DEFAULT * Game.SCALE);
        public static final int FOX_HEIGHT = (int)(FOX_HEIGHT_DEFAULT * Game.SCALE);
        public static final int FOX_DRAWOFFSET_X = (int)(5 * Game.SCALE);
        public static final int FOX_DRAWOFFSET_Y = (int)(10 * Game.SCALE);

        public static final int SNAIL = 1;
        public static final int S_RUNNING = 0;
        public static final int S_IDLE = 1;
        public static final int S_UNIDLE = 2;
        public static final int S_DEAD = 3;
        public static final int SNAIL_WIDTH_DEFAULT = 48;
        public static final int SNAIL_HEIGHT_DEFAULT = 32;
        public static final int SNAIL_WIDTH = (int)(SNAIL_WIDTH_DEFAULT * Game.SCALE);
        public static final int SNAIL_HEIGHT = (int)(SNAIL_HEIGHT_DEFAULT * Game.SCALE);
        public static final int SNAIL_DRAWOFFSET_X = (int)(10 * Game.SCALE);
        public static final int SNAIL_DRAWOFFSET_Y = (int)(10 * Game.SCALE);

        public static int GetSpriteAmount(int enemy_type, int enemy_state){
            switch(enemy_type){
                case FOX:
                    switch(enemy_state){
                        case F_RUNNING:
                            return 6;
                        case F_IDLE:
                            return 4;
                        case F_DEAD:
                        default:
                            return 3;
                    }
                case SNAIL:
                    switch(enemy_state){
                        case S_RUNNING:
                        case S_IDLE:
                        case S_UNIDLE:
                        case S_DEAD:
                            return 8;
                    }
            }
            return 0;
        }

    }

    public static int GetMaxHealth(int enemy_type) {
        switch (enemy_type){
            case FOX:
                return 50;
            case SNAIL:
                return 60;
            default:
                return 1;

        }
    }

    public static int GetEnemyDmg(int enemy_type){
        switch (enemy_type) {
            case FOX:
                return 10;
            case SNAIL:
                return 10;
            default:
                return 0;
        }
    }


    public static class UI {
        public static class Buttons { //Button size
            public static final int B_WIDTH_DEFAULT = 96;
            public static final int B_HEIGHT_DEFAULT = 32;
//            public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
//            public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
            public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * 3f); //size drawing buttons
            public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * 3f); //size drawing buttons
        }

        public static class PauseButtons {
            public static final int SOUND_SIZE_DEFAULT = 32;
            public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * 1.5f); //sound buttons drawing size
        }

        public static class URMButtons {
            public static final int URM_DEFAULT_SIZE = 32;
            public static final int URM_SIZE = (int) (URM_DEFAULT_SIZE * 1.5f);
        }

        public static class VolumeButtons{
            public static final int VOLUME_DEFAULT_WIDTH = 28;
            public static final int VOLUME_DEFAULT_HEIGHT = 44;
            public static final int SLIDER_DEFAULT_WIDTH = 215;

            public static final int VOLUME_WIDTH = (int)(VOLUME_DEFAULT_WIDTH * 1.3f);
            public static final int VOLUME_HEIGHT = (int)(VOLUME_DEFAULT_HEIGHT * 1.3f);
            public static final int SLIDER_WIDTH = (int)(SLIDER_DEFAULT_WIDTH * 1.3f);
        }

    }


    public static class Directions {
        public static final int LEFT = 0;
        public static final int RIGHT = 1;
        public static final int UP = 2;
        public static final int DOWN = 3;
    }
    public static class PlayerConstants {
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int JUMP = 2;
        public static final int ATTACK = 3;
        public static final int DEAD = 4;

        public static int GetSpriteAmount(int player_action) {
            switch (player_action) {
                case RUNNING:
                case IDLE:
                    return 6; //having 6 animations about running
                case JUMP:
                case ATTACK:
                case DEAD:
                default:
                    return 4;
            }
        }
    }
}
