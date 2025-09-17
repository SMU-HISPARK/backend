<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>HISPARK FORUM VIEW</title>
    <link rel="stylesheet" href="/css/c_view.css">
    <link rel="stylesheet" href="/css/common.css">
    <link rel="stylesheet" href="/css/FV.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
    
</head>
<body>
    <div style="width:100%; height:150px; margin-bottom:20px; position: relative; background-color:#035fe0;">
        <a style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); font-size:30px; color:white; font-weight:700;">header</a>
    </div>

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
				    <div class="comment-item">
				        <div class="comment-meta">
				            <span class="comment-author">${comment.member.name}</span>
				            <span class="comment-date"><fmt:formatDate value="${comment.scdate}" pattern="yyyy.MM.dd HH:mm"/></span>
				        </div>
				        <div class="comment-body">${comment.sccontent}</div>
				        <div class="comment-actions">
				            
				            <%-- 댓글 작성자이거나 관리자인 경우 수정/삭제 버튼 표시 --%>
				            <c:if test="${loggedInMemberId == comment.member.memberId || sessionScope.loggedInMember.name == '관리자'}">
				                <a href="/board/forum_comment_edit?scno=${comment.scno}" class="comment-action-icon">
				                    <i class="fas fa-edit"></i>
				                </a>
				                
				                <a href="javascript:void(0);" onclick="deleteComment(${comment.scno})" class="comment-action-icon">
				                    <i class="fas fa-trash-alt"></i>
				                </a>
				            </c:if>
				            <%-- 좋아요 버튼 --%>
				            <div class="comment-like-box">
							    <a href="javascript:void(0);" class="comment-like-button" data-scno="${comment.scno}">
							        <i class="fa-heart <c:if test="${commentLikedStatus[comment.scno]}">fas</c:if><c:if test="${!commentLikedStatus[comment.scno]}">far</c:if>"></i>
							    </a>
							    <span class="comment-like-count">${boardService.getCommentLikeCount(comment.scno)}</span>
							</div>
				        </div>
				    </div>
				</c:forEach>
            </div>
            
            <c:if test="${not empty loggedInMemberId}">
                <div class="comment-form">
                    
                        <input type="hidden" name="bno" value="${board.bno}">
                        <textarea class="form-control" name="sccontent" rows="3" placeholder="댓글을 입력하세요..." required></textarea>
                        <div class="btn-primary">
                            <button type="submit" >등록</button>
                        </div>
                    
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
                <c:if test="${loggedInMemberId == comment.member.memberId || sessionScope.loggedInMember.name == '관리자'}">
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
        // 좋아요 버튼 클릭 이벤트 처리
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
                        // 로그인 페이지로 리디렉션
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
    });
    
    // 기존 삭제 스크립트 (이 코드가 이미 있다면 추가하지 마세요)
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
</script>
</body>
</html>