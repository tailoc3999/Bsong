package controller.admin;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daos.CategoryDAO;
import models.Category;
import utils.StringUtil;

public class AdminEditCatController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CategoryDAO categoryDAO;

	public AdminEditCatController() {
		super();
		categoryDAO = new CategoryDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");

		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/admin/cat/index?msg=2");
			return;
		}
		Category item = categoryDAO.getItemById(id);
		request.setAttribute("cat", item);
		RequestDispatcher rd = request.getRequestDispatcher("/views/admin/cat/edit.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/admin/cat/index?msg=2");
			return;
		}
		String name = request.getParameter("name");
		// validate

		// del Space
		name = StringUtil.delSpace(name);
		String nameF = StringUtil.makeSlug2(name);
		// kiểm tra ký tự đặc biệt
		Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(nameF);
		boolean b = m.find();
		if (b) {
			response.sendRedirect(request.getContextPath() + "/admin/cat/edit?err=1&id=" + id);
			return;
		}
		// kiểm tra độ dài chuỗi
		if (name.length() < 5) {
			response.sendRedirect(request.getContextPath() + "/admin/cat/edit?err=2&id=" + id);
			return;
		}

		// check trùng
		Category category = new Category(id, name);
		if (categoryDAO.checkName(category) > 0) {
			response.sendRedirect(request.getContextPath() + "/admin/cat/edit?err=3&id=" + id);
			return;
		}
		
		if (categoryDAO.editItem(category) > 0) {
			// success
			response.sendRedirect(request.getContextPath() + "/admin/cat/index?msg=1");
			return;
		} else {
			// fail
			response.sendRedirect(request.getContextPath() + "/admin/cat/edit?err=4&id=" + id);
			return;
		}
	}
}
