package controller.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import constants.DefineConstant;
import daos.UserDAO;
import models.User;

public class AdminIndexUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserDAO userDAO;
	
	public AdminIndexUserController() {
		super();
		userDAO = new UserDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		// get number of songs
		int numberOfUsers = userDAO.numberOfItems();
		int numberOfPages = (int) Math.ceil((float) numberOfUsers / DefineConstant.NUMBER_PER_PAGE_USER);

		int currentPage = 1;
		try {
			currentPage = Integer.parseInt(request.getParameter("page"));
		} catch (NumberFormatException e) {

		}

		if (currentPage > numberOfPages || currentPage < 1) {
			currentPage = 1;
		}

		int offset = (currentPage - 1) * DefineConstant.NUMBER_PER_PAGE_USER;

		List<User> users = userDAO.getItemsPagination(offset);
		request.setAttribute("userlist", users);
		request.setAttribute("numberOfUsers", numberOfUsers);
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("currentPage", currentPage);
		RequestDispatcher rd = request.getRequestDispatcher("/views/admin/user/index.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
