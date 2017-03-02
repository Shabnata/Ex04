
package Controller;

import DB.UserDB;
import Model.*;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet{

	ServletContext sc;

	@Override
	public void init(){
		this.sc = this.getServletContext();

	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String username = request.getParameter("Username");
		String password = request.getParameter("Password");
		RequestDispatcher dispatcher;

		UserDB userDB = new UserDB();
		User crrUser = userDB.getUser(username);

		if(crrUser != null && crrUser.getUserPas().equals(password)){

			Cookie userName = new Cookie("username", crrUser.getUserID());
			userName.setMaxAge(60 * 60 * 24 * 7);
			userName.setPath("/");
			response.addCookie(userName);
			response.sendRedirect("SearchBookPage.jsp");

		} else {
			dispatcher = request.getRequestDispatcher("ErrorLogin.jsp");
			dispatcher.forward(request, response);
		}
		if(userDB != null){
			userDB.closeConnection();
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException{
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException{
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
