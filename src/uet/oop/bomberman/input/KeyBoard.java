package uet.oop.bomberman.input;

import javafx.scene.Scene;

public class KeyBoard {
    public boolean up;
    public boolean down;
    public boolean left;
    public boolean right;
    public boolean space;

    /**
     * add listener
     */
    public void addListener(Scene scene) {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    up = true;
                    break;
                case DOWN:
                    down = true;
                    break;
                case LEFT:
                    left = true;
                    break;
                case RIGHT:
                    right = true;
                    break;
                case SPACE:
                    space = true;
                    break;
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case UP:
                    up = false;
                    break;
                case DOWN:
                    down = false;
                    break;
                case LEFT:
                    left = false;
                    break;
                case RIGHT:
                    right = false;
                    break;
                case SPACE:
                    space = false;
            }
        });
    }
}
