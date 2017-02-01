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
		try{
			PreparedStatement ps = this.cn.prepareStatement(""
				+ "SELECT books.isbn, "
				+ "       books.title, "
				+ "       books.author, "
				+ "       books.p_year, "
				+ "       books.cover, "
				+ "		  books.copy_cnt, "
				+ "       books.book_category, "
				+ "       categories.cat_name, "
				+ "       Count(book_copies.copy_code) AS copy_count "
				+ "FROM   books "
				+ "       JOIN categories "
				+ "         ON books.isbn = ? "
				+ "            AND books.book_category = categories.cat_name "
				+ "       JOIN book_copies "
				+ "         ON books.isbn = book_copies.isbn "
				+ "       JOIN loaned_books "
				+ "         ON book_copies.copy_code = loaned_books.copy_code "
				+ "            AND loaned_books.returned = false "
				+ "GROUP  BY books.isbn, "
				+ "          books.title, "
				+ "          books.author, "
				+ "          books.p_year, "
				+ "          books.cover, "
				+ "			 books.copy_cnt, "
				+ "          books.book_category, "
				+ "          categories.cat_name");
			ps.setString(1, isbn);
			ResultSet rs = ps.executeQuery();
			if(rs.first()){
				bk = new Book();
				bk.setISBN(rs.getString("isbn"));
				bk.setTitle(rs.getString("title"));
				bk.setAuthorName(rs.getString("author"));

				Category bk_cat = new Category();
				bk_cat.setCatID(rs.getInt("book_category"));
				bk_cat.setCatName(rs.getString("cat_name"));
				bk.setCategory(bk_cat);

				bk.setBookYear(Year.parse(rs.getString("p_year"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
				bk.setCoverPath(rs.getString("cover"));
				bk.setCopyCounter(rs.getInt("copy_cnt"));
				bk.setAvailableCopies(rs.getInt("copy_count"));
			}
		} catch(SQLException e){
			// TODO
			// Write an error
			System.err.println("*\n*\n*\n" + e.getMessage() + "\n*\n*\n*");
		}

		return bk;
		
		// TODO Delete this before submitting if the replacement code above works
		// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
		/*
		Book bk = null;
		PreparedStatement ps;
		Category bk_cat;
		PreparedStatement bkCatPS;
		PreparedStatement copyCntPS;

		try{
			ps = this.cn.prepareStatement("SELECT * FROM books WHERE LOWER(isbn)=?");
			ps.setString(1, isbn.toLowerCase());

			bkCatPS = this.cn.prepareStatement("SELECT * FROM categories WHERE cat_name=?");
			copyCntPS = this.cn.prepareStatement(""
				+ "SELECT Count(*) AS copy_count "
				+ "FROM   book_copies "
				+ "WHERE  book_copies.isbn = ? "
				+ "       AND copy_code NOT IN(SELECT bc.copy_code "
				+ "                            FROM   (SELECT copy_code "
				+ "                                    FROM   book_copies "
				+ "                                    WHERE  book_copies.isbn = ?) AS bc "
				+ "                                   JOIN loaned_books "
				+ "                                     ON bc.copy_code = loaned_books.copy_code ");

			ResultSet rs = ps.executeQuery();
			ResultSet bkCatRS;
			ResultSet copyCntRS;
			while(rs.next()){
				bk = new Book();
				bk.setISBN(rs.getString("isbn"));
				bk.setTitle(rs.getString("title"));
				bk.setAuthorName(rs.getString("author"));

				bkCatPS.setString(1, rs.getString("book_category"));
				bkCatRS = bkCatPS.executeQuery();
				while(bkCatRS.next()){
					bk_cat = new Category();
					bk_cat.setCatID(bkCatRS.getInt("id"));
					bk_cat.setCatName(bkCatRS.getString("cat_name"));
					bk.setCategory(bk_cat);
				}

				bk.setBookYear(Year.parse(rs.getString("p_year"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
				bk.setCoverPath(rs.getString("cover"));
				bk.setCopyCounter(Integer.parseInt(rs.getString("copy_cnt")));

				copyCntPS.setString(1, bk.getISBN());
				copyCntPS.setString(2, bk.getISBN());
				copyCntRS = copyCntPS.executeQuery();
				while(copyCntRS.next()){
					bk.setAvailableCopies(copyCntRS.getInt("copy_count"));
				}

			}
		} catch(SQLException e){
			// TODO
			// Write an error
			System.err.println("*\n*\n*\n" + e.getMessage() + "\n*\n*\n*");
		}
		return bk;
		 */
		 // </editor-fold>
	}

	public ArrayList<Book> getBooksByTitle(String title){
		ArrayList<Book> bksLst = new ArrayList<>();

		try{
			Book bk;
			Category bk_cat;
			PreparedStatement ps = this.cn.prepareStatement(""
				+ "SELECT books.isbn, "
				+ "       books.title, "
				+ "       books.author, "
				+ "       books.p_year, "
				+ "       books.cover, "
				+ "       books.copy_cnt, "
				+ "       books.book_category, "
				+ "       categories.cat_name, "
				+ "       Count(book_copies.copy_code) AS copy_count "
				+ "FROM   books "
				+ "       JOIN categories "
				+ "         ON Lower(books.isbn) LIKE ? "
				+ "            AND books.book_category = categories.cat_name "
				+ "       JOIN book_copies "
				+ "         ON books.isbn = book_copies.isbn "
				+ "       JOIN loaned_books "
				+ "         ON book_copies.copy_code = loaned_books.copy_code "
				+ "            AND loaned_books.returned = false "
				+ "GROUP  BY books.isbn, "
				+ "          books.title, "
				+ "          books.author, "
				+ "          books.p_year, "
				+ "          books.cover, "
				+ "          books.copy_cnt, "
				+ "          books.book_category, "
				+ "          categories.cat_name");
			ps.setString(1, "%" + title.toLowerCase() + "%");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				bk = new Book();
				bk.setISBN(rs.getString("isbn"));
				bk.setTitle(rs.getString("title"));
				bk.setAuthorName(rs.getString("author"));

				bk_cat = new Category();
				bk_cat.setCatID(rs.getInt("book_category"));
				bk_cat.setCatName(rs.getString("cat_name"));
				bk.setCategory(bk_cat);

				bk.setBookYear(Year.parse(rs.getString("p_year"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
				bk.setCoverPath(rs.getString("cover"));
				bk.setCopyCounter(rs.getInt("copy_cnt"));
				bk.setAvailableCopies(rs.getInt("copy_count"));

				bksLst.add(bk);
			}
		} catch(SQLException e){
			// TODO
			// Write an error
			System.err.println("*\n*\n*\n" + e.getMessage() + "\n*\n*\n*");
		}

		return bksLst;

		// TODO Delete this before submitting if the replacement code above works
		// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
		/*
		ArrayList<Book> bksLst = new ArrayList<>();
		Book bk;
		Category bk_cat;
		PreparedStatement ps;
		PreparedStatement bkCatPS;
		PreparedStatement copyCntPS;

		try{
			ps = this.cn.prepareStatement("SELECT * FROM books WHERE LOWER(title) LIKE ?");
			ps.setString(1, "%" + title.toLowerCase() + "%");

			bkCatPS = this.cn.prepareStatement("SELECT * FROM categories WHERE cat_name=?");
			copyCntPS = this.cn.prepareStatement("SELECT COUNT(*) AS copy_count FROM book_copies WHERE book_copies.isbn=? AND copy_code NOT IN("
				+ "SELECT bc.copy_code FROM "
				+ "(SELECT copy_code FROM book_copies WHERE book_copies.isbn=?) AS bc "
				+ "JOIN loaned_books "
				+ "ON "
				+ "bc.copy_code=loaned_books.copy_code WHERE loaned_books.returned=false "
				+ ")");

			ResultSet rs = ps.executeQuery();
			ResultSet bkCatRS;
			ResultSet copyCntRS;
			while(rs.next()){
				bk = new Book();
				bk.setISBN(rs.getString("isbn"));
				bk.setTitle(rs.getString("title"));
				bk.setAuthorName(rs.getString("author"));

				bkCatPS.setString(1, rs.getString("book_category"));
				bkCatRS = bkCatPS.executeQuery();
				while(bkCatRS.next()){
					bk_cat = new Category();
					bk_cat.setCatID(bkCatRS.getInt("id"));
					bk_cat.setCatName(bkCatRS.getString("cat_name"));
					bk.setCategory(bk_cat);
				}

				bk.setBookYear(Year.parse(rs.getString("p_year"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
				bk.setCoverPath(rs.getString("cover"));

				copyCntPS.setString(1, bk.getISBN());
				copyCntPS.setString(2, bk.getISBN());
				copyCntRS = copyCntPS.executeQuery();
				while(copyCntRS.next()){
					bk.setAvailableCopies(copyCntRS.getInt("copy_count"));
				}

				bksLst.add(bk);
			}
		} catch(SQLException e){
			// TODO
			// Write an error
			System.err.println("*\n*\n*\n" + e.getMessage() + "\n*\n*\n*");
		}
		return bksLst;
		 */
		 // </editor-fold>
	}

	public boolean addBook(String isbn, String title, String author, Category cat, Year year, String cover, int numOfCopies){

		PreparedStatement ps;
		boolean failed = false;
		try{
			//boolean prevState = cn.getAutoCommit();
			//cn.setAutoCommit(false);

			ps = cn.prepareStatement(""
				+ "INSERT INTO books "
				+ "            (isbn, "
				+ "             title, "
				+ "             author, "
				+ "             book_category, "
				+ "             p_year, "
				+ "             cover, "
				+ "             copy_cnt) "
				+ "VALUES     (?, "
				+ "            ?, "
				+ "            ?, "
				+ "            ?, "
				+ "            ?, "
				+ "            ?, "
				+ "            ?)");
			ps.setString(1, isbn);
			ps.setString(2, title);
			ps.setString(3, author);
			ps.setString(4, cat.getCatName());
			ps.setString(5, Integer.toString(year.getValue()) + "-01-01");
			ps.setString(6, cover);
			ps.setInt(7, numOfCopies);

			if(ps.executeUpdate() != 0){
				PreparedStatement psc = cn.prepareStatement(""
					+ "INSERT INTO book_copies "
					+ "            (isbn, "
					+ "             copy_code, "
					+ "             copy_cond) "
					+ "VALUES     (?, "
					+ "            ?, "
					+ "            1)");
				psc.setString(1, isbn);
				for(int i = 1; i <= numOfCopies && !failed; i++){
					psc.setString(2, isbn + "_" + String.format("%03d", i));
					if(psc.executeUpdate() == 0){
						failed = true;
					}
				}

			} else {
				failed = true;
			}

			//if(failed){
			//	cn.rollback();
			//} else {
			//	cn.commit();
			//}
			//cn.setAutoCommit(prevState);
		} catch(SQLException e){
			// TODO
			// Write an error
			System.err.println("*\n*\n*\n" + e.getMessage() + "\n*\n*\n*");
			return false;
		}
		return !failed;
	}

}
