
package Controller;

import DB.CategoryDB;
import Model.Category;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AddCategoryServlet", urlPatterns = {"/AddCategoryServlet"})
public class AddCategoryServlet extends HttpServlet{

	ServletContext sc;

	@Override
	public void init(){
		this.sc = this.getServletContext();
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		CategoryDB cDB = new CategoryDB();
		RequestDispatcher dispatcher;
		String catName = request.getParameter("category");

		Boolean added = cDB.addCategory(catName);
		if(!added){
			dispatcher = request.getRequestDispatcher("AddCategoryPageNotAdded.jsp");
			dispatcher.forward(request, response);

		} else {
			Category ct = cDB.getCategory(request.getParameter("category"));
			request.setAttribute("category", ct);
			dispatcher = request.getRequestDispatcher("AddCategoryPageResult.jsp");
			dispatcher.forward(request, response);
		}

		if(cDB != null){
			cDB.closeConnection();
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

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException{

		processRequest(request, response);
	}

	@Override
	public String getServletInfo(){
		return "Short description";
	}// </editor-fold>

}
