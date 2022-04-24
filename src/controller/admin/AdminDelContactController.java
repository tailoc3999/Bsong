package controller.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daos.ContactDAO;

public class AdminDelContactController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ContactDAO contactDAO;

	public AdminDelContactController() {
		super();
		contactDAO = new ContactDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		int id;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/admin/contact/index?err=1");
			return;
		}
		if (contactDAO.delItem(id) > 0) {
			// success
			response.sendRedirect(request.getContextPath() + "/admin/contact/index?msg=1");
			return;
		} else {
			//fail
			response.sendRedirect(request.getContextPath() + "/admin/contact/index?err=2");
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
