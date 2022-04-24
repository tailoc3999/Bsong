<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/public/inc/header.jsp" %>
<div class="content_resize">
  <div class="mainbar">
    <div class="article">
    <%
    	CategoryDAO catDAO = new CategoryDAO();
    	Song item = (Song) request.getAttribute("song");
    	if (item != null) {
    %>
      <h1><%=item.getName() %></h1>
      <div class="clr"></div>
      <p>Ngày đăng: <%=item.getDate_create() %>. Lượt xem: <%=item.getCounter() %></p>
      <div class="vnecontent">
          <%=item.getDetail_text() %>
      </div>
      <%} %>
    </div>
    <div class="article">
    	<h2>Bài viết liên quan</h2>
    <%
    	@SuppressWarnings("unchecked")
    	ArrayList<Song> items = (ArrayList<Song>) request.getAttribute("related");
    	if (items != null && items.size() > 0) {
    		Category category;
    		for (Song songRelation : items) {
    			category = catDAO.getItemById(songRelation.getCategory().getId());
    			String urlSlug = request.getContextPath() + "/detail/"+ StringUtil.makeSlug(category.getName()) + "/" + StringUtil.makeSlug(songRelation.getName()) + "-" + songRelation.getId() + ".html";
    		
    %>
      
      <div class="clr"></div>
      <div class="comment"> <a href="<%=urlSlug %>"><img src="<%=request.getContextPath() %>/files/<%=songRelation.getPicture() %>" width="40" height="40" alt="" class="userpic" /></a>
        <h2><a href="<%=urlSlug %>"><%=songRelation.getName() %></a></h2>
        <p><%=songRelation.getPreview_text() %></p>
      </div>
      <%
    		}
    	} else {
      %>
      <span style='font-size: 20px; color:red'>Không có bài hát liên quan</span>
      <%} %>
    </div>
  </div>
  <div class="sidebar">
  <%@ include file="/templates/public/inc/leftbar.jsp" %>
  </div>
  <div class="clr"></div>
</div>
<%@ include file="/templates/public/inc/footer.jsp" %>
