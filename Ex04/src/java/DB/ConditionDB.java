
package DB;

import Model.*;
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
public ConditionDB(Connection cn) {
		this.cn = cn;
	}

	public ConditionDB() {
	}
	
	public Condition getCondition(int conKey) throws ClassNotFoundException, SQLException{
		Class.forName("org.apache.derby.jdbc.ClientDriver");
		String urlCn = "jdbc:derby://localhost:1527/LibraryDB";
		Connection cn = DriverManager.getConnection(urlCn, "administrator", "123456");

		Condition cd = null;
		//PreparedStatement ps = null;
		Statement ps = null;
		try{
			//ps = this.cn.prepareStatement("SELECT * FROM students WHERE st_id = ?");
			//ps.setString(1, st_id);
			ps = cn.createStatement();

			ResultSet rs = ps.executeQuery("SELECT * FROM conditions WHERE con_key=\'" + conKey + "\'");
			while(rs.next()){
				cd = new Condition();
				cd.setConKey(rs.getInt("con_key"));
				cd.setConDesc(rs.getString("con_desc"));
			}
			cn.close();
		} catch(SQLException e){
			// TODO
			// Write an error
		}
		return cd;
	}
	public ArrayList<Condition> getConditions() throws ClassNotFoundException, SQLException{
		Class.forName("org.apache.derby.jdbc.ClientDriver");
		String urlCn = "jdbc:derby://localhost:1527/LibraryDB";
		Connection cn = DriverManager.getConnection(urlCn, "administrator", "123456");

		ArrayList<Condition> con = new ArrayList<Condition>();
		Statement ps = null;
		try{

			ps = cn.createStatement();

			ResultSet rs = ps.executeQuery("select * from Conditions");

			while(rs.next()){
				Condition cd = new Condition();
				cd.setConKey(rs.getInt(2));
				cd.setConDesc(rs.getString(3));
				con.add(cd);
			}
			cn.close();
		} catch(SQLException e){
			// TODO
			// Write an error
		}
		return con;
	}
}
