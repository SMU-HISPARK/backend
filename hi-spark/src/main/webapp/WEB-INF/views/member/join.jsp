<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원 가입</title>
</head>
<body>
    <div style="text-align: center; margin-top: 50px;">
        <h2>회원 가입</h2>
        <form action="/member/join" method="post">
            <div>
                <label for="loginId">아이디:</label>
                <input type="text" id="loginId" name="loginId" required>
            </div>
            <br>
            <div>
                <label for="password">비밀번호:</label>
                <input type="password" id="password" name="password" required>
            </div>
            <br>
            <div>
                <label for="name">이름:</label>
                <input type="text" id="name" name="name" required>
            </div>
            <br>
            <div>
                <label for="email">이메일:</label>
                <input type="email" id="email" name="email" required>
            </div>
            <br>
            <button type="submit">가입</button>
        </form>
    </div>
</body>
</html>