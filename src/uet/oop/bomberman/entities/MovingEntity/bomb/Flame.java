package uet.oop.bomberman.entities.MovingEntity.bomb;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.entities.MovingEntity.MovingEntity;
import uet.oop.bomberman.entities.MovingEntity.enemy.Enemy;
import uet.oop.bomberman.entities.StillEntity.StillEntity;

import static uet.oop.bomberman.Board.*;

public class Flame extends MovingEntity {

    protected int _direction;
    private int _radius;
    protected int xOrigin, yOrigin;
    protected FlameSegment[] _flameSegments = new FlameSegment[0];

    /**
     *
     * @param x hoành độ bắt đầu của Flame
     * @param y tung độ bắt đầu của Flame
     * @param direction là hướng của Flame
     * @param radius độ dài cực đại của Flame
     */
    public Flame(int x, int y, int direction, int radius) {
        xOrigin = x;
        yOrigin = y;
        setX(x);
        setY(y);
        _direction = direction;
        _radius = radius;
        createFlameSegments();
    }

    /**
     * Tạo các FlameSegment, mỗi segment ứng một đơn vị độ dài
     */
    private void createFlameSegments() {
        /**
         * tính toán độ dài Flame, tương ứng với số lượng segment
         */
        _flameSegments = new FlameSegment[calculatePermitedDistance()];

        /**
         * biến last dùng để đánh dấu cho segment cuối cùng
         */

        // TODO: tạo các segment dưới đây
        boolean last = false;

        int x = (int)getX();
        int y = (int)getY();
        for (int i = 0; i < _flameSegments.length; i++) {
            last = i == _flameSegments.length -1 ? true : false;

            switch (_direction) {
                case 0: y--; break;
                case 1: x++; break;
                case 2: y++; break;
                case 3: x--; break;
            }
            _flameSegments[i] = new FlameSegment(x, y, _direction, last);
        }
    }

    /**
     * Tính toán độ dài của Flame, nếu gặp vật cản là Brick/Wall, độ dài sẽ bị cắt ngắn
     * @return
     */
    private int calculatePermitedDistance() {
        // TODO: thực hiện tính toán độ dài của Flame
        int radius = 0;
        double X = getX();
        double Y = getY();
        while(radius < _radius) {
            boolean test1 = false;
            if(_direction == 0) {
                Y--;
                if(X * 32 - 16 < player.getX() && player.getX() < X * 32 + 30) {
                    if(Y * 32 - 26 < player.getY() && player.getY() < Y * 32 + 30) {
                        player.kill();
                    }
                }
            }
            if(_direction == 1) {
                X++;
                if(X * 32 - 32 < player.getX() && player.getX() < X * 32 + 30) {
                    if(Y * 32 - 26 < player.getY() && player.getY() < Y * 32 + 28) {
                        player.kill();
                    }
                }
            }
            if(_direction == 2) {
                Y++;
                if(X * 32 - 16 < player.getX() && player.getX() < X * 32 + 30) {
                    if(Y * 32 - 28 < player.getY() && player.getY() < Y * 32 + 30) {
                        player.kill();
                    }
                }
            }
            if(_direction == 3) {
                X--;
                if(X * 32 - 22 < player.getX() && player.getX() < X * 32 + 34) {
                    if(Y * 32 - 28 < player.getY() && player.getY() < Y * 32 + 28) {
                        player.kill();
                    }
                }
            }

            for (StillEntity element : gach) {
                if (X * 32 == element.getX() && element.getY() == Y * 32) {
                    test1 = true;
                    break;
                }
            }
            
            boolean test = false;
            for (Enemy element : enemys) {
                if(X * 32 - 30 < element.getX() && element.getX() < X * 32 + 30) {
                    if(Y * 32 - 30 < element.getY() && element.getY() < Y * 32 + 30) {
                        element.kill();
                        test = true;
                    }
                }
            }

            if (test1) {
                ++radius;
                break;
            }

            if (!test) {
                ++radius;
            } else {
                ++radius;
                break;
            }
        }
        return radius;
    }

    public FlameSegment flameSegmentAt(double x, double y) {
        for (FlameSegment flameSegment : _flameSegments) {
            if (flameSegment.getX() == x && flameSegment.getY() == y)
                return flameSegment;
        }
        return null;
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
        for (FlameSegment flameSegment : _flameSegments) {
            flameSegment.update();
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        for (FlameSegment flameSegment : _flameSegments) {
            flameSegment.render(gc);
        }
    }
}