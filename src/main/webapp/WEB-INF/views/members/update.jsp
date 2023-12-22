<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>update.jsp</title>
</head>
<body>
<h1>/members/update.jsp</h1>

		<!-- 로그인 여부(세션정보)에 따라서 페이지 이동 (JSTL) -->
		<c:if test="${ empty id }">
		   <c:redirect url="/members/login"/>	
		</c:if>
		
	<fieldset>
		<legend>스프링MVC 회원수정</legend>
		<form method="post">
			아이디 <input type="text" name="userid" value="${memberVO.userid }" readonly><br>
			비밀번호 <input type="password" name="userpw" ><br>
			이름 <input type="text" name="username" value="${memberVO.username }"><br>
			이메일 <input type="text" name="useremail" value="${memberVO.useremail }"><br>
			
			<input type="submit" value="회원수정">
		</form>
	</fieldset>
</body>
</html>