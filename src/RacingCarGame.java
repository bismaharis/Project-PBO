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
      menuMusic = new MusicPlayer("res/audio/Menu.wav", true);
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
    mainMenu.setSize(600, 600);
    mainMenu.setLayout(new BorderLayout());
    Font font = new Font("Arial", Font.BOLD, 35);

    // Panel dengan gambar latar
    JPanel wrapper = new BackgroundPanel("res/images/startBg.png");
    wrapper.setPreferredSize(new Dimension(600, 600));
    wrapper.setLayout(new BorderLayout());

    JPanel wrap = new JPanel();
    wrap.setLayout(new BoxLayout(wrap, BoxLayout.Y_AXIS));
    wrap.setOpaque(false); // Agar transparan dan tidak menutupi background

    // Tambahkan lebih banyak spasi di atas tombol
    wrap.add(Box.createVerticalGlue());  // Vertical glue untuk menyeimbangkan posisi tombol
    wrap.add(Box.createVerticalStrut(150));  // Tambahkan jarak besar dari atas (sekitar 150px)

    // Tombol Play
    JButton playButton = new JButton("PLAY");
    playButton.setBorder(BorderFactory.createEmptyBorder(10, 250, 10, 0));
    playButton.setContentAreaFilled(false);
    playButton.setFont(font);
    playButton.setForeground(Color.WHITE);

    // Tombol Score
    JButton scoreButton = new JButton("SCORE");
    scoreButton.setBorder(BorderFactory.createEmptyBorder(10, 230, 10, 0));
    scoreButton.setContentAreaFilled(false);
    scoreButton.setFont(font);
    scoreButton.setForeground(Color.white);

    // Tombol Tutorial
    JButton tutorialButton = new JButton("TUTORIAL");
    tutorialButton.setBorder(BorderFactory.createEmptyBorder(10, 205, 10, 0));
    tutorialButton.setContentAreaFilled(false);
    tutorialButton.setFont(font);
    tutorialButton.setForeground(Color.white);

    // Tombol Exit
    JButton exitButton = new JButton("EXIT");
    exitButton.setBorder(BorderFactory.createEmptyBorder(10, 250, 10, 0));
    exitButton.setContentAreaFilled(false);
    exitButton.setFont(font);
    exitButton.setForeground(Color.WHITE);

    // Menambahkan tombol ke dalam panel
    wrap.add(playButton);
    wrap.add(scoreButton);
    wrap.add(tutorialButton);
    wrap.add(exitButton);
    wrap.add(Box.createVerticalGlue()); // Agar tombol tetap terpusat

    wrapper.add(wrap, BorderLayout.CENTER);
    mainMenu.add(wrapper, BorderLayout.CENTER);

    mainMenu.setLocationRelativeTo(null);
    mainMenu.setVisible(true);

    // Tambahkan efek suara beep saat tombol ditekan
    MusicPlayer buttonSound = new MusicPlayer("res/audio/Click.wav", false);

    playButton.addActionListener(e -> {
          buttonSound.run();
          // Hentikan musik menu dan mulai game
          stopMusic();
          getPlayerName();
      });

    scoreButton.addActionListener(e -> {
          buttonSound.run();
          showScores();
      });
      
    tutorialButton.addActionListener(e -> {
          buttonSound.run();
          showTutorial();
      });
    
    exitButton.addActionListener(e -> {
          buttonSound.run();
          System.exit(0);
      });

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
            new GameWindow(playerName, database); // Teruskan database dan playerName
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

  private void showTutorial() {
    JFrame tutorialFrame = new JFrame("Tutorial");
    tutorialFrame.setSize(600, 600);

    // Panel utama dengan latar belakang gambar
    JPanel panelTutorial = new BackgroundPanel("res/images/howToPlay.png"); // Menggunakan BackgroundPanel
    panelTutorial.setLayout(new BoxLayout(panelTutorial, BoxLayout.Y_AXIS)); // Menggunakan BoxLayout vertikal

    // Panel untuk judul tutorial
    JPanel titlePanel = new JPanel();
    titlePanel.setOpaque(false); // Agar background tidak menutupi
    JLabel labelTutorial = new JLabel("", SwingConstants.CENTER);
    labelTutorial.setForeground(Color.WHITE);
    labelTutorial.setFont(new Font("Impact", Font.BOLD, 20)); // Mengatur font
    labelTutorial.setBorder(new EmptyBorder(20, 0, 20, 0)); // Memberikan ruang di atas dan bawah
    titlePanel.add(labelTutorial);

    // Menambahkan panel judul ke panel utama
    panelTutorial.add(titlePanel);

    // Data tutorial
    String[] columnNames = {"STEP FOR WIN THE GAME"};
    Object[][] tutorialData = {
        {"1. Teken tombol panah kiri dan kanan untuk arahin mobil"},
        {"2. Hindari rintangan dan kumpulkan sebanyak mungkin poin."},
        {"3. Game akan berhenti jika tabrakan dengan mobil musuh"},
        {"4. Semoga berhasil!"}
    };

    // Membuat tabel untuk tutorial
    JTable tutorialTable = new JTable(tutorialData, columnNames);
    tutorialTable.setForeground(Color.WHITE);
    tutorialTable.setBackground(new Color(0, 0, 0, 150)); 
    tutorialTable.setFont(new Font("Impact", Font.PLAIN, 16)); 
    tutorialTable.setRowHeight(40); 
    tutorialTable.setEnabled(false);
    tutorialTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); 

    // Menambahkan tabel ke dalam JScrollPane
    JScrollPane scrollPane = new JScrollPane(tutorialTable);
    scrollPane.setOpaque(false); 
    scrollPane.getViewport().setOpaque(false);
    scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
    scrollPane.setPreferredSize(new Dimension(460, 400));

    // Menambahkan JScrollPane ke panel utama
    panelTutorial.add(scrollPane);

    // Panel untuk tombol "Back"
    JPanel buttonPanel = new JPanel();
    buttonPanel.setOpaque(false);
    buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT); 
    JButton backButton = new JButton("Kembali");
    backButton.setFont(new Font("Impact", Font.BOLD, 18));
    backButton.setForeground(Color.BLACK);
    backButton.setBackground(Color.WHITE); 
    backButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); 
    backButton.setFocusPainted(false); 
    backButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
    backButton.addActionListener(e -> {
        tutorialFrame.dispose(); 
        createMainMenu();
    });

    // Menambahkan tombol ke dalam panel bawah
    buttonPanel.add(backButton);
    panelTutorial.add(buttonPanel);

    // Menambahkan panel tutorial ke dalam frame
    tutorialFrame.add(panelTutorial);
    tutorialFrame.setLocationRelativeTo(null); 
    tutorialFrame.setVisible(true);
}



private void showScores() {
    JFrame scoreFrame = new JFrame("Scores");
    scoreFrame.setSize(600, 600);

    // Panel utama dengan latar belakang gambar
    JPanel panelScore = new BackgroundPanel("res/images/startBg.png");
    panelScore.setLayout(new BoxLayout(panelScore, BoxLayout.Y_AXIS)); 
    panelScore.add(Box.createVerticalStrut(50)); 

    // Panel untuk judul (text "Score")
    JPanel titlePanel = new JPanel();
    titlePanel.setOpaque(false); 
    JLabel labelScore = new JLabel("", SwingConstants.CENTER);
    labelScore.setForeground(Color.WHITE);
    labelScore.setFont(new Font("Impact", Font.BOLD, 20)); 
    labelScore.setBorder(new EmptyBorder(20, 0, 20, 0)); 
    titlePanel.add(labelScore);

    // Menambahkan panel judul ke panel utama
    panelScore.add(titlePanel);

    // Data skor
    List<String> scores = database.getScores(); // Ambil data nama dan skor dari database
    Object[][] scoreData = new Object[scores.size()][2]; // Matriks data untuk tabel

    // Memasukkan data nama dan skor ke dalam matriks
    for (int i = 0; i < scores.size(); i++) {
        String[] scoreParts = scores.get(i).split(":"); // Pisahkan nama dan skor
        scoreData[i][0] = scoreParts[0]; // Nama Pemain
        scoreData[i][1] = scoreParts[1]; // Skor Pemain
    }

    // Kolom tabel
    String[] columnNames = {"NAMA PEMAIN", "SKOR"};

    // Membuat tabel untuk skor
    JTable scoreTable = new JTable(scoreData, columnNames);
    scoreTable.setForeground(Color.WHITE);
    scoreTable.setBackground(new Color(0, 0, 0, 150)); 
    scoreTable.setFont(new Font("Impact", Font.PLAIN, 16)); 
    scoreTable.setRowHeight(30); 
    scoreTable.setEnabled(false);
    scoreTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); 
    scoreTable.setSelectionBackground(new Color(0, 102, 204)); 

    // Menambahkan tabel ke dalam JScrollPane
    JScrollPane scrollPane = new JScrollPane(scoreTable);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER); 
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
    scrollPane.setOpaque(false);  // Membuat scrollpane transparan
    scrollPane.getViewport().setOpaque(false);
    scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // Menambahkan JScrollPane ke panel utama
    panelScore.add(scrollPane);

    // Tambahkan spasi vertikal di bawah tabel
    panelScore.add(Box.createVerticalStrut(20));

    // Panel untuk tombol "Back"
    JPanel buttonPanel = new JPanel();
    buttonPanel.setOpaque(false);
    buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT); 
    JButton backButton = new JButton("Kembali");
    backButton.setFont(new Font("Impact", Font.BOLD, 18));
    backButton.setForeground(Color.BLACK);
    backButton.setBackground(Color.WHITE); 
    backButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); 
    backButton.setFocusPainted(false); 
    backButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
    backButton.addActionListener(e -> {
        scoreFrame.dispose(); 
        createMainMenu();
    });

    // Menambahkan tombol ke dalam panel bawah
    buttonPanel.add(backButton);
    panelScore.add(buttonPanel);

    // Menambahkan panel skor ke dalam frame
    scoreFrame.add(panelScore);
    scoreFrame.setLocationRelativeTo(null); // Posisi di tengah layar
    scoreFrame.setVisible(true);
}
}