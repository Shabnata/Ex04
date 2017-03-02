
package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DButil{

	public static Connection getConnection(){
		Connection connection = null;

		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
			String urlCn = "jdbc:derby://localhost:1527/LibraryDB";
			connection = DriverManager.getConnection(urlCn, "root", "root");
		} catch(ClassNotFoundException | SQLException ex){
			Logger.getLogger(DButil.class.getName()).log(Level.SEVERE, null, ex);
		}

		return connection;
	}

}

//
//package Util;
//
//import java.sql.*;
//
//public class DButil {
//    private static Connection connection = null;
//    public static Connection getConnection() {
//
//        if (connection != null)
//
//            return connection;
//        else {
//
//            try {
//                Class.forName("org.apache.derby.jdbc.ClientDriver");
//                String urlCn ="jdbc:derby://localhost:1527/LibraryDB";
//                connection = DriverManager.getConnection(urlCn, "root", "root");
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return connection;
//        }
//    }
//}
