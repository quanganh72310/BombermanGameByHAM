package uet.oop.bomberman.entities.MovingEntity.enemy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.MovingEntity.enemy.ai.AILow;
import uet.oop.bomberman.entities.MovingEntity.enemy.ai.AIMedium;
import uet.oop.bomberman.graphics.Sprite;
import java.util.Random;

import static uet.oop.bomberman.Board.*;
import static uet.oop.bomberman.Board.getBombAt;

/**
 * Oneal có tốc độ di chuyển thay đổi, lúc nhanh, lúc chậm và di chuyển "thông minh" hơn so với Balloom (biết đuổi theo Bomber).
 */

public class Oneal extends Enemy {

    public Oneal(double x, double y, Image img) {
        super(x, y, img);
    }

    public Oneal() {
        super();
    }

    public Oneal(int x, int y) {
        super(x, y, Sprite.oneal_dead, 1.2, 100);

        _sprite = Sprite.oneal_left1;

        _ai = new AIMedium(this);
        _direction = -1;
    }

    @Override
    protected void calculateMove() {
        // TODO: Tính toán hướng đi và di chuyển Enemy theo _ai và cập nhật giá trị cho _direction
        // TODO: sử dụng canMove() để kiểm tra xem có thể di chuyển tới điểm đã tính toán hay không
        // TODO: sử dụng move() để di chuyển
        // TODO: nhớ cập nhật lại giá trị cờ _moving khi thay đổi trạng thái di chuyển

        double xa = 0, ya = 0;

        if(getX() - 32 < player.getX() && player.getX() < getX() + 32) {
            if (getY() < player.getY()) {
                _direction = 2;
            } else if (getY() > player.getY()) {
                _direction = 0;
            }
        } else if(getY() - 32 < player.getY() && player.getY() < getY() + 32) {
            if (getX() < player.getX()) {
                _direction = 1;
            } else if (getX() > player.getX()) {
                _direction = 3;
            }
        }

        if(_direction == 0) ya = -1.0;
        if(_direction == 2) ya = 1.0;
        if(_direction == 3) xa = -1.0;
        if(_direction == 1) xa = 1.0;

        if(canMove(xa, ya)) {
            _steps -= 1 + rest;
            move(xa * _speed, ya * _speed);
            _moving = true;
        } else {
            _direction = _ai.calculateDirection();
            xa = 0;
            ya = 0;

            if(_direction == 0) ya = -1.0;
            if(_direction == 2) ya = 1.0;
            if(_direction == 3) xa = -1.0;
            if(_direction == 1) xa = 1.0;
            if(canMove(xa, ya)) {
                _steps -= 1 + rest;
                move(xa * _speed, ya * _speed);
                _moving = true;
            } else {
                _steps = 0;
                _moving = false;
            }
        }
    }

    @Override
    public void move(double xa, double ya) {
        if(!_alive) return;
        setY(getY() + ya);
        setX(getX() + xa);
    }

    @Override
    public boolean canMove(double x, double y) {
        double xr1 = getX(), yr1 = getY();
        double xr2 = getX(), yr2 = getY();
        if(_direction == 0) {
            yr1 = yr1 - _speed;
            yr2 = yr1;
            xr1 += _speed;
            xr2 = xr1 + 32 - 2 * _speed;
            //đi lên
        }
        if(_direction == 1) {
            xr1 = xr1 + 32 + _speed;
            xr2 = xr1;
            yr1 += _speed;
            yr2 = yr1 + 32 - 3 * _speed;
            //sang phải
        }
        if(_direction == 2) {
            yr1 = yr1 + 32 + _speed;
            yr2 = yr1;
            xr1 += _speed;
            xr2 = xr1 + 32 - 2 * _speed;
            //đi xuống
        }
        if(_direction == 3) {
            xr1 = xr1 - _speed;
            xr2 = xr1;
            yr1 += _speed;
            yr2 = yr1 + 32 - 3 * _speed;
            //sang trái
        }

        Entity a1 = getGachAt(xr1, yr1);
        Entity a2 = getGachAt(xr2, yr2);
        Entity a3 = getBombAt(xr1, yr1);
        Entity a4 = getBombAt(xr2, yr2);

        return a1 == null && a2 == null && a3 == null && a4 == null;
    }

    @Override
    protected void chooseSprite() {
        switch(_direction) {
            case 0:
            case 1:
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, _animate, 60);
                } else {
                    _sprite = Sprite.oneal_left1;
                }
                break;
            case 2:
            case 3:
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, _animate, 60);
                } else {
                    _sprite = Sprite.oneal_left1;
                }
                break;
        }
    }

    @Override
    public void update() {
        animate();

        if(!_alive) {
            afterKill();
            return;
        }

        calculateMove();
    }
}
