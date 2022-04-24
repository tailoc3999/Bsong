package controller.auth;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import daos.UserDAO;
import models.User;
import utils.AuthUtil;
import utils.StringUtil;

public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;
	
	public LoginController() {
		super();
		userDAO = new UserDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// chuyển hướng khi đã đăng nhập
		if (AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/admin/index");
			return;
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/views/admin/auth/login.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		
		if (AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/admin/index");
			return;
		}
		
		// get data
		String username = request.getParameter("username");
		String password = StringUtil.md5(request.getParameter("password"));
		
		User user = userDAO.findByUserAndPassword(username, password);
		if (user != null) {
			session.setAttribute("userInfo", user);
			response.sendRedirect(request.getContextPath() + "/admin/index");
			return;
		} else {
			response.sendRedirect(request.getContextPath() + "/auth/login?err=1");
			return;
		}
	}

}
