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
    <div class="top_div">
        <button onclick="self.close()" class="chatCloseBtn" >
            <i class="fa-solid fa-chevron-left" style="color: #035fe0;"></i>
        </button>

		<c:if test="${artist.ano == 1}">
	        <div class="profile_div">
            	<img class="profile_img" src="../images/artistimage/승민_셀카.png" style="border-radius: 50%;">
            	<p class="profile_name">이승민</p>
        	</div>
		</c:if>
		<c:if test="${artist.ano == 2}">
	        <div class="profile_div">
            	<img class="profile_img" src="../images/artistimage/유현_셀카.png" style="border-radius: 50%;">
            	<p class="profile_name">공유현</p>
        	</div>
		</c:if>
		<c:if test="${artist.ano == 3}">
	        <div class="profile_div">
            	<img class="profile_img" src="../images/artistimage/경_셀카.png" style="border-radius: 50%;">
            	<p class="profile_name">윤경</p>
        	</div>
		</c:if>
		<c:if test="${artist.ano == 4}">
	        <div class="profile_div">
            	<img class="profile_img" src="../images/artistimage/지온_셀카.png" style="border-radius: 50%;">
            	<p class="profile_name">박지온</p>
        	</div>
		</c:if>
		<c:if test="${artist.ano == 5}">
	        <div class="profile_div">
            	<img class="profile_img" src="../images/artistimage/정훈_셀카.png" style="border-radius: 50%;">
            	<p class="profile_name">한정훈</p>
        	</div>
		</c:if>
    </div>

	<div class="middle_div">
		<c:forEach var="chat" items="${history}">
			<c:choose>
				<c:when test="${chat.send == 1}">
					<div class="chat ch2">
						<div class="textbox">${chat.message}</div>
						<p class="chat_time"><fmt:formatDate value="${chat.created_at}" pattern="HH:mm"/></p>
					</div>
				</c:when>

				<c:otherwise>
					<div class="chat ch1">
						<div class="icon">
							<img class="profile_img" src="../images/artistimage/${artist.ano}_셀카.png" style="border-radius:50%;">
						</div>
						<div class="chat_col">
							<p class="chat_name">${artist.name}</p>
							<div class="textbox">${chat.message}</div>
						</div>
						<p class="chat_time"><fmt:formatDate value="${chat.createdAt}" pattern="HH:mm"/></p>
					</div>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</div>


    <div class="bottom_div">
        <textarea class="chat_text" placeholder="내용을 입력해주세요."></textarea>
        <button class="sendChatBtn" >
            <i class="fa-regular fa-paper-plane" style="color: #ccc;"></i>
        </button>
    </div>

    <script src="../js/Artist_Chat.js"></script>

</body>
</html>