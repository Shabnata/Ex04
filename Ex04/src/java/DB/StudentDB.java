package DB;

import Model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDB {

	private Connection cn;

	public StudentDB(Connection cn) {
		this.cn = cn;
	}

	public Student getStudent(String st_id) {

		Student st = null;
		PreparedStatement ps = null;
		try {
			ps = this.cn.prepareStatement("SELECT * FROM students WHERE st_id = ?");
			ps.setString(1, st_id);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				st = new Student();
				st.setStudentID(rs.getString("st_id"));
				st.setFirstName(rs.getString("f_name"));
				st.setLastName(rs.getString("l_name"));
				st.setEmailAddress(rs.getString("email"));
				st.setCurrentFines(Double.parseDouble(rs.getString("fine")));
			}
			ps.close();
		}
		catch(SQLException e){
			// TODO
			// Write an error
		}
		return st;
	}

}
