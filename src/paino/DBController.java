package paino;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Database Controller. WON'T WORK without proper class path (sqlite-jdbc-3.16.1.jar) and the SQL script file.
 * @version 1.0
 * @since 1.0
 * @author Chaoyi Han
 */
public class DBController {
    private final Connection CONN;
    private final Statement STMT;
    private final HashMap<String, String[]> HEAD;
    
    /**
     * Constructor.
     * @param fn The file name of SQLite Database, suffix not included.
     * @throws SQLException Just throw it upwards
     */
    public DBController(String fn) throws SQLException {
        CONN = DriverManager.getConnection("jdbc:sqlite:" + fn + ".db");
        STMT = CONN.createStatement();
        STMT.setQueryTimeout(60);
        HEAD = new HashMap<>();
        HEAD.put("LeftHand", new String[]{"text", "Note", "text", "LeftHand", "integer", "Profile"});
        HEAD.put("Music", new String[]{"integer", "mID", "text", "Name", "text", "Date", "integer", "Profile", "text", "Notes", "text", "Mode"});
        HEAD.put("Profile", new String[]{"integer", "pID", "text", "Name", "text", "Date"});
        HEAD.put("Sample", new String[]{"integer", "sID", "text", "Name", "text", "Date", "integer", "Profile", "text", "Notes", "integer", "Source"});
    }
    
    /**
     * Database updater. Send all queries without results here.
     * @param queryString The query string.
     * @throws SQLException THROW
     */
    private void update(String queryString) throws SQLException {
        STMT.executeUpdate(queryString);
    }
    
    /**
     * Database querier. Send all queries with results here.
     * @param queryString The query string
     * @param types Return types and names. even positions hold types where odd positions hold name. They are paired respectively.
     * @return An ArrayList contains a list of HashMap. Values need to be casted before use.
     * @throws SQLException THROW
     */
    private ArrayList<HashMap<String, Object>> query(String queryString, String[] types) throws SQLException {
        ArrayList<HashMap<String, Object>> result = new ArrayList();
        ResultSet data = STMT.executeQuery(queryString);
        for(int i = 0; data.next(); i++) {
            HashMap<String, Object> row = new HashMap<>();
            for(int j = 0; j < types.length; j = j + 2) {
                switch(types[j]) {
                    case "text":
                        row.put(types[j + 1], data.getString(types[j + 1]));
                        break;
                    case "integer":
                        row.put(types[j + 1], data.getInt(types[j + 1]));
                        break;
                    case "real":
                        row.put(types[j + 1], data.getDouble(types[j + 1]));
                        break;
                    case "blob":
                        row.put(types[j + 1], data.getBlob(types[j + 1]));
                        break;
                }
            }
            result.add(i, row);
        }
        return result;
    }
    
    /**
     * SQL Script reader. Read SQL scripts in the directory 'sql'.
     * @param fn File name. the directory and suffix are not included.
     * @return The content of the file.
     * @throws IOException THROW
     */
    private String sqlReader(String fn) throws IOException {
        return fileReader("sql/" + fn + ".sql", true);
    }
    
    /**
    * File Reader
    * @param fn File name
    * @param seperate Seperate the line or not
    * @return The content of the file.
    * @throws IOException THROW
    */
    private String fileReader(String fn, boolean seperate) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fn));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        while(line != null) {
            sb.append(line);
            if(seperate) {
                sb.append(System.lineSeparator());
            }
            line = br.readLine();
        }
        return sb.toString();
    }
    
    /**
     * Initialise the DB. WARNING: it will erase the whole database contents.
     */
    public void initDB() {
        try {
            update(sqlReader("sample"));
        } catch (SQLException | IOException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Create a profile.
     * @param name The name of the profile.
     * @return True means success, false means already have the same name.
     */
    public boolean createProfile(String name) {
        try {
            ArrayList<HashMap<String, Object>> result = query("SELECT COUNT(*) AS \"Count\" FROM \"main\".\"Profile\" WHERE \"Name\" = '" + name + "'", new String[]{"integer", "Count"});
                if((int)result.get(0).get("Count") > 0) {
                return false;
            } else {
                update("INSERT INTO \"main\".\"Profile\" (\"Name\") VALUES ('" + name + "')");
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    /**
     * Create a sample.
     * @param name The name of the sample
     * @param profile The id of the profile
     * @param notes The notes set
     * @param source The source, 0 means from file while 1 means MIDI keyboard.
     * @return True means success, false means already have the same name.
     */
    public boolean createSample(String name, int profile, String notes, int source) {
        try {
            ArrayList<HashMap<String, Object>> result = query("SELECT COUNT(*) AS \"Count\" FROM \"main\".\"Sample\" WHERE \"Name\" = '" + name + "'", new String[]{"integer", "Count"});
            if((int)result.get(0).get("Count") > 0) {
                return false;
            } else {
                update("INSERT INTO \"main\".\"Sample\" (\"Name\", \"Profile\", \"Notes\", \"Source\") VALUES ('" + name + "', " + String.valueOf(profile) + ", '" + notes + "', " + String.valueOf(source) + ")");
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    /**
     * Store music.
     * @param name The name of the sample
     * @param profile The id of the profile
     * @param notes The notes set
     * @param mode The mode. Format: int,int,int means: source, previous looking count, next looking count respectively.
     * @return True means success, false means already have the same name.
     */
    public boolean storeMusic(String name, int profile, String notes, String mode) {
        try {
            ArrayList<HashMap<String, Object>> result = query("SELECT COUNT(*) AS \"Count\" FROM \"main\".\"Music\" WHERE \"Name\" = '" + name + "'", new String[]{"integer", "Count"});
            if((int)result.get(0).get("Count") > 0) {
                return false;
            } else {
                update("INSERT INTO \"main\".\"Music\" (\"Name\", \"Profile\", \"Notes\", \"Mode\") VALUES ('" + name + "', " + String.valueOf(profile) + ", '" + notes + "', '" + mode + "')");
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    /**
     * Create Left Hand.
     * @param note Main note
     * @param leftHand Left hand note which works together
     * @param profile The id of the profile
     * @return True means success, false means already have the same name.
     */
    public boolean createLeftHand(String note, String leftHand, int profile) {
        try {
            ArrayList<HashMap<String, Object>> result = query("SELECT COUNT(*) AS \"Count\" FROM \"main\".\"LeftHand\" WHERE \"Note\" = '" + note + "' AND \"LeftHand\" = '" + leftHand + "' AND \"Profile\" = " + String.valueOf(profile), new String[]{"integer", "Count"});
            if((int)result.get(0).get("Count") > 0) {
                return false;
            } else {
                update("INSERT INTO \"main\".\"LeftHand\" (\"Note\", \"LeftHand\", \"Profile\") VALUES ('" + note + "', '" + leftHand + "', " + String.valueOf(profile) + ")");
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    /**
     * Get the whole table. Used for the displaying.
     * @param name Name of the table
     * @param conditions SQL conditions. Format: s[0] column name(1), s[1] operator(1), s[2] value(1), s[3] column name(2), s[4] operator(2) ... Example for table Music: new String[]{"Name", "=", "'Test'", "Profile", "=", "3"}
     * @return The whole table
     */
    public ArrayList<HashMap<String, Object>> getTable(String name, String[] conditions) {
        try {
            StringBuilder sb = new StringBuilder();
            if(conditions != null) {
                sb.append(" WHERE");
                for(int i = 0; i < conditions.length; i = i + 3) {
                    sb.append(" \"").append(conditions[i]).append("\" ").append(conditions[i + 1]).append(" ").append(conditions[i + 2]);
                }
            }
            return query("SELECT * FROM \"main\".\"" + name + "\"" + sb.toString(), HEAD.get(name));
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<>();
    }
    
    /**
     * Get left hand note.
     * @param note The note given
     * @param profile The profile
     * @return The note
     */
    public String[] getLeftHand(String note, int profile) {
        try {
            ArrayList<HashMap<String, Object>> result = query("SELECT \"LeftHand\" FROM \"main\".\"LeftHand\" WHERE \"Note\" = '" + note + "' AND \"Profile\" = " + String.valueOf(profile), new String[]{"text", "LeftHand"});
            String[] rtn = new String[result.size()];
            for(int i = 0; i < result.size(); i++) {
                rtn[i] = (String)result.get(i).get("LeftHand");
            }
            return rtn;
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new String[0];
    }
    
    /**
     * Get the upcoming notes by the notes given.
     * @param notes The notes given. Format: Notes divided by ;. There must be a ; at the end. Exmaple: 1,1,3,3.0;2,5,2,1.0;
     * @param count The count of notes looking after the notes given.
     * @param profile The profile which want to look at
     * @return The notes set which has the most time appear.
     */
    public String getUpcoming(String notes, int count, int profile) {
        try {
            String post = new String(new char[count]).replace("\0", "_,_,_,_._;");
            HashMap<String, Integer> stats = new HashMap<>();
            ArrayList<HashMap<String, Object>> result = query("SELECT \"Notes\" FROM \"main\".\"Sample\" WHERE \"Notes\" LIKE '%" + notes + post + "%' AND \"Profile\" = " + profile, new String[]{"text", "Notes"});
            for(int i = 0; i < result.size(); i++) {
                String item = (String)result.get(i).get("Notes");
                for (int j = -1; (j = item.indexOf(notes, j + 1)) != -1; ) {
                    if(j + notes.length() + 10 * count < item.length() + 1) {
                        String find = item.substring(j + notes.length(), j + notes.length() + 10 * count);
                        if(stats.containsKey(find)) {
                            stats.put(find, stats.get(find) + 1);
                        } else {
                            stats.put(find, 1);
                        }
                    }
                }
            }
            HashMap.Entry<String, Integer> maxEntry = null;
            for (HashMap.Entry<String, Integer> entry : stats.entrySet()) {
                if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                    maxEntry = entry;
                }
            }
            if(maxEntry == null) {
                return "";
            } else {
                return maxEntry.getKey();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    /**
     * Insert a sample file to the database.
     * @param fn The file name
     * @param name The name of the sample
     * @param profile The id of the profile
     * @param notes The notes set
     * @param source The source, 0 means from file while 1 means MIDI keyboard.
     */
    public void sampleReader(String fn, String name, int profile, String notes, int source) {
        try {
            createSample(name, profile, fileReader(fn, false), source);
        } catch (IOException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Renamer for database.
     * @param table Table which contains the item
     * @param name New name
     * @param id ID of the name
     */
    public void renamer(String table, String name, int id) {
        try {
            update("UPDATE \"main\".\"" + table + "\" SET \"Name\" = '" + name + "' WHERE \"" + table.substring(0, 1).toLowerCase() + "ID\" = " + String.valueOf(id));
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Get the most frequent start note.
     * @param profile Which profile we are seeking
     * @return the note
     */
    public String getStarter(int profile) {
        try {
            HashMap<String, Integer> stats = new HashMap<>();
            ArrayList<HashMap<String, Object>> result = query("SELECT \"Notes\" FROM \"main\".\"Sample\" WHERE \"Profile\" = " + profile, new String[]{"text", "Notes"});
            for(int i = 0; i < result.size(); i++) {
                String item = (String)result.get(i).get("Notes");
                String find = item.substring(0, 10);
                if(stats.containsKey(find)) {
                    stats.put(find, stats.get(find) + 1);
                } else {
                    stats.put(find, 1);
                }
            }
            HashMap.Entry<String, Integer> maxEntry = null;
            for (HashMap.Entry<String, Integer> entry : stats.entrySet()) {
                if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                    maxEntry = entry;
                }
            }
            if(maxEntry == null) {
                return "";
            } else {
                return maxEntry.getKey();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    /**
     * Remove line from the database
     * @param table Table name
     * @param id Table ID
     */
    public void remove(String table, int id) {
        try {
            if("Profile".equals(table)) {
                    update("DELETE FROM \"main\".\"Sample\" WHERE \"Profile\" = " + String.valueOf(id));
                    update("DELETE FROM \"main\".\"Music\" WHERE \"Profile\" = " + String.valueOf(id));
                    update("DELETE FROM \"main\".\"LeftHand\" WHERE \"Profile\" = " + String.valueOf(id));
            }
            update("DELETE FROM \"main\".\"" + table + "\" WHERE \"" + table.substring(0, 1).toLowerCase() + "ID\" = " + String.valueOf(id));
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Remove the left hand
     * @param leftHand Left hand note given
     * @param note Note work with
     * @param profile the profile
     */
    public void removeLeftHand(String leftHand, String note, int profile) {
        try {
            update("DELETE FROM \"main\".\"LeftHand\" WHERE \"Profile\" = " + String.valueOf(profile) + " AND \"LeftHand\" = '" + leftHand + "' AND \"Note\" = '" + note + "'");
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}