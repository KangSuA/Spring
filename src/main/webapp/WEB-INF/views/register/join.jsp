<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
	#joinForm ul{
		overflow:auto;
	}
	#joinForm li{
		float:left;
		width:20%;
		padding:20px 0;
		border-bottom:1px solid gray;
		line-height:40px;
	}
	#joinForm li:nth-child(2n){
		width:80%;
	}
	#joinForm li:last-child{
		width:100%;
	}
	#addr{
		width:80%;
	}
</style>
<script>
	$(function(){
		//아이디 중복 검사
		$("input[value=아이디중복검사]").click(function(){
			if($("#userid").val()!=""){
				window.open("idCheck?userid="+$("#userid").val(),"chk","width=400,height=300"); //주소 창이름 옵션
			}else{
				alert("아이디를 입력후 중복검사하세요.");
			}
			
		});
		
		//아이디 입력란에 키보드를 입력하면 아이디중복검사 초기화
		$("#userid").keyup(function(){
			$("#idStatus").val("N");
		});
		
		//우편번호 검색
		$("#zipSearch").on('click',function(){
			window.open("zipcodeSearch","zipcode","width=500, height=600px");
		});
		
		//유효성검사
		$("#joinForm").submit(function(){
			if($("#userid").val()==""){
				alert("아이디를 입력하세요..");
				return false;
			}
			//아이디검사
			// 첫번째 문자 영어 대소문자/ 숫자, _ 가능/ 8~15자 사이
			//var reg = /^[A-Za-z]{1}[A-Za-z_]{7,14}$/;
			var reg = /^[A-Za-z]{1}\w{7,14}$/
			
			if(!reg.test($("#userid").val())){ //유효한 값이면 true, 잘못된 데이터이면 false
				alert("아이디 첫번째문자는 영어 대소문자로 시작하여야 하며, \n영어대소문자, 숫자, _ 가능, 아이길이는 8~15글자까지 가능");
				return false;
			}
			if($("#idStatus").val()=="N"){
				alert("아이디 중복검사를 하세요.");
				return false;
			}
			//비밀번호
			if($("#userpwd").val()==""){
				alert("비밀번호를 입력하세요.");
				return false;
			}
			if($("#userpwd").val() != $("#userpwd2").val()){
				alert("비밀번호가 일치하지 않습니다.");
				return false;
			}
			//이름검사
			reg = /^[가-힣]{2,10}$/
			if(!reg.test($("#username").val())){
				alert("이름은 2~10글자까지 한글만 가능합니다.");
				return false;
			}
		});
	});
</script>
<div class="container">
	<h1>회원가입 폼</h1>
	<form method="post" id="joinForm">
		<ul>
			<li>아이디</li>
			<li>
				<input type="text" name="userid" id="userid" minlength="8" maxlength="15"/>
				<input type="button" value="아이디중복검사"/>
				<input type="hidden" id="idStatus" value="N"/>
			</li>
			<li>비밀번호</li>
			<li><input type="password" name="userpwd" id="userpwd" minlength="8" maxlength="15" value="12345678"/></li>
			<li>비밀번호확인</li>
			<li><input type="password" name="userpwd2" id="userpwd2" value="12345678"/></li>
			<li>이름</li>
			<li><input type="text" name="username" id="username" minlength="2" maxlength="10" value="홍길동"/></li>
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
			<li>이메일</li>
			<li><input type="text" name="email" id="email"/></li>
			<li>우편번호</li>
			<li>
				<input type="text" name="zipcode" id="zipcode"/>
				<input type="button" value="우편번호찾기" id="zipSearch"/>
			</li>
			<li>주소</li>
			<li><input type="text" name="addr" id="addr"/></li>
			<li>상세주소</li>
			<li><input type="text" name="detailaddr" id="detailaddr"/></li>
			<li>취미</li>
			<li>
				<input type="checkbox" name="hobby" value="야구"/>야구
				<input type="checkbox" name="hobby" value="축구"/>축구
				<input type="checkbox" name="hobby" value="농구"/>농구
				<input type="checkbox" name="hobby" value="족구"/>족구
				<input type="checkbox" name="hobby" value="피구"/>피구
				<input type="checkbox" name="hobby" value="배구"/>배구
			</li>
			<li> <input type="submit" value="회원가입"/></li>
		</ul>
	<form/>
</div>