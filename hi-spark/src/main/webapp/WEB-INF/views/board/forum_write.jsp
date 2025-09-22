<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="../layout/Cheader.jsp" %>
<title>HISPARK FORUM EDIT</title>
<link rel="stylesheet" href="/css/c_write.css">
<link rel="stylesheet" href="/css/common.css">

</head>
<body>
<div class="breadcrumb-container">
<a href="#">홈</a> / <a href="/board/forum_list">게시판</a> / <a href="/board/forum_list"><span class="current">자유게시판</span></a>
</div>

<div class="board-container">
    <h2 class="board-title">FORUM</h2>
    
    <form class="write-form" action="/board/forum_write_proc" method="post" enctype="multipart/form-data">
        <input type="hidden" name="bno" value="${board.bno}">
        
        <div class="form-group">
            <label for="post-title">제목</label>
            <input type="text" id="post-title" name="btitle" placeholder="제목을 입력하세요" value="${board.btitle}" required>
        </div>
        
        <div class="form-group">
            <label for="post-content">내용</label>
            <textarea id="post-content" name="bcontent" placeholder="내용을 입력하세요" required>${board.bcontent}</textarea>
        </div>
        
        <div class="form-group">
            <label for="post-file">파일 첨부</label>
            <input type="file" id="post-file" name="file">
        </div>
        
        <div class="button-group">
            <button type="submit" class="write-btn" style="background-color: #035fe0; color: #fff; border-color: #035fe0;">확인</button>
            <button type="button" class="cancel-btn" style="background-color: #fafafa; color: #333;" onclick="location.href='/board/forum_view?bno=${board.bno}'">취소</button>
        </div>
    </form>
</div>
<%@ include file="../layout/footer.jsp" %>