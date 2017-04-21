package dbcontroller;


import java.io.IOException;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrator
 */
public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        DBController db = new DBController("storage");
        if(db.createProfile("Test4")) {
            System.out.println("sss");
        } else {
            System.out.println("lll");
        }
        System.out.println(db.getTable("Profile", new String[]{"pID", "=", "2"}).get(0).get("Name"));
        System.out.println(db.getUpcoming("1,1,3,3.0;", 1));
        //db.initDB();
    }
}
