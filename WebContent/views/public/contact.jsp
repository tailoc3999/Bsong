<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/public/inc/header.jsp" %>
<div class="content_resize">
  <div class="mainbar">
    <div class="article">
      <h2><span>Liên hệ</span></h2>
      <div class="clr"></div>
      <p>Khi bạn có thắc mắc, vui lòng gửi liên hệ, chúng tôi sẽ phản hồi trong thời gian sớm nhất.</p>
    </div>
    <%
  		String namee = request.getParameter("name");
		String email = request.getParameter("email");
		String website = request.getParameter("website");
		String message = request.getParameter("message");
    
    	String msg = request.getParameter("msg");
   		String err = request.getParameter("err");
    	if ("1".equals(msg)) {
    		out.print("<span style='color: green; font-weight: bold; padding-left: 20px'>Gửi liên hệ thành công</span>");
    	}
    	if ("1".equals(err)) {
    		out.print("<span style='color: red; font-weight: bold; padding-left: 20px'>Gửi liên hệ thất bại</span>");
    	}
    %>
    <div class="article">
      <h2>Gửi liên hệ đến chúng tôi</h2>
      <div class="clr"></div>
      <form action="<%=request.getContextPath() %>/contact" method="post" id="form">
        <ol>
          <li>
            <label for="name">Họ tên</label>
            <input id="name" value="<%if(namee != null) out.print(namee); %>" name="name" class="text" />
            <%
            	if ("2".equals(err)) {
            		out.print("<label id =\"name-error\" class=\"error\" for=\"name\">Không được nhập ký tự đặc biệt</label>");
                }
                if ("3".equals(err)) {
                    out.print("<label id =\"name-error\" class=\"error\" for=\"name\">Họ tên ít nhất 10 ký tự</label>");
                }
            %>
          </li>
          <li>
            <label for="email">Email</label>
            <input id="email" value="<%if(namee != null) out.print(email); %>" name="email" class="text" />
          </li>
          <li>
            <label for="website">Website</label>
            <input id="website" value="<%if(namee != null) out.print(website); %>" name="website" class="text" />
          </li>
          <li>
            <label for="message">Nội dung</label>
            <textarea id="message" name="message" rows="8" cols="50"><%if(namee != null) out.print(message); %></textarea>
          </li>
          <li>
            <input type="image" name="imageField" id="imageField" src="<%=request.getContextPath() %>/resources/public/images/submit.gif" class="send" />
            <div class="clr"></div>
          </li>
        </ol>
      </form>
      <script type="text/javascript">
			$(document).ready(function () {
				$('#form').validate({
					rules: {
						"name": {
							required: true,
							minlength: 10,
							maxlength: 50,
						},
						"email": {
							required: true,
							email: true,
						},
						"website": {
							url: true,
						},
						"message": {
							required: true,
							minlength: 10,
						},
					},
					messages: {
						"name": {
							required: "Vui lòng nhập họ tên",
							minlength: "Họ và tên ít nhất 10 kí tự",
							maxlength: "Họ và tên nhiều nhất 50 kí tự",
						},
						"email": {
							required: "Vui lòng nhập đúng email",
							email: "Vui lòng nhập đúng email",
						},
						"website": {
							url: "Không đúng định dạng",
						},
						"message": {
							required: "Vui lòng điền nội dung",
							minlength: "Họ và tên ít nhất 10 kí tự",
						},
					},
				});
			});	
		</script>
    </div>
  </div>
  <div class="sidebar">
  </div>
  <div class="clr"></div>
</div>
<%@ include file="/templates/public/inc/footer.jsp" %>
