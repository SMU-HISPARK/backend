<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>게시글 상세</title>
    <style>
        .container { max-width: 800px; margin: 0 auto; padding: 20px; }
        h2 { margin-bottom: 20px; }
        .post-header { border-bottom: 1px solid #ddd; margin-bottom: 15px; padding-bottom: 10px; }
        .post-header span { margin-right: 10px; font-size: 14px; color: #555; }
        .post-content { min-height: 200px; line-height: 1.6; margin-bottom: 20px; }
        .post-actions button {
            padding: 6px 12px; margin-right: 10px;
            background: #1a1a1a; color: #fff; border: none; cursor: pointer;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>${board.btitle}</h2>

    <div class="post-header">
        <span>작성자: ${board.member.memberId}</span>
        <span>조회수: ${board.bhits}</span>
        <span>작성일: <fmt:formatDate value="${board.bdate}" pattern="yyyy-MM-dd HH:mm"/></span>
    </div>

    <div class="post-content">
        ${board.bcontent}
    </div>

    <!-- 로그인한 회원이 글쓴이일 경우만 수정/삭제 버튼 보이게 -->
    <c:if test="${sessionScope.session_id == board.member.memberId}">
        <div class="post-actions">
            <button onclick="location.href='/community/edit?bno=${board.bno}'">수정</button>
            <button onclick="deletePost(${board.bno})">삭제</button>
        </div>
    </c:if>

    <button onclick="location.href='/community'">목록으로</button>
</div>

<script>
    function deletePost(bno){
        if(confirm("정말 삭제하시겠습니까?")){
            location.href = "/community/delete?bno=" + bno;
        }
    }
</script>
</body>
</html>
<%@ include file="../layout/footer.jsp" %>
