
package DB;

import Model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class BookDB{

	private Connection cn;

	public BookDB(Connection cn){
		this.cn = cn;
	}

	public Book getBookByISBN(String isbn){

		Book bk = null;
		PreparedStatement ps;
		try {
			ps = this.cn.prepareStatement("SELECT * FROM books WHERE isbn=?");
			ps.setString(1, isbn);

			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				bk = new Book();
				bk.setISBN(rs.getString("isbn"));
				bk.setTitle(rs.getString("title"));
				bk.setAuthorName(rs.getString("author"));
				bk.setBookYear(Year.parse(rs.getString("p_year"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
				bk.setCoverPath(rs.getString("cover"));
				bk.setCopyCounter(Integer.parseInt(rs.getString("copy_cnt")));
				bk.setCopyCounter(rs.getInt("copy_cnt")); // TODO Change this to be available copies

			}
		} catch(SQLException e){
			// TODO
			// Write an error
		}
		return bk;
	}

	public ArrayList<Book> getBooksByTitle(String title){

		ArrayList<Book> bksLst = new ArrayList<>();
		Book bk;
		PreparedStatement ps;
		try {
			ps = this.cn.prepareStatement("SELECT * FROM books WHERE title LIKE ?");
			ps.setString(1, "%" + title + "%");

			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				bk = new Book();
				bk.setISBN(rs.getString("isbn"));
				bk.setTitle(rs.getString("title"));
				bk.setAuthorName(rs.getString("author"));
				bk.setBookYear(Year.parse(rs.getString("p_year"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
				bk.setCoverPath(rs.getString("cover"));
				bk.setCopyCounter(Integer.parseInt(rs.getString("copy_cnt")));
				bk.setCopyCounter(rs.getInt("copy_cnt")); // TODO Change this to be available copies
				bksLst.add(bk);

			}
		} catch(SQLException e){
			// TODO
			// Write an error
			int x;
			x = 12;
			x = 123;
		}
		return bksLst;
	}

}
