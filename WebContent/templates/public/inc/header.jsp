<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<title>BSong</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<%=request.getContextPath() %>/resources/public/css/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/public/css/coin-slider.css" />
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/public/js/jquery-3.5.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/public/js/jquery.validate.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/public/js/script.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/public/js/coin-slider.min.js"></script>

<style type="text/css">
		#form ol li label.error {color:red}
</style>
</head>
<body>
<div class="main">
  <div class="header">
    <div class="header_resize">
      <div class="logo">
        <h1><a href="<%=request.getContextPath() %>/home">BSong <small>Một dự án khóa JAVA tại VinaEnter Edu</small></a></h1>
      </div>
      <div class="menu_nav">
        <ul>
        	<%
        	int checkid = 0 ;
        		if (request.getAttribute("id") != null) {
        			checkid = (int) request.getAttribute("id");
        		}
        	%>
          <li class="<%if(checkid == 1) out.print("active"); %>"><a href="<%=request.getContextPath() %>/home"><span>Trang chủ</span></a>
          <li class="<%if(checkid == 2) out.print("active"); %>"><a href="<%=request.getContextPath() %>/contact"><span>Liên hệ</span></a></li>
        </ul>
      </div>
      <div class="clr"></div>
      <div class="slider">
        <div id="coin-slider"><a href="#"><img src="<%=request.getContextPath() %>/resources/public/images/slide1.jpg" width="935" height="307" alt="" /></a> <a href="#"><img src="<%=request.getContextPath() %>/resources/public/images/slide2.jpg" width="935" height="307" alt="" /></a> <a href="#"><img src="<%=request.getContextPath() %>/resources/public/images/slide3.jpg" width="935" height="307" alt="" /></a></div>
        <div class="clr"></div>
      </div>
      <div class="clr"></div>
    </div>
  </div>
  <div class="content">