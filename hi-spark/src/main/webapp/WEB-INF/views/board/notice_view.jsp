<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/Cheader.jsp" %>
    <title>HISPARK NOTICE VIEW</title>
    <link rel="stylesheet" href="/css/c_view.css">
    <link rel="stylesheet" href="/css/common.css">
</head>
<body>
   

    <div class="breadcrumb-container">
        <a href="#">홈</a> / <a href="#">게시판</a> / <span class="current"><a href="/board/notice_list">NOTICE</a></span>
    </div>

    <div class="board-container">
        <h2 class="board-title">NOTICE</h2>
        
        <div class="view-header">
            <h3>${notice.btitle}</h3>
            <div class="view-info">
                <span class="view-info-name">${notice.member.name}</span>
                <span>작성일</span>
                <span class="view-info-datehit">
                    <fmt:formatDate value="${notice.bdate}" pattern="yyyy.MM.dd" />
                </span>
                <span>조회수</span>
                <span class="view-info-datehit">${notice.bhit}</span>
            </div>
        </div>

        <div class="view-content">
    <c:set var="content" value="${fn:replace(notice.bcontent, newline, '<br>')}" />
    ${content}
    
    <c:if test="${not empty notice.bfile}">
        <div style="margin-top: 20px;">
            <img src="/display/${notice.bfile}" alt="첨부 이미지" style="max-width: 720px; height: auto;">
        </div>
    </c:if>
</div>
        
        <div class="view-navigation">
    <table style="width:100%; border-collapse: collapse;">
        <colgroup>
            <col style="width: 80px;">
            <col>
            <col style="width: 80px;">
        </colgroup>
        <tbody>
            <c:choose>
                <c:when test="${not empty nextNotice}">
                    <tr>
                        <td>다음글</td>
                        <td><a href="/board/notice_view?bno=${nextNotice.bno}">${nextNotice.btitle}</a></td>
                        <td><fmt:formatDate value="${nextNotice.bdate}" pattern="MM.dd" /></td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td>다음글</td>
                        <td><a>다음글이 없습니다.</a></td>
                        <td></td>
                    </tr>
                </c:otherwise>
            </c:choose>

            <c:choose>
                <c:when test="${not empty previousNotice}">
                    <tr>
                        <td>이전글</td>
                        <td><a href="/board/notice_view?bno=${previousNotice.bno}">${previousNotice.btitle}</a></td>
                        <td><fmt:formatDate value="${previousNotice.bdate}" pattern="MM.dd" /></td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td>이전글</td>
                        <td><a>이전글이 없습니다.</a></td>
                        <td></td>
                    </tr>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>
</div>

        <div class="button-group">
            <div class="edit-delete-buttons">
                <c:if test="${sessionScope.loggedInMember != null && sessionScope.loggedInMember.name == '관리자'}">
                    <button type="button" class="edit-button" onclick="location.href='/board/notice_edit?bno=${notice.bno}'">수정</button>
                    <button type="button" class="delete-button" onclick="deleteNotice(${notice.bno})">삭제</button>
                </c:if>
                <button type="button" class="list-button" onclick="location.href='/board/notice_list'">목록</button>
            </div>
        </div>
    </div>

    <script>
    function deleteNotice(bno) {
        if (confirm('정말로 삭제하시겠습니까?')) {
            // POST 요청을 보내기 위해 폼을 동적으로 생성
            let form = document.createElement('form');
            form.setAttribute('method', 'post');
            form.setAttribute('action', '/board/notice_delete');
            
            let hiddenField = document.createElement('input');
            hiddenField.setAttribute('type', 'hidden');
            hiddenField.setAttribute('name', 'bno');
            hiddenField.setAttribute('value', bno);
            
            form.appendChild(hiddenField);
            document.body.appendChild(form);
            form.submit();
        }
    }
    </script>
<%@ include file="../layout/footer.jsp" %>