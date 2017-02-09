
package Controller;

import DB.BookDB;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
@WebServlet(name = "DeleteBookServlet", urlPatterns = {"/DeleteBookServlet"})
public class DeleteBookServlet extends HttpServlet{

	ServletContext sc;

	@Override
	public void init(){
		this.sc = this.getServletContext();

		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
		} catch(ClassNotFoundException e){
			Logger.getLogger(DeleteBookServlet.class.getName()).log(Level.SEVERE, null, e);
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
		RequestDispatcher rd = request.getRequestDispatcher("DeleteBookPage.jsp");
		String bookIsbn = request.getParameter("bookIsbn");
		if(bookIsbn != null){
			try {
				cn = DriverManager.getConnection(this.sc.getInitParameter("cnurl"), this.sc.getInitParameter("DBUsername"), this.sc.getInitParameter("DBPassword"));
				BookDB bDB = new BookDB(cn);

				if(bDB.deleteBookByISBN(bookIsbn)){
					request.setAttribute("servletMessage", "Book with ISBN " + bookIsbn + " was deleted successfully.");
				} else {
					request.setAttribute("servletMessage", "Cannot delete Book with ISBN " + bookIsbn);
				}
			} catch(SQLException e){
				Logger.getLogger(DeleteBookServlet.class.getName()).log(Level.SEVERE, null, e);
			} finally {
				try {
					if(cn != null){
						cn.close();
					}
				} catch(SQLException e){
					Logger.getLogger(DeleteBookServlet.class.getName()).log(Level.SEVERE, null, e);
				}
			}
		}

		rd.forward(request, response);

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
