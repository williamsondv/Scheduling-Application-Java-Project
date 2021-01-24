/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.TimeZone;

/**
 *
 * @author willi
 */
public class DBConnection {
    
    private static final String databaseName = "U05inD";
    private static final String DB_URL = "jdbc:mysql://wgudb.ucertify.com/" + databaseName+ "?allowMultiQueries=true";
    private static final String username = "U05inD";
    private static final String password = "53688514896";
    private static final String driver = "com.mysql.jdbc.Driver";
    public static Connection conn;
    
    public static void makeConnection() throws ClassNotFoundException, SQLException, Exception 
    {
    Class.forName(driver);
    conn = (Connection) DriverManager.getConnection(DB_URL, username, password);
    }
    
    public static void closeConnection() throws SQLException, Exception 
    {
    conn.close(); 
    }
    
    public static String getSystemTimeZone() 
    {
      
        String timeZone = Calendar.getInstance().getTimeZone().getID();
        return timeZone;
      
    }
    
    //52.206.157.109
    
}
