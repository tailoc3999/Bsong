<%@page import="utils.StringUtil"%>
<%@page import="constants.DefineConstant"%>
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
                <h2>Quản lý danh mục</h2>
            </div>
        </div>
        <!-- /. ROW  -->
        <%
        	String msg = request.getParameter("msg");
        	if ("success".equals(msg)) {
        		out.print("<span style='color: green; font-weight: bold'>Thêm danh mục thành công</span>");
        	}
        	if ("1".equals(msg)) {
        		out.print("<span style='color: green; font-weight: bold'>Sửa danh mục thành công</span>");
        	}
        	if ("2".equals(msg)) {
        		out.print("<span style='color: red; font-weight: bold'>ID không tồn tại</span>");
        	}
        	if ("3".equals(msg)) {
        		out.print("<span style='color: green; font-weight: bold'>Xóa danh mục thành công</span>");
        	}
        	if ("4".equals(msg)) {
        		out.print("<span style='color: red; font-weight: bold'>Xóa danh mục thất bại</span>");
        	}
        	if ("error".equals(msg)) {
        		out.print("<span style='color: red; font-weight: bold'>Thêm danh mục thất bại</span>");
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
                                    <a href="<%=request.getContextPath() %>/admin/cat/add" class="btn btn-success btn-md">Thêm</a>
                                </div>
                            </div>

                            <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Tên danh mục</th>
                                        <th width="160px">Chức năng</th>
                                    </tr>
                                </thead>
                                <tbody>
                                
                                	<%
                                		@SuppressWarnings("unchecked")
                                		List<Category> categories = (List<Category>) request.getAttribute("catlist");
                                		if (categories == null || categories.size() == 0) {
                                			out.print("<tr><td colspan = '3' style='color: red; font-weight: bold'>Không tồn tại danh mục nào cả!</td></tr>");
                                		}
                                		if(categories != null && categories.size() > 0){
                                			for(Category category : categories){
                                				int id = category.getId();
                                				String name = category.getName();
                                				String urlSlug = request.getContextPath() + "/admin/cat/edit/" + StringUtil.makeSlug(name) + "-" + id + ".html";
                                	%>
                                
                                    <tr>
                                        <td><%=id %></td>
                                        <td class="center"><%=name %></td>
                                        <td class="center">
                                            <a href="<%=urlSlug %>" title="" class="btn btn-primary"><i class="fa fa-edit "></i> Sửa</a>
                                            <a href="<%=request.getContextPath() %>/admin/cat/del?id=<%=id %>" title=""
                                            onclick="return confirm('Bạn có chắc chắn muốn xóa?');" class="btn btn-danger"><i class="fa fa-pencil"></i> Xóa</a>
                                        </td>
                                    </tr>
                                    <%
                                			}
                                		}
                                    %>
                                    
                                </tbody>
                            </table>
                            <%
                            	int numberOfCats = (int) request.getAttribute("numberOfCats");
                            	int numberOfPages = (int) request.getAttribute("numberOfPages");
                            	int currentPage = (int) request.getAttribute("currentPage");
                            	if (categories != null && categories.size() > 0 && numberOfCats > DefineConstant.NUMBER_PER_PAGE_CAT) {
                            %>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="dataTables_info" id="dataTables-example_info" style="margin-top:27px">Hiển thị từ <%=(currentPage - 1) * DefineConstant.NUMBER_PER_PAGE_CAT + 1 %> đến <%=(currentPage - 1) * DefineConstant.NUMBER_PER_PAGE_CAT + DefineConstant.NUMBER_PER_PAGE_CAT %> của <%=numberOfCats %> danh mục</div>
                                </div>
                                <div class="col-sm-6" style="text-align: right;">
                                    <div class="dataTables_paginate paging_simple_numbers" id="dataTables-example_paginate">
                                        <ul class="pagination">
                                        	<%
                                        		if (currentPage != 1) {
                                        	%>
                                            <li class="paginate_button previous disabled" aria-controls="dataTables-example" tabindex="0" id="dataTables-example_previous"><a href="<%=request.getContextPath()%>/admin/cat/index?page=<%=currentPage - 1%>">Trang trước</a></li>
                                          	<% }
                                          		String active = "";
                                          		for (int i = 1; i <= numberOfPages; i++) {
                                          			if (i == currentPage) {
                                          				active = "active";
                                          			} else {
                                          				active = "";
                                          			}
                                          	%>
                                            <li class="paginate_button <%=active %>" aria-controls="dataTables-example" tabindex="0"><a href="<%=request.getContextPath()%>/admin/cat/index?page=<%=i%>"><%=i %></a></li>
                                           <%} 
                                          		if (currentPage != numberOfPages) {
                                           %>
                                            <li class="paginate_button next" aria-controls="dataTables-example" tabindex="0" id="dataTables-example_next"><a href="<%=request.getContextPath()%>/admin/cat/index?page=<%=currentPage + 1%>">Trang tiếp</a></li>
                                        	<%} %>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <% } %>
                        </div>

                    </div>
                </div>
                <!--End Advanced Tables -->
            </div>
        </div>
    </div>
</div>
<script>
    document.getElementById("category").classList.add('active-menu');
</script>
<!-- /. PAGE INNER  -->
<%@ include file="/templates/admin/inc/footer.jsp" %>