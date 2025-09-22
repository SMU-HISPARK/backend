<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="../layout/Cheader.jsp" %>

    <style>
        /* 기본 스타일 */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body{
            width:100%;
        }

        a{
            color:inherit;
            text-decoration:none;
        }


        .location{
            padding: 20px 0 0 0;
            font-size:14px;
            margin-right:30px;
            margin-bottom:30px;

        }

        .location .current{
            color:#1a1a1a;
        }


        .location ul{
            display: flex;
            justify-content: right;
            margin-right:10px;
        }

        .location li{
            display: inline-block;
            text-decoration: none;
            color: #a0a0a0;
        }


        .location li:not(:first-child):before {
            content: "/";
            display: inline-block;
            margin: 0 4px 0 2px;
            vertical-align: top;
            color:#a0a0a0;
        }





        /* 메인 컨테이너 */
        .main-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 20px 40px;
            display: flex;
            gap: 30px;
        }

        /* 왼쪽 영역 (Notice + Vote) */
        .left-section {
            flex: 1;
            display: flex;
            flex-direction: column;
            gap: 30px;
        }

        /* 오른쪽 영역 (Forum) */
        .right-section {
            flex: 1;
        }

        /* 게시판 공통 스타일 */
        .board-container {
            background-color: white;
            border: 1px solid #ddd;
            padding: 30px;
        }

        .board-title {
            font-size: 24px;
            font-weight: bold;
            margin-bottom: 20px;
            color: #1a1a1a;
            border-bottom: 2px solid #035fe0;
            padding-bottom: 10px;
        }

        /* 게시글 목록 (Table) 스타일 */
        .post-table {
            width: 100%;
            border-collapse: collapse;
        }

        .post-table th, .post-table td {
            padding: 15px 0;
            border-bottom: 1px solid #eee;
            text-align: center;
            line-height: 22px;
        }

        .post-table th {
            height: 29.5px;
        }

        .post-table td {
            color: #636363;
            height: 50px;
        }

        .post-table thead {
            background-color: #f1f3f5;
            font-weight: 700;
            font-size: 18px;
        }

        .post-table tbody {
            font-size: 16px;
        }

        .post-table td:nth-child(2) {
            text-align: left;
            padding-left: 15px;
            font-weight: 400;
        }

        .post-table .post-title a {
            text-decoration: none;
            color: #1a1a1a;
        }

        /* 댓글 수 스타일 */
        .c-count {
            font-size: 13px;
            color: #035fe0;
            vertical-align: middle;
            margin-left: 5px;
        }

        /* 더보기 버튼 */
        .more-button {
            display: block;
            width: 100%;
            margin-top: 20px;
            padding: 12px;
            background-color: #f8f9fa;
            border: 1px solid #ddd;
            color: #666;
            text-align: center;
            text-decoration: none;
            font-size: 14px;
            transition: background-color 0.2s;
        }

        .more-button:hover {
            background-color: #e9ecef;
        }

        /* 반응형 */
        @media (max-width: 768px) {
            .main-container {
                flex-direction: column;
                gap: 20px;
            }
            
            .left-section {
                gap: 20px;
            }
            
            .board-container {
                padding: 20px;
            }
            
            .board-title {
                font-size: 20px;
            }
        }

        .titlebox{
            max-width:1230px;
            margin:0 auto;
        }
        .communitytitle{
            margin-top:60px;
            margin-bottom:30px;
            font-size:32px;
            text-align: center;
            font-size: 32px;
            color: #2c3e50;
            padding-bottom: 20px;
        }

    </style>
</head>
<body>
    
    <div class="titlebox">
        <div class="location">
            <ul>
                <li><a href="/">홈</a></li>
                <li class="current"><a href="#">게시판</a></li>
            </ul>
        </div>
        <h2 class="communitytitle">COMMMUNITY</div>

    </div>
    <div class="main-container">
        <div class="left-section">
            <!-- Notice 게시판 -->
            <div class="board-container">
                <h2 class="board-title"><a href="/board/notice_list">NOTICE</a></h2>
                <table class="post-table">
                    <colgroup>
                        <col style="width: 60px;">
                        <col>
                        <col style="width: 80px;">
                        <col style="width: 60px;">
                    </colgroup>
                    <thead>
                        <tr>
                            <th>번호</th>
                            <th>제목</th>
                            <th>작성자</th>
                            <th>조회</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>5</td>
                            <td>
                                <span class="post-title"><a href="#">2024년 하반기 이벤트 안내</a></span>
                            </td>
                            <td>관리자</td>
                            <td>1,245</td>
                        </tr>
                        <tr>
                            <td>4</td>
                            <td>
                                <span class="post-title"><a href="#">사이트 점검 안내 (9월 20일)</a></span>
                            </td>
                            <td>관리자</td>
                            <td>892</td>
                        </tr>
                        <tr>
                            <td>3</td>
                            <td>
                                <span class="post-title"><a href="#">개인정보처리방침 개정 안내</a></span>
                            </td>
                            <td>관리자</td>
                            <td>567</td>
                        </tr>
                        <tr>
                            <td>2</td>
                            <td>
                                <span class="post-title"><a href="#">신규 서비스 오픈 예정</a></span>
                            </td>
                            <td>관리자</td>
                            <td>1,023</td>
                        </tr>
                    </tbody>
                </table>
                <a href="/board/notice_list" class="more-button">더보기</a>
            </div>

            <!-- Vote 게시판 -->
            <div class="board-container">
                <h2 class="board-title"><a href="/board/vote_list">VOTE<a></a></h2>
                <table class="post-table">
                    <colgroup>
                        <col style="width: 60px;">
                        <col>
                        <col style="width: 80px;">
                        <col style="width: 60px;">
                    </colgroup>
                    <thead>
                        <tr>
                            <th>번호</th>
                            <th>제목</th>
                            <th>작성자</th>
                            <th>참여</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>12</td>
                            <td>
                                <span class="post-title"><a href="#">가장 좋아하는 신곡은? <span class="c-count">[투표중]</span></a></span>
                            </td>
                            <td>음악팬</td>
                            <td>156</td>
                        </tr>
                        <tr>
                            <td>11</td>
                            <td>
                                <span class="post-title"><a href="#">다음 콘서트 개최지 투표 <span class="c-count">[마감]</span></a></span>
                            </td>
                            <td>이벤트팀</td>
                            <td>342</td>
                        </tr>
                        <tr>
                            <td>10</td>
                            <td>
                                <span class="post-title"><a href="#">올해의 베스트 앨범 투표</a></span>
                            </td>
                            <td>뮤직러버</td>
                            <td>89</td>
                        </tr>
                        <tr>
                            <td>9</td>
                            <td>
                                <span class="post-title"><a href="#">팬미팅 시간대 선호도 조사</a></span>
                            </td>
                            <td>팬클럽</td>
                            <td>203</td>
                        </tr>
                    </tbody>
                </table>
                <a href="/board/vote_list" class="more-button">더보기</a>
            </div>
        </div>

        <!-- 오른쪽 영역: Forum -->
        <div class="right-section">
            <div class="board-container">
                <h2 class="board-title"><a href="/board/forum_list">FORUM</a></h2>
                <table class="post-table">
                    <colgroup>
                        <col style="width: 80px;">
                        <col>
                        <col style="width: 90px;">
                        <col style="width: 74px;">
                        <col style="width: 74px;">
                    </colgroup>
                    <thead>
                        <tr>
                            <th>번호</th>
                            <th>제목</th>
                            <th>작성자</th>
                            <th>조회</th>
                            <th>좋아요</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>25</td>
                            <td>
                                <span class="post-title"><a href="forum_view.html">이번 앨범 진짜 최고네요 ㅠㅠ <span class="c-count">[15]</span></a></span>
                            </td>
                            <td>플둥이</td>
                            <td>158</td>
                            <td>32</td>
                        </tr>
                        <tr>
                            <td>24</td>
                            <td>
                                <span class="post-title"><a href="#">혹시 포토카드 교환하실 분 계신가요? <span class="c-count">[5]</span></a></span>
                            </td>
                            <td>버니사랑</td>
                            <td>212</td>
                            <td>55</td>
                        </tr>
                        <tr>
                            <td>23</td>
                            <td>
                                <span class="post-title"><a href="#">콘서트 후기 공유합니다! <span class="c-count">[23]</span></a></span>
                            </td>
                            <td>콘서트매니아</td>
                            <td>387</td>
                            <td>89</td>
                        </tr>
                        <tr>
                            <td>22</td>
                            <td>
                                <span class="post-title"><a href="#">신곡 뮤비 해석 같이 해봐요 <span class="c-count">[12]</span></a></span>
                            </td>
                            <td>뮤비분석가</td>
                            <td>156</td>
                            <td>28</td>
                        </tr>
                        <tr>
                            <td>21</td>
                            <td>
                                <span class="post-title"><a href="#">팬미팅 같이 가실 분 구해요 <span class="c-count">[8]</span></a></span>
                            </td>
                            <td>솔로팬</td>
                            <td>134</td>
                            <td>15</td>
                        </tr>
                        <tr>
                            <td>20</td>
                            <td>
                                <span class="post-title"><a href="#">다음 앨범 컨셉 예상해봐요 <span class="c-count">[31]</span></a></span>
                            </td>
                            <td>컨셉장인</td>
                            <td>298</td>
                            <td>67</td>
                        </tr>
                        <tr>
                            <td>19</td>
                            <td>
                                <span class="post-title"><a href="#">굿즈 후기 올려요~ <span class="c-count">[4]</span></a></span>
                            </td>
                            <td>굿즈수집가</td>
                            <td>189</td>
                            <td>42</td>
                        </tr>
                        <tr>
                            <td>18</td>
                            <td>
                                <span class="post-title"><a href="#">라이브 영상 모음집 만들었어요 <span class="c-count">[19]</span></a></span>
                            </td>
                            <td>편집러</td>
                            <td>423</td>
                            <td>91</td>
                        </tr>
                    </tbody>
                </table>
                <a href="/board/forum_list" class="more-button">더보기</a>
            </div>
        </div>
    </div>
<%@ include file="../layout/footer.jsp" %>