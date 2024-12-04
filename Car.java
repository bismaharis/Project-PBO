import javax.swing.*;
import java.awt.*;

public class Car extends Rectangle {
    private boolean isPlayer;
    private Image carImage;

    public Car(int x, int y, boolean isPlayer) {
        super(x, y, 75, 150);
        this.isPlayer = isPlayer;

        if (isPlayer) {
            carImage = new ImageIcon("res/image/player.png").getImage();
        } else {
            carImage = new ImageIcon("res/image/enemy.png").getImage();
        }
    }

    public void moveLeft() {
        if (x > 0)
            x -= 50;
    }

    public void moveRight() {
        if (x < 750)
            x += 50;
    }

    public void draw(Graphics g) {
        if (carImage != null) {
            g.drawImage(carImage, x, y, 150, 200, null);
        } else {
            g.setColor(isPlayer ? Color.BLUE : Color.RED);
            g.fillRect(x, y, width, height);
        }
    }
}
