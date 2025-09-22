<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/Cheader.jsp" %>
    <title>HISPARK FORUM VIEW</title>
    <link rel="stylesheet" href="/css/c_view.css">
    <link rel="stylesheet" href="/css/common.css">
    <link rel="stylesheet" href="/css/FV.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
    <script type="text/javascript" src="/js/base.js"></script>
</head>
<body>

    <div class="breadcrumb-container">
        <a href="#">홈</a> / <a href="/board/forum_list">게시판</a> / <span class="current"><a href="/board/forum_list">자유게시판</a></span>
    </div>

    <div class="board-container">
        <h2 class="board-title">FORUM</h2>
        
        <div class="view-header">
            <h3>${board.btitle}</h3>
            <div class="view-info">
                <span class="view-info-name">${board.member.name}</span>
                <span>작성일</span>
                <span class="view-info-datehit">
                    <fmt:formatDate value="${board.bdate}" pattern="yyyy.MM.dd" />
                </span>
                <span>조회수</span>
                <span class="view-info-datehit">${board.bhit}</span>
            </div>
        </div>
 
        <div class="view-content">
			<c:set var="newline" value="<%= System.getProperty(\"line.separator\") %>" />
			<c:set var="content" value="${fn:replace(board.bcontent, newline, '<br>')}" />

			<c:if test="${!originalContent.equals(board.bcontent)}">
			    <span style='color: #035fe0;'>AI 클린봇이 적용된 게시글입니다. 욕설이나 비방이 감지된 문장은 삭제되거나 필터링됩니다.</span><br><br>
			</c:if>
            ${content}
            
            <c:if test="${not empty board.bfile}">
                <div style="margin-top: 20px;">
                    <img src="/display/${board.bfile}" alt="첨부 이미지" style="max-width: 100%; height: auto; display: block;">
                </div>
            </c:if>

            <div class="like-container">
                <div class="like-box">
                    <a href="#" class="like-button-style" id="like-button">
                        <span id="like-icon">
                            <c:choose>
                                <c:when test="${isLiked}">
                                    <i class="fa-solid fa-heart liked"></i>
                                </c:when>
                                <c:otherwise>
                                    <i class="fa-regular fa-heart"></i>
                                </c:otherwise>
                            </c:choose>
                        </span>
                        <span class="like-count" id="like-count">${likeCount}</span>
                    </a>
                </div>
            </div>
        </div>

        <div class="comment-section">
            <h4>댓글 <span id="commentCount">(${commentCount})</span></h4>
            <div class="comment-List">
                <c:forEach var="comment" items="${comments}">
                    <div class="comment-item" data-scno="${comment.scno}">
                        <div class="comment-meta">
                            <span class="comment-author">${comment.member.name}</span>
                            <span class="comment-date"><fmt:formatDate value="${comment.scdate}" pattern="yyyy.MM.dd HH:mm"/></span>
                        </div>
                        <div class="comment-body">
							<c:if test="${!originalComment.equals(comment.sccontent)}">
							    <span style='color: #035fe0;'>AI 클린봇이 적용된 댓글입니다. 욕설이나 비방이 감지된 문장은 삭제되거나 필터링됩니다.</span><br><br>
							</c:if>

							${comment.sccontent}
						
						</div>
                        <div class="comment-actions">
                            
                            <%-- 댓글 작성자이거나 관리자인 경우 수정/삭제 버튼 표시 --%>
                            <c:if test="${sessionScope.loggedInMember.memberId == comment.member.memberId || sessionScope.loggedInMember.name == '관리자'}">
                                <a href="javascript:void(0);" onclick="editComment(${comment.scno})" class="comment-action-icon">
								    <i class="fas fa-edit"></i>
								</a>
                                
                                <a href="javascript:void(0);" onclick="deleteComment(${comment.scno})" class="comment-action-icon">
                                    <i class="fas fa-trash-alt"></i>
                                </a>
                            </c:if>
                            <%-- 좋아요 버튼 --%>
                            <div class="comment-like-box">
                                <a href="javascript:void(0);" class="comment-like-button" data-scno="${comment.scno}">
                                    <i class="fa-heart <c:if test="${boardService.isCommentLikedByUser(comment.scno, sessionScope.loggedInMember.memberId)}">fas</c:if><c:if test="${!boardService.isCommentLikedByUser(comment.scno, sessionScope.loggedInMember.memberId)}">far</c:if>"></i>
                                </a>
                                <span class="comment-like-count">${boardService.getCommentLikeCount(comment.scno)}</span>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            
            <c:if test="${not empty loggedInMember}">
                <div class="comment-form">
                    <form id="scommentForm" action="/board/addComment" method="post">
                        <input type="hidden" name="bno" value="${board.bno}">
                        <textarea class="form-control" name="sccontent" style="width:1208px; " rows="3" placeholder="댓글을 입력하세요..." required></textarea>
                        <div class="btn-primary" style="align-items:flex-end; margin-left:1150px;">
                            <button type="submit">등록</button>
                        </div>
                    </form>
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
                        <c:when test="${not empty nextBoard}">
                            <tr>
                                <td>다음글</td>
                                <td><a href="/board/forum_view?bno=${nextBoard.bno}">${nextBoard.btitle}</a></td>
                                <td><fmt:formatDate value="${nextBoard.bdate}" pattern="MM.dd" /></td>
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
                        <c:when test="${not empty prevBoard}">
                            <tr>
                                <td>이전글</td>
                                <td><a href="/board/forum_view?bno=${prevBoard.bno}">${prevBoard.btitle}</a></td>
                                <td><fmt:formatDate value="${prevBoard.bdate}" pattern="MM.dd" /></td>
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
                <%-- 게시글 작성자이거나 관리자인 경우 수정/삭제 버튼 표시 --%>
                <c:if test="${sessionScope.loggedInMember.memberId == board.member.memberId || sessionScope.loggedInMember.name == '관리자'}">
                    <button type="button" class="edit-button" onclick="location.href='/board/forum_edit?bno=${board.bno}'">수정</button>
                    <button type="button" class="delete-button" onclick="deletePost(${board.bno})">삭제</button>
                </c:if>
            </div>
            <button type="button" class="list-button" onclick="location.href='/board/forum_list'">목록</button>
        </div>
    </div>
    
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    $(document).ready(function() {
        // 게시글 좋아요 버튼 클릭 이벤트 처리
        $('#like-button').on('click', function(e) {
            e.preventDefault();

            let bno = ${board.bno};

            $.ajax({
                url: '/api/board/toggleLike',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({
                    bno: bno
                }),
                success: function(response) {
                    if (response.success) {
                        let likeIcon = $('#like-icon i');
                        let likeCount = $('#like-count');

                        if (response.isLiked) {
                            // 좋아요를 누른 상태 (빈 하트 -> 채워진 하트)
                            likeIcon.removeClass('fa-regular').addClass('fa-solid liked');
                        } else {
                            // 좋아요를 취소한 상태 (채워진 하트 -> 빈 하트)
                            likeIcon.removeClass('fa-solid liked').addClass('fa-regular');
                        }
                        // 좋아요 수 업데이트
                        likeCount.text(response.likeCount);
                    } else {
                        alert(response.message);
                        // 로그인 페이지로 리다이렉션
                        if (response.message === "로그인이 필요합니다.") {
                            window.location.href = '/member/login';
                        }
                    }
                },
                error: function(xhr, status, error) {
                    alert('좋아요 처리 중 오류가 발생했습니다.');
                    console.error("AJAX Error: ", status, error);
                }
            });
        });

        // 댓글 좋아요 버튼 클릭 이벤트 처리
        $(document).on('click', '.comment-like-button', function(e) {
            e.preventDefault();
            
            const scno = $(this).data('scno');
            const likeIcon = $(this).find('i');
            const likeCountSpan = $(this).siblings('.comment-like-count');
            
            $.ajax({
                url: '/api/comment/toggleLike',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({
                    scno: scno
                }),
                success: function(response) {
                    if (response.success) {
                        if (response.isLiked) {
                            // 좋아요를 누른 상태
                            likeIcon.removeClass('far').addClass('fas');
                        } else {
                            // 좋아요를 취소한 상태
                            likeIcon.removeClass('fas').addClass('far');
                        }
                        // 좋아요 수 업데이트
                        likeCountSpan.text(response.likeCount);
                    } else {
                        alert(response.message);
                        if (response.message === "로그인이 필요합니다.") {
                            window.location.href = '/member/login';
                        }
                    }
                },
                error: function(xhr, status, error) {
                    alert('댓글 좋아요 처리 중 오류가 발생했습니다.');
                    console.error("AJAX Error: ", status, error);
                }
            });
        });
    });
    
    // 게시글 삭제 스크립트
    function deletePost(bno) {
        if (confirm('정말로 삭제하시겠습니까?')) {
            let form = document.createElement('form');
            form.setAttribute('method', 'post');
            form.setAttribute('action', '/board/forum_delete');
            
            let hiddenField = document.createElement('input');
            hiddenField.setAttribute('type', 'hidden');
            hiddenField.setAttribute('name', 'bno');
            hiddenField.setAttribute('value', bno);
            
            form.appendChild(hiddenField);
            document.body.appendChild(form);
            form.submit();
        }
    }
    $(document).on('click', '.save-comment', function() {
        let scno = $(this).closest('.comment-item').data('scno');
        saveComment(scno);
    });

    $(document).on('click', '.cancel-edit', function() {
        let scno = $(this).closest('.comment-item').data('scno');
        let originalContent = $(this).closest('.comment-item').find('.original-content').val();
        cancelEdit(scno, originalContent);
    });
    
 // 댓글 수정 함수 (AJAX)
    function editComment(scno) {
        // 1. 댓글 아이템을 찾습니다.
        let commentItem = $('div.comment-item[data-scno="' + scno + '"]');
        let commentBody = commentItem.find('.comment-body');
        let currentContent = commentBody.text().trim();

        // 2. 현재 댓글 내용을 수정 가능한 textarea로 변경합니다.
        let editFormHtml = `
            <div class="edit-comment-form">
                <textarea class="edit-textarea form-control" style="width:1208px; margin-top:50px;">${currentContent}</textarea>
                <input type="hidden" class="original-content" value="${currentContent}">
                <div class="edit-actions" style="margin-top: 5px; padding-left:1065px;">
                    <button class="save-comment" onclick="saveComment(${scno})">저장</button>
                    <button class="cancel-edit" onclick="cancelEdit(${scno})">취소</button>
                </div>
            </div>
        `;
        commentBody.html(editFormHtml);
    }

    // 수정 내용 저장 함수
    function saveComment(scno) {
        let commentItem = $('div.comment-item[data-scno="' + scno + '"]');
        let newContent = commentItem.find('.edit-textarea').val();

        if (newContent.trim() === '') {
            alert('댓글 내용을 입력해주세요.');
            return;
        }

        $.ajax({
            url: '/board/updateComment',
            type: 'POST',
            data: {
                scno: scno,
                sccontent: newContent
            },
            success: function(response) {
                if (response.success) {
                    alert('댓글이 성공적으로 수정되었습니다.');
                    location.reload();
                } else {
                    alert(response.message);
                }
            },
            error: function() {
                alert('댓글 수정 중 오류가 발생했습니다.');
            }
        });
    }
    
    // 수정 취소 함수
    function cancelEdit(scno, originalContent) {
        let commentItem = $('div.comment-item[data-scno="' + scno + '"]');
        let commentBody = commentItem.find('.comment-body');
        commentBody.text(originalContent);
    }
    

    // 댓글 삭제 함수
    function deleteComment(scno) {
        if (confirm('정말로 댓글을 삭제하시겠습니까?')) {
            $.ajax({
                url: '/board/deleteComment',
                type: 'POST',
                data: {
                    scno: scno
                },
                success: function(response) {
                    if (response.success) {
                        alert('댓글이 삭제되었습니다.');
                        location.reload(); // 페이지 새로고침
                    } else {
                        alert(response.message);
                    }
                },
                error: function(xhr, status, error) {
                    alert('댓글 삭제 중 오류가 발생했습니다.');
                    console.error("AJAX Error: ", status, error);
                }
            });
        }
    }
</script>
<%@ include file="../layout/footer.jsp" %>