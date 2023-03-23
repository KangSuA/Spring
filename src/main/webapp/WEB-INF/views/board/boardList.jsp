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
	.board_list li{
		float:left;
		width:10%;
		height:40px;
		line-height:40px;
		border-bottom:1px solid #ddd;
	}
	.board_list li:nth-child(6n+3){
		width:50%;
		/*말줄임표시하기*/
		white-space:nowrap;/*줄바꾸지 않는다.*/
		overflow:hidden;/*넘치는 문자 숨기기*/
		text-overflow:ellipsis;/*넘친데이터가 있을때 말줄임 표시하기*/
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
		$("#searchForm").submit(function(){
			if($("#searchWord").val()==""){
				alert("검색어를 입력하세요");
				return false;
			}
			return true;
		});
		
		//---- 전체선택을 클릭하면 체크박스의 상태에 따라 선택 또는 해제 하는 기능 구현
		$("#allCheck").click(function(){
			$(".board_list input[name=noList]").prop("checked",$("#allCheck").prop("checked"))
		});
		
		//선택삭제 버튼을 클릭하면
		$("#chooseDel").click(function(){
			// 1개이상 삭제를 선택했을 때
			var checkCount = 0;
			$(".board_list input[name=noList]").each(function(idx, obj){
				if(obj.checked){ //$(obj.prop('checked'))
					checkCount++;
				}
			});
			
			if(checkCount>0){
				if(confirm(checkCount+'개의 글을 삭제하시겠습니까?')){
					$("#delList").submit();
				}
			}else{
				alert("1개 이상의 글을 선택 후 삭제하세요");
			}
			
		});
	});
</script>
<div class="container">
	<h1>게시판 목록</h1>
	<div class="board_header"><a href="/campus/board/boardWrite">글쓰기</a></div>
	<div class="pHeader">
		<div>총레코드 수 : ${vo.totalRecord}</div>
		<div>${vo.nowPage}/${vo.totalPage}</div>
	</div>
	<form method="post" action="/campus/board/boardMultiDel" id="delList">
		<!--  페이지번호, 검색키, 검색어 -->
		<input type="hidden" name="nowPage" value="${vo.nowPage}"/>
		<c:if test="${vo.searchWord!=' ' }">
			<input type="hidden" name="searchKey" value="${vo.searchKey }"/>
			<input type="hidden" name="searchWord" value="${vo.searchWord }"/>
		</c:if>
		
		<ul class="board_list">
			<li><input type="checkbox" id="allCheck">전체선택</li>
			<li>번호</li>
			<li>제목</li>
			<li>작성자</li>
			<li>조회수</li>
			<li>등록일</li>
			<c:set var="recordNum" value="${vo.totalRecord - (vo.nowPage-1)*vo.onePageRecord}"/>
			<c:forEach var="bDTO" items="${list}">
				<li>
					<c:if test="${bDTO.userid==logId}">
						<input type="checkbox" name="noList" value="${bDTO.no }"/>
					</c:if>
					<c:if test="${bDTO.userid!=logId}">
						<input type="checkbox" disabled/>
					</c:if>
				</li>
				<li>${recordNum}</li>
				<!-- 글내용 보기 -->
				<li><a href="boardView?no=${bDTO.no}&nowPage=${vo.nowPage}<c:if test="${vo.searchWord!=null}">&searchKey=${vo.searchKey}&searchWord=${vo.searchWord}</c:if>">${bDTO.subject}</a></li>
				<li>${bDTO.username }</li>
				<li>${bDTO.hit}</li>
				<li>${bDTO.writedate}</li>
				<c:set var="recordNum" value="${recordNum-1}" />
			</c:forEach>
		</ul>
	</form>
	<div>
		<input type="button" value="선택삭제" id="chooseDel"/>
	</div>
	<!-- 페이징 -->
	<div class="pagingDiv">
		<ul>
			<!-- 이전페이지 -->
			<c:if test="${vo.nowPage==1}"> <!-- 현재 페이지가 첫번째 페이지 일때 -->
				<li>prev</li>
			</c:if>
			<c:if test="${vo.nowPage>1}"> <!-- 현재 페이지가 첫번째 페이지가 아닐때 -->
				<li><a href="boardList?nowPage=${vo.nowPage-1}<c:if test='${vo.searchWord!=null}'>&searchKey=${vo.searchKey}&searchWord=${vo.searchWord}</c:if>">prev</a></li>
			</c:if>
			
			<!-- 페이지 번호 -->
			<c:forEach var="p" begin="${vo.startPageNum}" end="${vo.startPageNum+vo.onePageNumCount-1}">
				<c:if test="${p<=vo.totalPage}"> <!-- 표시할 페이지번호 -->
					<c:if test="${p==vo.nowPage}">
						<li style="background:#ddd;">
					</c:if>
					<c:if test="${p!=vo.nowPage}">
						<li>
					</c:if>
						<a href="boardList?nowPage=${p}<c:if test='${vo.searchWord!=null}'>&searchKey=${vo.searchKey}&searchWord=${vo.searchWord}</c:if>">${p}</a></li>
				</c:if>
			</c:forEach>
			
			<!-- 다음페이지 -->
			<c:if test="${vo.nowPage<vo.totalPage}"> <!--  다음페이지가 있을때 -->
				<li><a href="boardList?nowPage=${vo.nowPage+1}<c:if test='${vo.searchWord!=null}'>&searchKey=${vo.searchKey}&searchWord=${vo.searchWord}</c:if>">next</a></li>
			</c:if>
			<c:if test="${vo.nowPage==vo.totalPage}"> <!-- 현재페이지가 마지막일 때 -->
				<li>next</li>
			</c:if>
		</ul>
	</div>
	
	<div class="searchDiv">
		<form method="get" id="searchForm" action="boardList">
			<select name="searchKey">
				<option value="subject">제목</option>
				<option value="username">작성자</option>
				<option value="content">글내용</option>
			</select>
			<input type="text" name="searchWord" id="searchWord"/>
			<input type="submit" value="Search"/>
		</form>
	</div>
</div>