package uet.oop.bomberman.entities.StillEntity;

import javafx.scene.image.Image;
import uet.oop.bomberman.sound.Sound;

import static uet.oop.bomberman.Board.enemys;
import static uet.oop.bomberman.Board.player;
import static uet.oop.bomberman.BombermanGame.upLevel;

/**
 * Portal là đối tượng được giấu phía sau một đối tượng Brick.
 * Khi Brick đó bị phá hủy, Portal sẽ hiện ra và nếu tất cả Enemy đã bị tiêu diệt thì
   người chơi có thể qua Level khác bằng cách di chuyển vào vị trí của Portal.
 */

public class Portal extends StillEntity{
    public Portal(int x, int y, Image img) {
        super(x, y, img);
    }

    public Portal() {
        super();
    }

    @Override
    public void update() {
        if (enemys.size() > 0) {
            return;
        }
        if (getX() - 8 < player.getX() && player.getX() < getX() + 16) {
            if (getY() - 16 < player.getY() && player.getY() < getY() + 16) {
                upLevel = true;
                Sound.play("CRYST_UP");
            }
        }
    }
}
