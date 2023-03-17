<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
	.boardSubMenu{
		background:gray;
		padding:50px 0;
	}
	#comment{
		width:50%;
		height:80px;
	}
	#commentList>li{
		padding:10px 0;
		border-bottom:1px solid #ddd;
	}
</style>
<script>
	function boardDel(){
		if(confirm("정말 삭제하시겠습니까?")){
			location.href="boardDel?no=${dto.no}&nowPage=${vo.nowPage}<c:if test="${vo.searchWord!=null}">&searchKey=${vo.searchKey }&searchWord=${vo.searchWord }</c:if>";
		}
	}
	$(function(){ 
		//댓글목록=============================
		//ajax로 해당 게시물에 대한 무든 댓글을 선택하여 뷰에 표시하는 함수
		function commentList(){
			$.ajax({
				url:"/campus/commentList",
				data:{
					no:${dto.no}
				},
				type:'GET',
				success:function(comment){
					//원래있는 댓글을 지운다.
					//$("#commentList").html("");
					
					var tag = "";
					$(comment).each(function(i,cDTO){
						tag += "<li><div><b>"+cDTO.userid+"("+cDTO.writedate+")</b>";
						
						//본인이 쓴 댓글일때 수정/삭제
						if(cDTO.userid == '${logId}'){
							tag += "<input type='button' value='Edit'/>";
							tag += "<input type='button' value='Del' title='"+cDTO.c_no+"'/>";
							tag += "<p>"+cDTO.coment+"</p></div>";
							
							//댓글 수정 폼 만들기
							tag += "<div style='display:none'>";
							tag += "<form method='post'>";
							tag += "<input type='hidden' name='c_no' value='"+cDTO.c_no+"'/>";
							tag += "<textarea name='coment' style='width:400px;height:80px;'>"+cDTO.coment+"</textarea>";
							tag += "<input type='submit' value='댓글수정하기'/>";
							tag += "</form>";
							tag += "</div>";
						}else{
							tag += "<p>"+cDTO.coment+"</p></div>";
						}
						
						tag += "</li>";
					});
					$("#commentList").html(tag);
				},error : function(e){
					console.log(e.responseText)
				}
			});
		}
		//댓글쓰기=============================
		$("#commentForm").submit(function(){
			//코멘트가 있을때 ajax실행
			if($("#coment").val()==""){
				alert("댓글을 입력후 등록하세요.");
				return false;
			}
			//코멘트가 있을 때
			//폼의 값을 쿼리문으로 만들기
			//폼의 컴포넌트의 데이터를 name속성의 값과 value속성의 값을 이용하여 쿼리문을 만들어준다.
			var query = $(this).serialize(); //no=45&coment=ddd //serialize는 쿼리문을 만들어주는 함수
			console.log(query);
			$.ajax({
				url:"/campus/commentSend",
				data:query,
				type:"POST",
				success:function(result){
					console.log(result);
					//기존에 입력한 댓글 지우기
					$("#comment").val("");
					//댓글목록을 다시 뿌려준다.
					commentList();
				},error:function(e){
					console.log(e.responseText);
				}
			});
			return false; //form의 기본이벤트때문 다음실행이 있고 그것을 중단한다.
		});
		//댓글 수정폼 보여주기
		$(document).on('click','#commentList input[value=Edit]',function(){
			//기존에 열어놓은 폼이나, 숨겨놓은 댓글내용을 처리하고
			//$("#commentList>li>div:nth-first").css('display','block');
			//$("#commentList>li>div:nth-last").css('display','none');
			$(this).parent().css("display","none"); // 댓글은 숨기고
			$(this).parent().next().css("display","block"); //수정폼 보여주기
		});
		//댓글 수정 - db
		$(document).on('click','#commentList input[value=댓글수정하기]',function(){
			//데이터 준비
			var params = $(this).parent().serialize(); // c_no=34&coment=test
			var url = "/campus/commentEdit";
			
			$.ajax({
				url:url,
				data:params,
				type:"POST",
				success:function(result){
					console.log(result);
					//댓글 목록 다시 뿌리기
					commentList();
				},error:function(e){
					console.log(e.responseText);
				}
			});
			
			return false;
		});
		//댓글 삭제
		$(document).on('click',"#commentList input[value=Del]",function(){
			
			if(confirm("댓글을 삭제할까요?")){
				var params = "c_no="+$(this).attr("title");
				console.log(params);
				$.ajax({
					url:"/campus/commentDelete",
					data:params,
					success:function(result){
						console.log(result);
						commentList();
					},error:function(e){
						console.log(e.responseText);
					}
				});
			}
			
		});
		
		//댓글목록 뿌려주기 -- 처음
		commentList();
	})
</script>
<div class="container">
	<h1>글 내용 보기</h1>
	<ul id="view">
		<li>번호</li>
		<li>${dto.no }</li>
		<li>글쓴이</li>
		<li>${dto.username }</li>
		<li>조회수</li>
		<li>${dto.hit }</li>
		<li>등록일</li>
		<li>${dto.writedate }</li>
		<li>제목</li>
		<li>${dto.subject }</li>
		<li>내용</li>
		<li>${dto.content }</li>
	</ul>
	<div class="boardSubMenu">
		<a href="boardList?nowPage=${vo.nowPage}<c:if test="${vo.searchWord!=null}">&searchKey=${vo.searchKey}&searchWord=${vo.searchWord}</c:if>">글목록</a>
		<c:if test="${logId==dto.userid}">
			<a href="boardEdit?no=${dto.no }&nowPage=${vo.nowPage}<c:if test="${vo.searchWord!=null }">&searchKey=${vo.searchKey }&searchWord=${vo.searchWord }</c:if>">수정</a>
			<a href="javascript:boardDel()">삭제</a>
		</c:if>
	</div>
	
	<!-- 댓글 -->
	<hr/>
	<div>
		<c:if test="${logStatus=='Y'}"> <!-- 로그인한 경우 댓글쓰기 폼 보여주기 -->
			<form method="post" id="commentForm">
				<input type="hidden" name="no" value="${dto.no}"/>
				<textarea name="coment" id="comment"></textarea>
				<button>댓글등록</button>
			</form>
		</c:if>
		<ul id="commentList">
			
		</ul>
	</div>
</div>