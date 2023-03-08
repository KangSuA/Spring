<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
	#pwdSearchForm ul{
		overflow:auto;
	}
	#pwdSearchForm li{
		float:left;
		width:20%;
		padding:20px 0;
		line-height:20px;
	}
	#pwdSearchForm li:nth-child(2n){
		width:80%;
	}
</style>
<script>
	$(function(){
		$("#pwdSearchForm").submit(function(){
			//userid not null
			if($("#userid").val()==""){
				alert("아이디를 입력하세요.");
				return false;
			}
			//username not null
			if($("#username").val()==""){
				alert("이름을 입력하세요.");
				return false;
			}
			//check tel
			var tel = $("#tel1").val()+"-"+$("#tel2").val()+"-"+$("#tel3").val();
			reg = /^(010|02|301|041|051)-[0-9]{3,4}-[0-9]{4}$/
			if(!reg.test(tel)){
				alert("전화번호를 잘못입력하였습니다.");
				return false;
			}
		});
	})
</script>
<div class="container">
	<h1>비밀번호 찾기</h1>
	<h3>회원정보에 등록한 것과 동일한 아이디와 이름, 연락처를 입력해주세요.</h3>
	<form method="post" id="pwdSearchForm" action="pwdSearchOk">
		<ul>
			<li>아이디</li>
			<li><input test="text" name="userid" id="userid"/></li>
			<li>이름</li>
			<li><input test="text" name="username" id="username"/></li>
			<li>연락처</li>
			<li>
				<select name="tel1" id="tel1">
					<option value="010">010</option>
					<option value="02">02</option>
					<option value="031">031</option>
					<option value="041">041</option>
					<option value="051">051</option>
				</select> -
				<input type="text" name="tel2" id="tel2" maxlength="4"/> -
				<input type="text" name="tel3" id="tel3" maslength="4"/>
			</li>
			<li><input type="submit" value="아이디 찾기"/></li>
		</ul>
	</form>
</div>