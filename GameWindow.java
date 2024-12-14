import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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

    private int backgroundY1 = 0;
    private int backgroundY2 = -700;
    private Random random;
    private int lastObstacleX = -1;  // Posisi latar belakang kedua, mulai di bawah layar

    public GameWindow(String playerName, Database database) {
        this.playerName = playerName;
        this.database = database;
        this.random = new Random();

        backgroundImage = new ImageIcon("res/images/road.png").getImage();

        gameMusic = new MusicPlayer("res/audio/Play.wav", true);
        gameMusicThread = new Thread(gameMusic);
        gameMusicThread.start();

        JFrame gameFrame = new JFrame("Racing Car Game");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(600, 700);
        gameFrame.add(this);

        setFocusable(true);
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(600, 700));
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    
        g.drawImage(backgroundImage, 0, backgroundY1, getWidth(), getHeight(), this);
        g.drawImage(backgroundImage, 0, backgroundY2, getWidth(), getHeight(), this);
    
        playerCar.draw(g);
        for (Car obstacle : obstacles) {
            obstacle.draw(g);
        }
    
        g.setColor(Color.WHITE);
        g.setFont(new Font("Impact", Font.BOLD, 20));
        g.drawString("Score: " + score, 230, 50);
        g.drawString("Player: " + playerName, 10, 50); // Tambahkan nama pemain di HUD
    }

    public void actionPerformed(ActionEvent e) {
        backgroundY1 += speed;
        backgroundY2 += speed;
    
        if (backgroundY1 >= getHeight()) {
            backgroundY1 = -getHeight();
        }
        if (backgroundY2 >= getHeight()) {
            backgroundY2 = -getHeight();
        }
    
        // Penambahan mobil merah
        if (Math.random() < 0.015) {
            int[] lanes = {40, 140, 240, 340, 400}; // Jalur
            int obstacleX;
        
            do {
                obstacleX = lanes[random.nextInt(lanes.length)]; // Pilih jalur secara acak
            } while (obstacleX == lastObstacleX); // Ulangi jika posisi sama dengan rintangan terakhir
        
            obstacles.add(new Car(obstacleX, 0, false)); // Tambahkan rintangan baru
            lastObstacleX = obstacleX; // Perbarui posisi rintangan terakhir
        }
    
        Iterator<Car> iterator = obstacles.iterator();
        while (iterator.hasNext()) {
            Car obstacle = iterator.next();
            obstacle.y += speed;
    
            if (obstacle.y > 700) {
                iterator.remove();
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

    private void stopMusic() {
        if (gameMusic != null) {
            gameMusic.stop();
        }
        if (gameMusicThread != null && gameMusicThread.isAlive()) {
            gameMusicThread.interrupt();
        }
    }

    MusicPlayer buttonSound = new MusicPlayer("res/audio/Game-Over.wav", false);

    private void gameOver() {
        timer.stop();
        buttonSound.run();
        stopMusic();

        // Simpan skor ke database
        if (playerName != null && !playerName.isEmpty() && database != null) {
        database.insertScore(playerName, score); // Simpan skor pemain ke database
    }
    
        // Panel overlay utama untuk Game Over
        JPanel overlayPanel = new JPanel();
        overlayPanel.setLayout(null); // Posisi bebas
        overlayPanel.setBackground(new Color(0, 0, 0, 150)); 
        overlayPanel.setBounds(0, 0, getWidth(), getHeight());
    
        // Panel Game Over di tengah
        JPanel gameOverPanel = new JPanel();
        gameOverPanel.setLayout(null); 
        gameOverPanel.setBackground(new Color(120, 30, 30)); 
        int panelWidth = 400;
        int panelHeight = 300;
        int panelX = (getWidth() - panelWidth) / 2;
        int panelY = (getHeight() - panelHeight) / 2; 
        gameOverPanel.setBounds(panelX, panelY, panelWidth, panelHeight);
    
        // Label "Game Over"
        JLabel gameOverLabel = new JLabel("Game Over", SwingConstants.CENTER);
        gameOverLabel.setForeground(Color.WHITE);
        gameOverLabel.setFont(new Font("Impact", Font.BOLD, 30));
        gameOverLabel.setBounds(0, 30, panelWidth, 50); 
        gameOverPanel.add(gameOverLabel);
    
        // Label "Your Score"
        JLabel scoreLabel = new JLabel("Your Score: " + score, SwingConstants.CENTER);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Impact", Font.PLAIN, 20));
        scoreLabel.setBounds(0, 100, panelWidth, 30);
        gameOverPanel.add(scoreLabel);
    
        // Tombol "Back To Menu"
        JButton mainMenuButton = new JButton("Menu");
        mainMenuButton.setFont(new Font("Impact", Font.BOLD, 16));
        mainMenuButton.setForeground(Color.WHITE);
        mainMenuButton.setBackground(new Color(100, 0, 0)); // Merah gelap
        mainMenuButton.setFocusPainted(false);

        // Lebar dan tinggi tombol
        int buttonWidth = 130;
        int buttonHeight = 40;

        // Hitung posisi agar tombol berada di tengah horizontal dan pada posisi Y tertentu
        int xPosition = (panelWidth - buttonWidth) / 2; 
        int yPosition = 180; // Posisi Y tetap

        mainMenuButton.setBounds(xPosition, yPosition, buttonWidth, buttonHeight);
        mainMenuButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Tambahkan ActionListener
        mainMenuButton.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(this).dispose();
            new RacingCarGame();
        });
        gameOverPanel.add(mainMenuButton);
    
        // Tambahkan overlay dan panel ke frame utama
        overlayPanel.add(gameOverPanel);
        this.setLayout(null);
        this.add(overlayPanel);
        this.repaint();
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
