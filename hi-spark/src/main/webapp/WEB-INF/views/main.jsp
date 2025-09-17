<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>메인 페이지</title>
</head>
<style>
    h2 {
        display: inline-block;
    }
</style>
<body>

<h2><a href="/board/forum_list">자유 리스트</a></h2>
<h2> | </h2>
<h2><a href="/board/forum_view">자유 뷰</a></h2>
<h2> | </h2>
<h2><a href="/board/forum_write">자유 쓰기</a></h2><br>
<h2><a href="/board/notice_list">공지 리스트</a></h2><h2> | </h2>
<h2>공지 뷰(막힘)</h2><h2> | </h2>
<h2><a href="/board/notice_write">공지 쓰기</a></h2><br>
<h2><a href="/board/vote_list">투표 리스트</a></h2><h2> | </h2>
<h2>투표 뷰(막힘)</h2><h2> | </h2>
<h2><a href="/board/vote_write">투표 쓰기</a></h2><br>
<h2><a href="/board/test">테스트</a></h2>
<br>
<hr>

<c:if test="${empty sessionScope.loggedInMember}">
    <h2><a href="/member/join">회원가입</a></h2><h2> | </h2>
    <h2><a href="/member/login">로그인</a></h2>
</c:if>

<c:if test="${not empty sessionScope.loggedInMember}">
    <h2>아이디 : ${sessionScope.loggedInMember.loginId}</h2>
    <h2><a href="member/logout">로그아웃</a></h2>
</c:if>

</body>
</html>