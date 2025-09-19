<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="../layout/Cheader.jsp" %>
<title>HISPARK FORUM WRITE</title>
<link rel="stylesheet" href="/css/c_write.css">
<link rel="stylesheet" href="/css/common.css">

</head>
<body>
<div class="breadcrumb-container">
<a href="#">홈</a> / <a href="#">게시판</a> / <a href="#"><span class="current">자유게시판</span></a>
</div>

<div class="board-container">
    <h2 class="board-title">FORUM</h2>
    
    <form class="write-form" action="/board/forum_write_proc" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label for="post-title">제목</label>
            <input type="text" id="post-title" name="btitle" placeholder="제목을 입력하세요" required>
        </div>
        
        <div class="form-group">
            <label for="post-content">내용</label>
            <textarea id="post-content" name="bcontent" placeholder="내용을 입력하세요" required></textarea>
        </div>
        
        <div class="form-group">
            <label for="post-file">파일 첨부</label>
            <input type="file" id="post-file" name="file">
        </div>
        
        <div class="button-group">
            <button type="submit" class="submit-button">작성</button>
            <button type="button" class="cancel-button" onclick="history.back()">취소</button>
        </div>
    </form>
</div>

</body>
</html>