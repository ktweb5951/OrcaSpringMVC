<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>게시글 조회</title>
		<link rel="stylesheet" href="../resources/css/main.css">
	</head>
	<body>
		<h1>게시글 조회</h1>
			<ul>
				<li>
					<label>제목</label>
					<span>${board.boardTitle }</span>
				</li>
				<li>
					<label>작성자</label>
					<span>${board.boardWriter }</span>
				</li>
				<li>
					<label>내용</label>
					<p>${board.boardContent}<p>
				</li>
				<li>
					<img alt-="첨부파일" src="../resources/buploadFiles/${board.boardFileRename }"><br>
					<label>첨부파일</label>
					<!--String으로 받을 수 없고 변환작업이 필요함 -->
					<!-- 이미지 보여주기 -->
<%-- 					<img alt="첨부파일" src="${noticeFilepath }"> --%>
					<!-- 이미지 다운로드 -->
					<a href="../resources/buploadFiles/${board.boardFileRename }" download>${board.boardFileRename }</a>
				</li>
			</ul>
			<div>
			<c:url var="bUrl" value="/board/delete.kh">
				<c:param name="boardNo" value="${board.boardNo }"></c:param>
				<c:param name="boardWriter" value="${board.boardWriter }"></c:param>
			</c:url>				
			<c:if test="${board.boardWriter eq sessionScope.memberId }">
				<button type="button" onclick="showModifyPage();">수정하기</button>
			</c:if>
				<button type="button" onclick="deleteBoard('${bUrl}');">삭제하기</button>
				<button type="button" onclick="moveListPage();">목록으로</button>
			</div>
			<br>			
<!-- 			댓글등록 -->
			<form action="/reply/add.kh" method="post">
				<input type="hidden" name="refBoardNo" value="${board.boardNo }">
				<table width="500" border="1">
					<tr>
						<td>
							댓글작성
						</td>
						<td>
							<textarea rows="3" cols="55" name="replyContent"></textarea>
						</td>
						<td>
							<input type="submit" value="완료">
						</td>
					</tr>
				</table>
			</form>
<!-- 			댓글등록 -->
			<table width="500" border="1">
				<c:forEach var="reply" items="${rList }">
				<tr>
					<td>${reply.replyWriter}</td>
					<td>${reply.replyContent}</td>
					<td>${reply.rCreateDate }</td>
					<td>
						<a href="javascript:void(0)" onclick="showModifyForm(this);">수정하기</a>
						<c:url var="delUrl" value="/reply/delete.kh">
							<c:param name="replyNo" value="${reply.replyNo }"></c:param>
<!-- 							작성한 사람만 지우도록 하기 위해서 추가 -->
							<c:param name="replyWriter" value="${reply.replyWriter }"></c:param>
<!-- 							성공하면 디테일jsp로 가기 위해 필요한 boardNo세팅 -->
							<c:param name="refBoardNo" value="${reply.refBoardNo }"></c:param>
						</c:url>
						<a href="javascript:void(0);" onclick="deleteReply('${delUrl}');">삭제하기</a>
					</td>
				</tr>
				<tr id="replyModifyForm" style="display:none">
<!-- 					<form action="/reply/update.kh" method="post">					 -->
<%-- 						<input type="hidden" name="replyNo" value="${reply.replyNo }"> --%>
<%-- 						<input type="hidden" name="refBoardNo" value="${reply.refBoardNo }"> --%>
<%-- 						<td colspan="3"><input type="text" size="50" name="replyContent" value="${reply.replyContent }"></td> --%>
<!-- 						<td><input type="submit" value="완료"></td> -->
<!-- 					</form> -->
						<td colspan="3"><input id="replyContent" type="text" size="50" name="replyContent" value="${reply.replyContent }"></td>
						<td><input type="button" value="완료" onclick="replyModify(this, '${reply.replyNo}', '${reply.refBoardNo }');"></td>					
				</tr>
				</c:forEach>

			</table>
			<script>
				function deleteBoard(bUrl){
					location.href = bUrl;
				}
			
				function deleteReply(url){
					//DELETE FROM REPLY_TBL WHERE REPLY_NO = 샵{replyNo} AND R_STAUS= 'Y'
					//UPDATE REPLY_TBL SET R_STATUS = 'N' WHERE REPLY_NO=샵{replyNo}
					location.href = url;
				}
				function showModifyPage(){
					const boardNo = "${board.boardNo}";
					location.href="/board/modify.kh?boardNo="+boardNo;
				}
				function moveListPage(){
					location.href="/board/list.kh";
				}
				function showModifyForm(obj){
// 					#2. DOM프로그래밍을 이용하는 방법
	// 				<tr id="replyModifyForm" style="display:none">
	// 					<td colspan="3"><input type="text" size="50" value="${reply.replyContent }"></td>
	// 					<td><input type="button" value="완료"></td>
	// 				</tr>		
// 					const trTag = document.createElement("tr");
// 					const tdTag1 = document.createElement("td");
// 					tdTag1.colSpan = 3;
// 					const inputTag1 = document.createElement("input");
// 					inputTag1.type = "text";
// 					inputTag1.size =50;
// 					inputTag1.value =replyContent;
// 					tdTag1.appendChild(inputTag1);
// 					const tdTag2= document.createElement("td");
// 					const inputTag2 = document.createElement("input");
// 					inputTag2.type ="button";
// 					inputTag2.value = "완료";
// 					tdTag2.appendChild(inputTag2);
// 					trTag.appendChild(tdTag1);
// 					trTag.appendChild(tdTag2);
// 					console.log(trTag);
// 					//클릭한 a를 포함하고 있는 tr다음에 수정폼에 있는 tr추가하기
// 					const prevTrTag = obj.parentElement.parentElement;
// // 					if(!prevTrTag.nextElementSibling = null || !pervTrTag.nextElementSibling.querySelector("input"))
// 					prevTrTag.parentNode.insertBefore(trTag, prevTrTag.nextSibling);
						
// 					#1. HTML태그, display:none 사용하는방법
// 					document.querySelector("#replyModifyForm").style.display="";
// 					온클릭 함수 this 매개변수 스크립트에 obj 매개변수
					obj.parentElement.parentElement.nextElementSibling.style.display="";
				}
				
				function replyModify(obj,replyNo, refBoardNo){
					const form = document.createElement("form");
					form.action = "/reply/update.kh";
					form.method = "post";
					const input = document.createElement("input");
					input.type = "hidden";
					input.value = replyNo;
					input.name = "replyNo";
					const input2 = document.createElement("input");
					input2.type = "hidden";
					input2.value= refBoardNo;
					input2.name = "refBoardNo";
					const input3 = document.createElement("input");
					input3.type = "text";
					//this를 이용해서 내가 원하는 노드 찾기(this를 이용한 노드 탐색)
					input3.value = obj.parentElement.previousElementSibling.childNodes[0].value;
					input3.name = "replyContent";
					form.appendChild(input);	
					form.appendChild(input2);
					form.appendChild(input3);
					document.body.appendChild(form);
					form.submit();
				}	
				
				
			</script>

	</body>
</html>