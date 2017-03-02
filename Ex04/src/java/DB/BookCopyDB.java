
package DB;

import Model.BookCondition;
import Model.BookCopy;
import Util.DButil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Denis Sh
 */
public class BookCopyDB{

	private Connection cn;

	public BookCopyDB() {
		cn = DButil.getConnection();
	}

	public void closeConnection(){
		if(this.cn != null){
			try {
				this.cn.close();
			} catch(SQLException e){
				Logger.getLogger(BookCopyDB.class.getName()).log(Level.SEVERE, null, e);
			}
		}
	}
	
	
	// Returns null if book not found, empty list if no copies
	public ArrayList<BookCopy> getAllCopiesOfBook(String book_isbn){
		ArrayList<BookCopy> bcLst = null;
		String copiesQuery = ""
			+ "SELECT book_copies.copy_code, "
			+ "       conditions.con_key, "
			+ "       conditions.con_desc "
			+ "FROM   books "
			+ "       JOIN book_copies "
			+ "         ON books.isbn = book_copies.isbn "
			+ "       JOIN conditions "
			+ "         ON book_copies.copy_cond = conditions.con_key "
			+ "WHERE  books.isbn = ?";

		try {
			PreparedStatement ps = this.cn.prepareStatement(copiesQuery);
			ps.setString(1, book_isbn);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				bcLst = new ArrayList<>();
				BookCopy bc;
				BookCondition bcC;

				do {
					bcC = new BookCondition();
					bcC.setConDesc(rs.getString("con_desc"));
					bcC.setConKey(rs.getInt("con_key"));

					bc = new BookCopy();
					bc.setCOPY_CODE(rs.getString("copy_code"));
					bc.setCopyCondition(bcC);

					bcLst.add(bc);
				} while(rs.next());
			}
		} catch(SQLException e){
			Logger.getLogger(BookCopyDB.class.getName()).log(Level.SEVERE, null, e);
		}
		return bcLst;
	}

	// Returns null if book not found, empty list if no usable copies
	public ArrayList<BookCopy> getUsableCopiesOfBook(String book_isbn){
		ArrayList<BookCopy> bcLst = null;
		String copiesQuery = ""
			+ "SELECT book_copies.copy_code, "
			+ "       conditions.con_key, "
			+ "       conditions.con_desc "
			+ "FROM   books "
			+ "       JOIN book_copies "
			+ "         ON books.isbn = book_copies.isbn "
			+ "            AND book_copies.copy_cond != 5"
			+ "       LEFT JOIN loaned_books "
			+ "              ON book_copies.copy_code = loaned_books.copy_code "
			+ "                 AND loaned_books.returned = true "
			+ "       JOIN conditions "
			+ "         ON book_copies.copy_cond = conditions.con_key "
			+ "WHERE  books.isbn = ? "
			+ "       AND book_copies.copy_code NOT IN(SELECT copy_code "
			+ "                                        FROM   loaned_books "
			+ "                                        WHERE  returned = false)";

		try {
			PreparedStatement ps = this.cn.prepareStatement(copiesQuery);
			ps.setString(1, book_isbn);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				bcLst = new ArrayList<>();
				BookCopy bc;
				BookCondition bcC;

				do {
					bcC = new BookCondition();
					bcC.setConDesc(rs.getString("con_desc"));
					bcC.setConKey(rs.getInt("con_key"));

					bc = new BookCopy();
					bc.setCOPY_CODE(rs.getString("copy_code"));
					bc.setCopyCondition(bcC);

					bcLst.add(bc);
				} while(rs.next());
			}
		} catch(SQLException e){
			Logger.getLogger(BookCopyDB.class.getName()).log(Level.SEVERE, null, e);
		}
		return bcLst;
	}

	public Boolean updateBookCopyCondition(BookCopy bookCopy){

		Boolean updated = false;
		BookCopy bc = bookCopy;

		try {
			PreparedStatement pst = cn.prepareStatement("UPDATE book_copies SET copy_cond=? WHERE copy_code=?");
			pst.setDouble(1, bc.getCopyCondition().getConKey());
			pst.setString(2, bc.getCOPY_CODE());
			updated = pst.execute();
		} catch(SQLException e){
			Logger.getLogger(BookCopyDB.class.getName()).log(Level.SEVERE, null, e);
		}

		return updated;
	}

	public BookCopy getBookCopyByCopyCode(String copyCode) throws ClassNotFoundException{
		BookCopy bc = null;
		String copiesQuery = "select * from book_copies where copy_code=?";

		try {
			PreparedStatement ps = this.cn.prepareStatement(copiesQuery);
			ps.setString(1, copyCode);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				bc = new BookCopy();

				do {
					bc.setCOPY_CODE(copyCode);
					ConditionDB conDB = new ConditionDB();
					BookCondition bCon = new BookCondition();
					bCon = conDB.getCondition(rs.getInt("copy_cond"));
					bc.setCopyCondition(bCon);

				} while(rs.next());
			}
		} catch(SQLException e){
			Logger.getLogger(BookCopyDB.class.getName()).log(Level.SEVERE, null, e);
		}
		return bc;
	}

	public Boolean updateBookCopyLoanState(String copyCode){
		Boolean updated = false;

		try {
			PreparedStatement pst = this.cn.prepareStatement("UPDATE loaned_books SET returned=? WHERE copy_code=? and returned=?");
			pst.setString(1, "true");
			pst.setString(2, copyCode);
			pst.setString(3, "false");
			updated = pst.execute();

		} catch(SQLException e){
			Logger.getLogger(BookCopyDB.class.getName()).log(Level.SEVERE, null, e);
		}

		return updated;

	}

}
