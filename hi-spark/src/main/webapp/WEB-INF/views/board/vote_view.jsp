<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/Cheader.jsp" %>
    <title>HISPARK VOTE VIEW</title>
    <link rel="stylesheet" href="/css/c_view.css">
    <link rel="stylesheet" href="/css/VV.css">
    <link rel="stylesheet" href="/css/common.css">
    <script src="/js/vote_view.js" defer></script>
    <script type="text/javascript" src="/js/base.js"></script>
</head>
<body>

    <div class="breadcrumb-container">
        <a href="/">홈</a> / <a href="/board/vote_list">게시판</a> / <span class="current">투표게시판</span>
    </div>

    <div class="board-container">
        <h2 class="board-title">VOTE</h2>
        
        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>
        
        <div class="view-header" data-poll-end-date="${poll.poll_end_date}" >
		    <h3>${poll.poll_title}</h3>
		    <div class="view-info">
		        <span class="view-info-name">${poll.member.nickname}</span>
		        <span>작성일</span>
		        <span class="view-info-datehit"><fmt:formatDate value="${poll.poll_start_date}" pattern="yyyy.MM.dd"/></span>
		        <span>종료일</span>
		        <span class="view-info-datehit"><fmt:formatDate value="${poll.poll_end_date}" pattern="yyyy.MM.dd"/></span>
		    </div>
		</div>
        
        <div class="view-content">
            <%-- <div class="poll-content">${poll.poll_content}</div> --%>
        
            <c:choose>
                <c:when test="${hasVoted || isPollEnded}">
                    <div class="vote-options-container">
                        <div class="vote-title-re">
                            <h4>${poll.poll_title}</h4>
                            <span id="voteEndTime" class="vote-end-time" style="padding-top:20px;">
                                <c:if test="${isPollEnded}">투표가 종료되었습니다.</c:if>
                                <c:if test="${!isPollEnded}">투표 종료 : <fmt:formatDate value="${poll.poll_end_date}" pattern="yyyy.MM.dd HH:mm"/></c:if>
                            </span>
                        </div>
                        
                        <ul class="vote-options">
                            <c:forEach var="item" items="${pollItems}">
                                <c:set var="voteCount" value="${voteCounts[item.item_no] == null ? 0 : voteCounts[item.item_no]}" />
                                <c:set var="percentage" value="${totalVotes > 0 ? (voteCount / totalVotes) * 100 : 0}" />
                                <li class="vote-item" data-option-id="${item.item_no}">
                                    <div class="vote-item-label">
                                        <div class="vote-text-line">
                                            <span class="vote-option-name">${item.item_content}</span>
                                            <span class="vote-count-info"><span class="vote-count-value">${voteCount}표</span> (<span class="percent-value"><fmt:formatNumber value="${percentage}" pattern="#.##"/>%</span>)</span>
                                        </div>
                                        <div class="vote-progress"><div class="progress-bar" style="width:${percentage}%;"></div></div>
                                    </div>
                                </li>
                            </c:forEach>
                        </ul>
                        <div id="voteActionArea">
                            <div class="vote-status-message closed" id="voteEndStatus">
                                <c:if test="${isPollEnded}">투표가 종료되었습니다.</c:if>
                                <c:if test="${!isPollEnded && hasVoted}">투표에 참여하셨습니다.</c:if>
                            </div>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:if test="${not empty loginId}">
                        <form id="voteForm" action="/board/vote" method="post">
                            <div class="vote-options-container">
                                <input type="hidden" name="pollNo" value="${poll.poll_no}">
                                <input type="hidden" id="selectedItemNo" name="itemNo" value="">

                                <div class="vote-title-re">
                                    <h4>${poll.poll_title}</h4>
                                    <span id="voteEndTime" class="vote-end-time">투표 종료 : <fmt:formatDate value="${poll.poll_end_date}" pattern="yyyy.MM.dd HH:mm"/></span>
                                </div>
                                <ul class="vote-options">
                                    <c:forEach var="item" items="${pollItems}">
                                        <li class="vote-item" data-option-id="${item.item_no}">
                                            <div class="vote-item-label">
                                                <div class="vote-text-line">
                                                    <span class="vote-option-name">${item.item_content}</span>
                                                </div>
                                            </div>
                                        </li>
                                    </c:forEach>
                                </ul>
                                <div id="voteActionArea">
                                    <div class="vote-status-message imminent" id="voteEndStatus">투표 진행중</div>
                                    <button type="submit" id="submitButton" class="submit-button" style="display:none;">투표하기</button>
                                </div>
                            </div>
                        </form>
                    </c:if>
                    <c:if test="${empty loginId}">
                        <div class="vote-options-container">
                            <div class="vote-title-re">
                                <h4>${poll.poll_title}</h4>
                                <span class="vote-end-time">투표 종료일: <fmt:formatDate value="${poll.poll_end_date}" pattern="yyyy.MM.dd HH:mm"/></span>
                            </div>
                            <div class="poll-login-required">
                                <p>투표하려면 <a href="/member/login">로그인</a>이 필요합니다.</p>
                            </div>
                        </div>
                    </c:if>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="view-navigation">
            <table style="width:100%; border-collapse: collapse;">
                <colgroup>
                    <col style="width: 140px;">
                    <col>
                </colgroup>
                <tbody>
                    <tr>
                        <td>다음글</td>
                        <td>
                            <c:if test="${not empty nextPoll}">
                                <a href="/board/vote_view?pollNo=${nextPoll.poll_no}">${nextPoll.poll_title}</a>
                            </c:if>
                            <c:if test="${empty nextPoll}">
                                다음글이 없습니다.
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <td>이전글</td>
                        <td>
                            <c:if test="${not empty previousPoll}">
                                <a href="/board/vote_view?pollNo=${previousPoll.poll_no}">${previousPoll.poll_title}</a>
                            </c:if>
                            <c:if test="${empty previousPoll}">
                                이전글이 없습니다.
                            </c:if>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div class="button-group">
            <div class="edit-delete-buttons">
                <c:if test="${loginId == poll.member.loginId}">
                    <button type="button" class="edit-button" onclick="location.href='/board/vote_edit?pollNo=${poll.poll_no}'">수정</button>
                    <button type="button" class="delete-button" onclick="deletePoll(${poll.poll_no})">삭제</button>
                </c:if>
            </div>
            <a href="/board/vote_list"><button type="button" class="list-button">목록</button></a>
        </div>
    </div>
    
    <script>
    function deletePoll(pollNo) {
        if (confirm('정말로 삭제하시겠습니까?')) {
            var form = document.createElement('form');
            form.method = 'POST';
            form.action = '/board/vote_delete';
            
            var input = document.createElement('input');
            input.type = 'hidden';
            input.name = 'pollNo';
            input.value = pollNo;
            
            form.appendChild(input);
            document.body.appendChild(form);
            form.submit();
        }
    }
    </script>
    <c:if test="${not empty alertMessage}">
    <script>
        alert("${alertMessage}");
    </script>
</c:if>
   
<%@ include file="../layout/footer.jsp" %>