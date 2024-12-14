import javax.swing.*;
import java.awt.*;

public class Car extends Rectangle {
    private boolean isPlayer;
    private Image carImage;

    public Car(int x, int y, boolean isPlayer) {
        super(x, y, 75, 150);
        this.isPlayer = isPlayer;

        if (isPlayer) {
            carImage = new ImageIcon("res/images/player.png").getImage();
        } else {
            carImage = new ImageIcon("res/images/enemy.png").getImage();
        }
    }

    public void moveLeft() {
        if (x > 40) // Batas kiri adalah 0
            x -= 20;
    }

    public void moveRight() {
        if (x + width < 480) // Batas kanan adalah lebar layar (600)
            x += 20;
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