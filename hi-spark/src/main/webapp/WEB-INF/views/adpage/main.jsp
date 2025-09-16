<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../css/mypage/mypage.css">
    <title>관리자페이지</title>
    <style>

        .profile-details {
            display: flex;
            flex-direction: column;
            align-items: center; 
            gap: 10px;
            margin: 0 auto;
        }

        .form-group {
            display: flex;
            align-items: center;
            gap : 10px;
        }

        .form-group label {
            width: 110px;
            text-align: left;
        }

        .form-group input {
            padding: 10px;
            border: 1px solid #ddd;
            font-size: 15px;
        }

        .form-group input[type="text"].tel {
            width: 162px; 
        }

        .form-group input:not(.tel) {
            width: 540px; 
        }

        /* 수정 불가능한 필드 스타일 */
        .form-group input[readonly]:not(.editable) {
            background-color: #f0eeee;
        }
        

        /* 비밀번호 확인 필드 (초기 숨김) */
        .password-check {
            display: none;
        }

        /* 버튼 */
        .btn {
            border: 1px solid #ccc;
            padding: 10px 20px;
            margin : 20px 10px -10px 0;
            cursor: pointer;
            font-size: 15px;
            font-weight: 400;
        }

        .upBtn{
            background : #1a1a1a;
            color : #FAFAFA;
        }

        .pwBtn{
            background : #FFF;
            color : #1a1a1a;
        }

        .form-group div {
            display: flex;
            justify-content: flex-start;
            margin-top: 10px;
        }

    </style>
</head>
<body>
        <ul class="menu">
            <li  class = "selected">
            <a href = "/adpage/data">통계</a>
            </li>
            <li>
                <a href = "/adpage/notice">공지 관리</a>
            </li>
            <li>
                <a href = "/adpage/shop">샵 관리</a>
            </li>
        </ul>
        
        <div class="content">
            <div class="profile-details">
                <div class="form-group">
                    <label>아이디</label>
                    <input type="text" value="aaa" readonly>
                </div>
                <div class="form-group">
                    <label>이름</label>
                    <input type="text" value="홍길동" readonly>
                </div>
                <div class="form-group">
                    <label>닉네임</label>
                    <input type="text" class="editable" value="길동스" readonly>
                </div>
                <div class="form-group">
                    <label>전화번호</label>
                    <input type="text" class="tel editable" value="010" readonly>
                    <a>-</a>
                    <input type="text" class="tel editable" value="1234" readonly>
                    <a>-</a>
                    <input type="text" class="tel editable" value="5678" readonly>
                </div>
                <div class="form-group">
                    <label>이메일</label>
                    <input type="email" value="hong@example.com" readonly>
                </div>
                <div class="form-group">
                    <label>가입일</label>
                    <input type="text" value="2024-01-15" readonly>
                </div>
                <div class="form-group password-check" id="passwordCheck">
                    <label>비밀번호 확인</label>
                    <input type="password" id="passwordInput" placeholder="비밀번호를 입력하세요">
                </div>
                <div class="form-group">
                    <label></label>
                    <div>
                        <button class="btn upBtn" id="editBtn">정보 수정</button>
                        <button class="btn pwBtn" onclick="openPopup()">비밀번호 변경</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
            
    </div>

    <div class = "blank" style = "height : 100px; ">

    </div>
    
    <script>

    </script>
</body>
</html>
<%@ include file="../layout/footer.jsp" %>