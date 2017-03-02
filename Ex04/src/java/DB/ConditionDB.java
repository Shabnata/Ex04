package DB;

import Model.*;
import Util.DButil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Kotya
 */
public class ConditionDB {

	private Connection cn;
	public ConditionDB() {
		cn = DButil.getConnection();
	}

	public void closeConnection() {
		try {
			cn.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public BookCondition getCondition(int conKey) {
		

		BookCondition cd = null;
		//PreparedStatement ps = null;
		Statement ps = null;
		try {
			//ps = this.cn.prepareStatement("SELECT * FROM students WHERE st_id = ?");
			//ps.setString(1, st_id);
			ps = cn.createStatement();

			PreparedStatement pst = cn.prepareStatement("SELECT * FROM conditions WHERE con_key=?");
			pst.setInt(1, conKey);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				cd = new BookCondition();
				cd.setConKey(rs.getInt("con_key"));
				cd.setConDesc(rs.getString("con_desc"));
			}
		} catch (SQLException e) {
			// TODO
			// Write an error
		}
		return cd;
	}

	public ArrayList<BookCondition> getConditions() {


		ArrayList<BookCondition> con = new ArrayList<BookCondition>();
		Statement ps = null;
		try {

			ps = cn.createStatement();

			ResultSet rs = ps.executeQuery("select * from Conditions");

			while (rs.next()) {
				BookCondition cd = new BookCondition();
				cd.setConKey(rs.getInt(2));
				cd.setConDesc(rs.getString(3));
				con.add(cd);
			}
		} catch (SQLException e) {
			// TODO
			// Write an error
		}
		return con;
	}
}
