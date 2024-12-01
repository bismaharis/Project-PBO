import java.awt.Graphics;
import javax.swing.ImageIcon;

public class Car {
    private String imagePath = "D:/PBO COOK/test/res/image/player.png";
    private String imagePath2 = "D:/PBO COOK/test/res/image/enemy.png";
    private Boolean cekCar = true;
    public int posX, posY;
    String nama;

    // Setter untuk cekCar
    public void setCar(boolean cekCar) {
        this.cekCar = cekCar;
    }

    // Constructor
    public Car(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    // Getter untuk posX
    public int getX() {
        return this.posX;
    }

    // Getter untuk posY
    public int getY() {
        return this.posY;
    }

    // Setter untuk posX
    public void setX(int x) {
        this.posX = x;
    }

    // Setter untuk posY
    public void setY(int y) {
        this.posY = y;
    }

    // Method untuk memindahkan mobil ke kiri
    public void moveLeft() {
        if (posX > -60 && posX <= 150) {
            this.posX -= Settings.CAR_SPEED;
        }
    }

    // Method untuk memindahkan mobil ke kanan
    public void moveRight() {
        if (posX >= -60 && posX < 150) {
            this.posX += Settings.CAR_SPEED;
        }
    }

    // Method untuk menggambar mobil
    public void draw(Graphics g) {
        ImageIcon img;
        if (cekCar == true) {
            img = new ImageIcon(imagePath);
        } else {
            img = new ImageIcon(imagePath2);
        }
        if (posX > -280 && posX < 280) {
            g.drawImage(img.getImage(), posX, posY, null);
        }
    }
}
