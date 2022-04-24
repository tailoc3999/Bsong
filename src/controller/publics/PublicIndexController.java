package controller.publics;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import constants.DefineConstant;
import daos.SongDAO;
import models.Song;

public class PublicIndexController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SongDAO songDAO;
	
	public PublicIndexController() {
		super();
		songDAO = new SongDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		
		int id = 1;
		request.setAttribute("id", id);
		
		int currentPage = 1;
		try {
			currentPage = Integer.parseInt(request.getParameter("page"));
		} catch (Exception e) {
			currentPage = 1;
		}
		
		int numberOfSongs = songDAO.numberOfItems();
		int numberOfPages = (int) Math.ceil((float) numberOfSongs / DefineConstant.NUMBER_PER_PAGE);
		
		if (currentPage > numberOfPages || currentPage < 1) {
			currentPage = 1;
		}
		int offset = (currentPage - 1) * DefineConstant.NUMBER_PER_PAGE;
		
//		String sname = "";
//		if(request.getParameter("sname") != null) {
//			// tìm kiếm
//			sname = request.getParameter("sname");
//		}
//		
//		List<Song> listSearch = songDAO.findAllByName(offset, sname);
		
		List<Song> songs = songDAO.getSongsPagination(offset);
		
//		if(!"".equals(sname)) {
//			songs = listSearch;
//			numberOfSongs = songs.size();
//			numberOfPages = (int) Math.ceil((float) numberOfSongs / DefineConstant.NUMBER_PER_PAGE);
//		}
		
		request.setAttribute("songs", songs);
		request.setAttribute("numberOfSongs", numberOfSongs);
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("currentPage", currentPage);
		RequestDispatcher rd = request.getRequestDispatcher("/views/public/index.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
