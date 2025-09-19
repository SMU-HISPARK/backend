<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>VLAST SHOP 투표게시판 보기</title>
    <script type="text/javascript" src="/js/base.js"></script>
    <style>
        /* Pretendard 폰트 불러오기 */
        @font-face {font-family: 'Pretendard'; src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Thin.woff') format('woff'); font-weight: 100; font-display: swap; }
        @font-face {font-family: 'Pretendard'; src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-ExtraLight.woff') format('woff'); font-weight: 200; font-display: swap;}
        @font-face {font-family: 'Pretendard'; src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Light.woff') format('woff'); font-weight: 300; font-display: swap;}
        @font-face {font-family: 'Pretendard'; src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Regular.woff') format('woff'); font-weight: 400; font-display: swap;}
        @font-face {font-family: 'Pretendard'; src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Medium.woff') format('woff'); font-weight: 500; font-display: swap;}
        @font-face {font-family: 'Pretendard'; src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-SemiBold.woff') format('woff'); font-weight: 600; font-display: swap;}
        @font-face {font-family: 'Pretendard'; src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Bold.woff') format('woff'); font-weight: 700; font-display: swap;}
        @font-face {font-family: 'Pretendard'; src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-ExtraBold.woff') format('woff'); font-weight: 800; font-display: swap;}
        @font-face {font-family: 'Pretendard'; src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Black.woff') format('woff'); font-weight: 900; font-display: swap;}
        
        /* 기본 스타일 */
        body { font-family: 'Pretendard', sans-serif; margin: 0 auto; padding: 0; background-color: #ffffff; }
        .board-container { max-width: 1230px; margin: 40px auto; padding: 20px; background-color: #fff; }
        .board-title { text-align: center; font-size: 32px; color: #2c3e50; padding-bottom: 20px; border-bottom: 2px solid #2c3e50; margin-bottom: 20px;}
        
        /* 상단 경로 (Breadcrumb) 스타일 */
        .breadcrumb-container { max-width: 1230px; margin: 20px auto 0; padding: 0 20px; text-align: right; font-size: 14.4px; color: #7f8c8d; }
        .breadcrumb-container a { text-decoration: none; color: #7f8c8d; }
        .breadcrumb-container a:hover { text-decoration: underline; }
        .breadcrumb-container span.current { font-weight: bold; color: #333; }

        /* 게시글 보기 스타일 */
		.view-header { border-bottom: 2px solid #ddd; padding-bottom: 20px;padding-top: 10px; margin-bottom: 20px; }
		.view-header h3 { margin: 0 0 10px 0; font-size: 1.5em; color: #34495e; }
		.view-info { display: flex;align-items: center; margin-top:15px; font-size: 15px; color: #7f8c8d; 
		}
		.view-info span { padding-right:16px; }
		
		/* 작성자 이름 폰트 크기 조정 */
		.view-info .view-info-name {
		    font-size: 1.1em; /* '관리자' 글씨만 1.2배 키움 */
		    font-weight: bold;
		    color: #34495e;
		}
		.view-info-datehit {margin-left:-10px;}
        
        
        .view-content { padding: 20px 0; border-bottom: none; line-height: 1.8;}
        .view-content img { max-width: 100%; height: auto; display: block; margin: 20px auto;}
        /* 이전/다음글 스타일 */
        .view-navigation { margin-top: 20px; border-top: 1px solid #ddd;}
        .view-navigation table tr td { padding: 10px 0; border-bottom: 1px solid #ddd;}
        .view-navigation table tr td:first-child { text-align: center;}
        .view-navigation a { text-decoration: none; color: #333;}
        .view-navigation a:hover { text-decoration: underline;}

        /* 버튼 그룹 스타일 */
        .button-group { display: flex; justify-content: flex-end; gap: 10px; margin-top: 20px;}
        .button-group button { padding: 10px 20px; font-size: 1em; border: 1px solid #ddd; cursor: pointer;}
        .button-group .list-button { background-color: #2c3e50; color: #fff; border-color: #2c3e50;}
        .button-group .list-button:hover { background-color: #34495e; }
        .button-group .edit-button { background-color: #3498db; color: #fff; border-color: #3498db;}
        .button-group .delete-button { background-color: #e74c3c; color: #fff; border-color: #e74c3c;}
        .button-group .edit-button:hover { background-color: #2980b9;}
        .button-group .delete-button:hover { background-color: #c0392b;}
        .button-group .edit-delete-buttons { display: flex; gap: 10px; }
        
        /* 투표 항목 스타일 */
        .vote-options-container {
            width: 40%;
            margin: 20px auto 0;
            item-align: center;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 8px;
            background-color: #f9f9f9;
        }
        .vote-title-re {
            text-align: center;
            font-size: 1.1em;
            font-weight: 600;
            color: #34495e;
            margin-bottom: 15px;
            padding-bottom: 15px;
            border-bottom: 1px solid #ddd;
            display: flex;
		    flex-direction: column;
		    align-items: center;
        }
        
        /* 투표 종료일 스타일 */
        .vote-end-time {
            font-size: 0.9em;
            color: #7f8c8d;
            margin-top: 10px;
        }
        
        .vote-options {
            list-style: none;
            padding: 0;
            margin: 0;
        }
        .vote-item { 
            margin-bottom: 10px;
            padding-bottom: 10px;
            border-bottom: 1px solid #eee;
        }
        .vote-item:last-child {
            margin-bottom: 0;
            padding-bottom: 0;
            border-bottom: none;
        }
        .vote-button { 
            display: flex;
            flex-direction: column;
            width: 100%;
            padding: 10px;
            font-size: 1em;
            text-align: left;
            border: none;
            background-color: transparent;
            cursor: pointer;
            transition: background-color 0.2s, box-shadow 0.2s;
        }
        
        .vote-text-line {
            display: flex;
            justify-content: space-between;
            align-items: center;
            width: 100%;
            margin-bottom: 5px;
        }

        .vote-option-name {
            font-weight: 500;
        }

        .vote-count-info {
            font-size: 0.9em;
            color: #555;
        }

        .vote-count-info .percent-value {
            font-weight: bold;
            color: #e74c3c;
        }
        
        /* 투표율 진행바 스타일 */
        .vote-progress {
            background-color: #e0e0e0;
            border-radius: 4px;
            height: 10px;
        }
        .progress-bar {
            height: 100%;
            border-radius: 4px;
            background-color: #3498db;
            transition: width 0.5s ease-in-out;
        }
        
        /* 투표 상태 알림 */
        .vote-status-message {
        	width:300px;
            text-align: center;
            font-size: 1.1em;
            font-weight: bold;
            margin-top: 10px;
            padding: 10px;
            border-radius: 4px;
        }
        .vote-status-message.closed {
            color: #e74c3c;
            background-color: #fce7e5;
        }
        .vote-status-message.imminent {
            color: #e67e22;
            background-color: #fef1e5;
        }
    </style>
</head>
<body>
    <div style="width:100%; height:150px; margin-bottom:20px; position: relative; background-color:#035fe0;">
        <a style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); font-size:30px; color:white; font-weight:700;">header</a>
    </div>

    <div class="breadcrumb-container">
        <a href="#">홈</a> / <a href="#">게시판</a> / <span class="current"><a href="#">투표게시판</a></span>
    </div>

    <div class="board-container">
        <h2 class="board-title">투표게시판</h2>
        
        <div class="view-header">
            <h3>다음에 출시될 굿즈로 더 보고 싶은 것은?</h3>
            <div class="view-info">
                <span class="view-info-name">관리자</span>
                <span>작성일</span>
                <span class="view-info-datehit">2025.08.30</span>
                <span>조회수</span>
                <span class="view-info-datehit">4775</span>
                
            </div>
            
        </div>

        <div class="view-content">
            <p>
                안녕하세요! VLAST SHOP입니다.<br>
                다음 굿즈에 대한 투표를 진행합니다. 많은 참여 부탁드립니다!
            </p>
            <div class="vote-options-container">
            	
            	<div class="vote-title-re">
	    <h4>다음에 출시될 굿즈로 더 보고 싶은 것은?</h4>
	    <span id="voteEndTime" class="vote-end-time"></span>
	    <div class="vote-status-message" id="voteEndStatus"></div>
	</div>
            	
                <ul class="vote-options">
                    
                    <li class="vote-item">
                        <button class="vote-button" onclick="vote(1)">
                            <div class="vote-text-line">
                                <span class="vote-option-name">포토카드 세트</span>
                                <span class="vote-count-info"><span class="vote-count-value">45표</span> (<span class="percent-value">45%</span>)</span>
                            </div>
                            <div class="vote-progress"><div class="progress-bar" style="width:45%;"></div></div>
                        </button>
                    </li>
                    
                    <li class="vote-item">
                        <button class="vote-button" onclick="vote(2)">
                            <div class="vote-text-line">
                                <span class="vote-option-name">스티커 &amp; 마스킹테이프</span>
                                <span class="vote-count-info"><span class="vote-count-value">30표</span> (<span class="percent-value">30%</span>)</span>
                            </div>
                            <div class="vote-progress"><div class="progress-bar" style="width:30%;"></div></div>
                        </button>
                    </li>
                    
                    <li class="vote-item">
                        <button class="vote-button" onclick="vote(3)">
                            <div class="vote-text-line">
                                <span class="vote-option-name">아크릴 스탠드</span>
                                <span class="vote-count-info"><span class="vote-count-value">25표</span> (<span class="percent-value">25%</span>)</span>
                            </div>
                            <div class="vote-progress"><div class="progress-bar" style="width:25%;"></div></div>
                        </button>
                    </li>
                    
                </ul>
            </div>
        </div>
        
        <div class="view-navigation">
            <table style="width:100%; border-collapse: collapse;">
                <colgroup>
                    <col style="width: 80px;">
                    <col>
                    <col style="width: 80px;">
                </colgroup>
                <tbody>
                    <%--  <c:if test="${not empty previousArticle}"> --%>
                        <tr>
                            <td>이전글</td>
                            <td><a href="/article/notice/${previousArticle.id}">${previousArticle.title}</a></td>
                            <td>${previousArticle.date}</td>
                        </tr>
                    <%-- </c:if> --%>
                    <%-- <c:if test="${not empty nextArticle}"> --%>
                        <tr>
                            <td>다음글</td>
                            <td><a href="/article/notice/${nextArticle.id}">${nextArticle.title}</a></td>
                            <td>${nextArticle.date}</td>
                        </tr>
                    <%-- </c:if> --%>
                </tbody>
            </table>
        </div>

        <div class="button-group">
            <div class="edit-delete-buttons">
                <%--<c:if test="${isAuthor}">--%>
                    <button type="button" class="edit-button">수정</button>
                    <button type="button" class="delete-button">삭제</button>
                <%--</c:if>--%>
            </div>
            <button type="button" class="list-button">목록</button>
        </div>
    </div>
    
    <script>
        document.addEventListener('DOMContentLoaded', () => {
            const voteEndTimeElement = document.getElementById('voteEndTime');
            const voteEndStatusElement = document.getElementById('voteEndStatus');
            const voteEndTime = new Date('2025-09-05T20:00:00'); // 투표 마감 시간 설정

            const updateTime = () => {
                const now = new Date();
                const diffMs = voteEndTime.getTime() - now.getTime();
                
                // 투표 종료일 표시
                const formattedDate = voteEndTime.toLocaleString('ko-KR', {
                    year: 'numeric',
                    month: 'long',
                    day: 'numeric',
                    hour: '2-digit',
                    minute: '2-digit',
                    second: '2-digit'
                });
                voteEndTimeElement.textContent = `투표 종료일: ${formattedDate}`;

                if (diffMs > 0) {
                    const diffHours = Math.floor(diffMs / (1000 * 60 * 60));
                    const diffMinutes = Math.floor((diffMs % (1000 * 60 * 60)) / (1000 * 60));
                    const diffSeconds = Math.floor((diffMs % (1000 * 60)) / 1000);
                    
                    voteEndStatusElement.textContent = `투표 진행중 (${diffHours}시간 ${diffMinutes}분 ${diffSeconds}초 남음)`;
                    voteEndStatusElement.classList.remove('closed');
                    voteEndStatusElement.classList.add('imminent');
                    
                } else {
                    // 투표 종료
                    voteEndStatusElement.textContent = '투표가 종료되었습니다.';
                    voteEndStatusElement.classList.remove('imminent');
                    voteEndStatusElement.classList.add('closed');
                }
            };

            // 1초마다 시간 업데이트
            setInterval(updateTime, 1000);
            updateTime(); // 페이지 로드 시 즉시 업데이트
        });
    </script>
</body>
</html>