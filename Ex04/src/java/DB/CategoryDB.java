
package DB;

import Model.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CategoryDB {
	private Connection cn;
	
	public CategoryDB(Connection cn) {
		this.cn = cn;
	}
	public CategoryDB() {
	}
	
	public ArrayList<Category> getCategories() throws ClassNotFoundException, SQLException {
		Class.forName("org.apache.derby.jdbc.ClientDriver");
		String urlCn = "jdbc:derby://localhost:1527/LibraryDB";
		Connection cn = DriverManager.getConnection(urlCn, "administrator", "123456");

		ArrayList<Category> cat = null;
		Statement ps = null;
		try {

			ps = cn.createStatement();

			

			ResultSet rs = ps.executeQuery("select * from Categories");

			while (rs.next()) {
				Category c= new Category();
				c.setCatID(rs.getInt(1));
				c.setCatName(rs.getString(2));
				cat.add(c);
			}
			cn.close();
		} catch (SQLException e) {
			// TODO
			// Write an error
		}
		return cat;
	}
	
	public Boolean addCategory(String catName) throws ClassNotFoundException, SQLException {
		Boolean added = false;
		Class.forName("org.apache.derby.jdbc.ClientDriver");
		String urlCn = "jdbc:derby://localhost:1527/LibraryDB";
		Connection cn = DriverManager.getConnection(urlCn, "administrator", "123456");

		Category cat = null;
		//PreparedStatement ps = null;
		Statement ps = null;
		try {			
			PreparedStatement pst = cn.prepareStatement("insert into categories (cat_name) values (?)");
				pst.setString(1, catName);
				
			int nor = pst.executeUpdate();
			if(nor!=0){
				added=true;
			}
				
			cn.close();
		} catch (SQLException e) {
			// TODO
			// Write an error
		}
		return added;
	}
}
