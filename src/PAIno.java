
import java.sql.SQLException;
import javax.swing.SwingUtilities;

public class PAIno {
    public static DBController db;
    public static void main(String[] args) throws SQLException {
        db = new DBController("storage");
        //db.initDB();
        SwingUtilities.invokeLater(() -> {
            VirtualPiano frame;
            frame = new VirtualPiano();
            frame.setVisible(true);
        });
    }

}
