package uet.oop.bomberman.entities.StillEntity;

import javafx.scene.image.Image;

/**
 * Wall là đối tượng cố định, không thể phá hủy bằng Bomb cũng như không thể đặt Bomb lên được,
   Bomber và Enemy không thể di chuyển vào đối tượng này.
 */

public class Wall extends StillEntity {

    public Wall(double x, double y, Image img) {
        super(x, y, img);
    }

    public Wall() {
        super();
    }


    @Override
    public void update() {

    }
}
