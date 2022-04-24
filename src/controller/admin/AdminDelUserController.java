package controller.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import daos.UserDAO;
import models.User;

public class AdminDelUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;

	public AdminDelUserController() {
		super();
		userDAO = new UserDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/admin/user/index?err=1");
			return;
		}
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userInfo");
		User user = userDAO.getItem(id);
		if("admin".equals(user.getUserName())) {
			// không được xóa
			response.sendRedirect(request.getContextPath() + "/admin/user/index?err=6");
			return;
		} else {
			if ("admin".equals(userLogin.getUserName())) {
				// được xóa
				if(userDAO.delItem(id) > 0) {
					// success
					response.sendRedirect(request.getContextPath() + "/admin/user/index?msg=3");
					return;
				} else {
					// fail
					response.sendRedirect(request.getContextPath() + "/admin/user/index?err=3");
					return;
				}
			} else {
				// không được xóa
				response.sendRedirect(request.getContextPath() + "/admin/user/index?err=6");
				return;
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
