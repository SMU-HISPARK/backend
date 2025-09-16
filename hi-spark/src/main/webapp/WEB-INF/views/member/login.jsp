<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>로그인</title>
</head>
<body>
    <div style="text-align: center; margin-top: 100px;">
        <h2>로그인</h2>
        <form action="/member/login" method="post">
            <div>
                <input type="text" name="loginId" placeholder="아이디" required>
            </div>
            <br>
            <div>
                <input type="password" name="password" placeholder="비밀번호" required>
            </div>
            <br>
            <button type="submit">로그인</button>
        </form>
    </div>

    <script>
        const urlParams = new URLSearchParams(window.location.search);
        if (urlParams.get('error')) {
            alert('로그인에 실패했습니다. 아이디와 비밀번호를 확인해주세요.');
        }
    </script>
</body>
</html>