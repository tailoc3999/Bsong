package controller.admin;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import daos.UserDAO;
import models.User;
import utils.StringUtil;

public class AdminAddUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;

	public AdminAddUserController() {
		super();
		userDAO = new UserDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userInfo");
		// chỉ admin mới có quyền thêm người dùng
		if (!"admin".equals(userLogin.getUserName())) {
			response.sendRedirect(request.getContextPath() + "/admin/user/index?err=4");
			return;
		}

		RequestDispatcher rd = request.getRequestDispatcher("/views/admin/user/add.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userInfo");
		// chỉ admin mới có quyền thêm người dùng
		if (!"admin".equals(userLogin.getUserName())) {
			response.sendRedirect(request.getContextPath() + "/admin/user/index?err=4");
			return;
		}

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String fullname = request.getParameter("fullname");
		// validation
		// del khoảng trắng
		username = StringUtil.delSpace(username);
		password = StringUtil.delSpace(password);
		fullname = StringUtil.delSpace(fullname);
		// kiểm tra ký tự đặc biệt username
		String usernameF = StringUtil.makeSlug2(username);
		Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(usernameF);
		boolean b = m.find();
		if (b) {
			response.sendRedirect(request.getContextPath() + "/admin/user/add?errn=1");
			return;
		}
		// kiểm tra độ dài chuỗi
		if (username.length() < 5) {
			response.sendRedirect(request.getContextPath() + "/admin/user/add?errn=2");
			return;
		}
		// kiểm tra ký tự đặc biệt password
		String passwordF = StringUtil.makeSlug2(password);
		Pattern pp = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		Matcher mp = pp.matcher(passwordF);
		boolean bp = mp.find();
		if (bp) {
			response.sendRedirect(request.getContextPath() + "/admin/user/add?errp=1");
			return;
		}
		// kiểm tra độ dài chuỗi
		if (password.length() < 6) {
			response.sendRedirect(request.getContextPath() + "/admin/user/add?errp=2");
			return;
		}
		// kiểm tra ký tự đặc biệt fullname
		String fullnameF = StringUtil.makeSlug2(fullname);
		Pattern pfn = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		Matcher mfn = pfn.matcher(fullnameF);
		boolean bfn = mfn.find();
		if (bfn) {
			response.sendRedirect(request.getContextPath() + "/admin/user/add?errf=1");
			return;
		}
		// kiểm tra độ dài chuỗi
		if (fullname.length() < 10) {
			response.sendRedirect(request.getContextPath() + "/admin/user/add?errf=2");
			return;
		}

		String url = "";
		StringBuilder sbd;
		if (userDAO.hasUser(username)) {
			sbd = new StringBuilder();
			url = sbd.append("/views/admin/user/add.jsp?err=2").toString();
			RequestDispatcher rd = request.getRequestDispatcher(url);
			rd.forward(request, response);
			return;
		}

		password = StringUtil.md5(password);

		User Item = new User(username, password, fullname);

		if (userDAO.addItem(Item) > 0) {
			// success
			sbd = new StringBuilder();
			url = sbd.append(request.getContextPath()).append("/admin/user/index?msg=1").toString();
			response.sendRedirect(url);
			return;
		} else {
			// fail
			RequestDispatcher rd = request.getRequestDispatcher("/views/admin/user/add.jsp?err=1");
			rd.forward(request, response);
			return;
		}
	}

}
