<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/Cheader.jsp" %>
    <title>HISPARK VOTE LIST</title>
    <link rel="stylesheet" href="/css/c_list.css">
    <link rel="stylesheet" href="/css/common.css">
    
    
</head>
<body>
<div class="breadcrumb-container">
        <a href="#">홈</a> / <a href="#">게시판</a> / <span class="current">투표게시판</span>
    </div>

    <div class="board-container">
    
    <h2 class="board-title">VOTE</h2>
        
        <table class="vote-table">
        <colgroup>
                <col style="width: 80px;">
                <col>
                <col style="width: 120px;">
                <col style="width: 120px;">
                <col style="width: 80px;">
                <col style="width: 130px;">
            </colgroup>
            <thead>
                <tr>
                    <th>번호</th>
                	<th>제목</th>
                    <th>시작일</th>
                    <th>종료일</th>
                    <th>투표수</th>
                    <th>상태</th>
    
            </tr>
            </thead>
            <tbody>
    <c:set var="pageSize" value="10" />
    <c:forEach var="item" items="${polls}" varStatus="status">
        <tr>
            <%-- 전체 게시물 수와 현재 페이지, 페이지 내 순서를 이용해 번호 계산 --%>
            <td>${totalElements - ((currentPage - 1) * pageSize) - status.index}</td>
            <td>
                <a href="/board/vote_view?pollNo=${item.poll.poll_no}" class="vote-title-link">
                    ${item.poll.poll_title}
                </a>
            </td>
            <td>
                <fmt:formatDate value="${item.poll.poll_start_date}" pattern="yyyy.MM.dd"/>
            </td>
            <td>
                <fmt:formatDate value="${item.poll.poll_end_date}" pattern="yyyy.MM.dd"/>
            </td>
            <td>${item.voteCount}</td>
            <td>
                <c:choose>
                    <c:when test="${item.poll.poll_end_date.time > System.currentTimeMillis()}">
                        <span class="vote-status active">진행 중</span>
                    </c:when>
                    <c:otherwise>
                        <span class="vote-status closed">투표 종료</span>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
    
    <c:if test="${empty polls}">
        <tr>
            <td colspan="6" style="text-align: center;padding: 40px;">
                등록된 투표가 없습니다.
            </td>
        </tr>
    </c:if>
</tbody>
        </table>

        <div class="search-container" style="justify-content: space-between;">
            <form action="/board/vote_list" method="get" style="display: flex; gap: 8px;">
                <input type="hidden" name="page" 
 value="${currentPage}">
                <select name="searchType">
                    <option value="poll_title" <c:if test="${param.searchType == 'poll_title'}">selected</c:if>>제목</option>
                    <option value="poll_content" <c:if test="${param.searchType == 'poll_content'}">selected</c:if>>내용</option>
                </select>
                <input 
 type="text" name="keyword" placeholder="검색어를 입력하세요" value="${param.keyword}">
                <button type="submit">찾기</button>
            </form>
            <c:if test="${sessionScope.loggedInMember != null && sessionScope.loggedInMember.name == '관리자'}">
            <a href="/board/vote_write" class="write-button">글쓰기</a>
            </c:if>
        </div>
        
      
   <div class="pg-container">
        <div class="paginator">
            <c:if test="${totalPages > 0}">
                <div class="pg-btns">
                    <c:if test="${currentPage > 1}">
                        <button onclick="location.href='/board/vote_list?page=1<c:if test="${not empty param.keyword}">&searchType=${param.searchType}&keyword=${param.keyword}</c:if>'">
      
                       <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
                                <path d="M21.9323 22.5362C22.6042 21.8162 22.6042 20.7122 21.8842 20.0162L13.5562 12.0002L23.1562 2.76019L21.4762 1.00819L10.0282 12.0002L21.4763 22.9922L21.9323 22.5122L21.9323 22.5362Z" fill="currentColor"/>
                        
         <path d="M12.4786 22.5362C13.1506 21.8162 13.1506 20.7122 12.4306 20.0162L4.10256 12.0002L13.7266 2.76019L12.0466 1.00819L0.598563 12.0002L12.0226 22.9922L12.4786 22.5122L12.4786 22.5362Z" fill="currentColor"/>
                            </svg>
                        </button>
                        <button 
 onclick="location.href='/board/vote_list?page=${currentPage - 1}<c:if test="${not empty param.keyword}">&searchType=${param.searchType}&keyword=${param.keyword}</c:if>'">
                            <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
                                <path d="M16.3325 1.47C17.0125 2.18 16.9925 3.3 16.2825 3.98L7.9425 12L17.5625 21.25L15.8825 23L4.4425 12L15.8825 1L16.3325 1.47Z" fill="currentColor"/>
              
               </svg>
                        </button>
                    </c:if>
                    <c:if test="${currentPage == 1}">
                   
         <button disabled style="opacity: 0.3; cursor: not-allowed;">
                            <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
                                <path d="M21.9323 22.5362C22.6042 21.8162 22.6042 20.7122 21.8842 20.0162L13.5562 12.0002L23.1562 2.76019L21.4762 1.00819L10.0282 12.0002L21.4763 22.9922L21.9323 22.5122L21.9323 22.5362Z" fill="currentColor"/>
        
                         <path d="M12.4786 22.5362C13.1506 21.8162 13.1506 20.7122 12.4306 20.0162L4.10256 12.0002L13.7266 2.76019L12.0466 1.00819L0.598563 12.0002L12.0226 22.9922L12.4786 22.5122L12.4786 22.5362Z" fill="currentColor"/>
                            </svg>
                        </button>
         
                <button disabled style="opacity: 0.3;
 cursor: not-allowed;">
                            <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
                                <path d="M16.3325 1.47C17.0125 2.18 16.9925 3.3 16.2825 3.98L7.9425 12L17.5625 21.25L15.8825 23L4.4425 12L15.8825 1L16.3325 1.47Z" fill="currentColor"/>
                  
           </svg>
                        </button>
                    </c:if>
                </div>
                
              
   <div class="pg-numbers">
                    <c:forEach var="pageNum" begin="${startPage}" end="${endPage}">
                        <c:choose>
                            <c:when test="${pageNum == currentPage}">
                   
              <a class="active">${pageNum}</a>
                            </c:when>
                            <c:otherwise>
                              
   <a href="/board/vote_list?page=${pageNum}<c:if test="${not empty param.keyword}">&searchType=${param.searchType}&keyword=${param.keyword}</c:if>">${pageNum}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
      
           
                <div class="pg-btns">
                    <c:if test="${currentPage < totalPages}">
                        <button onclick="location.href='/board/vote_list?page=${currentPage + 1}<c:if test="${not empty param.keyword}">&searchType=${param.searchType}&keyword=${param.keyword}</c:if>'">
                    
         <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
                                <path d="M7.6675 1.47C6.9875 2.18 7.0075 3.3 7.7175 3.98L16.0575 12L6.4375 21.25L8.1175 23L19.5575 12L8.1175 1L7.6675 1.47Z" fill="currentColor"/>
                            </svg>
           
              </button>
                        <button onclick="location.href='/board/vote_list?page=${totalPages}<c:if test="${not empty param.keyword}">&searchType=${param.searchType}&keyword=${param.keyword}</c:if>'">
                            <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
                        
         <path d="M2.06775 1.46381C1.39575 2.18381 1.39575 3.28781 2.11575 3.98381L10.4437 11.9998L0.84375 21.2398L2.52375 22.9918L13.9718 11.9998L2.52375 1.00781L2.06775 1.48781V1.46381Z" fill="currentColor"/>
                                <path d="M11.5214 1.46381C10.8494 2.18381 10.8494 3.28781 11.5694 3.98381L19.8974 11.9998L10.2734 21.2398L11.9534 22.9918L23.4014 11.9998L11.9774 1.00781L11.5214 1.48781V1.46381Z" fill="currentColor"/>
                            </svg>
    
                     </button>
                    </c:if>
                    <c:if test="${currentPage == totalPages}">
                        <button disabled style="opacity: 0.3;
 cursor: not-allowed;">
                            <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
                                <path d="M7.6675 1.47C6.9875 2.18 7.0075 3.3 7.7175 3.98L16.0575 12L6.4375 21.25L8.1175 23L19.5575 12L8.1175 1L7.6675 1.47Z" fill="currentColor"/>
                  
           </svg>
                        </button>
                        <button disabled style="opacity: 0.3;
 cursor: not-allowed;">
                            <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
                                <path d="M2.06775 1.46381C1.39575 2.18381 1.39575 3.28781 2.11575 3.98381L10.4437 11.9998L0.84375 21.2398L2.52375 22.9918L13.9718 11.9998L2.52375 1.00781L2.06775 1.48781V1.46381Z" fill="currentColor"/>
                  
               <path d="M11.5214 1.46381C10.8494 2.18381 10.8494 3.28781 11.5694 3.98381L19.8974 11.9998L10.2734 21.2398L11.9534 22.9918L23.4014 11.9998L11.9774 1.00781L11.5214 1.48781V1.46381Z" fill="currentColor"/>
                            </svg>
                        </button>
                    
 </c:if>
                </div>
            </c:if>
        </div>
    </div>
    </div>
    
<script>
    // 컨트롤러에서 전달받은 메시지가 있는지 확인
    var authError = '${authError}';
    if (authError) {
        alert(authError);
    }
</script>
<%@ include file="../layout/footer.jsp" %>