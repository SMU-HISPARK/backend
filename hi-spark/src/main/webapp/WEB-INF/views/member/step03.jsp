<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원 가입 완료</title>
    <style>
        /* 기본 스타일 및 폰트 설정 */
        body {
            
            margin: 0;
            padding: 0;
            background-color: #fafafa;
            color: #1a1a1a;
            line-height: 1.5;
        }
        /* --- 폰트 및 색상 규칙 --- */
        .text-dark-black { color: #1a1a1a; }
        .text-mid-black { color: #636363; }
        .text-dark-gray { color: #A0A0A0; }
        .text-light-gray { color: #CCC; }
        .text-white { color: #fafafa; }
        .font-normal { font-weight: 400; }
        .font-bold { font-weight: 700; }
        .mb-7 { margin-bottom: 7px; }
        .container {
            width: 100%;
            max-width: 1230px;
            margin: 0 auto; /* 가로 중앙 정렬 */
            box-sizing: border-box;
            text-align: center;
        }
        /* --- 페이지 레이아웃 --- */
          @font-face {
        font-family: 'Pretendard';
        src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Regular.woff') format('woff');
        font-weight: 400;
        font-display: swap;
    }
    @font-face {
        font-family: 'Pretendard';
        src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Bold.woff') format('woff');
        font-weight: 700;
        font-display: swap;
    }
    * { font-family: 'Pretendard'; }
    ul, ol { list-style: none; padding: 0; margin: 0; }

    header {
      width: 100%;
      height: 150px;
      background-color: #035fe0;
    }

    .container {
      width: 100%;
      max-width: 720px;
      margin: 0 auto;
      box-sizing: border-box;
    }

    .title {
        margin: 0 auto;
        text-align: center;
        padding: 70px 0;
        font-size: 30px;
        font-weight: bold;
    }


    .orderStep {
        text-align: center;
        padding-bottom: 20px;
        margin-bottom: 10px;
    }

    .orderStep ul {
        display: flex;
        justify-content: center;
        gap: 40px;
    }

    .orderStep li {
      display: inline-block;
      font-size: 14px;
    }

    /* 수정된 부분: 현재 단계 스타일 완전히 적용 */
    .orderStep li .current {
        color: #035fe0;
        font-weight: 700;
        font-size: 15px;
    }

        .message-box {
            max-width: 600px;
            margin: 0 auto;
            padding: 50px 20px;
            border-top: 1px solid #ccc;
            border-bottom: 1px solid #ccc;
            box-sizing: border-box;
            border-radius: 0;
        }
        .message-box p {
            font-size: 18px;
            font-weight: 500;
            color: #035fe0;
            margin-bottom: 30px;

        }
        /* --- 버튼 스타일 --- */
        .btn-box {
            display: flex;
            justify-content: center;
            gap: 15px;
        }
        .btn {
            width: 160px;
            height: 50px;
            font-size: 15px;
            font-weight: 500;
            border: 1px solid #035fe0;
            background-color: #035fe0;
            color: #fafafa;
            cursor: pointer;
            box-sizing: border-box;
            border-radius: 0;
        }
        @media (max-width: 768px) {
            .btn-box {
                flex-direction: column;
                gap: 10px;
            }
            .btn {
                width: 100%;
            }
        }
    </style>
</head>
<body>
    <div class="container">
            <div class="steps">
      <div class="title">회원가입</div>
      <div class="orderStep">
        <ul>
          <li>1. 약관동의</li>
          <li>2. 정보입력</li>
          <li><span class="current">3. 가입완료</span></li>
        </ul>
      </div>
    </div>
        <div class="message-box">
            <p>회원가입이 완료되었습니다.</p>
            <div class="btn-box">
                <button class="btn" onclick="location.href='vlast_shop_login.html'">로그인 페이지로 이동</button>
            </div>
        </div>
    </div>
</body>
</html>
<%@ include file="../layout/footer.jsp" %>
