<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/admin/inc/header.jsp" %>

<div id="page-wrapper">
    <div id="page-inner">
        <div class="row">
            <div class="col-md-12">
                <h2>Đăng nhập</h2>
            </div>
        </div>
        <!-- /. ROW  -->
        <hr />
        <div class="row">
            <div class="col-md-12">
                <!-- Form Elements -->
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-md-12">
                            	<%
                            		if (request.getParameter("err") != null) {
                            	%>
                            	<span style='color:red; font-weight:bold'>Sai tên đăng nhập hoặc mật khẩu.</span>
                            	<%} %>
                                <form role="form" method="post" id="form" action="<%=request.getContextPath()%>/auth/login">
                                    <div class="form-group">
                                        <label for="name">Tên đăng nhập</label>
                                        <input type="text" id="username" name="username" class="form-control" />
                                    </div>
                                    <div class="form-group">
                                        <label for="name">Mật khẩu</label>
                                        <input type="password" id="password" name="password" class="form-control" />
                                    </div>
                                    <button type="submit" name="submit" class="btn btn-success btn-md">Đăng nhập</button>
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
												},
											},
											messages: {
												"username": {
													required: "Vui lòng nhập tên truy cập",
													minlength: "Tên truy cập ít nhất 5 kí tự",
													maxlength: "Tên truy cập nhiều nhất 32 kí tự",
												},
												"password": {
													required: "Vui lòng nhập mật khẩu",
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
<!-- /. PAGE WRAPPER  -->
<%@ include file="/templates/admin/inc/footer.jsp" %>