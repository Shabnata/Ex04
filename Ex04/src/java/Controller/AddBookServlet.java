
package Controller;

import DB.*;
import Model.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class AddBookServlet extends HttpServlet{

	ServletContext sc;

	@Override
	public void init(){
		this.sc = this.getServletContext();

		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
		} catch(ClassNotFoundException e){
			Logger.getLogger(AddBookServlet.class.getName()).log(Level.SEVERE, null, e);
		}

	}

	// <editor-fold defaultstate="collapsed" desc="Pancackes.">
	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 *
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */// </editor-fold>
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		Connection cn = null;
		RequestDispatcher rd = null;
		Book bk;
		ArrayList<Category> catLst;
		try {
			cn = DriverManager.getConnection(this.sc.getInitParameter("cnurl"), this.sc.getInitParameter("DBUsername"), this.sc.getInitParameter("DBPassword"));

			CategoryDB cDB = new CategoryDB();
			catLst = cDB.getCategories();
			request.setAttribute("categories", catLst);

			String catParam = request.getParameter("category");
			if(catParam != null){
				Category addToCategory = null;
				for(Category c : catLst){
					if(c.getCatName().equals(catParam)){
						addToCategory = c;
						break;
					}
				}

				BookDB bDB = new BookDB(cn);
				boolean failed = !bDB.addBook(request.getParameter("isbn"), request.getParameter("title"), request.getParameter("author"), addToCategory, Year.parse(request.getParameter("year")), request.getParameter("cover"), Integer.parseInt(request.getParameter("numCopies")));
				if(!failed){
					bk = bDB.getBookByISBN(request.getParameter("isbn"));
					rd = request.getRequestDispatcher("AddBookPageResult.jsp");
					request.setAttribute("book", bk);
				} else {
					rd = request.getRequestDispatcher("AddBookPageNotAdded.jsp");
				}
				//cn.close();
			} else {
				rd = request.getRequestDispatcher("AddBookPage.jsp");
			}
		} catch(SQLException | ClassNotFoundException e){
			Logger.getLogger(AddBookServlet.class.getName()).log(Level.SEVERE, null, e);
		} finally {
			try {
				if(cn != null){
					cn.close();
				}
			} catch(SQLException e){
				Logger.getLogger(AddBookServlet.class.getName()).log(Level.SEVERE, null, e);
			}
		}

		if(rd != null){
			rd.forward(request, response);
		}

	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 *
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 *
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo(){
		return "Short description";
	}// </editor-fold>

}
