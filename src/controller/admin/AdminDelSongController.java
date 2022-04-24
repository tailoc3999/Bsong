package controller.admin;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daos.SongDAO;
import models.Song;

public class AdminDelSongController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SongDAO songDAO;

	public AdminDelSongController() {
		super();
		songDAO = new SongDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/admin/song/index?err=2");
			return;
		}
		
		Song song = songDAO.getById(id);
		if (song == null) {
			response.sendRedirect(request.getContextPath() + "/admin/song/index?err=2");
			return;
		}
		
		if(songDAO.delItem(id) > 0) {
			// success
			// xóa ảnh
			final String dirPartName = request.getServletContext().getRealPath("/files");
			String picture = song.getPicture();
			if (!picture.isEmpty()) {
				String filePartName = dirPartName + File.separator + picture;
				System.out.println(filePartName);
				File file = new File(filePartName);
				if (file.exists()) {
					file.delete();
				}
			}
			
			response.sendRedirect(request.getContextPath() + "/admin/song/index?msg=3");
			return;
		} else {
			// fail
			response.sendRedirect(request.getContextPath() + "/admin/song/index?err=3");
			return;
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
