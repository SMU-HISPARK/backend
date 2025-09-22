<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://kit.fontawesome.com/e674411d10.js" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="../css/Artist_chat.css">
    <title>아티스트 챗봇</title>
</head>
<body>
	<script>
	// 서버에서 받아온 history를 JS에서 사용하기 위해 JSON으로 변환
	window.historyData = [
		<c:forEach var="chat" items="${history}" varStatus="status">
		{
			message: "${fn:escapeXml(chat.message)}",
			send: ${chat.send},
			createdAt: "${chat.createdAt}"
		}<c:if test="${!status.last}">,</c:if>
		</c:forEach>
	];
	</script>
    <div class="top_div">
        <button onclick="self.close()" class="chatCloseBtn" >
            <i class="fa-solid fa-chevron-left" style="color: #035fe0;"></i>
        </button>

		<c:if test="${artist.ano == 1}">
	        <div class="profile_div">
            	<img class="profile_img" src="../images/artistimage/이승민_셀카.png" style="border-radius: 50%;">
            	<p class="profile_name">이승민</p>
        	</div>
		</c:if>
		<c:if test="${artist.ano == 2}">
	        <div class="profile_div">
            	<img class="profile_img" src="../images/artistimage/공유현_셀카.png" style="border-radius: 50%;">
            	<p class="profile_name">공유현</p>
        	</div>
		</c:if>
		<c:if test="${artist.ano == 3}">
	        <div class="profile_div">
            	<img class="profile_img" src="../images/artistimage/윤경_셀카.png" style="border-radius: 50%;">
            	<p class="profile_name">윤경</p>
        	</div>
		</c:if>
		<c:if test="${artist.ano == 4}">
	        <div class="profile_div">
            	<img class="profile_img" src="../images/artistimage/박지온_셀카.png" style="border-radius: 50%;">
            	<p class="profile_name">박지온</p>
        	</div>
		</c:if>
		<c:if test="${artist.ano == 5}">
	        <div class="profile_div">
            	<img class="profile_img" src="../images/artistimage/한정훈_셀카.png" style="border-radius: 50%;">
            	<p class="profile_name">한정훈</p>
        	</div>
		</c:if>
    </div>

	<div class="middle_div">
	</div>


   <div class="bottom_div">
      <c:if test="${loginId  == null}">
         <p class="error">⚠️ 비회원 이용 시 채팅 기록은 저장되지 않습니다.</p>
		 <div class="chat_div_not">
		    <textarea class="chat_text" placeholder="내용을 입력해주세요."></textarea>
		    <button class="sendChatBtn" >
		       <i class="fa-regular fa-paper-plane" style="color: #ccc;"></i>
		    </button>
		 </div>
      </c:if>
      
	  <c:if test ="${loginId  != null}">
		<div class="chat_div">
		   <textarea class="chat_text" placeholder="내용을 입력해주세요."></textarea>
		   <button class="sendChatBtn" >
		      <i class="fa-regular fa-paper-plane" style="color: #ccc;"></i>
		   </button>
		</div>

	  </c:if>
    </div>

    <script src="../js/Artist_Chat.js"></script>

</body>
</html>