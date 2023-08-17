<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>마이페이지</title>
		<link rel="stylesheet" href="../resources/css/main.css">
	</head>
	<body>
		<h1>마이페이지</h1>
		<form action="/member/modify.kh" method="post">
			<fieldset>
				<ul>
					<li>
						<label>아이디</label>
						<input type="text" name="memberId" value="${member.memberId}" readonly>	
					</li>
					<li>
						<label> 비밀번호 *</label>
						<input type="password" name="memberPw" >	
					</li>
					<li>
						<label>이름</label>
						<input type="text" name="memberName" value="${member.memberName}" readonly>	
					</li>
					<li>
						<label>나이</label>
						<input type="text" name="memberAge" value="${member.memberAge}" readonly>
					</li>
					<li>
						<label>성별</label>
						<input type="hidden" name="memberGender" value="${member.memberGender}" readonly>
						<c:if test="${member.memberGender eq 'M'}">남자</c:if>
						<c:if test="${member.memberGender eq 'F'}">여자</c:if>
					</li>
					<li>
						<label>이메일 *</label>
						<input type="text" name="memberEmail" value="${member.memberEmail }">	
					</li>
					<li>
						<label>전화번호 *</label>
						<input type="text" name="memberPhone" value="${member.memberPhone }">	
					</li>
					<li>
						<label>주소 *</label>
						<input type="text" name="memberAddress" value= "${member.memberAddress }">
					</li>
					<li>
						<label>취미 *</label>
						<input type="text" name="memberHobby" value="${member.memberHobby}">
					</li>
				</ul>
			</fieldset>
			<div>
				<input type="submit" value="수정하기">
			</div>
		</form>
		<div>
			<a href="/home.kh">이전페이지로 이동</a>
			
		</div>
	</body>
</html>