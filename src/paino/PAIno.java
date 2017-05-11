package paino;

import java.sql.SQLException;
import javax.swing.SwingUtilities;

public class PAIno {
    public static DBController db;
    public static void main(String[] args) throws SQLException {
        db = new DBController("storage");
        //db.initDB();
        SwingUtilities.invokeLater(() -> {
            MidiHandler frame = new MidiHandler();
            frame.setVisible(true);
        });
    }

}
