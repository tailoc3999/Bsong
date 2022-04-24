package controller.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daos.CategoryDAO;

public class AdminDelCatController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CategoryDAO categoryDAO;

	public AdminDelCatController() {
		super();
		categoryDAO = new CategoryDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/admin/cat/index?msg=2");
			return;
		}
		
		if (categoryDAO.delItem(id) > 0) {
			// success
			response.sendRedirect(request.getContextPath() + "/admin/cat/index?msg=3");
			return;
		} else {
			// fail
			response.sendRedirect(request.getContextPath() + "/admin/cat/index?msg=4");
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
