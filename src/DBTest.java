package dbcontroller;

import java.io.IOException;
import java.sql.SQLException;
import javax.sound.midi.InvalidMidiDataException;

/**
 * @since 1.0
 * @version 1.0
 * @author Chaoyi Han
 */
public class DBTest {
    public static void main(String[] args) throws SQLException, IOException, InvalidMidiDataException {
        DBController db = new DBController("storage");
        if(db.createProfile("Test4")) {
            System.out.println("Create successful");
        } else {
            System.out.println("Profile exists.");
        }
        System.out.println(db.getTable("Profile", new String[]{"pID", "=", "2"}).get(0).get("Name"));
        //db.createSample("Treble", 2, "2,1,4,1.0;4,0,4,1.0;5,0,4,1.0;5,1,4,1.0;7,0,4,3.0;7,0,4,1.0;1,0,5,1.5;7,0,4,0.5;5,1,4,1.0;1,0,5,1.0;7,0,4,1.5;5,1,4,0.5;5,0,4,1.0;4,0,4,0.5;2,1,4,0.5;4,0,4,2.0;2,1,4,2.0;", 0);
        System.out.println(db.getUpcoming("1,1,3,3.0;", 1, 2));
        System.out.println(db.getUpcoming("5,0,4,1.0;5,1,4,1.0;7,0,4,3.0;", 3, 2));
        //db.initDB(); //THIS WILL ERASE THE DATABASE
    }
}