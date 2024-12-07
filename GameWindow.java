import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class GameWindow extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private Car playerCar;
    private List<Car> obstacles;
    private int score;
    private int speed;
    private String playerName;
    private Database database;
    private Image backgroundImage;
    private Thread gameMusicThread;
    private MusicPlayer gameMusic;

    // Variabel untuk posisi latar belakang
    private int backgroundY1 = 0;
    private int backgroundY2 = -700;  // Posisi latar belakang kedua, mulai di bawah layar

    public GameWindow(String playerName, Database database) {
        this.playerName = playerName;
        this.database = database;

        backgroundImage = new ImageIcon("res/images/road.png").getImage(); // Pastikan gambar latar belakang sesuai

        gameMusic = new MusicPlayer("res/audio/Play.wav", true);
        gameMusicThread = new Thread(gameMusic);
        gameMusicThread.start();

        JFrame gameFrame = new JFrame("Racing Car Game");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(500, 700);
        gameFrame.add(this);

        setFocusable(true);
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(500, 700));
        addKeyListener(this);

        initGame();
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
    }

    private void initGame() {
        playerCar = new Car(200, 500, true);
        obstacles = new ArrayList<>();
        score = 0;
        speed = 10;

        timer = new Timer(30, this);
        timer.start();
    }

    private void stopMusic() {
        if (gameMusic != null) {
            gameMusic.stop();
        }
        if (gameMusicThread != null && gameMusicThread.isAlive()) {
            gameMusicThread.interrupt();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Gambar dua latar belakang untuk efek scrolling
        g.drawImage(backgroundImage, 0, backgroundY1, getWidth(), getHeight(), this);
        g.drawImage(backgroundImage, 0, backgroundY2, getWidth(), getHeight(), this);

        // Gambar mobil pemain dan musuh
        playerCar.draw(g);
        for (Car obstacle : obstacles) {
            obstacle.draw(g);
        }

        // Menampilkan skor
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 230, 50);
    }

    public void actionPerformed(ActionEvent e) {
    // Update posisi latar belakang
    backgroundY1 += speed;
    backgroundY2 += speed;

    // Jika latar belakang pertama mencapai bawah layar, geser ke atas
    if (backgroundY1 >= getHeight()) {
        backgroundY1 = -getHeight();  // Geser latar belakang pertama ke atas
    }
    // Jika latar belakang kedua mencapai bawah layar, geser ke atas
    if (backgroundY2 >= getHeight()) {
        backgroundY2 = -getHeight();  // Geser latar belakang kedua ke atas
    }

    // Penambahan untuk musuh dan mobil
    if (Math.random() < 0.02) {
        // Mengatur posisi musuh lebih sering muncul di tengah
        int randomLane = (int) (Math.random() * 3);  // Tiga jalur jalan
        if (Math.random() < 0.6) {
            // 60% peluang musuh muncul di tengah
            randomLane = 1;  // Tengah = kolom 200 (indeks 1)
        }
        obstacles.add(new Car(randomLane * 200, 0, false));  // Mengatur posisi X musuh
    }

    // Iterasi menggunakan Iterator untuk menghindari ConcurrentModificationException
    Iterator<Car> iterator = obstacles.iterator();
    while (iterator.hasNext()) {
        Car obstacle = iterator.next();
        obstacle.y += speed;

        // Jika mobil musuh melewati batas bawah layar, hapus dari list
        if (obstacle.y > 600) {
            iterator.remove();  // Menghapus dengan aman menggunakan iterator
            score++;
            speed++;
        } else if (obstacle.intersects(playerCar)) {
            timer.stop();
            gameOver();
            break;
        }
    }

    repaint();
}


    private void gameOver() {
        timer.stop();
        stopMusic();
        int option = JOptionPane.showConfirmDialog(
            this,
            "Game Over! Your score: " + score + "\nDo you want to return to the main menu?",
            "Game Over",
            JOptionPane.YES_NO_OPTION
        );

        database.insertScore(playerName, score);

        if (option == JOptionPane.YES_OPTION) {
            SwingUtilities.getWindowAncestor(this).dispose();
            new RacingCarGame().createMainMenu();
        } else {
            System.exit(0);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            playerCar.moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            playerCar.moveRight();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}
