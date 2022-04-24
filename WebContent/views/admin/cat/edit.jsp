<%@page import="models.Category"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/admin/inc/header.jsp" %>
<%@ include file="/templates/admin/inc/leftbar.jsp" %>
<div id="page-wrapper">
    <div id="page-inner">
        <div class="row">
            <div class="col-md-12">
                <h2>Sửa danh mục</h2>
            </div>
        </div>
        <!-- /. ROW  -->
        <%
        	String name = request.getParameter("name");
        	int id = 0;
        	Category category = (Category) request.getAttribute("cat");
        	if (category != null) {
        		id = category.getId();
        		name = category.getName();
        	}
        	String err = request.getParameter("err");
        	if ("4".equals(err)) {
        		out.print("<span style='color: red; font-weight: bold'>Sửa danh mục thất bại</span>");
        	}
        	if ("3".equals(err)) {
        		out.print("<span style='color: red; font-weight: bold'>Danh mục đã tồn tại</span>");
        	}
        %>
        <hr />
        <div class="row">
            <div class="col-md-12">
                <!-- Form Elements -->
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-md-12">
                                <form action="<%=request.getContextPath() %>/admin/cat/edit?id=<%=id %>" role="form" method="post" id="form">
                                    <div class="form-group">
                                        <label for="name">Tên danh mục</label>
                                        <input type="text" id="name" value="<%if (name != null) out.print(name); %>" name="name" class="form-control" />
                                    	<%
                                    		if ("1".equals(err)) {
                                    			out.print("<label id =\"name-error\" class=\"error\" for=\"name\">Không được nhập ký tự đặc biệt</label>");
                                    		}
                                    		if ("2".equals(err)) {
                                				out.print("<label id =\"name-error\" class=\"error\" for=\"name\">Tên danh mục ít nhất 5 ký tự</label>");
                                			}
                                    	%>
                                    </div>
                                    <button type="submit" name="submit" class="btn btn-success btn-md">Lưu</button>
                                </form>
                                <script type="text/javascript">
									$(document).ready(function () {
										$('#form').validate({
											rules: {
												"name": {
													required: true,
													minlength: 5,
							 						maxlength: 255,
												},
											},
											messages: {
												"name": {
													required: "Vui lòng nhập tên danh mục",
													minlength: "Tên danh mục ít nhất 5 kí tự",
													maxlength: "Tên danh mục nhiều nhất 255 kí tự",
												},
											},
										});
									});	
								</script>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- End Form Elements -->
            </div>
        </div>
        <!-- /. ROW  -->
    </div>
    <!-- /. PAGE INNER  -->
</div>
<script>
    document.getElementById("category").classList.add('active-menu');
</script>
<!-- /. PAGE WRAPPER  -->
<%@ include file="/templates/admin/inc/footer.jsp" %>