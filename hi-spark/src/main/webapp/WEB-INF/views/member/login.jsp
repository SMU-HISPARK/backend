<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <script src="http://code.jquery.com/jquery-latest.min.js"></script>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>로그인</title>
  <style>
  	@font-face {
            font-family: 'Pretendard4';
            src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Regular.woff') format('woff');
            font-weight: 400;
            font-display: swap;
        }

       @font-face {
           font-family: 'Pretendard5';
           src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Medium.woff') format('woff');
           font-weight: 500;
           font-display: swap;
       }

       @font-face {
           font-family: 'Pretendard6';
           src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-SemiBold.woff') format('woff');
           font-weight: 600;
           font-display: swap;
       }

       @font-face {
           font-family: 'Pretendard7';
           src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Bold.woff') format('woff');
           font-weight: 700;
           font-display: swap;
       }
    body {
      font-family: 'Pretendard4', sans-serif;
      margin: 0;
      padding: 0;
      background: #fff;
      color: #1a1a1a;
    }
    .icons {
      display: flex;
      gap: 15px;
      font-size: 18px;
      cursor: pointer;
    }
    /* Container */
    .container {
      max-width: 1230px;
      margin: 0 auto;
      display: flex;
      justify-content: center;
    }
    .login-box {
      width: 320px;
      text-align: center;
    }
    .login-box h1{
    	padding : 70px 0;
    	font-size : 30px;
    }
    
    .login-box h2 {
      font-size: 20px;
      font-weight: 700;
      margin-bottom: 20px;
      color: #1a1a1a;
    }
    /* Input */
    .input-box {
      width: 100%;
      padding: 12px;
      margin: 5px 0;
      border: 1px solid #ccc;
      font-size: 14px;
      font-weight: 400;
      height: 40px;
      outline: none;
      border-radius: 0;
      box-sizing: border-box;
    }
    .input-box:focus {
      outline: 1.5px solid #1a1a1a;
    }
    /* Buttons */
    .btn {
      width: 100%;
      height: 42px;
      font-size: 15px;
      font-weight: 500;
      margin: 15px 0;
      cursor: pointer;
      border: none;
      border-radius: 0;
    }
    .btn-login {
      background-color: #035fe0;
      color: #fafafa;
    }
    .btn-signup {
      background-color: #fafafa;
      border: 1px solid #ccc;
      color: #1a1a1a;
      width: 140px;
      height: 45px;
      margin-top: 15px;
    }
    /* Links */
    .links {
      margin: 15px 0;
      font-size: 13px;
    }
    .links a {
      text-decoration: none;
      color: #636363;
      margin: 0 5px;
    }
    /* Signup box */
    .signup-box {
      border: 1px solid #ccc;
      padding: 20px;
      margin-top: 30px;
      text-align: center;
    }
    .signup-box p {
      font-size: 13px;
      font-weight: 400;
      color: #636363;
      margin: 7px 0;
    }
    .signup-box strong {
      color: #1a1a1a;
      font-weight: 700;
    }
  </style>
  <script>
  	if(${notFound} == "1")){
  		alert("아이디 또는 비밀번호가 다릅니다.");
  	}
  </script>
</head>
<body>
    <div class="icons">
      <i class="fas fa-search"></i>
      <i class="fas fa-user"></i>
      <i class="fas fa-shopping-bag"></i>
    </div>
  </div>
  <div class="container">
    <div class="login-box">
      <h1>로그인</h1>
      <form action="/member/login" method="post">
        <input type="text" name="username" placeholder="아이디" class="input-box" required>
        <input type="password" name="password" placeholder="비밀번호" class="input-box" required>
        <button type="submit" class="btn btn-login">로그인</button>
      </form>
      <div class="links">
        <a href="#">아이디 찾기</a> |
        <a href="#">비밀번호 찾기</a>
      </div>
      <div class="signup-box">
        <p><strong>아직 회원이 아니신가요?</strong></p>
        <p>지금 회원가입을 하시면<br>다양하고 특별한 혜택이 준비되어 있습니다.</p>
        <button class="btn-signup">회원가입</button>
      </div>
    </div>
  </div>
  <div class = "blank" style = "height : 100px;">
  	
  </div>
  
</body>
</html>
<%@ include file="../layout/footer.jsp" %>