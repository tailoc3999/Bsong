package controller.publics;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import daos.SongDAO;
import models.Song;

public class PublicDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SongDAO songDAO;
	
	public PublicDetailController() {
		super();
		songDAO = new SongDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text.html");
		
		int id;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/404");
			return;
		}
		
		Song song = songDAO.getById(id);
		if(song == null) {
			response.sendRedirect(request.getContextPath() + "/404");
			return;
		}
		
		HttpSession session = request.getSession();
		String hasVisited = (String) session.getAttribute("hasVisited: " + id);
		if (hasVisited == null) {
			session.setAttribute("hasVisited: " + id, "yes");
			session.setMaxInactiveInterval(600);
			// increase page view
			songDAO.increaseView(id);
		}
		
		request.setAttribute("song", song);
		
		ArrayList<Song> songs = songDAO.relatedSong(song);
		request.setAttribute("related", songs);
		
		RequestDispatcher rd = request.getRequestDispatcher("/views/public/detail.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
