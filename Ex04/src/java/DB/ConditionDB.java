package DB;

import Model.*;
import Util.DButil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
		if (this.cn != null) {
			try {
				this.cn.close();
			} catch (SQLException e) {
				Logger.getLogger(ConditionDB.class.getName()).log(Level.SEVERE, null, e);
			}
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
			Logger.getLogger(ConditionDB.class.getName()).log(Level.SEVERE, null, e);
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
			Logger.getLogger(ConditionDB.class.getName()).log(Level.SEVERE, null, e);
		}
		return con;
	}

}
