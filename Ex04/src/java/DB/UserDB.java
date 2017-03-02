
package DB;

import Model.*;
import Util.DButil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kotya
 */
public class UserDB{

	private Connection cn;

	public UserDB(){
		cn = DButil.getConnection();
	}

	public void closeConnection(){
		if(this.cn != null){
			try {
				this.cn.close();
			} catch(SQLException e){
				Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, e);
			}
		}
	}

	public User getUser(String userID){

		User us = null;
		//PreparedStatement ps = null;
		Statement ps = null;
		try {
			ps = cn.createStatement();
			ResultSet rs = ps.executeQuery("SELECT * FROM accounts WHERE acc_id=\'" + userID + "\'");
			while(rs.next()){
				us = new User();
				us.setUserID(rs.getString("acc_id"));
				us.setUserPas(rs.getString("acc_pass"));
				us.setUserType(rs.getString("acc_type"));
			}

		} catch(SQLException e){
			// TODO 
			// Write an error
		}
		return us;
	}

}
