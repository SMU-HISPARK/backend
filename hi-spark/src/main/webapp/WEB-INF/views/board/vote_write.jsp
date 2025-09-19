<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/Cheader.jsp" %>
    <title>HISPARK VOTE WRITE</title>
    <link rel="stylesheet" href="/css/c_write.css">
    <link rel="stylesheet" href="/css/VW.css">
    <link rel="stylesheet" href="/css/common.css">
    <script src="/js/vote_write.js" defer></script>

    
</head>

    <div class="breadcrumb-container">
        <a href="#">홈</a> / <a href="#">게시판</a> / <a href="#"><span class="current">투표게시판</span></a>
    </div>

    <div class="board-container">
        <h2 class="board-title">VOTE</h2>
        
        <form class="write-form" action="/board/vote_write_proc" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="poll_title">제목</label>
                <input type="text" id="poll_title" name="poll_title" placeholder="제목을 입력하세요" required>
            </div>
            
            <div class="form-group">
                <label for="poll_content">내용</label>
                <textarea id="poll_content" name="poll_content" placeholder="내용을 입력하세요"></textarea>
            </div>

            <div class="form-group">
                <label for="poll_end_date">투표 종료</label>
                <input type="datetime-local" id="poll_end_date" name="poll_end_date" required>
            </div>
            
            <div class="form-group">
                <div class="label-group">
                    <label></label>
                    <button type="button" class="add-option-btn">+</button>
                </div>
                <div class="vote-options-wrapper">
                    <div id="vote-options">
                        <div class="vote-option">
                            <input type="text" name="poll_items" placeholder="투표 항목을 입력하세요" required>
                            <button type="button" class="remove-option-btn">삭제</button>
                        </div>
                        <div class="vote-option">
                            <input type="text" name="poll_items" placeholder="투표 항목을 입력하세요" required>
                            <button type="button" class="remove-option-btn">삭제</button>
                        </div>
                    </div>
                </div>
            </div>
            
            <div class="file-group">
                <label for="post_file">파일 첨부</label>
                <input type="file" id="post_file" name="post_file">
            </div>

            <div class="button-group">
                <button type="submit" class="save-button">저장</button>
                <button type="button" class="cancel-button">취소</button>
            </div>
        </form>
    </div>
    
<%@ include file="../layout/footer.jsp" %>