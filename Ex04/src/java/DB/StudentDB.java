package DB;

import Model.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentDB {

	private Connection cn;

	public StudentDB(Connection cn) {
		this.cn = cn;
	}
	public StudentDB() {
	}

	public Student getStudent(String st_id) throws ClassNotFoundException, SQLException {
		Class.forName("org.apache.derby.jdbc.ClientDriver");
		String urlCn = "jdbc:derby://localhost:1527/LibraryDB";
		Connection cn = DriverManager.getConnection(urlCn, "administrator", "123456");

		Student st = null;
		//PreparedStatement ps = null;
		Statement ps = null;
		try {
			//ps = this.cn.prepareStatement("SELECT * FROM students WHERE st_id = ?");
			//ps.setString(1, st_id);
			ps = cn.createStatement();

			

			ResultSet rs = ps.executeQuery("SELECT * FROM students WHERE st_id=\'"+st_id+"\'");
			while (rs.next()) {
				st = new Student();
				st.setStudentID(rs.getString("st_id"));
				st.setFirstName(rs.getString("f_name"));
				st.setLastName(rs.getString("l_name"));
				st.setEmailAddress(rs.getString("email"));
				st.setCurrentFines(Double.parseDouble(rs.getString("fine")));
			}
			cn.close();
		} catch (SQLException e) {
			// TODO
			// Write an error
		}
		return st;
	}
	
//	public Boolean addStudent() throws ClassNotFoundException, SQLException {
//		Boolean added = false;
//		Class.forName("org.apache.derby.jdbc.ClientDriver");
//		String urlCn = "jdbc:derby://localhost:1527/LibraryDB";
//		Connection cn = DriverManager.getConnection(urlCn, "administrator", "123456");
//
//		Student st = null;
//		//PreparedStatement ps = null;
//		Statement ps = null;
//		try {
//			//ps = this.cn.prepareStatement("SELECT * FROM students WHERE st_id = ?");
//			//ps.setString(1, st_id);
//			ps = cn.createStatement();
//			int nor = ps.executeUpdate("insert");
//			
//			
//			//ResultSet rs = ps.executeQuery("SELECT * FROM students WHERE st_id=\'"+st_id+"\'");
//			ResultSet rs = ps.executeUpdate("SELECT * FROM students WHERE st_id=\'"+st_id+"\'");
//			while (rs.next()) {
//				st = new Student();
//				st.setStudentID(rs.getString("st_id"));
//				st.setFirstName(rs.getString("f_name"));
//				st.setLastName(rs.getString("l_name"));
//				st.setEmailAddress(rs.getString("email"));
//				st.setCurrentFines(Double.parseDouble(rs.getString("fine")));
//			}
//			cn.close();
//		} catch (SQLException e) {
//			// TODO
//			// Write an error
//		}
//		return added;
//	}

}
