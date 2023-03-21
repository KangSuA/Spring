<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
	.board_header{
		padding:30px;
		background-color:#ddd;
	}
	.pHeader>div{
		width:50%;
		float:left;
		padding:10px 0;
		background:#888;
		color:white;
	}
	.pHeader>div:last-child{
		text-align:right;
	}
	.board_list, pHeader{
		clear:both;
		overflow:auto;
	}
	.board_list li{
		float:left;
		width:10%;
		height:40px;
		line-height:40px;
		border-bottom:1px solid #ddd;
	}
	.board_list li:nth-child(5n+2){
		width:55%;
		/*말줄임표시하기*/
		white-space:nowrap;/*줄바꾸지 않는다.*/
		overflow:hidden;/*넘치는 문자 숨기기*/
		text-overflow:ellipsis;/*넘친데이터가 있을때 말줄임 표시하기*/
	}
	.board_list li:nth-child(5n){
		width:15%
	}
	.pagingDiv li{
		float:left;
		padding:10px 20px;
	}
	.pagingDiv a:link, .pagingDiv a:hover, .pagingDiv a:visited, .board_list a:link, .board_list a:hover, .board_list a:visited{
		color:#000;
	}
	.searchDiv{
		clear:left;
		padding:10px;
		text-align:center;
	}
</style>
<script>
	$(function(){
		
	});
</script>
<div class="container">
	<h1>게시판 목록</h1>
	<div class="board_header"><a href="answerWrite">글쓰기</a></div>
	<div class="pHeader">
	<div>총 레코드 수 : ${totalRecord }</div>
	<div>&nbsp;</div>
	</div>
		<ul class="board_list">
			<li>번호</li>
			<li>제목</li>
			<li>작성자</li>
			<li>조회수</li>
			<li>등록일</li>
			<c:forEach var="aDTO" items="${list}">
				<li>${aDTO.no}</li>
				<li><a href="answerView?=no=${aDTO.no}">${aDTO.subject}</a></li>
				<li>${aDTO.userid }</li>
				<li>${aDTO.hit}</li>
				<li>${aDTO.writedate}</li>
			</c:forEach>
		</ul>
</div>