<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/headerM.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/css/mypage/mypage.css">
    <title>가입 동아리</title>
    <style>
        .message{
            border : 1px border #e0e0e06e;
            background : #e0e0e06e;
            padding : 15px 10px;
            margin-bottom : 20px;
            font-size : 13px;
            font-weight : 400;
            color : #1a1a1ab6;
        }

        .detail-section {
		    margin-bottom: 30px;
		}
		
		.club-grid {
		    display: grid;
		    grid-template-columns: repeat(auto-fill, minmax(180px, 1fr));
		    gap: 15px 25px;
		}
		
		.club-card {
		    border: 1px solid #ddd;
		    padding: 15px;
		    text-align: center;
		    cursor: pointer;
		}
		
		.club-card.inactive {
		    border: 1px dashed #ccc;
		    color: #999;
		    background-color: #fafafa;
		    cursor: default;
		}
		
		.club-emoji img {
		    width: 50px;
		    height: 50px;
		    margin-bottom: 10px;
		}
		
		.club-name {
		    font-weight: 400;
		    margin-bottom: 5px;
		    font-size: 14px;
		}
		
		.join-date {
		    font-size: 12px;
		    color: #666;
		}
		
		/* 가입 상태별 텍스트 색상 */
		.club-card:not(.inactive) .join-date {
		    color: #035fe0;
		    font-weight: 500;
		}
		
		.club-card.inactive .join-date {
		    color: #999;
		}

        /* 버튼 */
        .btn {
            background : #FFF;
            border: 1px solid #ccc;
            padding: 10px 20px;
            cursor: pointer;
            font-size: 14px;
            font-weight: 400;
            transition: background 0.2s ease;
        }

        /* 하단 안내 문구 */
        .detail-section p {
            font-size: 12px;
            color: #666;
            margin-top: 15px;
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
            <li  class = "selected">
                <a href = "/mypage/club">가입 동아리</a>
            </li>
            <li>
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
            
            <div class = "message">
                <p>* 가입한 동아리를 확인 가능합니다.</p>
                <p style = "font-size : 12px;">  </p>
                <p>* 동아리 이름을 클릭하면 해당 동아리의 상세 에피소드를 확인할 수 있습니다.</p>
            </div>
            <div class="detail-section">
		    <div class="club-grid">
                <c:forEach var="club" items="${clubs}">
                    <div class="club-card ${club.finishedAt != null ? '' : 'inactive'}" onclick="viewClubEpisode('${club.name}')">
                        <div class="club-emoji">
                            <img src="${club.imageUrl}" alt="${club.name}">
                        </div>
                        <div class="club-name">${club.name}</div>
                        <div class="join-date">${club.finishedAt != null ? '가입' : '미가입'}</div>
                    </div>
                </c:forEach>
            </div>
            
            <div style="text-align: center; margin-top: 30px;">
                <button class="btn" onclick="startNewTest()">새 테스트 시작하기</button>
            </div>
        </div>
    </div>
    </div>

    <script>
	 // 동아리명과 club_id 매핑
	    const clubMap = {
	        "도서부": 1,
	        "밴드부": 2,
	        "선도부": 3,
	        "운동부": 4,
	        "제과제빵부": 5
	    };
	
	    function viewClubEpisode(clubName) {
	        const clubId = clubMap[clubName];
	        if (!clubId) {
	            alert("해당 동아리가 존재하지 않습니다.");
	            return;
	        }
	
	        // club_id 기반 상세 페이지 이동
	        window.location.href = `/game/result`;
	    }
	
	    function startNewTest() {
	        alert('새로운 동아리 매칭 테스트를 시작합니다.');
	        window.location.href = "/game";
	    }
    </script>
</body>
</html>
<%@ include file="../layout/footer.jsp" %>