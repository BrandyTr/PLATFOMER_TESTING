package utilz;

import Main.Game;

public class Constants {

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
