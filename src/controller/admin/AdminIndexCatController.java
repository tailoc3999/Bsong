package controller.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import constants.DefineConstant;
import daos.CategoryDAO;
import models.Category;

public class AdminIndexCatController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CategoryDAO categoryDAO;
	
	public AdminIndexCatController() {
		super();
		categoryDAO = new CategoryDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");

		// get number of songs
		int numberOfCats = categoryDAO.numberOfItems();
		int numberOfPages = (int) Math.ceil((float) numberOfCats / DefineConstant.NUMBER_PER_PAGE_CAT);

		int currentPage = 1;
		try {
			currentPage = Integer.parseInt(request.getParameter("page"));
		} catch (NumberFormatException e) {

		}

		if (currentPage > numberOfPages || currentPage < 1) {
			currentPage = 1;
		}

		int offset = (currentPage - 1) * DefineConstant.NUMBER_PER_PAGE_CAT;

		List<Category> categories = categoryDAO.getCategoriesPagination(offset);
		request.setAttribute("numberOfCats", numberOfCats);
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("catlist", categories);
		RequestDispatcher rd = request.getRequestDispatcher("/views/admin/cat/index.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
