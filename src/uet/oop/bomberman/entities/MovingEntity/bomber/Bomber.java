package uet.oop.bomberman.entities.MovingEntity.bomber;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.MovingEntity.MovingEntity;
import uet.oop.bomberman.entities.MovingEntity.enemy.Enemy;
import uet.oop.bomberman.entities.MovingEntity.bomb.Bomb;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

import java.util.Iterator;

import static uet.oop.bomberman.Board.*;
import static uet.oop.bomberman.BombermanGame.*;

/**
 * Bomber là nhân vật chính của trò chơi.
 * Bomber có thể di chuyển theo 4 hướng trái/phải/lên/xuống theo sự điều khiển của người chơi.
 */

public class Bomber extends MovingEntity {

    protected int _timeBetweenPutBombs = 0;

    public Bomber(double x, double y, Image img) {
        super(x, y, img);
    }

    public Bomber() {
        super();
    }

    @Override
    protected void calculateMove() {
        // TODO: xử lý nhận tín hiệu điều khiển hướng đi từ _input và gọi move() để thực hiện di chuyển
        // TODO: nhớ cập nhật lại giá trị cờ _moving khi thay đổi trạng thái di chuyển
        double xa = 0, ya = 0;
        if(keyBoard.up) ya--;
        if(keyBoard.down) ya++;
        if(keyBoard.left) xa--;
        if(keyBoard.right) xa++;

        if(xa != 0 || ya != 0)  {
            move(xa * _speed, ya * _speed);
            _moving = true;
        } else {
            _moving = false;
        }
    }

    @Override
    public void move(double xa, double ya) {
        // TODO: sử dụng canMove() để kiểm tra xem có thể di chuyển tới điểm đã tính toán hay không và thực hiện thay đổi tọa độ _x, _y
        // TODO: nhớ cập nhật giá trị _direction sau khi di chuyển
        if(xa > 0) _direction = 1;
        if(xa < 0) _direction = 3;
        if(ya > 0) _direction = 2;
        if(ya < 0) _direction = 0;

        if(canMove(0, ya)) { //separate the moves for the player can slide when is colliding
            setY(getY() + ya);
        }

        if(canMove(xa, 0)) {
            setX(getX() + xa);
        }
    }

    @Override
    public void kill() {
        if (!_alive) return;
        _alive = false;
        Sound.play("endgame3");
    }

    @Override
    protected void afterKill() {
        if (_timeAfter > 0) --_timeAfter;
        else {
            if (_timeAfter > -30) {
                -- _timeAfter;
                return;
            }
            running = false;
            Image im = new Image("gameOver.png");
            View.setImage(im);
            View.setX(-496);
            View.setY(-208);
            View.setScaleX(0.5);
            View.setScaleY(0.5);
        }
    }

    @Override
    public boolean canMove(double x, double y) {
        // TODO: kiểm tra có đối tượng tại vị trí chuẩn bị di chuyển đến và có thể di chuyển tới đó hay không
        if (!_alive) {
            return false;
        }
        for (int c = 0; c < 4; c++) { //colision detection for each corner of the player
            double xt = ((getX() + x) + c % 2 * 9) / 32; //divide with tiles size to pass to tile coordinate
            double yt = ((getY() + 32 + y) + c / 2 * 10 - 13) / 32; //these values are the best from multiple tests

            Entity a1 = getGachAt(xt * 32 + 12, yt * 32 - 16);
            Entity a2 = getGachAt(xt * 32 + 12, yt * 32);
            Entity a3 = getGachAt(xt * 32, yt * 32 - 16);
            Entity a4 = getGachAt(xt * 32, yt * 32);

            Entity a_1 = getBombAt(xt * 32 + 12, yt * 32 - 16);
            Entity a_2 = getBombAt(xt * 32 + 12, yt * 32);
            Entity a_3 = getBombAt(xt * 32, yt * 32 - 16);
            Entity a_4 = getBombAt(xt * 32, yt * 32);

            int countBomb = bombs.size();

            boolean test = false;
            if (countBomb > 0) {
                if (bombs.get(countBomb - 1).getX() - 22 < getX() && getX() < bombs.get(countBomb - 1).getX() + 32) {
                    if (bombs.get(countBomb - 1).getY() - 28 < getY() && getY() < bombs.get(countBomb - 1).getY() + 28) {
                        test = true;
                    }
                }
            }
            if(!test) {
                if (a1 != null || a2 != null || a3 != null || a4 != null || a_1 != null || a_2 != null || a_3 != null || a_4 != null) {
                    return false;
                }
            } else {
                if (a1 != null || a2 != null || a3 != null || a4 != null) {
                    return false;
                }
            }
        }
        return true;
    }

    //sprite
    private void chooseSprite() {
        switch (_direction) {
            case 0:
                _sprite = Sprite.player_up;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2, _animate, 20);
                }
                break;
            case 2:
                _sprite = Sprite.player_down;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2, _animate, 20);
                }
                break;
            case 3:
                _sprite = Sprite.player_left;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2, _animate, 20);
                }
                break;
            default:
                _sprite = Sprite.player_right;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
                }
                break;
        }
    }

    /**
     * Kiểm tra xem có đặt được bom hay không? nếu có thì đặt bom tại vị trí hiện tại của Bomber
     */
    private void detectPlaceBomb() {
        // TODO: kiểm tra xem phím điều khiển đặt bom có được gõ và giá trị _timeBetweenPutBombs, Game.getBombRate() có thỏa mãn hay không
        // TODO: Game.getBombRate() sẽ trả về số lượng bom có thể đặt liên tiếp tại thời điểm hiện tại
        // TODO: _timeBetweenPutBombs dùng để ngăn chặn Bomber đặt 2 Bomb cùng tại 1 vị trí trong 1 khoảng thời gian quá ngắn
        // TODO: nếu 3 điều kiện trên thỏa mãn thì thực hiện đặt bom bằng placeBomb()
        // TODO: sau khi đặt, nhớ giảm số lượng Bomb Rate và reset _timeBetweenPutBombs về 0
        if(keyBoard.space && Board.getBombRate() > 0 && _timeBetweenPutBombs < 0) {

                double xt = getX() + 8;
                double yt = getY() + 16;
                placeBomb((int) xt, (int) yt);
                Board.addBombRate(-1);

                _timeBetweenPutBombs = 30;
            }
        }

    protected void placeBomb(int x, int y) {
        // TODO: thực hiện tạo đối tượng bom, đặt vào vị trí (x, y)
        Bomb b = new Bomb(x / 32, y / 32, Sprite.bomb.getFxImage());
        bombs.add(b);
        Sound.play("BOM_SET");
    }

    private void clearBombs() {
        Iterator<Bomb> bs = bombs.iterator();

        Bomb b;
        while (bs.hasNext()) {
            b = bs.next();
            if (b.isRemoved()) {
                bs.remove();
                Board.addBombRate(1);
            }
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (_alive)
            chooseSprite();
        else {
            _sprite = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, _animate, 120);
        }
        gc.drawImage(_sprite.getFxImage(), x, y);
    }

    @Override
    public void update() {
        collideWithEnemy();
        clearBombs();
        if (_timeBetweenPutBombs < -7500) _timeBetweenPutBombs = 0;
        else _timeBetweenPutBombs--;
        animate();

        calculateMove();
        detectPlaceBomb();
        if (!_alive) {
            afterKill();
        }
    }

    protected void collideWithEnemy() {
        boolean test = false;
        for (Enemy element : enemys) {
            if(getX() - 26 < element.getX() && element.getX() < getX() + 28) {
                if(getY() - 28 < element.getY() && element.getY() < getY() + 28) {
                    test = true;
                }
            }
        }
        if (test) {
            kill();
        }
    }
}
