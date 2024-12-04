import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
public class RacingCarGame {
  private JFrame mainMenu;
  private Database database;

  public RacingCarGame() {
      database = new Database();

      createMainMenu();

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

      exitButton.addActionListener(e -> System.exit(0));

      wrapper.add(wrap, BorderLayout.CENTER);
      mainMenu.add(wrapper, BorderLayout.CENTER);

      mainMenu.setLocationRelativeTo(null);
      mainMenu.setVisible(true);
  }
}
