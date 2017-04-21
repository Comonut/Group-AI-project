package dbcontroller;

/**
 *
 * @author Frank
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DBController {
    private final Connection CONN;
    private final Statement STMT;
    private final HashMap<String, String[]> HEAD;
    
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
    
    private void update(String queryString) throws SQLException {
        STMT.executeUpdate(queryString);
    }
    
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
    
    private String sqlReader(String fn) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("sql/" + fn + ".sql"));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        while(line != null) {
            sb.append(line).append(System.lineSeparator());
            line = br.readLine();
        }
        return sb.toString();
    }
    
    public void initDB() throws IOException, SQLException {
        update(sqlReader("init"));
    }
    
    public boolean createProfile(String name) throws SQLException {
        ArrayList<HashMap<String, Object>> result = query("SELECT COUNT(*) AS \"Count\" FROM \"main\".\"Profile\" WHERE \"Name\" = '" + name + "'", new String[]{"integer", "Count"});
        if((int)result.get(0).get("Count") > 0) {
            return false;
        } else {
            update("INSERT INTO \"main\".\"Profile\" (\"Name\") VALUES ('" + name + "')");
            return true;
        }
    }
    
    public boolean createSample(String name, int profile, String notes, int source) throws SQLException {
        ArrayList<HashMap<String, Object>> result = query("SELECT COUNT(*) AS \"Count\" FROM \"main\".\"Sample\" WHERE \"Name\" = '" + name + "'", new String[]{"integer", "Count"});
        if((int)result.get(0).get("Count") > 0) {
            return false;
        } else {
            update("INSERT INTO \"main\".\"Sample\" (\"Name\", \"Profile\", \"Notes\", \"Source\") VALUES ('" + name + "', " + profile + ", '" + notes + "', " + source + ")");
            return true;
        }
    }
    
    public boolean storeMusic(String name, int profile, String notes, String mode) throws SQLException {
        ArrayList<HashMap<String, Object>> result = query("SELECT COUNT(*) AS \"Count\" FROM \"main\".\"Music\" WHERE \"Name\" = '" + name + "'", new String[]{"integer", "Count"});
        if((int)result.get(0).get("Count") > 0) {
            return false;
        } else {
            update("INSERT INTO \"main\".\"Music\" (\"Name\", \"Profile\", \"Mode\", \"Source\") VALUES ('" + name + "', " + profile + ", '" + notes + "', '" + mode + "')");
            return true;
        }
    }
    
    public boolean createLeftHand(String note, String leftHand, int profile) throws SQLException {
        ArrayList<HashMap<String, Object>> result = query("SELECT COUNT(*) AS \"Count\" FROM \"main\".\"LeftHand\" WHERE \"Note\" = '" + note + "' AND \"LeftHand\" = '" + leftHand + "' AND \"Profile\" = " + profile, new String[]{"integer", "Count"});
        if((int)result.get(0).get("Count") > 0) {
            return false;
        } else {
            update("INSERT INTO \"main\".\"LeftHand\" (\"Note\", \"LeftHand\", \"Profile\") VALUES ('" + note + "', '" + leftHand + "', " + profile + ")");
            return true;
        }
    }
    
    public ArrayList<HashMap<String, Object>> getTable(String name, String[] conditions) throws SQLException {
        StringBuilder sb = new StringBuilder();
        if(conditions != null) {
            sb.append(" WHERE");
            for(int i = 0; i < conditions.length; i = i + 3) {
                sb.append(" \"").append(conditions[i]).append("\" ").append(conditions[i + 1]).append(" ").append(conditions[i + 2]);
            }
        }
        return query("SELECT * FROM \"main\".\"" + name + "\"" + sb.toString(), HEAD.get(name));
    }
    
    public String[] getLeftHand(String note, int profile) throws SQLException {
        ArrayList<HashMap<String, Object>> result = query("SELECT \"LeftHand\" FROM \"main\".\"LeftHand\" WHERE \"Note\" = '" + note + "' AND \"Profile\" = " + profile, new String[]{"text", "LeftHand"});
        String[] rtn = new String[result.size()];
        for(int i = 0; i < result.size(); i++) {
            rtn[i] = (String)result.get(i).get("LeftHand");
        }
        return rtn;
    }
    
    public String getUpcoming(String notes, int count) throws SQLException {
        String post = new String(new char[count]).replace("\0", "_,_,_,_._;");
        HashMap<String, Integer> stats = new HashMap<>();
        ArrayList<HashMap<String, Object>> result = query("SELECT \"Notes\" FROM \"main\".\"Sample\" WHERE \"Notes\" LIKE '" + notes + post + "'", new String[]{"text", "Notes"});
        for(int i = 0; i < result.size(); i++) {
            String item = (String)result.get(i).get("Notes");
            for (int j = -1; (j = item.indexOf(notes, j + 1)) != -1; ) {
                String find = item.substring(j + 10, j + 10 * (count + 1));
                if(stats.containsKey(find)) {
                    stats.put(find, stats.get(find) + 1);
                } else {
                    stats.put(find, 1);
                }
            }
        }
        HashMap.Entry<String, Integer> maxEntry = null;
        for (HashMap.Entry<String, Integer> entry : stats.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }
        return maxEntry.getKey();
    }
}