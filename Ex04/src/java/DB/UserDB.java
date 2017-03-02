package DB;

import Model.*;
import Util.DButil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Kotya
 */
public class UserDB {

	private Connection cn;

	public UserDB() {
		cn = DButil.getConnection();
	}

	public void closeConnection() {
		try {
			cn.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}
	public User getUser(String userID) throws ClassNotFoundException, SQLException {
		Class.forName("org.apache.derby.jdbc.ClientDriver");
		String urlCn = "jdbc:derby:C:\\Users\\Kotya\\Desktop\\College\\Year 3\\Semester A\\BasicWeb\\HW\\Ex04\\LibraryDB";
		Connection cn = DriverManager.getConnection(urlCn, "administrator", "123456");

		User us = null;
		//PreparedStatement ps = null;
		Statement ps = null;
		try {
			ps = cn.createStatement();
			ResultSet rs = ps.executeQuery("SELECT * FROM accounts WHERE acc_id=\'"+userID+"\'");
			while (rs.next()) {
				us = new User();
				us.setUserID(rs.getString("acc_id"));
				us.setUserPas(rs.getString("acc_pass"));
				us.setUserType(rs.getString("acc_type"));	
			}
			cn.close();
		} catch (SQLException e) {
			// TODO
			// Write an error
		}
		return us;
	}
}
