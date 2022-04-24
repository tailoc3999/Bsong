package controller.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daos.CategoryDAO;
import daos.SongDAO;
import daos.UserDAO;

public class AdminIndexController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CategoryDAO categoryDAO;
	private SongDAO songDAO;
	private UserDAO userDAO;

	public AdminIndexController() {
		super();
		userDAO = new UserDAO();
		categoryDAO = new CategoryDAO();
		songDAO = new SongDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int numberOfCategories = categoryDAO.numberOfItems();
		int numberOfSongs = songDAO.numberOfItems();
		int numberOfUsers = userDAO.numberOfItems();
		
		request.setAttribute("numberOfCategories", numberOfCategories);
		request.setAttribute("numberOfSongs", numberOfSongs);
		request.setAttribute("numberOfUsers", numberOfUsers);
		RequestDispatcher rd = request.getRequestDispatcher("/views/admin/index.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
