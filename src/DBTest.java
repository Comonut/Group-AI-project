package dbcontroller;


import java.io.IOException;
import java.sql.SQLException;

/**
 * Database Controller Tester. WON'T WORK without correct paths
 * @version 1.0
 * @since 1.0
 * @author Chaoyi Han
 */
public class DBTest {
    public static void main(String[] args) throws SQLException, IOException {
        DBController db = new DBController("storage");
        if(db.createProfile("Test4")) {
            System.out.println("sss");
        } else {
            System.out.println("lll");
        }
        System.out.println(db.getTable("Profile", new String[]{"pID", "=", "2"}).get(0).get("Name"));
        System.out.println(db.getUpcoming("1,1,3,3.0;", 1));
        //db.initDB(); //THIS WILL ERASE THE DATABASE
    }
}
