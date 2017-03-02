package Controller;

import DB.*;
import Model.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

	ServletContext sc;

	@Override
	public void init() {
		this.sc = this.getServletContext();

		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
		} catch (ClassNotFoundException e) {

		}
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException {
		String username = request.getParameter("Username");
		String password = request.getParameter("Password");
		RequestDispatcher dispatcher;
		Class.forName("org.apache.derby.jdbc.ClientDriver");
		String urlCn = "jdbc:derby:C:\\Users\\Kotya\\Desktop\\College\\Year 3\\Semester A\\BasicWeb\\HW\\Ex04\\LibraryDB";
		Connection cn = DriverManager.getConnection(urlCn, "administrator", "123456");

		UserDB userDB = new UserDB();
		User crrUser = userDB.getUser(username);

		if (crrUser != null && crrUser.getUserPas().equals(password)) {

			Cookie userName = new Cookie("username", crrUser.getUserID());
			userName.setMaxAge(60 * 60 * 24 * 7);
			userName.setPath("/");
			response.addCookie(userName);
			response.sendRedirect("SearchBookPage.jsp");
//			dispatcher = request.getRequestDispatcher("SearchStudentPage.jsp");
//			dispatcher.forward(request, response);
		} else {
//			dispatcher = request.getRequestDispatcher("SearchBookPage.jsp");
//			dispatcher.forward(request, response);
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (ClassNotFoundException ex) {
			//todo
		} catch (SQLException ex) {
			//todo
		}
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (ClassNotFoundException ex) {
			//todo
		} catch (SQLException ex) {
			//todo
		}
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
