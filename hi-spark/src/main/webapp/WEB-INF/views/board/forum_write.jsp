<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>HISPARK FORUM WRITE</title>
    <link rel="stylesheet" href="../css/c_write.css">
    <link rel="stylesheet" href="../css/common.css">
    
   
</head>
<body>
    <div style="width:100%; height:150px; margin-bottom:20px; position: relative; background-color:#035fe0;">
        <a style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); font-size:30px; color:white; font-weight:700;">header</a>
    </div>
    <div class="breadcrumb-container">
        <a href="#">홈</a> / <a href="#">게시판</a> / <a href="#"><span class="current">자유게시판</span></a>
    </div>

    <div class="board-container">
        <h2 class="board-title">FORUM</h2>
        
        <form class="write-form">
            <div class="form-group">
                <label for="post-title">제목</label>
                <input type="text" id="post-title" name="post-title" placeholder="제목을 입력하세요" required>
            </div>
            
            <div class="form-group">
                <label for="post-content">내용</label>
                <textarea id="post-content" name="post-content" placeholder="내용을 입력하세요" required></textarea>
            </div>
            
            <div class="form-group">
                <label for="post-file">파일 첨부</label>
                <input type="file" id="post-file" name="post-file">
            </div>

            <div class="button-group">
                <button type="submit" class="save-button">저장</button>
                <button type="button" class="cancel-button">취소</button>
            </div>
        </form>
    </div>
</body>
</html>