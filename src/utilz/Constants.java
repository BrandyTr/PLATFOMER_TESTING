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
