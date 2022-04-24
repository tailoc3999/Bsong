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
import utils.StringUtil;

@MultipartConfig
public class AdminAddSongController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private CategoryDAO categoryDAO;

	public AdminAddSongController() {
		super();
		categoryDAO = new CategoryDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");

		List<Category> categories = categoryDAO.getCategories();

		request.setAttribute("categories", categories);
		RequestDispatcher rd = request.getRequestDispatcher("/views/admin/song/add.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		int catID = 0;
		try {
			catID = Integer.parseInt(request.getParameter("category"));
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/admin/song/index?err=1");
			return;
		}
		SongDAO songDAO = new SongDAO();
		String name = request.getParameter("name");
		String description = request.getParameter("preview");
		String detail = request.getParameter("detail");
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
			response.sendRedirect(request.getContextPath() + "/admin/song/add?errn=1");
			return;
		}
		// kiểm tra độ dài chuỗi
		if (name.length() < 3) {
			response.sendRedirect(request.getContextPath() + "/admin/song/add?errn=2");
			return;
		}
		
		if (description.length() < 20) {
			response.sendRedirect(request.getContextPath() + "/admin/cat/add?errp=2");
			return;
		}

		Part filePart = request.getPart("picture");
		String fileName = filePart.getSubmittedFileName();
		if (!"".equals(fileName)) {
			String rootPath = request.getServletContext().getRealPath("");
			String dirUploadPath = rootPath + "files";
			File createDir = new File(dirUploadPath);

			if (!createDir.exists()) {
				createDir.mkdir();
			}
			// string, string builder
			StringBuilder sb = new StringBuilder();
			String filePath = sb.append(dirUploadPath).append(File.separator).append(fileName).toString();
			filePart.write(filePath); // Truyền vào đường dẫn upload file
			System.out.println("dirUploadPath :" + dirUploadPath);
		}
		Song song = new Song(name, description, detail, fileName, new Category(catID));
		int countRecordInserted = songDAO.add(song);
		if (countRecordInserted > 0) {
			// success
			response.sendRedirect(request.getContextPath() + "/admin/song/index?msg=1");
			return;
		} else {
			// fail
			List<Category> categories = categoryDAO.getCategories();

			request.setAttribute("categories", categories);
			RequestDispatcher rd = request.getRequestDispatcher("/views/admin/song/add.jsp?err=1");
			rd.forward(request, response);
			return;
		}
	}

}
