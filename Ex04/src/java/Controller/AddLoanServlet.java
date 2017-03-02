package Controller;

import DB.BookDB;
import DB.CategoryDB;
import DB.CookieDB;
import DB.LibraryPropsDB;
import DB.LoanDB;
import DB.StudentDB;
import DB.UserDB;
import Model.Book;
import Model.Category;
import Model.Loan;
import Model.Student;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Denis Sh
 */
@WebServlet(name = "AddLoanServlet", urlPatterns = {"/AddLoanServlet"})
public class AddLoanServlet extends HttpServlet {

	ServletContext sc;

	@Override
	public void init() {
		this.sc = this.getServletContext();

	}

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 *
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		RequestDispatcher rd;

		LibraryPropsDB lpDB = null;
		StudentDB sDB = null;
		LoanDB lnDB = null;
		CategoryDB cDB = null;
		BookDB bDB = null;
		UserDB myUserDb = null;

		Student st;
		Loan ln;

		boolean errors = false;
		ArrayList<String> errorLst = null;

		String stID = request.getParameter("stId");
		String loanID = request.getParameter("loanId");
		String catName = request.getParameter("catName");
		String bookISBN = request.getParameter("bookIsbn");

		sDB = new StudentDB();
		if (stID == null) {
			Cookie crrUsr = CookieDB.getCookieValue(request.getCookies(), "username");
			myUserDb = new UserDB();
			//TODO Remove crrUsr null check after adding redirects
			if (crrUsr != null && myUserDb.getUser(crrUsr.getValue()).getUserType().equals("user")) {
				stID = myUserDb.getUser(crrUsr.getValue()).getUserID();
				st = sDB.getStudent(stID);
			} else {
				rd = request.getRequestDispatcher("IdentifyStudentForLoanPage.jsp");
				if (bookISBN != null) {
					request.setAttribute("bookISBN", bookISBN);
				}
				rd.forward(request, response);
				return;
			}

		} else if ((st = sDB.getStudent(stID)) == null) {
			rd = request.getRequestDispatcher("IdentifyStudentForLoanPage.jsp");
			if (bookISBN != null) {
				request.setAttribute("bookISBN", bookISBN);
			}
			request.setAttribute("errMsg", "Student not found.");
			rd.forward(request, response);
			return;
		}

		if (catName == null && bookISBN == null) {
			sDB = new StudentDB();
			if (sDB.getStudent(stID) == null) {
				request.setAttribute("errMsg", "Student not found.");
				rd = request.getRequestDispatcher("IdentifyStudentForLoanPage.jsp");
				rd.forward(request, response);
				return;
			}
			cDB = new CategoryDB();
			ArrayList<Category> catLst = cDB.getCategories();
			rd = request.getRequestDispatcher("SelectCategoryForLoanPage.jsp");
			request.setAttribute("stId", stID);
			if (loanID != null) {
				lnDB = new LoanDB();
				ln = lnDB.getLoanByID(Integer.parseInt(loanID));
				request.setAttribute("loanId", loanID);
				request.setAttribute("loan", ln);

				bDB = new BookDB();
				HashMap<String, Book> bksMap = new HashMap<>();
				ln.getBooksInLoan().forEach((bc) -> {
					Book bk = bDB.getBookByBookCopy(bc);
					if (bk != null) {
						bksMap.put(bc.getCOPY_CODE(), bk);
					}
				});
				request.setAttribute("bksMap", bksMap);
			}
			request.setAttribute("catLst", catLst);
			rd.forward(request, response);
			return;
		} else if (bookISBN == null) {
			cDB = new CategoryDB();
			ArrayList<Book> bookLst = cDB.getBooksByCategoryName(catName);
			rd = request.getRequestDispatcher("SelectBookForLoanPage.jsp");
			request.setAttribute("stId", stID);
			if (loanID != null) {
				request.setAttribute("loanId", loanID);
			}
			request.setAttribute("bookLst", bookLst);
			rd.forward(request, response);
			return;
		}

		lpDB = new LibraryPropsDB();
		int maxFinesPerStudent = lpDB.getMaxFinesPerStudent();
		int maxBooksPerStudent = lpDB.getMaxBooksPerStudent();

		if (st.getCurrentFines() >= maxFinesPerStudent) {
			errors = true;
			if (errorLst == null) {
				errorLst = new ArrayList<>();
			}
			errorLst.add("Student with ID: " + st.getStudentID() + " has fines that exceed the library maximum.");
		}
		if (sDB.getCountLoanedBooks(st.getStudentID()) >= maxBooksPerStudent) {
			errors = true;
			if (errorLst == null) {
				errorLst = new ArrayList<>();
			}
			errorLst.add("Student with ID: " + st.getStudentID() + " loaned books count has exceeded the library maximum.");

		}

		if (errors == true) {
			rd = request.getRequestDispatcher("AddLoanPageError.jsp");
			request.setAttribute("errors", errorLst);
			rd.forward(request, response);
		}

		lnDB = new LoanDB();
		if (loanID == null) {
			GregorianCalendar start_d = new GregorianCalendar();
			GregorianCalendar ret_d = new GregorianCalendar();
			ret_d.add(GregorianCalendar.DAY_OF_MONTH, 14);
			ln = lnDB.getNewLoan(stID, start_d, ret_d);
		} else {
			ln = lnDB.getLoanByID(Integer.parseInt(loanID));
		}

		lnDB.addBookToLoan(ln.getLoanID(), bookISBN);
		ln = lnDB.getLoanByID(ln.getLoanID());

		cDB = new CategoryDB();
		ArrayList<Category> catLst = cDB.getCategories();
		rd = request.getRequestDispatcher("SelectCategoryForLoanPage.jsp");
		request.setAttribute("stId", stID);
		request.setAttribute("loanId", Integer.toString(ln.getLoanID()));
		request.setAttribute("loan", ln);
		bDB = new BookDB();
		HashMap<String, Book> bksMap = new HashMap<>();

		ln.getBooksInLoan().forEach((bc) -> {
			Book bk = bDB.getBookByBookCopy(bc);
			if (bk != null) {
				bksMap.put(bc.getCOPY_CODE(), bk);
			}
		});
		request.setAttribute("bksMap", bksMap);
		request.setAttribute("catLst", catLst);
		rd.forward(request, response);

		if (bDB != null) {
			bDB.closeConnection();
		}

		if (cDB != null) {
			cDB.closeConnection();
		}

		if (lnDB != null) {
			lnDB.closeConnection();
		}

		if (sDB != null) {
			sDB.closeConnection();
		}

	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 *
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 *
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

}
