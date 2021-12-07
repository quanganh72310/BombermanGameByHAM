package uet.oop.bomberman.entities.StillEntity.item;

import javafx.scene.image.Image;

import static uet.oop.bomberman.Board.player;

/**
 * Khi sử dụng Item này, Bomber sẽ được tăng vận tốc di chuyển thêm một giá trị thích hợp.
 */

public class SpeedItem extends Item{
    public SpeedItem(double x, double y, Image img) {
        super( x, y, img);
    }

    public SpeedItem() {
        super();
    }

    @Override
    public void upLevel() {
        player._speed = 2.2;
    }
}
