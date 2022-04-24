<%@page import="utils.StringUtil"%>
<%@page import="daos.SongDAO"%>
<%@page import="daos.CategoryDAO"%>
<%@page import="models.Song"%>
<%@page import="java.util.ArrayList"%>
<%@page import="models.Category"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<div class="searchform">
  <form id="formsearch" name="formsearch" method="get" action="<%=request.getContextPath()%>/home">
    <span>
    <input name="sname" class="editbox_search" id="editbox_search" maxlength="80" value="" placeholder="Tìm kiếm bài hát" type="text" />
    </span>
    <input name="button_search" src="<%=request.getContextPath() %>/resources/public/images/search.jpg" class="button_search" type="image" />
  </form>
</div>
<div class="clr"></div>
<div class="gadget">
  <h2 class="star">Danh mục bài hát</h2>
  <div class="clr"></div>
  <ul class="sb_menu">
  	<%
  		CategoryDAO categoryDAO = new CategoryDAO();
  		List<Category> categories = categoryDAO.getCategories();
  		if (categories.size() > 0) {
  			for (Category category : categories) {
  				int id = category.getId();
  				String name = category.getName();
  				String urlSlug = request.getContextPath() + "/category/" + StringUtil.makeSlug(category.getName()) + "-" + category.getId() + ".html";
  			
  	%>
    <li><a href="<%=urlSlug %>"><%=name %></a></li>
    <%
  			}
  		}
    %>
  </ul>
</div>

<div class="gadget">
  <h2 class="star"><span>Bài hát mới</span></h2>
  <div class="clr"></div>
  <ul class="ex_menu">
  	<%
  		SongDAO songDAO = new SongDAO();
  		List<Song> recentSong = songDAO.getSong(6);
  		if (recentSong.size() > 0) {
  			Category category;
  			for (Song song : recentSong) {
  				category = categoryDAO.getItemById(song.getCategory().getId());
  				String urlSlug = request.getContextPath() + "/detail/"+ StringUtil.makeSlug(category.getName()) + "/" + StringUtil.makeSlug(song.getName()) + "-" + song.getId() + ".html";
  				
  				int id = song.getId();
  				String name = song.getName();
  				String previewText = song.getPreview_text();
  	%>
    <li><a href="<%=urlSlug %>"><%=name %></a><br />
      <%if (previewText.length() > 50) out.print(previewText.substring(0, 50) + "..."); else out.print(previewText); %></li>
    <%
			}
  		}
    %>  
  </ul>
</div>