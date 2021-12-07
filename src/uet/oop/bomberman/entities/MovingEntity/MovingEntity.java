package uet.oop.bomberman.entities.MovingEntity;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

public abstract class MovingEntity extends Entity {

    public double _speed = 1.5;
    protected int _animate = 0;
    protected int _direction = -1;
    protected boolean _alive = true;
    public boolean _moving = false;
    public int _timeAfter = 40;
    protected boolean _removed = false;

    protected final int MAX_ANIMATE = 7500;

    protected void animate() {
        if(_animate < MAX_ANIMATE) _animate = _animate + 1;
        else _animate = 0;
    }

    public void remove() {
        _removed = true;
    }

    public boolean isRemoved() {
        return _removed;
    }

    public MovingEntity(double x, double y, Image img) {
        super( x, y, img);
    }

    public MovingEntity() {
        super();
    }

    /**public MovingEntity(int x, int y, Board board) {
        this.x = x;
        this.y = y;
        _board = board;
    }*/

    /**
     * Tính toán hướng đi
     */
    protected abstract void calculateMove();

    public abstract void move(double xa, double ya);

    /**
     * Được gọi khi đối tượng bị tiêu diệt
     */
    public abstract void kill();

    /**
     * Xử lý hiệu ứng bị tiêu diệt
     */
    protected abstract void afterKill();

    /**
     * Kiểm tra xem đối tượng có di chuyển tới vị trí đã tính toán hay không
     * @paramx
     * @paramy
     * @return
     */
    public abstract boolean canMove(double x, double y);

    @Override
    public void update() {

    }
}
