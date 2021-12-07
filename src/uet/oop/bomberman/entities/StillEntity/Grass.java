package uet.oop.bomberman.entities.StillEntity;

import javafx.scene.image.Image;

/**
 * Grass là đối tượng mà Bomber và Enemy có thể di chuyển xuyên qua, và cho phép đặt Bomb lên vị trí của nó
 */

public class Grass extends StillEntity {

    public Grass(double x, double y, Image img) {
        super(x, y, img);
    }

    public Grass() {
        super();
    }

    @Override
    public void update() {

    }
}
