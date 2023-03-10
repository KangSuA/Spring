<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="container">
	<h1>
		Hello world!  
	</h1>
	<div class="desc">
		javascript외부파일 script.js<br/>
		css외부파일 style.css<br/>
		이미지파일 
	</div>
	<div>
		번호 : ${num }<br/>
		이름 : ${name }<br/>
	</div>
	<div>
		<h2>파일업로드 구현</h2>
		<pre>
			pom.xml에 프레임워크 추가
				메이븐 저장소
			1. commons-fileupload
			2. commons-io
			
			환경설정파일(mvc-context.xml)에 MultipartResolver객체 추가
			
			파일업로드 위치를 생성
			1. webapp하위에 uploadfile폴더 생성
			2. servlet-context.xml에 폴더 등록한다.
		</pre>
	</div>
	<img src="img/ponyo004.jpg" onclick="test()"/>
</div>