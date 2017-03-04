package Controller;

import DB.BookDB;
import DB.CategoryDB;
import Model.*;
import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Denis Sh
 */
@WebServlet(name = "AddBookServlet", urlPatterns = {"/AddBookServlet"})
public class AddBookServlet extends HttpServlet {

	ServletContext sc;

	@Override
	public void init() {
		this.sc = this.getServletContext();
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		RequestDispatcher rd = null;
		Book bk;
		ArrayList<Category> catLst;
		CategoryDB cDB = null;
		BookDB bDB = null;

		cDB = new CategoryDB();
		catLst = cDB.getCategories();
		request.setAttribute("categories", catLst);

		String catParam = request.getParameter("category");
		if (catParam != null) {
			Category addToCategory = null;
			for (Category c : catLst) {
				if (c.getCatName().equals(catParam)) {
					addToCategory = c;
					break;
				}
			}

			bDB = new BookDB();

			boolean failed = !bDB.addBook(request.getParameter("isbn"), request.getParameter("title"), request.getParameter("author"), addToCategory, Year.parse(request.getParameter("year")), request.getParameter("cover"), Integer.parseInt(request.getParameter("numCopies")));
			if (!failed) {
				bk = bDB.getBookByISBN(request.getParameter("isbn"));
				rd = request.getRequestDispatcher("AddBookPageResult.jsp");
				request.setAttribute("book", bk);
			} else {
				rd = request.getRequestDispatcher("AddBookPageNotAdded.jsp");
			}

		} else {
			rd = request.getRequestDispatcher("AddBookPage.jsp");
		}

		if (bDB != null) {
			bDB.closeConnection();
		}

		if (cDB != null) {
			cDB.closeConnection();
		}

		if (rd != null) {
			rd.forward(request, response);
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
