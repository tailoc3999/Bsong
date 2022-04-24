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

public class AdminEditUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO; 

	public AdminEditUserController() {
		super();
		userDAO = new UserDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		
		int id;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/admin/user/index?err=2");
			return;
		}
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userInfo");
		if ("admin".equals(userLogin.getUserName()) || id == userLogin.getId()) {
			User item = userDAO.getItem(id);
			if (item != null) {
				request.setAttribute("user", item);
				RequestDispatcher rd = request.getRequestDispatcher("/views/admin/user/edit.jsp");
				rd.forward(request, response);
			} else {
				response.sendRedirect(request.getContextPath() + "/admin/user/index?err=2");
				return;
			}
		} else {
			// không có quyền
			response.sendRedirect(request.getContextPath() + "/admin/user/index?err=5");
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		
		int id;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/admin/user/index?err=2");
			return;
		}
		
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userInfo");
		// phân quyền
		if ("admin".equals(userLogin.getUserName()) || id == userLogin.getId()) {
			User item = userDAO.getItem(id);
			if (item == null) {
				response.sendRedirect(request.getContextPath() + "/admin/user/index?err=2");
				return;
			}
			String password = request.getParameter("password");
			// del Space
			password = password.replaceAll("\s", "");
			// kiểm tra ký tự đặc biệt password
			String passwordF = StringUtil.makeSlug2(password);
			Pattern pp = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
			Matcher mp = pp.matcher(passwordF);
			boolean bp = mp.find();
			if (bp) {
				response.sendRedirect(request.getContextPath() + "/admin/user/add?errp=1&id=" + id);
				return;
			}
			// kiểm tra độ dài chuỗi
			if (password.length() < 6) {
				response.sendRedirect(request.getContextPath() + "/admin/user/add?errp=2id=" + id);
				return;
			}
			
			if ("".equals(password)) {
				password = item.getPassWord();
			} else {
				password = StringUtil.md5(password);
			}
			String fullname = request.getParameter("fullname");
			fullname = StringUtil.delSpace(fullname);
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
			
			item.setPassWord(password);
			item.setFullName(fullname);
			String url = "";
			StringBuilder sbd;
			
			if(userDAO.editItem(item) > 0) {
				// success
				sbd = new StringBuilder();
				url = sbd.append(request.getContextPath()).append("/admin/user/index?msg=2").toString();
				response.sendRedirect(url);
				return;
			} else {
				// fail
				RequestDispatcher rd = request.getRequestDispatcher("/views/admin/user/edit.jsp?err=1");
				rd.forward(request, response);
				return;
			}
		} else {
			// không có quyền
			response.sendRedirect(request.getContextPath() + "/admin/user/index?err=5");
			return;
		}
	}
}
