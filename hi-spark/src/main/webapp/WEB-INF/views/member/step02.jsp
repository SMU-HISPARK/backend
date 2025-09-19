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

    .required {
      font-size: 13px;
      text-align: right;
      margin-bottom: 8px;
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
      padding: 4px 0 14px;
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
    <p class="required"><span style="color:red">*</span> 는 필수 입력사항입니다.</p>
    <div class="form-box">
      <form id="join_form" action="/member/step03" method="post">
        <table>
        <!--
          <tr>
            <td></td>
            <td></td>
          </tr>
          -->
          <tr>
            <td>아이디 <span style="color:red">*</span></td>
            <td>
              <div class="id-check-box">
                <input type="text" name="loginId" maxlength="16" pattern="[a-z0-9_]{4,16}" placeholder="영문소문자/숫자, 4~16자, _ 허용" required>
                <button type="button" onclick="idCheck()">중복확인</button>
              </div>
              <p class="id-check-message"></p>
            </td>
          </tr>
          <tr>
            <td>비밀번호 <span style="color:red">*</span></td>
            <td>
              <input type="password" name="password" id="password" maxlength="20" placeholder="영문 대소문자/숫자/특수문자, 8~20자" required>
              <p class="pw-valid-check"></p>
            </td>
          </tr>
          <tr>
            <td>비밀번호 확인 <span style="color:red">*</span></td>
            <td>
              <input type="password" id="password-confirm" required>
              <p class="pw-confirm-check"></p>
            </td>
          </tr>
          <tr>
            <td>이름 <span style="color:red"></span></td>
            <td><input type="text" name="name" maxlength="50"></td>
          </tr>
          <tr>
            <td>닉네임 <span style="color:red">*</span></td>
            <td><input type="text" name="nickname" maxlength="30" required></td>
          </tr>
          <tr>
            <td>이메일 <span style="color:red">*</span></td>
            <td>
              <div class="email-input">
                <input type="email" name="email" maxlength="100" placeholder="example@domain.com" required>
                <button type="button" onclick="mailCheck()">중복확인</button>
                <!--<button>인증번호받기</button>-->
              </div>
              <p class="mail-check-message"></p>
            </td>
          </tr>
          <tr>
            <td>휴대전화 <span style="color:red"></span></td>
            <td>
              <div class="phone-input">
                <input type="text" id="p_head" class="phone_number" maxlength="3">
                <span>-</span>
                <input type="text" id="p_body" class="phone_number" maxlength="4">
                <span>-</span>
                <input type="text" id="p_tail" class="phone_number" maxlength="4">
                <input type="hidden" id="phone" name="phone" value="">
              </div>
            </td>
          </tr>
          <!--
          <tr>
            <td>인증번호 확인</td>
            <td>
              <div class="code-input">
                <input type="text" placeholder="인증번호 입력">
                <button>확인</button>
              </div>
            </td>
          </tr>
          -->
        </table>
      </form>
    </div>

    <div class="btn-box">
      <button class="btn cancel">이전</button>
      <button class="btn submit" onclick="submitBtn()">확인</button>
    </div>
  </div>
  <script>
    
    // 폼 입력 검사용
    let id_flag = 0;
    let email_flag = 0;
    let pw_flag = 0;
    let pw_confirm_flag = 0;

  	// 확인 버튼 클릭 시 폼 유효성 검사 후 제출
  	function submitBtn(){
  		phoneNumbering();
      const form = document.getElementById('join_form');
      const inputs = form.querySelectorAll("[required]");

      for(let input of inputs){
        if(!input.value.trim()){
          alert('필수 입력 사항을 모두 입력해 주세요.');
          input.focus();
          return;
        }
      }

      if(id_flag == 0){
        alert("유효한 아이디를 입력해 주세요.");
        document.querySelector('input[name="loginId"]').focus();
        return;
      }

      if(email_flag == 0){
        alert("유효한 메일 주소를 입력해 주세요.");
        document.querySelector('input[name="email"]').focus();
        return;
      }

      if(pw_flag == 0){
        alert("유효한 비밀번호를 입력해 주세요.");
        document.querySelector('input[name="password"]').focus();
        return;
      }

      if(pw_confirm_flag == 0){
        alert("비밀번호가 일치하지 않습니다.");
        document.getElementById('password-confirm').focus();
        return;
      }

  		form.submit();
  	}

    // ID 체크
    function idCheck(){
      if(document.querySelector('input[name="loginId"]').validity.patternMismatch){
        insertText('.id-check-message','영문 소문자 또는 숫자만 4~16자 입력 가능합니다.','red');
        id_flag = 0;
        return;
      }

      $.ajax({
        url: '/member/idCheck',
        method: 'POST',
        data: 'loginId=' + $('input[name="loginId"]').val(),
        success: function(boolean){
          if(boolean){
            insertText('.id-check-message','사용 가능한 아이디입니다.','#035fe0');
            id_flag = 1;
          }else{
            insertText('.id-check-message','이미 존재하는 아이디입니다.','red');
            id_flag = 0;
          }
        },
        error: function(){
          alert("연결 실패");
          id_flag = 0;
        }
      });

    }

    // 이메일 중복 검사
    function mailCheck(){
      if(document.querySelector('input[name="email"]').validity.typeMismatch){
        insertText('.mail-check-message','유효하지 않은 형식입니다.','red');
        email_flag = 0;
        return;
      }else{
        document.querySelector('.mail-check-message').innerText = '';
      }

      $.ajax({
        url: '/member/mailCheck',
        method: 'POST',
        data: 'email=' + $('input[name="email"]').val(),
        success: function(boolean){
          console.log(boolean);
          if(boolean){
            insertText('.mail-check-message','사용 가능한 메일입니다.','#035fe0');
            email_flag = 1;
          }else{
            insertText('.mail-check-message','이미 존재하는 메일입니다.','red');
            email_flag = 0;
          }
        },
        error: function(){
          alert("연결 실패");
          email_flag = 0;
        }
      });
    }

    // 중복검사 후 폼 입력 변경 시 제출 불가
    // 아이디
    $('input[name="loginId"]').on('keyup change', function(){
      if(id_flag === 1){
        id_flag = 0;
        $('.id-check-message').text('');
      }
    });
    // 이메일
    $('input[name="email"]').on('keyup change', function() {
      if(email_flag === 1){
        email_flag = 0;
        $('.mail-check-message').text('');
      }
    });

    // 비밀번호 검사
    $('#password').keyup(function(){
      const pw_regex = /^[A-Za-z0-9!@#$%^&*()_+\-=\[\]{};':"\\|,.<>/?`~]{8,20}$/;
      if(!pw_regex.test(document.getElementById('password').value)){
        insertText('.pw-valid-check','8~20자 입력 가능합니다.','red');
        pw_flag = 0;
      }else{
        document.querySelector('.pw-valid-check').innerText = '';
        pw_flag = 1;
      }
      if($('#password-confirm').val() != ''){
        if($('#password-confirm').val() == $('#password').val()){
          $('.pw-confirm-check').text('');
          pw_confirm_flag = 1;
        }else{
          insertText('.pw-confirm-check','비밀번호가 일치하지 않습니다.','red');
          pw_confirm_flag = 0;
        }
      }
    });

    // 비밀번호 확인 검사
    $('#password-confirm').keyup(function(){
      if($('#password-confirm').val() == $('#password').val()){
        $('.pw-confirm-check').text('');
        pw_confirm_flag = 1;
      }else{
        insertText('.pw-confirm-check','비밀번호가 일치하지 않습니다.','red');
        pw_confirm_flag = 0;
      }
    });

    // 이전버튼 클릭 시 뒤로 가기
    $('.btn.cancel').click(function() {
      history.back();
    });


    // 전화번호 합치는 함수
  	function phoneNumbering(){
  		let number = "" + $('#p_head').val() + $('#p_body').val() + $('#p_tail').val();
  		$('#phone').val(number);
  	}

    // 리팩토링 함수 (근데 굳이 할 필요 없는듯...?)
    // 인풋 밑에 문구 추가하는 함수
    function insertText(element, textin, colorin){
      document.querySelector(element).innerText = textin;
      if(!(colorin === undefined)){
        document.querySelector(element).style.color = colorin;
      }
    }

    /*
    // 중복 검사 후 입력 변경 감지
    function flagDown(text_element, flag){
      if(flag == 1){
        flag = 0;
        $(text_element).text('');
      } 
    }
    */
  </script>
</body>
</html>
<%@ include file="../layout/footer.jsp" %>
