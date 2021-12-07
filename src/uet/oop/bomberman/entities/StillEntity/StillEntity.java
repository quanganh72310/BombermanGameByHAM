package uet.oop.bomberman.entities.StillEntity;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public abstract class StillEntity extends Entity {
    protected boolean _removed = false;

    public StillEntity(double x, double y, Image img) {
        super(x, y, img);
    }

    public StillEntity(double x, double y, Sprite _sprite) {
        super(x, y, _sprite);
    }

    public void remove() {
        _removed = true;
    }

    public boolean isRemoved() {
        return _removed;
    }

    public StillEntity() {
        super();
    }

    @Override
    public void update() {

    }

    public boolean collide(Entity e) {
        return false;
    }
}
