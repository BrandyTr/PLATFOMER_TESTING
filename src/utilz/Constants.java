package utilz;

import Main.Game;

import static utilz.Constants.EnemyConstant.*;

public class Constants {
    public static final int ANI_SPEED = 25;//lower speech faster animation

    public static class ObjectConstants {
        public static final int RED_POTION = 0;
        public static final int BLUE_POTION = 1;
        public static final int BARREL = 2;
        public static final int BOX = 3;

        public static final int RED_POTION_VALUE = 15;
        public static final int BLUE_POTION_VALUE = 10;

        public static final int CONTAINER_WIDTH_DEFAULT = 40;
        public static final int CONTAINER_HEIGHT_DEFAULT = 30;
        public static final int CONTAINER_WIDTH = (int) (Game.SCALE * CONTAINER_WIDTH_DEFAULT);
        public static final int CONTAINER_HEIGHT = (int) (Game.SCALE * CONTAINER_HEIGHT_DEFAULT);

        public static final int POTION_WIDTH_DEFAULT = 12;
        public static final int POTION_HEIGHT_DEFAULT = 16;
        public static final int POTION_WIDTH = (int) (Game.SCALE * POTION_WIDTH_DEFAULT);
        public static final int POTION_HEIGHT = (int) (Game.SCALE * POTION_HEIGHT_DEFAULT);

        public static int GetSpriteAmount(int object_type) {
            switch (object_type) {
                case RED_POTION, BLUE_POTION:
                    return 7;
                case BARREL, BOX:
                    return 8;
            }
            return 1;
        }
    }

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
            }
            return 0;
        }

    }

    public static int GetMaxHealth(int enemy_type) {
        switch (enemy_type){
            case FOX:
                return 15;
            default:
                return 1;

        }
    }

    public static int GetEnemyDmg(int enemy_type){
        switch (enemy_type) {
            case FOX:
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
            public static final int URM_SIZE = (int) (URM_DEFAULT_SIZE * 3f);
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
        public static final int FALLING = 5;

        public static int GetSpriteAmount(int player_action) {
            switch (player_action) {
                case RUNNING:
                case IDLE:
                    return 6; //having 6 animations about running
                case ATTACK:
                case DEAD:
                    return 4;
                case JUMP:
                    return 3;
                case FALLING:
                default:
                    return 1;
            }
        }
    }
}
