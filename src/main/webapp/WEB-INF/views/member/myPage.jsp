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
		
		<fieldset>
			<ul>
				<li>
					<label>아이디 : </label>
					<span>${member.memberId}</span>
				</li>
				<li>
					<label>이름 : </label>
					<span>${member.memberName}</span>
				</li>
				<li>
					<label>나이 : </label>
					<span>${member.memberAge}</span>
				</li>
				<li>
					<label>성별 : </label>
					<span>${member.memberGender}</span>
				</li>
				<li>
					<label>이메일 : </label>
					<span>${member.memberEmail }</span>
				</li>
				<li>
					<label>전화번호 : </label>
					<span>${member.memberPhone }</span>
				</li>
				<li>
					<label>주소 : </label>
					<span>${member.memberAddress }</span>
				</li>
				<li>
					<label>취미 : </label>
					<span>${member.memberHobby}</span>
				</li>
			</ul>
		</fieldset>
		<div>
			<a href="/home.kh">이전페이지로 이동</a>
			<a href="/member/modify.kh?memberId=${memberId}">정보수정</a>
			<a href="javascript:void(0)" onclick="checkDelete();">회원탈퇴</a>
		</div>
		<script>
			function checkDelete(){
				const memberId = '${sessionScope.memberId}'
				if(confirm("탈퇴하시겠습니까?")){
					location.href="/member/delete.kh?memberId="+memberId;
				}
			}
		</script>
	</body>
</html>