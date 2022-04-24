<%@page import="utils.StringUtil"%>
<%@page import="constants.DefineConstant"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="models.User"%>
<%@page import="models.Category"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/admin/inc/header.jsp"%>
<%@ include file="/templates/admin/inc/leftbar.jsp"%>
<div id="page-wrapper">
	<div id="page-inner">
		<div class="row">
			<div class="col-md-12">
				<h2>Quản lý người dùng</h2>
			</div>
		</div>
		<!-- /. ROW  -->
		<%
			User userLogin = (User) session.getAttribute("userInfo");
			String err = request.getParameter("err");
			String msg = request.getParameter("msg");
			if ("1".equals(msg)) {
				out.print("<span style='color: green; font-weight: bold; padding: 5px'>Thêm thành công</span>");
			}
			if ("2".equals(msg)) {
				out.print("<span style='color: green; font-weight: bold; padding: 5px'>Sửa thành công</span>");
			}
			if ("3".equals(msg)) {
				out.print("<span style='color: green; font-weight: bold; padding: 5px'>Xóa thành công</span>");
			}
			if ("1".equals(err)) {
				out.print("<span style='color: red; font-weight: bold; padding: 5px'>Có lỗi khi thêm</span>");
			}
			if ("2".equals(err)) {
				out.print("<span style='color: red; font-weight: bold; padding: 5px'>ID không tồn tại</span>");
			}
			if ("3".equals(err)) {
				out.print("<span style='color: red; font-weight: bold; padding: 5px'>Xóa người dùng thất bại</span>");
			}
			if ("4".equals(err)) {
				out.print("<span style='color: red; font-weight: bold; padding: 5px'>Không được phép thêm người dùng!!</span>");
			}
			if ("5".equals(err)) {
				out.print("<span style='color: red; font-weight: bold; padding: 5px'>Không được phép sửa người dùng!!</span>");
			}
			if ("6".equals(err)) {
				out.print("<span style='color: red; font-weight: bold; padding: 5px'>Không được phép xóa người dùng!!</span>");
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
									<a href="<%=request.getContextPath() %>/admin/user/add" class="btn btn-success btn-md">Thêm</a>
								</div>
							</div>
							<table class="table table-striped table-bordered table-hover"
								id="dataTables-example">
								<thead>
									<tr>
										<th>ID</th>
										<th>User Name</th>
										<th>Họ tên</th>
										<th width="160px">Chức năng</th>
									</tr>
								</thead>
								<tbody>

									<%
										@SuppressWarnings("unchecked")
										List<User> users = (List<User>) request.getAttribute("userlist");
										if (users.size() == 0 || users == null) {
											out.print("<tr><td colspan = '4' style='color: red; font-weight: bold '>Chưa có người dùng nào</td></tr>");
										}
										if (users != null && users.size() > 0) {
											for (User user : users) {
												int id = user.getId();
												String username = user.getUserName();
												String fullname = user.getFullName();
												String urlSlug = request.getContextPath() + "/admin/user/edit/" + StringUtil.makeSlug(fullname) + "-" + id + ".html";
									%>

									<tr>
										<td><%=id %></td>
										<td class="center"><%=username %></td>
										<td class="center"><%=fullname %></td>
										<td class="center">
										<%
											if ("admin".equals(userLogin.getUserName())) {
										%>
											<a href="<%=urlSlug %>" title="" class="btn btn-primary"><i class="fa fa-edit "></i> Sửa</a>
											<a onclick="return confirm('Bạn có chắc chắn muốn xóa?');" href="<%=request.getContextPath() %>/admin/user/del?id=<%=id %>" title="" class="btn btn-danger"><i class="fa fa-pencil"></i> Xóa</a>
										<%	} else { 
												if (user.getId() == userLogin.getId()) {
										%>
											<a href="<%=urlSlug %>" title="" class="btn btn-primary"><i class="fa fa-edit "></i> Sửa</a>
										<%}} %>
										</td>
									</tr>

									<%
											}
										}
									%>
								
								</tbody>
							</table>
							<%
								int numberOfUsers = (int) request.getAttribute("numberOfUsers");
								int numberOfPages = (int) request.getAttribute("numberOfPages");
								int currentPage = (int) request.getAttribute("currentPage");
								if (users != null && users.size() > 0 && numberOfUsers > DefineConstant.NUMBER_PER_PAGE_USER) {
							%>
							<div class="row">
								<div class="col-sm-6">
									<div class="dataTables_info" id="dataTables-example_info"
										style="margin-top: 27px">Hiển thị từ <%=(currentPage - 1) * DefineConstant.NUMBER_PER_PAGE_USER + 1 %> đến <%=(currentPage - 1) * DefineConstant.NUMBER_PER_PAGE_USER + DefineConstant.NUMBER_PER_PAGE_USER %> của <%=numberOfUsers %>
										người dùng</div>
								</div>
								<div class="col-sm-6" style="text-align: right;">
									<div class="dataTables_paginate paging_simple_numbers"
										id="dataTables-example_paginate">
										<ul class="pagination">
											<%
                                        		if (currentPage != 1) {
                                        	%>
											<li class="paginate_button previous disabled"
												aria-controls="dataTables-example" tabindex="0"
												id="dataTables-example_previous"><a href="<%=request.getContextPath()%>/admin/user/index?page=<%=currentPage - 1%>">Trang
													trước</a></li>
											<% }
												String active = "";
												for (int i = 1; i <= numberOfPages; i++) {
													if (i == currentPage) {
														active = "active";
													} else {
														active = "";
													}
											%>
											<li class="paginate_button <%=active %>"
												aria-controls="dataTables-example" tabindex="0"><a
												href="<%=request.getContextPath()%>/admin/user/index?page=<%=i%>"><%=i %></a></li>
											<%} 
												if (currentPage != numberOfPages) {
											%>
											<li class="paginate_button next"
												aria-controls="dataTables-example" tabindex="0"
												id="dataTables-example_next"><a href="<%=request.getContextPath()%>/admin/user/index?page=<%=currentPage + 1%>">Trang tiếp</a></li>
											<%} %>
										</ul>
									</div>
								</div>
							</div>
							<%} %>
						</div>

					</div>
				</div>
				<!--End Advanced Tables -->
			</div>
		</div>
	</div>
</div>
<script>
	document.getElementById("user").classList.add('active-menu');
</script>
<!-- /. PAGE INNER  -->
<%@ include file="/templates/admin/inc/footer.jsp"%>