<%@page import="constants.DefineConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/public/inc/header.jsp" %>
<div class="content_resize">
  <div class="mainbar">
  <%
  		Category categor1 = (Category) request.getAttribute("category");
  		if (categor1 != null) {
  %>
    <div class="article">
		<h1><%=categor1.getName() %></h1>
    </div>
    <%	}
  		@SuppressWarnings("unchecked")
		List<Song> songs = (ArrayList<Song>) request.getAttribute("songs");
		if (songs != null && songs.size() > 0) {
			int i = 0;
			for(Song song : songs) {
				i++;
				String urlSlug = request.getContextPath() + "/detail/"+ StringUtil.makeSlug(categor1.getName()) + "/" + StringUtil.makeSlug(song.getName()) + "-" + song.getId() + ".html";
    %>
    <div class="article">
      <h2><a href="<%=urlSlug %>" title="<%=song.getName() %>"><%=song.getName() %></a></h2>
      <p class="infopost">Ngày đăng: <%=song.getDate_create() %>. Lượt xem: <%=song.getCounter() %> <a href="#" class="com"><span><%=i %></span></a></p>
      <div class="clr"></div>
      <div class="img"><img src="<%=request.getContextPath() %>/files/<%=song.getPicture() %>" width="177" height="213" alt="<%=song.getName() %>" class="fl" /></div>
      <div class="post_content">
        <p><%=song.getPreview_text() %></p>
        <p class="spec"><a href="<%=urlSlug %>" class="rm">Chi tiết &raquo;</a></p>
      </div>
      <div class="clr"></div>
    </div>
    <%
			}
		} else {
    %>
    <span style='padding-left:25px; font-size: 20px; color:red; font-weight:bold; background:yellow'>Không có bài hát nào!</span>
    <%}
		int numberOfItems = (int) request.getAttribute("numberOfItems");
		int numberOfPages = (int) request.getAttribute("numberOfPages");
		int currentPage = (int) request.getAttribute("currentPage");
		if (songs != null && songs.size() > 0 && numberOfItems > DefineConstant.NUMBER_PER_PAGE) {
		%>
    <p class="pages"><small>Trang <%=currentPage %> của <%=numberOfPages %></small>
    <%
    	for (int i = 1; i <= numberOfPages; i++) {
    		if (i == currentPage) {
    %>
    <span><%=i %></span>
    <%} else { %>
    <a href="<%=request.getContextPath()%>/cat?id=<%=categor1.getId()%>&page=<%=i%>"><%=i %></a>
    <%}} 
    	if (numberOfPages != currentPage) {
    %>
    <a href="<%=request.getContextPath()%>/cat?id=<%=categor1.getId()%>&page=<%=currentPage + 1%>">&raquo;</a></p>
    <%	}
   		} %>
  </div>
  <div class="sidebar">
      <%@ include file="/templates/public/inc/leftbar.jsp" %>
  </div>
  <div class="clr"></div>
</div>
<%@ include file="/templates/public/inc/footer.jsp" %>