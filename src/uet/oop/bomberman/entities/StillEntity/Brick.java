package uet.oop.bomberman.entities.StillEntity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.MovingEntity.bomb.FlameSegment;
import uet.oop.bomberman.graphics.Sprite;

/**
 * Brick là đối tượng được đặt lên các ô Grass, không cho phép đặt Bomb lên nhưng có thể bị phá hủy bởi Bomb được đặt gần đó.
 * Bomber và Enemy thông thường không thể di chuyển vào vị trí Brick khi nó chưa bị phá hủy.
 */
public class Brick extends StillEntity {

    private int _animate = 0;
    protected boolean _destroyed = false;
    protected int _timeToDisapear = 30;
    protected boolean _removed = false;

    public Brick(double x, double y, Image img) {
        super( x, y, img);
    }

    public Brick(double x, double y, Sprite _sprite) {
        super( x, y, _sprite);
        this.img = _sprite.getFxImage();
    }

    public Brick() {
        super();
    }

    public Brick(double xUnit, double yUnit) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this._sprite = Sprite.brick;
        this.img = _sprite.getFxImage();
    }

    public void remove() {
        _removed = true;
    }

    public boolean isRemoved() {
        return _removed;
    }

    @Override
    public void update() {
        if(_destroyed) {
            int MAX_ANIMATE = 7500;
            if(_animate < MAX_ANIMATE) {
                _animate++;
            } else {
                _animate = 0;
            }
            if(_timeToDisapear > 1) {
                _timeToDisapear--;
            } else {
                remove();
            }
        }
    }

    public void destroy() {
        _destroyed = true;
    }

    public boolean collide(Entity e) {
        // TODO: xử lý khi va chạm với Flame
        if(e instanceof FlameSegment) {
            destroy();
        }
        return false;
    }

    private void chooseSprite() {
        if (_destroyed) {
            setImg(Sprite.movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2, _animate, 30).getFxImage());
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        chooseSprite();
        gc.drawImage(img, x, y);
    }
}
