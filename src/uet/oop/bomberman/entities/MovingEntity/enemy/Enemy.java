package uet.oop.bomberman.entities.MovingEntity.enemy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.MovingEntity.MovingEntity;
import uet.oop.bomberman.entities.MovingEntity.enemy.ai.AI;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

import static uet.oop.bomberman.Board.*;

/**
 * Enemy là các đối tượng mà Bomber phải tiêu diệt hết để có thể qua Level.
 * Enemy có thể di chuyển ngẫu nhiên hoặc tự đuổi theo Bomber tùy theo loại Enemy.
 */

public abstract class Enemy extends MovingEntity {
    protected int _points;

    protected double _speed;
    protected AI _ai;

    protected double MAX_STEPS;
    protected double rest;
    protected double _steps;

    protected int _finalAnimation = 30;
    protected Sprite _deadSprite;

    public Enemy(int x, int y, Sprite dead, double speed, int points) {
        this.setX(32 * x);
        this.setY(32 * y);

        _points = points;
        _speed = speed;

        MAX_STEPS = 32 / _speed;
        rest = (MAX_STEPS - (int) MAX_STEPS) / MAX_STEPS;
        _steps = MAX_STEPS;

        _timeAfter = 20;
        _deadSprite = dead;
    }
    public Enemy(double x, double y, Image img) {
        super( x, y, img);
    }

    public Enemy() {
        super();
    }

    @Override
    public void kill() {
        if(!_alive) return;
        _alive = false;
        Sound.play("AA126_11");
    }

    @Override
    protected void afterKill() {
        if(_timeAfter > 0) --_timeAfter;
        else {
            if(_finalAnimation > 0) --_finalAnimation;
            else
                remove();
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

    @Override
    public void render(GraphicsContext gc) {
        if (_alive)
            chooseSprite();
        else
            _sprite = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, _animate, 60);
        if (_sprite != null) {
            gc.drawImage(_sprite.getFxImage(), x, y);
        }
    }

    @Override
    protected void calculateMove() {
        // TODO: Tính toán hướng đi và di chuyển Enemy theo _ai và cập nhật giá trị cho _direction
        // TODO: sử dụng canMove() để kiểm tra xem có thể di chuyển tới điểm đã tính toán hay không
        // TODO: sử dụng move() để di chuyển
        // TODO: nhớ cập nhật lại giá trị cờ _moving khi thay đổi trạng thái di chuyển
        int xa = 0, ya = 0;
        if(_steps <= 0){
            _direction = _ai.calculateDirection();
            _steps = MAX_STEPS;
        }

        if(_direction == 0) ya--;
        if(_direction == 2) ya++;
        if(_direction == 3) xa--;
        if(_direction == 1) xa++;

        if(canMove(xa, ya)) {
            _steps -= 1 + rest;
            move(xa * _speed, ya * _speed);
            _moving = true;
        } else {
            _steps = 0;
            _moving = false;
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
            xr2 = xr1 + 32 - _speed;
            //đi lên
        }
        if(_direction == 1) {
            xr1 = xr1 + 32 + _speed;
            xr2 = xr1;
            yr2 = yr1 + 32 - _speed;
            //sang phải
        }
        if(_direction == 2) {
            yr1 = yr1 + 32 + _speed;
            yr2 = yr1;
            xr2 = xr1 + 32 - _speed;
            //đi xuống
        }
        if(_direction == 3) {
            xr1 = xr1 - _speed;
            xr2 = xr1;
            yr2 = yr1 + 32 - _speed;
            //sang trái
        }

        Entity a1 = getGachAt(xr1, yr1);
        Entity a2 = getGachAt(xr2, yr2);
        Entity a3 = getBombAt(xr1, yr1);
        Entity a4 = getBombAt(xr2, yr2);

        return a1 == null && a2 == null && a3 == null && a4 == null;
    }

    protected abstract void chooseSprite();
}
