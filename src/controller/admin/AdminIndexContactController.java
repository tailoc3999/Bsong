package controller.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import constants.DefineConstant;
import daos.ContactDAO;
import models.Contact;

public class AdminIndexContactController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ContactDAO contactDAO;

	public AdminIndexContactController() {
		super();
		contactDAO = new ContactDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");

		// get number of songs
		int numberOfContacts = contactDAO.numberOfItems();
		int numberOfPages = (int) Math.ceil((float) numberOfContacts / DefineConstant.NUMBER_PER_PAGE_CONTACT);

		int currentPage = 1;
		try {
			currentPage = Integer.parseInt(request.getParameter("page"));
		} catch (NumberFormatException e) {

		}

		if (currentPage > numberOfPages || currentPage < 1) {
			currentPage = 1;
		}

		int offset = (currentPage - 1) * DefineConstant.NUMBER_PER_PAGE_CONTACT;

		String cname = "";
		if (request.getParameter("cname") != null) {
			// tìm kiếm
			cname = request.getParameter("cname");
		}

		List<Contact> listSearch = contactDAO.findAllByName(offset, cname);

		List<Contact> contacts = contactDAO.getAllItemPagination(offset);

		if (!"".equals(cname)) {
			contacts = listSearch;
			numberOfContacts = contacts.size();
			numberOfPages = (int) Math.ceil((float) numberOfContacts / DefineConstant.NUMBER_PER_PAGE_CONTACT);
		}

		request.setAttribute("contacts", contacts);
		request.setAttribute("numberOfContacts", numberOfContacts);
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("currentPage", currentPage);
		RequestDispatcher rd = request.getRequestDispatcher("/views/admin/contact/index.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
