<%@page import="models.Song"%>
<%@page import="models.Category"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/admin/inc/header.jsp" %>
<%@ include file="/templates/admin/inc/leftbar.jsp" %>
<div id="page-wrapper">
    <div id="page-inner">
        <div class="row">
            <div class="col-md-12">
                <h2>Sửa bài hát</h2>
            </div>
        </div>
        <!-- /. ROW  -->
        <%
        	String errn = request.getParameter("errn");
      		String errp = request.getParameter("errp");
        %>
        <hr />
        <div class="row">
            <div class="col-md-12">
                <!-- Form Elements -->
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-md-12">
                          		  <%
                          		  		String name = request.getParameter("name");
                          		  		String catID = request.getParameter("category");
                          		  		String previewText = request.getParameter("preview");
                          		  		String detailText = request.getParameter("detail");
                          		  		String picture = "";
                                		Song song = (Song) request.getAttribute("song");
                                		int id = 0;
                                		if(song != null) {
                                			id = song.getId();
                                			name = song.getName();
                                			catID = String.valueOf(song.getCategory().getId());
                                			picture = song.getPicture();
                                			previewText = song.getPreview_text();
                                			detailText = song.getDetail_text();
                                		}
                                	%>
                                <form action="<%=request.getContextPath() %>/admin/song/edit?id=<%=id %>" role="form" method="post" enctype="multipart/form-data" id="form">
                                    <div class="form-group">
                                        <label for="name">Tên bài hát</label>
                                        <input type="text" id="name" value="<%if(name != null) out.print(name); %>" name="name" class="form-control" />
                                   <%
                                    	if ("1".equals(errn)) {
                                			out.print("<label id =\"name-error\" class=\"error\" for=\"name\">Không được nhập ký tự đặc biệt</label>");
                                		}
                                 	    if ("2".equals(errn)) {
                            				out.print("<label id =\"name-error\" class=\"error\" for=\"name\">Tên bài hát ít nhất 3 ký tự</label>");
                            			}
                                    %>
                                    </div>
                                    <div class="form-group">
                                        <label for="category">Danh mục bài hát</label>
                                        <select id="category" name="category" class="form-control">
	                                        <%
                                        	@SuppressWarnings("unchecked")
                                        	List<Category> categories = (ArrayList<Category>) request.getAttribute("categories");
                                        	if(categories != null && categories.size() > 0) {
                                        		for(Category category: categories){
                                        			int cat_id = category.getId();
                                        			String catName = category.getName();
                                        	
                                        %>
                                        	
	                                        <option value="<%=cat_id %>" <%if (catID != null && catID.equals(String.valueOf(cat_id))) out.print(" selected"); %>><%=catName %></option>
	                                         <%
                                        		}
                                        	}
                                        %>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label for="picture">Hình ảnh</label>
                                        <input type="file" name="picture" />
                                        <%
                                        	if (!picture.isEmpty()) {
                                        %>
                                        <img width="200px" height="200px" src="<%=request.getContextPath() %>/files/<%=picture %>" alt="<%=name %>"/>
                                        <%} %>
                                    </div>
                                    
                                    <div class="form-group">
                                        <label for="preview">Mô tả</label>
                                        <textarea id="preview" class="form-control" rows="3" name="preview"><%if(previewText != null) out.print(previewText); %></textarea>
                                   <%
                             	    	if ("2".equals(errp)) {
                        					out.print("<label id =\"preview-error\" class=\"error\" for=\"preview\">Mô tả bài hát ít nhất 20 ký tự</label>");
                        			}
                                    %>
                                    </div>
                                    <div class="form-group">
                                        <label for="detail">Chi tiết</label>
                                        <textarea id="detail" class="form-control" id="detail" rows="5" name="detail"><%if(detailText != null) out.print(detailText); %></textarea>
                                    </div>
                                    <button type="submit" name="submit" class="btn btn-success btn-md">Sửa</button>
                                </form>
                                <script type="text/javascript">
									$(document).ready(function () {
										$('#form').validate({
											rules: {
												"name": {
													required: true,
													minlength: 3,
							 						maxlength: 255,
												},
												"preview": {
													required: true,
													minlength: 20,
												},
											},
											messages: {
												"name": {
													required: "Vui lòng nhập tên bài hát",
													minlength: "Tên bài hát ít nhất 3 kí tự",
													maxlength: "Tên bài hát nhiều nhất 255 kí tự",
												},
												"preview": {
													required: "Vui lòng nhập mô tả bài hát bài hát",
													minlength: "Tên bài hát ít nhất 20 kí tự",
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
    document.getElementById("song").classList.add('active-menu');
</script>
<!-- /. PAGE WRAPPER  -->
<%@ include file="/templates/admin/inc/footer.jsp" %>