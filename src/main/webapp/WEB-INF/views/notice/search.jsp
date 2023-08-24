<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>공지사항 목록</title>
		<link rel="stylesheet" href="../resources/css/notice/notice.css">
	</head>
	<body>
		<h1>공지사항 목록</h1>
		<table>
			<thead>
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>작성자</th>
					<th>작성날짜</th>
					<th>첨부파일</th>
<!-- 					<th>조회수</th> -->
				</tr>
			</thead>
			<tbody>
			<!-- 	list데이터는 items에 넣었고 var에서 설정한 변수로 list데이터에서 -->
			<!-- 	꺼낸 값을 사용하고 i값은 varStatus로 사용 -->
				<c:forEach var="notice" items="${sList }" varStatus="i">
					<tr>
						<td>${i.count }</td>
						<!-- c:url : url을 관리하기 용이함 -->
						<c:url var="detailUrl" value="/notice/detail.kh">
							<c:param name="noticeNo" value="${notice.noticeNo}"></c:param>
						</c:url>
						<td><a href="${detailUrl}">${notice.noticeSubject}</td>
						<td>${notice.noticeWriter }</td>
						<td>
							<fmt:formatDate pattern="yyyy-MM-dd" value="${notice.nCreateDate }"/>			
						</td>
						<td>
							<c:if test="${!empty notice.noticeFilename }">O</c:if>
							<c:if test="${empty notice.noticeFilename }">X</c:if>
						</td>
<!-- 						<td> -->
<%-- 							<fmt:formatNumber pattern="##,###,###" value="123000"></fmt:formatNumber> --%>
<!-- 						</td> -->
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr align="center">
					<td colspan="5">
						<c:if test="${pInfo.startNavi ne 1}">
							<c:url var="pageUrl" value="/notice/search.kh">
								<c:param name="page" value="${pInfo.startNavi-1}"></c:param>
  								<c:param name="searchCondition" value="${searchCondition}"/>
      							<c:param name="searchKeyword" value="${searchKeyword}"/>							
							</c:url>
							<a href="${pageUrl }"><</a>
<!-- 							&nbsp; -->
						</c:if>
						<c:forEach begin="${pInfo.startNavi}" end="${pInfo.endNavi }" var="p">
							<c:url var="pageUrl" value="/notice/search.kh">
								<c:param name="page" value="${p }"></c:param>
  								<c:param name="searchCondition" value="${searchCondition}"/>
      							<c:param name="searchKeyword" value="${searchKeyword}"/>								
							</c:url>
							<a href="${pageUrl }">${p }</a>&nbsp;			
						</c:forEach>
						<c:if test="${pInfo.endNavi ne pInfo.naviTotalCount }"> 
							<c:url var="pageUrl" value="/notice/search.kh">
								<c:param name="page" value="${pInfo.endNavi+1}"></c:param>
  								<c:param name="searchCondition" value="${searchCondition}"/>
      							<c:param name="searchKeyword" value="${searchKeyword}"/>								
							</c:url>
							<a href="${pageUrl }">></a>
						</c:if>							
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<form action="/notice/search.kh" method="GET">
							<select name="searchCondition">
								<option value="all" <c:if test="${searchCondition =='all'}">selected</c:if>>전체</option>
								<option value="writer" <c:if test="${searchCondition =='writer'}">selected</c:if>>작성자</option>
								<option value="title" <c:if test="${searchCondition =='title'}">selected</c:if>>제목</option>
								<option value="content" <c:if test="${searchCondition =='content'}">selected</c:if>>내용</option>
							</select>
							<input type="text" name="searchKeyword" placeholder="검색어를 입력하세요" value="${searchKeyword}">
							<input type="submit" value="검색">
						</form>
					</td>
					<td>
						<button>글쓰기</button>
					</td>
				</tr>
			</tfoot>
		</table>
	</body>
</html>