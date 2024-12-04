import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
// import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Database {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/racingcar";
    private static final String USER = "root";
    private static final String PASS = "";
    private Connection conn;

    public Database() {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected succesfully");
            // JFrame frame = new JFrame();
            // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // frame.setSize(400,700);
            // frame.setVisible(true);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Failed to connect to the database. Please check your connection.", "Database Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1); // Keluar dari aplikasi jika koneksi gagal
        }
    }
    public void closeConnection(){
        try{
            if(conn != null && !conn.isClosed()){
                conn.close();
                System.out.println("connection closed");
            }
        }catch(SQLException e){
            System.out.println("error while connection: ");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Membuat instance database
        Database db = new Database();
        db.closeConnection();
    }
}
