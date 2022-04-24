package controller.admin;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import constants.GlobalConstant;
import daos.CategoryDAO;
import models.Category;
import utils.StringUtil;


public class AdminAddCatController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminAddCatController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/views/admin/cat/add.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		
		CategoryDAO categoryDAO = new CategoryDAO();
		String name = request.getParameter("name");
		// validate
		
		// del ky tự khoảng trắng
		name = StringUtil.delSpace(name);
		String nameF = StringUtil.makeSlug2(name);
		// kiểm tra ký tự đặc biệt
		Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(nameF);
		boolean b = m.find();
		if (b) {
			response.sendRedirect(request.getContextPath() + "/admin/cat/add?err=1");
			return;
		}
		// kiểm tra độ dài chuỗi
		if (name.length() < 5) {
			response.sendRedirect(request.getContextPath() + "/admin/cat/add?err=2");
			return;
		}
		
		// check trùng
		Category category = new Category(name);
		if (categoryDAO.checkName(category) > 0) {
			response.sendRedirect(request.getContextPath() + "/admin/cat/add?err=3");
			return;
		}
	 	int countRecordInserted = categoryDAO.add(category);
	 	String url = "";
	 	StringBuilder sbd = new StringBuilder();
	 	if(countRecordInserted > 0) {
	 		// success
	 		sbd = new StringBuilder();
	 		url = sbd.append(request.getContextPath()).append("/admin/cat/index?msg=").append(GlobalConstant.SUCCESS).toString();
	 		response.sendRedirect(url);
	 		return;
	 	} 
	 	// fail
 		url = sbd.append(request.getContextPath()).append("/admin/cat/index?msg=").append(GlobalConstant.ERROR).toString();
	 	response.sendRedirect(url);
	}

}
