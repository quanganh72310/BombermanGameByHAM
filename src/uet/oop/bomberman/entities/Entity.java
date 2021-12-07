package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Entity {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected double x, y;
    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected Image img;

    protected Sprite _sprite;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity (double xUnit, double yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
    }

    public Entity (double xUnit, double yUnit, Sprite _sprite) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this._sprite = _sprite;
    }

    public Entity() {

    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Image getImg() {
        return img;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public void set_sprite(Sprite _sprite) {
        this._sprite = _sprite;
    }

    public Sprite get_sprite() {
        return _sprite;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    public abstract void update();
}
