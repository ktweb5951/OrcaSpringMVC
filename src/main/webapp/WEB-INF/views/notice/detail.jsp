<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>공지사항 조회</title>
		<link rel="stylesheet" href="../resources/css/main.css">
	</head>
	<body>
		<h1>공지 조회</h1>
			<ul>
				<li>
					<label>제목</label>
					<span>${notice.noticeSubject }</span>
				</li>
				<li>
					<label>작성자</label>
					<span>${notice.noticeWriter }</span>
				</li>
				<li>
					<label>내용</label>
					<p>${notice.noticeContent}<p>
				</li>
				<li>
					<img alt-="첨부파일" src="../resources/nuploadFiles/${notice.noticeFileRename }"><br>
					<label>첨부파일</label>
					<!--String으로 받을 수 없고 변환작업이 필요함 -->
					<!-- 이미지 보여주기 -->
<%-- 					<img alt="첨부파일" src="${noticeFilepath }"> --%>
					<!-- 이미지 다운로드 -->
					<a href="../resources/nuploadFiles/${notice.noticeFileRename }" download>${notice.noticeFileRename }</a>
				</li>
			</ul>
			<div>		
				<button type="button" onclick="showModifyPage();">수정하기</button>
				<button type="button" onclick="deleteBoard();">삭제하기</button>
				<button type="button" onclick="moveListPage();">목록으로</button>
			</div>
			<script>
				function showModifyPage(){
					const noticeNo = "${notice.noticeNo}";
					location.href="/notice/modify.kh?noticeNo="+noticeNo;
				}
				function moveListPage(){
					location.href="/notice/list.kh";
				}
				
				function deleteBoard(){
					const noticeNo = "${notice.noticeNo}";
					 location.href = "/notice/delete.kh?noticeNo=" + noticeNo;
				}
			</script>

	</body>
</html>