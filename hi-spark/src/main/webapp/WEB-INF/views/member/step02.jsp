<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <script src="http://code.jquery.com/jquery-latest.min.js"></script>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>회원 가입</title>
  <style>
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

    body {
      margin: 0;
      padding: 0;
      background: #fff;
      color: #1a1a1a;
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
        border-bottom: 1px solid #1a1a1a;
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

    .form-box {
      margin: 0 auto;
      max-width: 720px;
      box-sizing: border-box;
      padding: 10px 30px;
    }

    .form-box table {
      width: 100%;
      border-collapse: collapse;
    }

    .form-box td {
      padding: 4px 0 12px;
      vertical-align: middle;
    }

    .form-box td:first-child {
      width: 140px;
      font-weight: 700;
      color: #1a1a1a;
    }

    input[type="text"], input[type="password"], input[type="email"], select {
      width: 100%;
      padding: 15px;
      font-size: 15px;
      height: 45px;
      border: 1px solid #ccc;
      box-sizing: border-box;
      background: white;
    }

    input:focus, select:focus {
      outline: 1.5px solid #1a1a1a;
    }

    /* 아이디, 이메일, 인증번호 입력 + 버튼 */
    .id-check-box,
    .email-input,
    .code-input {
      display: flex;
      align-items: center;
      gap: 12px;
    }

    .id-check-box input,
    .email-input input,
    .code-input input {
      flex: 1;
    }

    .id-check-box button,
    .email-input button,
    .code-input button {
      width: 110px;
      height: 45px;
      border: 1px solid #035fe0;
      background-color: #035fe0;
      color: #fff;
      cursor: pointer;
      font-size: 15px;
      font-weight: 500;
      flex-shrink: 0;
    }

    /* 휴대전화 입력칸 */
    .phone-input {
      display: flex;
      align-items: center;
      gap: 5px;
    }

    .phone-input select,
    .phone-input input {
      flex: 1;
      height: 45px;
      font-size: 15px;
      border: 1px solid #ccc;
      background: white;
      padding: 0 10px;
      box-sizing: border-box;
    }

    .phone-input span {
      width: 15px;
      text-align: center;
    }

    /* 하단 버튼 */
    .btn-box {
      display: flex;
      justify-content: center;
      gap: 15px;
      padding: 50px 0; /* 버튼 위아래 여백 동일하게 */
      margin: 0;
    }

    .btn-box .btn {
      width: 160px;
      height: 50px;
      font-size: 15px;
      font-weight: 400;
      border: 1px solid #ccc;
      background-color: white;
      color: #1a1a1a;
      cursor: pointer;
      box-sizing: border-box;
      margin: 0;
    }

    .btn-box .btn.submit {
      background-color: #035fe0;
      border: 1px solid #035fe0;
      color: #fff;
      font-weight: 500;
    }

    @media (max-width: 768px) {
      .btn-box {
        flex-direction: column;
        gap: 10px;
      }
      .btn-box .btn {
        width: 100%;
      }
    }
  </style>
  <script>
  	// 확인 버튼 클릭 시 제출 (폰 넘버링을 따로 두면 없어도 됨)
  	function submitBtn(){
  		phoneNumbering();
  		$("#join_form").submit();
  	}
  	
  	// 전화번호 합치는 함수
  	function phoneNumbering(){
  		let number = "" + $('#p_head').val() + $('#p_body').val() + $('#p_tail').val();
  		$('#phone').val(number);
  	}
  </script>
</head>
<body>
  <div class="container">
    <div class="steps">
      <div class="title">회원가입</div>
      <div class="orderStep">
        <ul>
          <li>1. 약관동의</li>
          <li><span class="current">2. 정보입력</span></li>
          <li>3. 가입완료</li>
        </ul>
      </div>
    </div>

    <div class="form-box">
      <form id="join_form" action="/member/step03" method="post">
        <table>
          <tr>
            <td>아이디 <span style="color:red"></span></td>
            <td>
              <div class="id-check-box">
                <input type="text" name="username" maxlength="16" pattern="[a-z0-9_]{4,16}" placeholder="영문소문자/숫자, 4~16자, _ 허용">
                <button type="button">중복확인</button>
              </div>
            </td>
          </tr>
          <tr>
            <td>비밀번호 <span style="color:red"></span></td>
            <td><input type="password" name="password" maxlength="20" 
            placeholder="영문 대소문자/숫자/특수문자 중 2가지 이상 조합, 8자~20자"></td>
          </tr>
          <tr>
            <td>비밀번호 확인 <span style="color:red"></span></td>
            <td><input type="password"></td>
          </tr>
          <tr>
            <td>이름</td>
            <td><input type="text" name="name"></td>
          </tr>
          <tr>
            <td>닉네임 <span style="color:red"></span></td>
            <td><input type="text" name="nickname" placeholder="사용할 닉네임 입력"></td>
          </tr>
          <tr>
            <td>휴대전화 <span style="color:red"></span></td>
            <td>
              <div class="phone-input">
                <select id="p_head">
                  <option>010</option>
                  <option>011</option>
                  <option>016</option>
                  <option>017</option>
                </select>
                <span>-</span>
                <input type="text" id="p_body" maxlength="4">
                <span>-</span>
                <input type="text" id="p_tail" maxlength="4">
                <input type="hidden" id="phone" name="phone" value="">
              </div>
            </td>
          </tr>
          <tr>
            <td>이메일 <span style="color:red"></span></td>
            <td>
              <div class="email-input">
                <input type="email" name="email" placeholder="example@domain.com">
                <button>인증번호받기</button>
              </div>
            </td>
          </tr>
          <tr>
            <td>인증번호 확인</td>
            <td>
              <div class="code-input">
                <input type="text" placeholder="인증번호 입력">
                <button>확인</button>
              </div>
            </td>
          </tr>
        </table>
      </form>
    </div>

    <div class="btn-box">
      <button class="btn cancel">취소</button>
      <button class="btn submit" onclick="submitBtn()">확인</button>
    </div>
  </div>
</body>
</html>
<%@ include file="../layout/footer.jsp" %>
