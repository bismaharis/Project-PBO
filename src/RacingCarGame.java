import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
public class RacingCarGame {
  private JFrame mainMenu;
  private Database database;
  private Thread musicThread;
  private MusicPlayer menuMusic; // Referensi ke MusicPlaye

  public RacingCarGame() {
      database = new Database();

      // Mulai musik latar untuk menu utama
      menuMusic = new MusicPlayer("res/songs/Pano.wav", true);
      musicThread = new Thread(menuMusic);
      musicThread.start();

      createMainMenu();

  }

  // Berhenti musik jika perlu
  private void stopMusic() {
      if (menuMusic != null) {
          menuMusic.stop();
      }
      if (musicThread != null && musicThread.isAlive()) {
          musicThread.interrupt();
      }
  }

  public void createMainMenu() {
      mainMenu = new JFrame("Racing Car Game");
      mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      mainMenu.setSize(500, 700);
      mainMenu.setLayout(new BorderLayout());
      Font font = new Font("Arial", Font.BOLD, 40);

      // Panel dengan gambar latar
      JPanel wrapper = new BackgroundPanel("res/image/bg.jpg");
      wrapper.setPreferredSize(new Dimension(500, 700));
      wrapper.setLayout(new BorderLayout());

      JPanel wrap = new JPanel();
      wrap.setLayout(new BoxLayout(wrap, BoxLayout.Y_AXIS));
      wrap.setOpaque(false); // Agar transparan dan tidak menutupi background

      JButton playButton = new JButton(">>   Play");
      playButton.setBorder(BorderFactory.createEmptyBorder(10, 150, 10, 0));
      playButton.setContentAreaFilled(false);
      playButton.setFont(font);
      playButton.setForeground(Color.WHITE);

      JButton scoreButton = new JButton(">>   Score");
      scoreButton.setBorder(BorderFactory.createEmptyBorder(10, 150, 10, 0));
      scoreButton.setContentAreaFilled(false);
      scoreButton.setFont(font);
      scoreButton.setForeground(Color.white);

      JButton exitButton = new JButton(">>   Exit");
      exitButton.setBorder(BorderFactory.createEmptyBorder(10, 150, 10, 0));
      exitButton.setContentAreaFilled(false);
      exitButton.setFont(font);
      exitButton.setForeground(Color.WHITE);

      wrap.add(Box.createVerticalGlue());
      wrap.add(playButton);
      wrap.add(scoreButton);
      wrap.add(exitButton);
      wrap.add(Box.createVerticalGlue());

      
      playButton.addActionListener(e -> {
          // Hentikan musik menu dan mulai game
          stopMusic();
          getPlayerName();
      });

      scoreButton.addActionListener(e -> {
          // mainMenu.dispose();
          showScores();
      });

      exitButton.addActionListener(e -> System.exit(0));

      wrapper.add(wrap, BorderLayout.CENTER);
      mainMenu.add(wrapper, BorderLayout.CENTER);

      mainMenu.setLocationRelativeTo(null);
      mainMenu.setVisible(true);
  }

  private void getPlayerName() {
      JFrame nameFrame = new JFrame("Enter Name");
      nameFrame.setSize(400, 200);

      JPanel wrapPlay = new JPanel();
      wrapPlay.setLayout(new GridLayout(3, 1));
      wrapPlay.setBorder(new EmptyBorder(10,20,10,20));
      


      JTextField nameField = new JTextField();
      nameField.setBorder(new EmptyBorder(0,20,0,20));

      JPanel button = new JPanel();
      JButton startButton = new JButton("Start Game");
      startButton.setPreferredSize(new Dimension(100,40));
      button.add(startButton);

      startButton.addActionListener(e -> {
          String playerName = nameField.getText().trim();
          if (!playerName.isEmpty()) {
              nameFrame.dispose();
              new GameWindow(playerName, database);
          } else {
              JOptionPane.showMessageDialog(nameFrame, "Please enter a valid name.");
          }
      });

      wrapPlay.add(new JLabel("Enter your name:"));
      wrapPlay.add(nameField);
      wrapPlay.add(button);
      nameFrame.add(wrapPlay);

      nameFrame.setLocationRelativeTo(null);
      nameFrame.setVisible(true);
  }

  private void showScores() {
      JFrame scoreFrame = new JFrame("Scores");
      scoreFrame.setSize(500, 700);
      
      scoreFrame.add(new BackgroundPanel("res/image/bg.jpg"));
      JPanel panelScore = new BackgroundPanel("res/image/1.jpg");
      panelScore.setLayout(new BorderLayout());
      JLabel labelScore = new JLabel("Score", SwingConstants.CENTER);
      labelScore.setForeground(Color.WHITE);
      labelScore.setFont(new Font("Arial", Font.BOLD, 24)); // Opsional: mengatur font
      labelScore.setBorder(new EmptyBorder(20,0,20,0));
      panelScore.add(labelScore, BorderLayout.NORTH);
      

 // Panel untuk menampilkan skor
      JPanel scoreListPanel = new JPanel();
      scoreListPanel.setLayout(new BoxLayout(scoreListPanel, BoxLayout.Y_AXIS));
      scoreListPanel.setOpaque(false); // Transparan untuk mendukung latar belakang
      scoreListPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
      scoreListPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

      // Data skor
      List<String> scores = database.getScores();

      // Menambahkan setiap skor sebagai JLabel ke panel
      for (String score : scores) {
          JLabel scoreLabel = new JLabel(score);
          scoreLabel.setForeground(Color.WHITE); // Warna teks
          scoreLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Font untuk teks
          scoreLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
          scoreLabel.setBorder(new EmptyBorder(0,20,0,0));
          // scoreLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT); // Teks di tengah
          scoreListPanel.add(scoreLabel);
          
      }

      // Tambahkan panel ke JScrollPane untuk mendukung pengguliran jika skor panjang
      JScrollPane scrollPane = new JScrollPane(scoreListPanel);
      scrollPane.getViewport().setOpaque(false);
      scrollPane.setOpaque(false);
      scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

      // Tambahkan JScrollPane ke panel utama
      panelScore.add(scrollPane, BorderLayout.CENTER);


      JButton backButton = new JButton("Back");
      backButton.addActionListener(e -> {
          scoreFrame.dispose();
          createMainMenu();
      });
      scoreFrame.add(panelScore);
      panelScore.add(backButton, BorderLayout.SOUTH);
      

      scoreFrame.setLocationRelativeTo(null);
      scoreFrame.setVisible(true);
  }

}