
package Util;

import java.sql.*;

public class DButil {
    private static Connection connection = null;
    public static Connection getConnection() {  
  
        if (connection != null)  
  
            return connection;  
        else {  
  
            try {  
                Class.forName("org.apache.derby.jdbc.ClientDriver");  
                String urlCn ="jdbc:derby://localhost:1527/LibraryDB";
                connection = DriverManager.getConnection(urlCn, "root", "root");  
  
            } catch (Exception e) {  
                e.printStackTrace();  
            } 
            return connection;  
        }  
    }  
}
