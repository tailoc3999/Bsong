<%@page import="constants.DefineConstant"%>
<%@page import="java.util.ArrayList"%>
<%@page import="models.Contact"%>
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
                <h2>Quản lý liên hệ</h2>
            </div>
        </div>
        <!-- /. ROW  -->
        <%
        	String msg = request.getParameter("msg");
        	String err = request.getParameter("err");
        	if ("1".equals(msg)) {
        		out.print("<span style='color: green; font-weight: bold; padding: 5px'>Xóa liên hệ thành công!</span>");
        	}
        	if ("1".equals(err)) {
        		out.print("<span style='color: red; font-weight: bold; padding: 5px'>ID không tồn tại</span>");
        	}
        	if ("2".equals(err)) {
        		out.print("<span style='color: red; font-weight: bold; padding: 5px'>Xóa liên hệ thất bại!!</span>");
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
                                <div class="col-sm-6" style="text-align: right; padding-right:200px">
                                    <form method="get" action="<%=request.getContextPath() %>/admin/contact/index">
                                        <input type="submit" name="search" value="Tìm kiếm" class="btn btn-warning btn-sm" style="float:right" />
                                        <input type="search" name="cname" class="form-control input-sm" placeholder="Nhập tên người liên hệ" style="float:right; width: 300px;" />
                                        <div style="clear:both"></div>
                                    </form><br />
                                </div>
                            </div>

                            <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Tên người liên hệ</th>
                                        <th>Email</th>
                                        <th>Website</th>
                                        <th>Message</th>
                                        <th width="160px">Chức năng</th>
                                    </tr>
                                </thead>
                                <tbody>
                                	<%
                                		@SuppressWarnings("unchecked")
                                		List<Contact> contacts = (ArrayList<Contact>) request.getAttribute("contacts");
                                		if (contacts != null && contacts.size() > 0) {
                                			for(Contact contact: contacts) {
                                				int id = contact.getId();
                                				String name = contact.getName();
                                				String email = contact.getEmail();
                                				String website = contact.getWebsite();
                                				String message = contact.getMessage();
                                	%>
                                    <tr>
                                        <td><%=id %></td>
                                        <td class="center"><%=name %></td>
                                        <td class="center"><%=email %></td>
                                        <td class="center"><%=website %></td>
                                        <td class="center"><%=message %></td>
                                        <td class="center">
                                            <a href="<%=request.getContextPath() %>/admin/contact/del?id=<%=id %>" title="" class="btn btn-danger"><i class="fa fa-pencil"></i> Xóa</a>
                                        </td>
                                    </tr>
                                    <%
                                			}
                                		}
                                    %>
                                </tbody>
                            </table>
                            <%
                           	 	int numberOfContacts = (int) request.getAttribute("numberOfContacts");
                        		int numberOfPages = (int) request.getAttribute("numberOfPages");
                        		int currentPage = (int) request.getAttribute("currentPage");
                        		if (contacts != null && contacts.size() > 0 && numberOfContacts > DefineConstant.NUMBER_PER_PAGE_CONTACT) {
                            %>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="dataTables_info" id="dataTables-example_info" style="margin-top:27px">Hiển thị từ 
                                    <%=(currentPage - 1) * DefineConstant.NUMBER_PER_PAGE_CONTACT + 1 %> đến 
                                    <%=(currentPage - 1) * DefineConstant.NUMBER_PER_PAGE_CONTACT + DefineConstant.NUMBER_PER_PAGE_CONTACT %>
                                     của <%=numberOfContacts %> liên hệ</div>
                                </div>
                                <div class="col-sm-6" style="text-align: right;">
                                    <div class="dataTables_paginate paging_simple_numbers" id="dataTables-example_paginate">
                                        <ul class="pagination">
                                        	<%
                                        		if (currentPage != 1) {
                                        	%>
                                            <li class="paginate_button previous disabled" aria-controls="dataTables-example" tabindex="0" 
                                            id="dataTables-example_previous"><a href="<%=request.getContextPath()%>/admin/contact/index?page=<%=currentPage - 1%>">Trang trước</a></li>
                                          	<% }
                                          		String active = "";
                                          		for (int i = 1; i <= numberOfPages; i++) {
                                          			if (i == currentPage) {
                                          				active = "active";
                                          			} else {
                                          				active = "";
                                          			}
                                          	%>
                                            <li class="paginate_button <%=active %>" aria-controls="dataTables-example" tabindex="0"><a href="<%=request.getContextPath()%>/admin/contact/index?page=<%=i%>"><%=i %></a></li>
                                        	<%} 
                                          		if (currentPage != numberOfPages) {
                                           %>
                                            <li class="paginate_button next" aria-controls="dataTables-example" tabindex="0" 
                                            id="dataTables-example_next"><a href="<%=request.getContextPath()%>/admin/contact/index?page=<%=currentPage + 1%>">Trang tiếp</a></li>
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
    document.getElementById("contact").classList.add('active-menu');
</script>
<!-- /. PAGE INNER  -->
<%@ include file="/templates/admin/inc/footer.jsp" %>