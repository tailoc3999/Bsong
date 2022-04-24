<%@page import="utils.StringUtil"%>
<%@page import="constants.DefineConstant"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="java.sql.Time"%>
<%@page import="models.Song"%>
<%@page import="java.util.ArrayList"%>
<%@page import="models.Category"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/admin/inc/header.jsp" %>
<%@ include file="/templates/admin/inc/leftbar.jsp" %>
<div id="page-wrapper">
    <div id="page-inner">
        <div class="row">
            <div class="col-md-12">
                <h2>Quản lý bài hát</h2>
            </div>
        </div>
        <!-- /. ROW  -->
        	<%
        		String err = request.getParameter("err");
        		String msg = request.getParameter("msg");
        		
        		if ("1".equals(msg)) {
        			out.print("<span style='color:green; background:yellow; font-weight:bold'>Thêm bài hát thành công</span>");
        		}
        		if ("2".equals(msg)) {
        			out.print("<span style='color:green; background:yellow; font-weight:bold'>Sửa bài hát thành công</span>");
        		}
        		if ("3".equals(msg)) {
        			out.print("<span style='color:green; background:yellow; font-weight:bold'>Xóa bài hát thành công</span>");
        		}
        		if ("1".equals(err)) {
        			out.print("<span style='color:red; background:yellow; font-weight:bold'>catID không tồn tại</span>");
        		}
        		if ("2".equals(err)) {
        			out.print("<span style='color:red; background:yellow; font-weight:bold'>ID không tồn tại</span>");
        		}
        		if ("3".equals(err)) {
        			out.print("<span style='color:red; background:yellow; font-weight:bold'>Có lỗi khi xóa</span>");
        		}
        	%>
        <hr />
        <div class="row">
            <div class="col-md-12">
                <!-- Advanced Tables -->
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="table-responsive">
                            <div class="row">
                                <div class="col-sm-6">
                                    <a href="<%=request.getContextPath() %>/admin/song/add" class="btn btn-success btn-md">Thêm</a>
                                </div>
                                <div class="col-sm-6" style="text-align: right;">
                                    <form method="get" action="<%=request.getContextPath() %>/admin/song/index">
                                        <input type="submit" name="search" value="Tìm kiếm" class="btn btn-warning btn-sm" style="float:right" />
                                        <input type="search" name="sname" class="form-control input-sm" placeholder="Nhập tên bài hát" style="float:right; width: 300px;" />
                                        <div style="clear:both"></div>
                                    </form><br />
                                </div>
                            </div>

                            <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Tên bài hát</th>
                                        <th>Mô tả</th>
                                        <th>Chi tiết</th>
                                        <th>Ngày đăng</th>
                                        <th>Hình ảnh</th>
                                        <th>Lượt nghe</th>
                                        <th>Loại nhạc</th>
                                        <th width="160px">Chức năng</th>
                                    </tr>
                                </thead>
                                <tbody>
                                	<%
                                	@SuppressWarnings("unchecked")
                        			List<Song> songs = (List<Song>) request.getAttribute("songs");
                                		if(songs != null && songs.size() > 0) {
                                				for(Song song : songs) {
                                					int id = song.getId();
                                					String songName = song.getName();
                                					int counter = song.getCounter();
                                					String picture = song.getPicture();
                                					String detail = song.getDetail_text();
                                					String preview = song.getPreview_text();
                                					Timestamp date_create = song.getDate_create();
                                					int cat_id = song.getCategory().getId();
                                					String catName = song.getCategory().getName();
                                					String urlSlug = request.getContextPath() + "/admin/song/edit/" + StringUtil.makeSlug(songName) + "-" + id + ".html";
                                	%>
                                    <tr>
                                        <td><%=id %></td>
                                        <td class="center"><%=songName %></td>
                                        <td class="center"><%=preview %></td>
                                        <td class="center"><%=detail %></td>
                                        <td class="center"><%=date_create %></td>
                                        <td class="center">
                                        <%
                                        	if(!"".equals(picture))	{
                                       	%>
											<img width="200px" height="200px" src="<%=request.getContextPath() %>/files/<%=picture %>" alt="<%=songName %>"/>
                                        <%} else { %>
                                        Không có hình ảnh!
                                        <%} %>
                                        </td>
                                        <td class="center"><%=counter %></td>
                                        <td class="center"><%=catName %></td>
                                        <td class="center">
                                            <a href="<%=urlSlug %>" title="" class="btn btn-primary"><i class="fa fa-edit "></i> Sửa</a>
                                            <a href="<%=request.getContextPath() %>/admin/song/del?id=<%=id %>" title="" class="btn btn-danger" onclick="return confirm('Bạn có chắc chắn muốn xóa?');"><i class="fa fa-pencil"></i> Xóa</a>
                                        </td>
                                    </tr>
                                    <%
                                				}
                                		} else {  %>
                                		<tr><td colspan="9" align="center">Chưa có bài hát nào!</td></tr>
                                   <%} %>
                                </tbody>
                            </table>
                            <%
                            	int numberOfSongs = (int) request.getAttribute("numberOfSongs");
                            	int numberOfPages = (int) request.getAttribute("numberOfPages");
                            	int currentPage = (int) request.getAttribute("currentPage");
                            	if (songs != null && songs.size() > 0 && numberOfSongs > DefineConstant.NUMBER_PER_PAGE) {
                            		
                            %>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="dataTables_info" id="dataTables-example_info" style="margin-top:27px">Hiển thị từ <%=(currentPage - 1) * DefineConstant.NUMBER_PER_PAGE + 1 %> đến <%=(currentPage - 1) * DefineConstant.NUMBER_PER_PAGE + DefineConstant.NUMBER_PER_PAGE %> của <%=numberOfSongs %> bài hát</div>
                                </div>
                                <div class="col-sm-6" style="text-align: right;">
                                    <div class="dataTables_paginate paging_simple_numbers" id="dataTables-example_paginate">
                                        <ul class="pagination">
                                        	<%
                                        		if (currentPage != 1) {
                                        	%>
                                            <li class="paginate_button previous disabled" aria-controls="dataTables-example" tabindex="0" id="dataTables-example_previous"><a href="<%=request.getContextPath() %>/admin/song/index?page=<%=currentPage - 1%>">Trang trước</a></li>
                                         	<% }
                                         		String active = "";
                                         		for (int i = 1; i <= numberOfPages; i++) {
                                         			if (i == currentPage){
                                         				active = " active";
                                         			} else {
                                         				active = "";
                                         			}
                                         	%>
                                            <li class="paginate_button <%=active %>" aria-controls="dataTables-example" tabindex="0"><a href="<%=request.getContextPath() %>/admin/song/index?page=<%=i %>"><%=i %></a></li>
											<%} 
												if (currentPage != numberOfPages) {
											%>
                                            <li class="paginate_button next" aria-controls="dataTables-example" tabindex="0" id="dataTables-example_next"><a href="<%=request.getContextPath()%>/admin/song/index?page=<%=currentPage + 1%>">Trang tiếp</a></li>
                                        	<% } %>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <%	} %>
                        </div>
                    </div>
                </div>
                <!--End Advanced Tables -->
            </div>
        </div>
    </div>
</div>
<script>
    document.getElementById("song").classList.add('active-menu');
</script>
<!-- /. PAGE INNER  -->
<%@ include file="/templates/admin/inc/footer.jsp" %>