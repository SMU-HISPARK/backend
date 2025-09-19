<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/Cheader.jsp" %>
    <title>HISPARK VOTE EDIT</title>
    <link rel="stylesheet" href="/css/c_write.css">
    <link rel="stylesheet" href="/css/VW.css">
    <link rel="stylesheet" href="/css/common.css">
    <script src="/js/vote_write.js" defer></script>
</head>
<body>

    <div class="breadcrumb-container">
        <a href="#">홈</a> / <a href="#">게시판</a> / <a href="#"><span class="current">투표게시판</span></a>
    </div>

    <div class="board-container">
        <h2 class="board-title">VOTE</h2>

        <form class="write-form" action="/board/vote_edit" method="post" enctype="multipart/form-data">
            <input type="hidden" name="pollNo" value="${poll.poll_no}">
            <input type="hidden" name="poll_file_old" value="${poll.poll_file}">
            
            <div class="form-group">
                <label for="poll_title">제목</label>
                <input type="text" id="poll_title" name="poll_title" value="${poll.poll_title}" required readonly>
            </div>
            <div style="text-align:center;">
            	<p>투표글 수정은 투표종료시간만 수정 가능합니다</p>
            </div>
            
            <div class="form-group">
                <label for="poll_end_date">종료일</label>
                <c:if test="${not empty poll.poll_end_date}">
                    <input type="datetime-local" id="poll_end_date" name="poll_end_date" value="<fmt:formatDate value="${poll.poll_end_date}" pattern="yyyy-MM-dd'T'HH:mm"/>" required>
                </c:if>
                <c:if test="${empty poll.poll_end_date}">
                    <input type="datetime-local" id="poll_end_date" name="poll_end_date" required>
                </c:if>
            </div>
            
            <div class="button-group">
                <button type="submit" class="save-button">수정</button>
                <button type="button" class="cancel-button" onclick="location.href='/board/vote_view?pollNo=${poll.poll_no}'">취소</button>
            </div>
        </form>
    </div>
<%@ include file="../layout/footer.jsp" %>