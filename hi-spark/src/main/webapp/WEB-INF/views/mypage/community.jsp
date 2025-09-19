<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/headerM.jsp" %>
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/css/mypage/mypage.css">
    <title>커뮤니티</title>
    <style>

        /* 기본 탭 버튼 스타일 */
        .tab-buttons .btn {
            border: none;
            font-size: 16px;
            color: #666;
            background: white;
            padding: 10px;
            cursor: pointer;
            font-weight: 500;
            margin-bottom : 10px;
        }

        /* 활성화된 탭 버튼 스타일 */
        .tab-buttons .btn.active {
            color: #000;
        }

        /* 기타 버튼들을 위한 기본 btn 클래스 */
        .btn:not(.tab-buttons .btn) {
            font-size: 13px;
            color: white;
            background: #1a1a1a;
            padding: 7px;
            cursor: pointer;
            font-weight: 500;
        }

        .btn {
            border : none;
            font-size : 16px;
            color : #666;
            background : white;
            padding: 10px;
            cursor: pointer;
            font-weight: 500;
            border: 1px solid #ccc;
        }


        /* 게시글/댓글 카드 */
        .post-list {
            display: grid;
            gap: 15px;
        }

        .post-item {
            border: 1px solid #ddd;
            padding: 20px;
            background-color: #fff;
        }


        .post-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 10px;
        }

        .post-title {
            font-weight: 500;
            font-size: 15px;
            color: #222;
        }

        .post-date {
            font-size: 12px;
            color: #666;
        }

        .post-content {
            line-height: 1.5;
            margin-bottom: 15px;
            font-size : 14px;
            color: #333;
        }

        .post-actions {
            display: flex;
        }


    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <div class = "title">마이페이지</div>
        </div>
        <ul class="menu">
            <li>
            <a href = "/mypage/member">회원정보</a>
            </li>
            <li>
                <a href = "/mypage/club">가입 동아리</a>
            </li>
            <li  class = "selected">
                <a href = "/mypage/community">게시글 관리</a>
            </li>
            <li>
                <a href = "/mypage/shop">주문내역</a>                
            </li>
            <li>
                <a href = "/mypage/point">적립금</a>                
            </li>
        </ul>
        
        <div class="content">
            <div class="tab-buttons">
                <button class="btn active" id="postBtn" onclick="showCommunityTab('posts')">게시글</button>
                <button class="btn" id="commentBtn" onclick="showCommunityTab('comments')">댓글</button>
            </div>
            
            <!-- 게시글 탭 -->
            <div id="posts-tab" class="community-tab">
		    <div class="post-list">
		        <c:forEach var="post" items="${postsList}">
		            <div class="post-item">
		                <div class="post-header">
		                    <div class="post-title">${post.btitle}</div>
		                    <div class="post-date">
						    <c:choose>
						        <c:when test="${not empty post.upBdate}">
						            작성일: <fmt:formatDate value="${post.bdate}" pattern="yyyy-MM-dd"/> / 
						            수정일: <fmt:formatDate value="${post.upBdate}" pattern="yyyy-MM-dd"/>
						        </c:when>
						        <c:otherwise>
						            작성일:<fmt:formatDate value="${post.bdate}" pattern="yyyy-MM-dd"/>
						        </c:otherwise>
						    </c:choose>
						</div>
		                </div>
		                <div class="post-content">
		                    ${post.bcontent}
		                </div>
		                <div class="post-actions">
		                    <button class="btn btn-small" onclick="viewPost(${post.bno})">게시글로 이동</button>
		                </div>
		            </div>
		        </c:forEach>
		    </div>
		</div>
            
            <!-- 댓글 탭 -->
			<div id="comments-tab" class="community-tab" style="display: none;">
		    <div class="post-list">
		        <c:forEach var="comment" items="${commentsList}">
		            <div class="post-item">
		                <div class="post-header">
		                    <div class="post-title">${comment.board.btitle}</div>
		                    <div class="post-date">
							    <c:choose>
							        <c:when test="${not empty comment.upScdate}">
							            작성일: <fmt:formatDate value="${comment.scdate}" pattern="yyyy-MM-dd"/> / 수정일: 
							            <fmt:formatDate value="${comment.upScdate}" pattern="yyyy-MM-dd"/>
							        </c:when>
							        <c:otherwise>
							            작성일: <fmt:formatDate value="${comment.scdate}" pattern="yyyy-MM-dd"/>
							        </c:otherwise>
							    </c:choose>
							</div>
		                </div>
		                <div class="post-content">
		                    ${comment.sccontent}
		                </div>
		                <div class="post-actions">
		                    <button class="btn btn-small" onclick="viewOriginalPost(${comment.board.bno})">원글 보기</button>
		                </div>
		            </div>
		        </c:forEach>
		    </div>
		</div>            

            <!-- pagination -->
            <div class="pg-container">
		      <div class="paginator">
		          <div class="pg-btns">
		              <button>
		                  <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
		                      <path d="M21.9323 22.5362C22.6042 21.8162 22.6042 20.7122 21.8842 20.0162L13.5562 12.0002L23.1562 2.76019L21.4762 1.00819L10.0282 12.0002L21.4763 22.9922L21.9323 22.5122L21.9323 22.5362Z" fill="currentColor"/>
		                      <path d="M12.4786 22.5362C13.1506 21.8162 13.1506 20.7122 12.4306 20.0162L4.10256 12.0002L13.7266 2.76019L12.0466 1.00819L0.598563 12.0002L12.0226 22.9922L12.4786 22.5122L12.4786 22.5362Z" fill="currentColor"/>
		                  </svg>
		              </button>
		              <button>
		                  <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
		                      <path d="M16.3325 1.47C17.0125 2.18 16.9925 3.3 16.2825 3.98L7.9425 12L17.5625 21.25L15.8825 23L4.4425 12L15.8825 1L16.3325 1.47Z" fill="currentColor"/>
		                  </svg>
		              </button>
		          </div>
		
		          <div class="pg-numbers">
		              <a class="active">1</a>
		              <a href="#">2</a>
		              <a href="#">3</a>
		              <a href="#">4</a>
		              <a href="#">5</a>
		          </div>
		
		          <div class="pg-btns">
		              <button>
		                  <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
		                      <path d="M7.6675 1.47C6.9875 2.18 7.0075 3.3 7.7175 3.98L16.0575 12L6.4375 21.25L8.1175 23L19.5575 12L8.1175 1L7.6675 1.47Z" fill="currentColor"/>
		                  </svg>
		              </button>
		              <button>
		                  <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
		                      <path d="M2.06775 1.46381C1.39575 2.18381 1.39575 3.28781 2.11575 3.98381L10.4437 11.9998L0.84375 21.2398L2.52375 22.9918L13.9718 11.9998L2.52375 1.00781L2.06775 1.48781V1.46381Z" fill="currentColor"/>
		                      <path d="M11.5214 1.46381C10.8494 2.18381 10.8494 3.28781 11.5694 3.98381L19.8974 11.9998L10.2734 21.2398L11.9534 22.9918L23.4014 11.9998L11.9774 1.00781L11.5214 1.48781V1.46381Z" fill="currentColor"/>
		                  </svg>
		              </button>
		          </div>
		      </div>
		  </div>
		            
        </div>
    </div>
</div>
    
    <script>
        // 커뮤니티 탭 전환 함수
        function showCommunityTab(tabName) {
            // 모든 탭 숨기기
            document.getElementById('posts-tab').style.display = 'none';
            document.getElementById('comments-tab').style.display = 'none';
            
            // 모든 탭 버튼 비활성화
            document.getElementById('postBtn').classList.remove('active');
            document.getElementById('commentBtn').classList.remove('active');
            
            // 선택된 탭 보이기
            if (tabName === 'posts') {
                document.getElementById('posts-tab').style.display = 'block';
                document.getElementById('postBtn').classList.add('active');
            } else if (tabName === 'comments') {
                document.getElementById('comments-tab').style.display = 'block';
                document.getElementById('commentBtn').classList.add('active');
            }
        }
        
        
        function viewPost(bno) {
            alert(`게시글 ${bno}을 조회합니다.`);
            window.location.href = `/board/forum_view?bno=${post.bno}&bType=1`;
        }
        
        function viewOriginalPost(bno) {
        	alert(`게시글 ${bno}을 조회합니다.`);
        	window.location.href = `/board/forum_view?bno=${post.bno}&bType=1`;

        }
        
        // 페이지 로드 시 게시글 탭 활성화
        document.addEventListener('DOMContentLoaded', function() {
            showCommunityTab('posts');
        });
    </script>
</body>
</html>
<%@ include file="../layout/footer.jsp" %>