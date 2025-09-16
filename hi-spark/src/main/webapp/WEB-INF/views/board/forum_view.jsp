<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>HISPARK FORUM VIEW</title>
    <link rel="stylesheet" href="../css/c_view.css">
    <link rel="stylesheet" href="../css/FV.css">
    <link rel="stylesheet" href="../css/common.css">
    
</head>
<body>
    <div style="width:100%; height:150px; margin-bottom:20px; position: relative; background-color:#035fe0;">
        <a style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); font-size:30px; color:white; font-weight:700;">header</a>
    </div>

    <div class="breadcrumb-container">
        <a href="#">홈</a> / <a href="#">게시판</a> / <span class="current"><a href="#">자유게시판</a></span>
    </div>

    <div class="board-container">
        <h2 class="board-title">FORUM</h2>
        
        <div class="view-header">
            <h3>이번 앨범 진짜 최고네요 ㅠㅠ</h3>
            <div class="view-info">
                <span class="view-info-name">플둥이</span>
                <span>작성일</span>
                <span class="view-info-datehit">2025.08.30</span>
                <span>조회수</span>
                <span class="view-info-datehit">158</span>
            </div>
        </div>

        <div class="view-content">
            <p>
                안녕하세요! 이번 앨범 진짜 너무 좋아서 감동의 눈물이 흐릅니다...<br>
                수록곡까지 하나도 빠짐없이 명곡이라 계속 듣고 있어요.<br>
                앨범 기획하신 분들께 진심으로 감사드립니다!
            </p>
        </div>

        <div class="comments-section">
            <h4>댓글 (3)</h4>
            <div class="comment-list">
                <div class="comment-item">
                    <div class="comment-meta">
                        <span>야옹이</span>
                        <span>2025.08.30 14:30</span>
                    </div>
                    <div class="comment-text">
                        맞아요! 타이틀 곡도 좋지만 수록곡이 정말 주옥 같아요. 저도 계속 반복해서 듣고 있습니다!
                    </div>
                    <div class="comment-actions">
                        <button type="button" class="comment-edit-btn"><i class="fa-solid fa-pencil"></i></button>
                        <button type="button" class="comment-delete-btn"><i class="fa-solid fa-trash"></i></button>
                    </div>
                </div>
                <div class="comment-item">
                    <div class="comment-meta">
                        <span>거북이</span>
                        <span>2025.08.30 14:35</span>
                    </div>
                    <div class="comment-text">
                        저도 같은 마음입니다 ㅠㅠ 너무 좋아서 덕질할 맛 나요.
                    </div>
                    <div class="comment-actions">
                        <button type="button" class="comment-edit-btn"><i class="fa-solid fa-pencil"></i></button>
                        <button type="button" class="comment-delete-btn"><i class="fa-solid fa-trash"></i></button>
                    </div>
                </div>
                <div class="comment-item">
                    <div class="comment-meta">
                        <span>아싸_정신차려</span>
                        <span>2025.08.30 15:01</span>
                    </div>
                    <div class="comment-text">
                        저도 덕분에 앨범을 더 잘 즐길 수 있는 것 같아요!
                    </div>
                    <div class="comment-actions">
                        <button type="button" class="comment-edit-btn"><i class="fa-solid fa-pencil"></i></button>
                        <button type="button" class="comment-delete-btn"><i class="fa-solid fa-trash"></i></button>
                    </div>
                </div>
            </div>
            
            <div class="comment-form">
                <textarea placeholder="댓글을 입력하세요"></textarea>
                <button type="button">댓글 작성</button>
            </div>
        </div>
        
        <div class="view-navigation">
            <table style="width:100%; border-collapse: collapse;">
                <colgroup>
                    <col style="width: 80px;">
                    <col>
                    <col style="width: 80px;">
                </colgroup>
                <tbody>
                      <%--  <c:if test="${not empty previousArticle}">  --%>
                        <tr>
                            <td>이전글</td>
                            <td><a href="/article/notice/${previousArticle.id}">${previousArticle.title}</a></td>
                            <td>${previousArticle.date}</td>
                        </tr>
                     <%-- </c:if>  --%>
                     <%-- <c:if test="${not empty nextArticle}">  --%>
                        <tr>
                            <td>다음글</td>
                            <td><a href="/article/notice/${nextArticle.id}">${nextArticle.title}</a></td>
                            <td>${nextArticle.date}</td>
                        </tr>
                     <%-- </c:if>  --%>
                </tbody>
            </table>
        </div>

        <div class="button-group">
            <div class="edit-delete-buttons">
                <!-- <%-- <c:if test="${isAuthor}"> --%> -->
                    <button type="button" class="edit-button">수정</button>
                    <button type="button" class="delete-button">삭제</button>
                <!-- <%-- </c:if> --%> -->
            </div>
            <button type="button" class="list-button">목록</button>
        </div>
    </div>
</body>
</html>