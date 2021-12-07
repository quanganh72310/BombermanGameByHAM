package uet.oop.bomberman.entities.MovingEntity.bomb;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.entities.MovingEntity.MovingEntity;
import uet.oop.bomberman.entities.MovingEntity.enemy.Enemy;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.Board.*;

public class FlameSegment extends MovingEntity {

    protected boolean _last;
    private static final int _Time = 20;

    /**
     *
     * @param x
     * @param y
     * @param direction
     * @param last cho biết segment này là cuối cùng của Flame hay không,
     *                segment cuối có sprite khác so với các segment còn lại
     */
    public FlameSegment(int x, int y, int direction, boolean last) {
        setX(x);
        setY(y);
        _last = last;
        _direction = direction;
    }

    @Override
    public void render(GraphicsContext gc) {
        switch (_direction) {
            case 0:
                if(!_last) {
                    _sprite = Sprite.movingSprite(Sprite.explosion_vertical, Sprite.explosion_vertical1, Sprite.explosion_vertical2, _animate, _Time);
                } else {
                    _sprite = Sprite.movingSprite(Sprite.explosion_vertical_top_last, Sprite.explosion_vertical_top_last1, Sprite.explosion_vertical_top_last2, _animate, _Time);
                }
                break;
            case 1:
                if(!_last) {
                    _sprite = Sprite.movingSprite(Sprite.explosion_horizontal, Sprite.explosion_horizontal1, Sprite.explosion_horizontal2, _animate, _Time);
                } else {
                    _sprite = Sprite.movingSprite(Sprite.explosion_horizontal_right_last, Sprite.explosion_horizontal_right_last1, Sprite.explosion_horizontal_right_last2, _animate, _Time);
                }
                break;
            case 2:
                if(!_last) {
                    _sprite = Sprite.movingSprite(Sprite.explosion_vertical, Sprite.explosion_vertical1, Sprite.explosion_vertical2, _animate, _Time);
                } else {
                    _sprite = Sprite.movingSprite(Sprite.explosion_vertical_down_last,
                        Sprite.explosion_vertical_down_last1, Sprite.explosion_vertical_down_last2, _animate, _Time);
                }
                break;
            case 3:
                if(!_last) {
                    _sprite = Sprite.movingSprite(Sprite.explosion_horizontal, Sprite.explosion_horizontal1, Sprite.explosion_horizontal2, _animate, _Time);
                } else {
                    _sprite = Sprite.movingSprite(Sprite.explosion_horizontal_left_last, Sprite.explosion_horizontal_left_last1, Sprite.explosion_horizontal_left_last2, _animate, _Time);
                }
                break;
        }
        gc.drawImage(_sprite.getFxImage(), getX() * 32, getY() * 32);
    }

    @Override
    protected void calculateMove() {

    }

    @Override
    public void move(double xa, double ya) {

    }

    @Override
    public void kill() {

    }

    @Override
    protected void afterKill() {

    }

    @Override
    public boolean canMove(double x, double y) {
        return false;
    }

    @Override
    public void update() {
        animate();
        for (Enemy element : enemys) {
            if(getX() * 32 - 30 < element.getX() && element.getX() < getX() * 32 + 30) {
                if(getY() * 32 - 28 < element.getY() && element.getY() < getY() * 32 + 28) {
                    element.kill();
                }
            }
        }

        if(getX() * 32 - 12 < player.getX() && player.getX() < getX() * 32 + 30) {
            if(getY() * 32 - 28 < player.getY() && player.getY() < getY() * 32 + 28) {
                player.kill();
            }
        }

        for (Bomb b : bombs) {
            if (getX() * 32 == b.getX() && getY() * 32 == b.getY()) {
                b.time_explode();
            }
        }
    }
}
