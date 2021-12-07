package uet.oop.bomberman.entities.MovingEntity.enemy.ai;
import uet.oop.bomberman.entities.MovingEntity.enemy.Enemy;

import static uet.oop.bomberman.Board.player;

public class AIMedium extends AI {

    Enemy _e;

    public AIMedium(Enemy e) {
        this._e = e;
    }
    @Override
    public int calculateDirection() {
        // TODO: cài đặt thuật toán tìm đường đi
        if(player == null)
            return random.nextInt(4);

        int vertical = random.nextInt(2);

        if(vertical == 1) {
            int v = calculateRowDirection();
            if(v != -1) {
                return v;
            } else {
                return calculateColDirection();
            }
        } else {
            int h = calculateColDirection();
            if(h != -1) {
                return h;
            } else {
                return calculateRowDirection();
            }
        }
    }

    protected int calculateColDirection() {
        if(player.getX() < _e.getX())
            return 3;
        else if(player.getX() > _e.getX())
            return 1;

        return -1;
    }

    protected int calculateRowDirection() {
        if(player.getY() < _e.getY())
            return 0;
        else if(player.getY() > _e.getY())
            return 2;
        return -1;
    }
}
