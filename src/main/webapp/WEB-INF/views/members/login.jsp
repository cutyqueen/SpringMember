<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>login.jsp</title>
</head>
<body>
<h4>http://localhost:8088/members/login 호출</h4>
<h4>/views/members/login.jsp</h4>
<fieldset>
<legend>로그인</legend>
<form action="" method="post">
	아이디 <input type="text" name="userid"><br>
	비밀번호 <input type="password" name="userpw"><br>
	<input type="submit" value="로그인">
	<input type="button" value="회원가입" onclick="location.href='/members/join';">
</form>
</fieldset>
</body>
</html>