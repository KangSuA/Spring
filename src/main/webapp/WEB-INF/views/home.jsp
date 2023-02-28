<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
	<link rel="stylesheet" href="inc/style.css" type="text/css"/>
	<script src="inc/script.js"></script>
</head>
<body>
	<h1>
		Hello world!  
	</h1>
	<div class="desc">
		javascript외부파일 script.js<br/>
		css외부파일 style.css<br/>
		이미지파일 
	</div>
	<img src="img/ponyo004.jpg" onclick="test()"/>


</body>
</html>
