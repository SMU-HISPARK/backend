<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>커뮤니티</title>
    <link rel="stylesheet" href="/css/community.css">
    <style>
        .container { max-width: 900px; margin: 0 auto; padding: 20px; }
        h2 { margin-bottom: 20px; font-weight: 600; }
        .btn-write {
            float: right; 
            margin-bottom: 15px;
            padding: 8px 15px;
            background: #1a1a1a;
            color: white;
            border: none;
            cursor: pointer;
        }
        .board-list { width: 100%; border-collapse: collapse; }
        .board-list th, .board-list td {
            border-bottom: 1px solid #ddd;
            padding: 10px;
            text-align: center;
            font-size: 14px;
        }
        .board-list th { background: #f9f9f9; }
        .board-list td.title { text-align: left; cursor: pointer; color:#333; }
        .board-list td.title:hover { text-decoration: underline; }
        .pagination { margin-top: 20px; text-align: center; }
        .pagination a {
            margin: 0 5px;
            padding: 5px 10px;
            border: 1px solid #ddd;
            text-decoration: none;
            color: #333;
        }
        .pagination a.active {
            background: #1a1a1a;
            color: white;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>커뮤니티</h2>

    <!-- 로그인한 회원만 글쓰기 버튼 보이기 -->
    <c:if test="${not empty sessionScope.session_id}">
        <button class="btn-write" onclick="location.href='/community/write'">글쓰기</button>
    </c:if>
    <div style="clear:both;"></div>

    <!-- 게시글 목록 -->
    <table class="board-list">
        <thead>
            <tr>
                <th>번호</th>
                <th style="width:50%;">제목</th>
                <th>작성자</th>
                <th>조회수</th>
                <th>작성일</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="board" items="${boardList}">
                <tr>
                    <td>${board.bno}</td>
                    <td class="title" onclick="viewPost(${board.bno})">${board.btitle}</td>
                    <td>${board.member.memberId}</td>
                    <td>${board.bhits}</td>
                    <td><fmt:formatDate value="${board.bdate}" pattern="yyyy-MM-dd"/></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <!-- 페이징 -->
    <div class="pagination">
        <c:if test="${page > 1}">
            <a href="?page=${page-1}">&lt;</a>
        </c:if>
        <c:forEach begin="1" end="${totalPages}" var="i">
            <a href="?page=${i}" class="${page == i ? 'active' : ''}">${i}</a>
        </c:forEach>
        <c:if test="${page < totalPages}">
            <a href="?page=${page+1}">&gt;</a>
        </c:if>
    </div>
</div>

<script>
    function viewPost(bno){
        location.href = "/community/detail?bno=" + bno;
    }
</script>
</body>
</html>
<%@ include file="../layout/footer.jsp" %>
