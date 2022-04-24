package controller.publics;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daos.ContactDAO;
import models.Contact;
import utils.StringUtil;

public class PublicContactController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ContactDAO contactDAO;

	public PublicContactController() {
		super();
		contactDAO = new ContactDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		int id = 2;
		request.setAttribute("id", id);
		
		RequestDispatcher rd = request.getRequestDispatcher("/views/public/contact.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String website = request.getParameter("website");
		String message = request.getParameter("message");
		// validation
		name = StringUtil.delSpace(name);
		String nameF = StringUtil.makeSlug2(name);
		// kiểm tra ký tự đặc biệt
		Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(nameF);
		boolean b = m.find();
		if (b) {
			response.sendRedirect(request.getContextPath() + "/contact?err=2");
			return;
		}
		// kiểm tra độ dài chuỗi
		if (name.length() < 10) {
			response.sendRedirect(request.getContextPath() + "/contact?err=3");
			return;
		}
		
		Contact contact = new Contact(name, email, website, message);
		if (contactDAO.sendContact(contact) > 0) {
			// success
			response.sendRedirect(request.getContextPath() + "/contact?msg=1");
			return;
		} else {
			//fail
			response.sendRedirect(request.getContextPath() + "/contact?err=1");
			return;
			
		}
	}

}
