package uet.oop.bomberman.entities.MovingEntity.enemy.ai;

public class AILow extends AI {

    @Override
    public int calculateDirection() {
        // TODO: cài đặt thuật toán tìm đường đi
        return random.nextInt(4);
    }

}
