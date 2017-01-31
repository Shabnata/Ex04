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
		try{
			ps = this.cn.prepareStatement("SELECT * FROM books WHERE LOWER(isbn)=?");
			ps.setString(1, isbn.toLowerCase());

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
			System.err.println("*\n*\n*\n" + e.getMessage() + "\n*\n*\n*");
		}
		return bk;
	}

	public ArrayList<Book> getBooksByTitle(String title){

		ArrayList<Book> bksLst = new ArrayList<>();
		Book bk;
		ArrayList<Category> bkCatLst;
		Category cat;
		PreparedStatement ps;
		PreparedStatement bkCats;
		PreparedStatement copyCnt;

		try{
			ps = this.cn.prepareStatement("SELECT * FROM books WHERE LOWER(title) LIKE ?");
			ps.setString(1, "%" + title.toLowerCase() + "%");

			bkCats = this.cn.prepareStatement("SELECT * FROM book_categories WHERE isbn=?");
			copyCnt = this.cn.prepareStatement("SELECT COUNT(*) FROM (SELECT * FROM book_copies WHERE book_copies.isbn=?) AS a JOIN loaned_books ON a.copy_code=loaned_books.copy_code WHERE loaned_books.returned=true");
			ResultSet rs = ps.executeQuery();
			ResultSet bkCatsRS;
			while(rs.next()){
				bk = new Book();
				bk.setISBN(rs.getString("isbn"));
				bk.setTitle(rs.getString("title"));
				bk.setAuthorName(rs.getString("author"));
				bk.setBookYear(Year.parse(rs.getString("p_year"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
				bk.setCoverPath(rs.getString("cover"));
				bk.setCopyCounter(rs.getInt("copy_cnt")); // TODO Change this to be available copies

				bkCats.setString(1, bk.getISBN());
				bkCatsRS = bkCats.executeQuery();
				bkCatLst = new ArrayList<>();
				while(bkCatsRS.next()){
					cat = new Category();
					cat.setCatID(bkCatsRS.getInt("id"));
					cat.setCatName(bkCatsRS.getString("cat_name"));
					bkCatLst.add(cat);
				}
				bk.setCategories(bkCatLst);

				bksLst.add(bk);

			}
		} catch(SQLException e){
			// TODO
			// Write an error
			System.err.println("*\n*\n*\n" + e.getMessage() + "\n*\n*\n*");
		}
		return bksLst;
	}

}
