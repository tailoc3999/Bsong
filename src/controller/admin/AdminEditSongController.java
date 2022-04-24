package controller.admin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import daos.CategoryDAO;
import daos.SongDAO;
import models.Category;
import models.Song;
import utils.FileUtil;
import utils.StringUtil;

@MultipartConfig
public class AdminEditSongController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SongDAO songDAO;
	private CategoryDAO categoryDAO;

	public AdminEditSongController() {
		super();
		songDAO = new SongDAO();
		categoryDAO = new CategoryDAO();
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
			response.sendRedirect(request.getContextPath() + "/admin/song/index?err=2");
			return;
		}
		Song item = songDAO.getById(id);
		request.setAttribute("song", item);

		CategoryDAO categoryDAO = new CategoryDAO();
		List<Category> categories = categoryDAO.getCategories();
		request.setAttribute("categories", categories);

		RequestDispatcher rd = request.getRequestDispatcher("/views/admin/song/edit.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");

		int id;
		int catID;
		try {
			id = Integer.parseInt(request.getParameter("id"));
			catID = Integer.parseInt(request.getParameter("category"));
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/admin/song/index?err=2");
			return;
		}

		String name = request.getParameter("name");
		String description = request.getParameter("preview");
		String detail = request.getParameter("detail");
		Part filePart = request.getPart("picture");
		// validation
		// del khoảng trắng
		description = StringUtil.delSpace(description);
		name = StringUtil.delSpace(name);
		// kiểm tra ký tự đặc biệt
		String nameF = StringUtil.makeSlug2(name);
		Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(nameF);
		boolean b = m.find();
		if (b) {
			response.sendRedirect(request.getContextPath() + "/admin/song/edit?errn=1&id=" + id);
			return;
		}
		// kiểm tra độ dài chuỗi
		if (name.length() < 3) {
			response.sendRedirect(request.getContextPath() + "/admin/song/edit?errn=2&id=" + id);
			return;
		}
		if (description.length() < 20) {
			response.sendRedirect(request.getContextPath() + "/admin/cat/edit?errp=2&id=" + id);
			return;
		}

		Song song = songDAO.getById(id);
		if (song == null) {
			response.sendRedirect(request.getContextPath() + "/admin/song/index?err=2");
			return;
		}
		// tạo thư mục lưu ảnh
		final String dirPartName = request.getServletContext().getRealPath("/files");
		File dirFile = new File(dirPartName);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		// lấy tên file từ part
		String fileName = FileUtil.getName(filePart);
		// đổi tên file
		String picture = "";
		String oldPicture = song.getPicture();
		if (fileName.isEmpty()) {
			picture = song.getPicture();
		} else {
			picture = FileUtil.rename(fileName);
		}
		// đường dẫn file
		String filePartName = dirPartName + File.separator + picture;

		song.setName(name);
		song.setPreview_text(description);
		song.setDetail_text(detail);
		song.setPicture(picture);
		song.setCategory(new Category(catID));
		int countRecordInserted = songDAO.editSong(song);
		if (countRecordInserted > 0) {
			// success
			if (!fileName.isEmpty()) {
				// xóa file cũ
				String oldFilePartName = dirPartName + File.separator + oldPicture;
				System.out.println(oldFilePartName);
				File oldFile = new File(oldFilePartName);
				if (oldFile.exists()) {
					oldFile.delete();
				//	System.out.println("đã xóa");
				} else {
				//	System.out.println("chưa xóa");
				}
				// ghi file
				filePart.write(filePartName);
			}
			response.sendRedirect(request.getContextPath() + "/admin/song/index?msg=2");
			return;
		} else {
			// fail
			List<Category> categories = categoryDAO.getCategories();

			request.setAttribute("categories", categories);
			RequestDispatcher rd = request.getRequestDispatcher("/views/admin/song/edit.jsp?err=2");
			rd.forward(request, response);
			return;
		}

	}
}
