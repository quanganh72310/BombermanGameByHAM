package uet.oop.bomberman.entities.MovingEntity.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.MovingEntity.enemy.ai.AILow;
import uet.oop.bomberman.graphics.Sprite;

/**
 * Balloom là Enemy đơn giản nhất, di chuyển ngẫu nhiên với vận tốc cố định.
 */

public class Balloom extends Enemy {

    public Balloom(double x, double y, Image img) {
        super(x, y, img);
    }

    public Balloom() {
        super();
    }

    public Balloom(int x, int y) {
        super(x, y, Sprite.balloom_dead, 0.8, 100);

        _sprite = Sprite.balloom_left1;

        _ai = new AILow();
        _direction = _ai.calculateDirection();
    }

    @Override
    protected void chooseSprite() {
        switch(_direction) {
            case 0:
            case 1:
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, _animate, 60);
                } else {
                    _sprite = Sprite.balloom_left1;
                }
                break;
            case 2:
            case 3:
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, _animate, 60);
                } else {
                    _sprite = Sprite.balloom_left1;
                }
                break;
        }
    }
}
