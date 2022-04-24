<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/admin/inc/header.jsp" %>
<%@ include file="/templates/admin/inc/leftbar.jsp" %>
<div id="page-wrapper">
    <div id="page-inner">
        <div class="row">
            <div class="col-md-12">
                <h2>Thêm người dùng</h2>
            </div>
        </div>
        <!-- /. ROW  -->
        <%
        	String errn = request.getParameter("errn");
       		String errp = request.getParameter("errp");
       		String errf = request.getParameter("errf");
        	String err = request.getParameter("err");
        	String username = request.getParameter("username");
        	String fullname = request.getParameter("fullname");
        	if("1".equals(err)) {
        		out.print("<span style='color: red; font-weight: bold; padding: 5px'>Có lỗi khi thêm</span>");
        	}
        	if("2".equals(err)) {
        		out.print("<span style='color: red; font-weight: bold; padding: 5px'>Tên đăng nhập đã tồn tại</span>");
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
                                <form role="form" method="post" id="form" action="<%=request.getContextPath()%>/admin/user/add">
                                    <div class="form-group">
                                        <label for="username">Tên đăng nhập</label>
                                        <input type="text" id="username" value="<%if(username != null) out.print(username); %>" name="username" class="form-control" />
                                    <%
                                    	if ("1".equals(errn)) {
                                			out.print("<label id =\"name-error\" class=\"error\" for=\"name\">Không được nhập ký tự đặc biệt</label>");
                                		}
                                 	    if ("2".equals(errn)) {
                            				out.print("<label id =\"name-error\" class=\"error\" for=\"name\">Tên đăng nhập ít nhất 5 ký tự</label>");
                            			}
                                    %>
                                    </div>
                                    <div class="form-group">
                                        <label for="password">Mật khẩu</label>
                                        <input type="password" id="password" value="" name="password" class="form-control" />
                                    <%
                                    	if ("1".equals(errp)) {
                                			out.print("<label id =\"password-error\" class=\"error\" for=\"password\">Không được nhập ký tự đặc biệt</label>");
                                		}
                                 	    if ("2".equals(errp)) {
                            				out.print("<label id =\"password-error\" class=\"error\" for=\"password\">Mật khẩu ít nhất 6 ký tự</label>");
                            			}
                                    %>
                                    </div>
                                    <div class="form-group">
                                        <label for="fullname">Họ tên</label>
                                        <input type="text" id="fullname" value="<%if(fullname != null) out.print(fullname); %>" name="fullname" class="form-control" />
                                    <%
                                    	if ("1".equals(errf)) {
                                			out.print("<label id =\"fullname-error\" class=\"error\" for=\"fullname\">Không được nhập ký tự đặc biệt</label>");
                                		}
                                 	    if ("2".equals(errf)) {
                            				out.print("<label id =\"fullname-error\" class=\"error\" for=\"fullname\">Họ tên ít nhất 10 ký tự</label>");
                            			}
                                    %>
                                    </div>
                                    <button type="submit" name="submit" class="btn btn-success btn-md">Thêm</button>
                                </form>
                                <script type="text/javascript">
									$(document).ready(function () {
										$('#form').validate({
											rules: {
												"username": {
													required: true,
													minlength: 5,
							 						maxlength: 32,
												},
												"password": {
													required: true,
													minlength: 6,
												},
												"fullname": {
													required: true,
													minlength: 10,
							 						maxlength: 50,
												},
											},
											messages: {
												"username": {
													required: "Vui lòng nhập tên đăng nhập",
													minlength: "Tên đăng nhập ít nhất 5 kí tự",
													maxlength: "Tên đăng nhập nhiều nhất 32 kí tự",
												},
												"password": {
													required: "Vui lòng nhập mật khẩu",
													minlength: "Mật khẩu ít nhất 6 kí tự",
												},
												"fullname": {
													required: "Vui lòng nhập họ tên",
													minlength: "Họ và tên ít nhất 10 kí tự",
													maxlength: "Họ và tên nhiều nhất 50 kí tự",
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
    document.getElementById("user").classList.add('active-menu');
</script>
<!-- /. PAGE WRAPPER  -->
<%@ include file="/templates/admin/inc/footer.jsp" %>