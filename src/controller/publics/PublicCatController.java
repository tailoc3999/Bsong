package controller.publics;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import constants.DefineConstant;
import daos.CategoryDAO;
import daos.SongDAO;
import models.Category;
import models.Song;


public class PublicCatController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SongDAO songDAO;
	private CategoryDAO categoryDAO;
	
	public PublicCatController() {
		super();
		songDAO = new SongDAO();
		categoryDAO = new CategoryDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		int id;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/404");
			return;
		}
		
		int currentPage = 1;
		try {
			currentPage = Integer.parseInt(request.getParameter("page"));
		} catch (Exception e) {
			currentPage = 1;
		}
		
		Category category = categoryDAO.getItemById(id);
		if (category == null) {
			response.sendRedirect(request.getContextPath() + "/404");
			return;
		}
		
		int numberOfItems = songDAO.numberOfItems(id);
		int numberOfPages = (int) Math.ceil((float) numberOfItems / DefineConstant.NUMBER_PER_PAGE);
		
		if (currentPage > numberOfPages || currentPage < 1) {
			currentPage = 1;
		}
		int offset = (currentPage - 1) * DefineConstant.NUMBER_PER_PAGE;
		
		List<Song> songs = songDAO.getSongPagination(category , offset);
		request.setAttribute("songs", songs);
		request.setAttribute("category", category);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("numberOfItems", numberOfItems);
		request.setAttribute("numberOfPages", numberOfPages);
		
		RequestDispatcher rd = request.getRequestDispatcher("/views/public/cat.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
