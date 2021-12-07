package uet.oop.bomberman.entities.MovingEntity.bomb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.MovingEntity.MovingEntity;
import uet.oop.bomberman.entities.MovingEntity.bomber.Bomber;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

/**
 * Bomb là đối tượng mà Bomber sẽ đặt và kích hoạt tại các ô Grass. Khi đã được kích hoạt, Bomber và Enemy không thể di chuyển vào vị trí Bomb.
 * Tuy nhiên ngay khi Bomber vừa đặt và kích hoạt Bomb tại ví trí của mình, Bomber có một lần được đi từ vị trí đặt Bomb ra vị trí bên cạnh.
 * Sau khi kích hoạt 2s, Bomb sẽ tự nổ, các đối tượng Flame được tạo ra.
 */

public class Bomb extends MovingEntity {

    protected double _timeToExplode = 120; //2 seconds - thoi gian phat no
    public int _timeAfter = 30;// thoi gian de no

    protected Flame[] _flames;
    protected boolean _exploded = false;

    public Bomb(double x, double y, Image img) {
        super(x, y, img);
        _sprite = Sprite.bomb;
    }

    public Bomb() {
        super();
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
        if(_timeToExplode > 0)
            _timeToExplode--;
        else {
            if(!_exploded) {
                explode();
            } else {
                updateFlames();
            }

            if(_timeAfter > 0) {
                _timeAfter--;
            } else {
                remove();
            }
        }
        animate();
    }

    @Override
    public void render(GraphicsContext gc) {
        if(_exploded) {
            _sprite =  Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2, _animate, 20);
            renderFlames(gc);
        } else
            _sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, _animate, 30);
        gc.drawImage(_sprite.getFxImage(), getX(), getY());
    }

    public void renderFlames(GraphicsContext gc) {
        for (Flame flame : _flames) {
            flame.render(gc);
        }
    }

    public void updateFlames() {
        for (Flame flame : _flames) {
            flame.update();
        }
    }

    /**
     * Xử lý Bomb nổ
     */
    protected void explode() {//nổ
        _exploded = true;
        // TODO: tạo các Flame
        _flames = new Flame[4];
        for (int i = 0; i < _flames.length; i++) {
            _flames[i] = new Flame((int) (getX() / 32), (int) (getY() / 32), i, Board.getBombRadius());
        }
        Sound.play("BOM_11_M");
    }
    public void time_explode() {
        _timeToExplode = 0;
    }

    public FlameSegment flameAt(double x, double y) {
        if(!_exploded) return null;

        for (Flame flame : _flames) {
            if (flame == null) return null;
            FlameSegment e = flame.flameSegmentAt(x, y);
            if (e != null) return e;
        }

        return null;
    }
}
