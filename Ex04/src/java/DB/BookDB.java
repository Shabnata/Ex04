
package DB;

import Model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.time.format.DateTimeFormatter;

public class BookDB{

	private Connection cn;

	public BookDB(Connection cn){
		this.cn = cn;
	}

	public Book getBookByISBN(String isbn){

		Book bk = null;
		PreparedStatement ps = null;
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
				bk.setCopyCounter(rs.getInt("copy_cnt"));

			}
			ps.close();
		} catch(SQLException e){
			// TODO
			// Write an error
		}
		return bk;
	}

}
