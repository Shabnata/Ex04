package DB;

import Util.DButil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Denis Sh
 */
public class LibraryPropsDB{

	private Connection cn;

	public LibraryPropsDB() {
		cn = DButil.getConnection();
	}

	public void closeConnection() {
		try {
			cn.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public String getLibraryName(){
		String libName = "";
		String getNameQuery = ""
			+ "SELECT lib_name "
			+ "FROM   library_props "
			+ "ORDER  BY lib_name asc "
			+ "FETCH first 1 ROWS only";

		try{
			Statement ps = this.cn.createStatement();
			ResultSet rs = ps.executeQuery(getNameQuery);
			if(rs.next()){
				libName = rs.getString("lib_name");
			}
		} catch(SQLException e){
			Logger.getLogger(LibraryPropsDB.class.getName()).log(Level.SEVERE, null, e);
		}
		return libName;
	}

	public boolean setLibraryName(String newName){

		String setNameQuery = ""
			+ "UPDATE library_props "
			+ "SET    lib_name = ?";

		try{
			PreparedStatement ps = this.cn.prepareCall(setNameQuery);
			ps.setString(1, newName);

			if(ps.executeUpdate() == 1){
				return true;
			}
		} catch(SQLException e){
			Logger.getLogger(LibraryPropsDB.class.getName()).log(Level.SEVERE, null, e);
		}
		return false;
	}

	public int getFinesPerDay(){
		int finesPerDay = 0;
		String getNameQuery = ""
			+ "SELECT fine_per_day "
			+ "FROM   library_props "
			+ "ORDER  BY lib_name asc "
			+ "FETCH first 1 ROWS only";

		try{
			Statement ps = this.cn.createStatement();
			ResultSet rs = ps.executeQuery(getNameQuery);
			if(rs.next()){
				finesPerDay = rs.getInt("fine_per_day");
			}
		} catch(SQLException e){
			Logger.getLogger(LibraryPropsDB.class.getName()).log(Level.SEVERE, null, e);
		}
		return finesPerDay;
	}

	public boolean setFinesPerDay(int newFine){

		String setFineQuery = ""
			+ "UPDATE library_props "
			+ "SET    fine_per_day = ?";

		try{
			PreparedStatement ps = this.cn.prepareCall(setFineQuery);
			ps.setInt(1, newFine);

			if(ps.executeUpdate() > 0){
				return true;
			}
		} catch(SQLException e){
			Logger.getLogger(LibraryPropsDB.class.getName()).log(Level.SEVERE, null, e);
		}
		return false;
	}

	public int getMaxFinesPerStudent(){
		int finesPerStudent = 0;
		String getNameQuery = ""
			+ "SELECT max_fines_per_student "
			+ "FROM   library_props "
			+ "ORDER  BY lib_name asc "
			+ "FETCH first 1 ROWS only";

		try{
			Statement ps = this.cn.createStatement();
			ResultSet rs = ps.executeQuery(getNameQuery);
			if(rs.next()){
				finesPerStudent = rs.getInt("max_fines_per_student");
			}
		} catch(SQLException e){
			Logger.getLogger(LibraryPropsDB.class.getName()).log(Level.SEVERE, null, e);
		}
		return finesPerStudent;
	}

	public boolean setMaxFinesPerStudent(int newFine){

		String setFineQuery = ""
			+ "UPDATE library_props "
			+ "SET    max_fines_per_student = ?";

		try{
			PreparedStatement ps = this.cn.prepareCall(setFineQuery);
			ps.setInt(1, newFine);

			if(ps.executeUpdate() > 0){
				return true;
			}
		} catch(SQLException e){
			Logger.getLogger(LibraryPropsDB.class.getName()).log(Level.SEVERE, null, e);
		}
		return false;
	}

	public int getMaxBooksPerStudent(){
		int booksPerStudent = 0;
		String getNameQuery = ""
			+ "SELECT max_books_per_student "
			+ "FROM   library_props "
			+ "ORDER  BY lib_name asc "
			+ "FETCH first 1 ROWS only";

		try{
			Statement ps = this.cn.createStatement();
			ResultSet rs = ps.executeQuery(getNameQuery);
			if(rs.next()){
				booksPerStudent = rs.getInt("max_books_per_student");
			}
		} catch(SQLException e){
			Logger.getLogger(LibraryPropsDB.class.getName()).log(Level.SEVERE, null, e);
		}
		return booksPerStudent;
	}

	public boolean setMaxBooksPerStudent(int newFine){

		String setBooksQuery = ""
			+ "UPDATE library_props "
			+ "SET    max_books_per_student = ?";

		try{
			PreparedStatement ps = this.cn.prepareCall(setBooksQuery);
			ps.setInt(1, newFine);

			if(ps.executeUpdate() > 0){
				return true;
			}
		} catch(SQLException e){
			Logger.getLogger(LibraryPropsDB.class.getName()).log(Level.SEVERE, null, e);
		}
		return false;
	}

	public boolean checkLibraryProps(){
		String varname1 = ""
			+ "SELECT * "
			+ "FROM   library_props";

		try{
			PreparedStatement ps = this.cn.prepareStatement(varname1);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				return true;
			}
		} catch(SQLException e){
			Logger.getLogger(LibraryPropsDB.class.getName()).log(Level.SEVERE, null, e);
		}
		return false;
	}

	public boolean setLibraryProps(String libName, int finePerDay, int maxFine, int maxBooks){
		String varname1 = ""
			+ "INSERT INTO library_props "
			+ "            (lib_name, "
			+ "             fine_per_day, "
			+ "             max_fines_per_student, "
			+ "             max_books_per_student) "
			+ "VALUES     (?, "
			+ "            ?, "
			+ "            ?, "
			+ "            ?)";
		String varname2 = ""
			+ "DELETE FROM library_props";
		PreparedStatement ps;
		try{
			ps = this.cn.prepareStatement(varname1);
			ps.setString(1, libName);
			ps.setInt(2, finePerDay);
			ps.setInt(3, maxFine);
			ps.setInt(4, maxBooks);

			if(this.checkLibraryProps()){
				PreparedStatement ps1 = this.cn.prepareStatement(varname2);
				if(ps1.executeUpdate() == 0){
					return false;
				}
			}
			if(ps.executeUpdate() == 1){
				return true;
			}
		} catch(SQLException e){
			Logger.getLogger(LibraryPropsDB.class.getName()).log(Level.SEVERE, null, e);
		}

		return false;
	}

}
