<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Artist_chat</title>
    <script src="https://kit.fontawesome.com/e674411d10.js" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="../css/Artist_chat.css">
</head>
<body>
    <div class="top_div">
        <button onclick="self.close()" class="chatCloseBtn" >
            <i class="fa-solid fa-chevron-left" style="color: #035fe0;"></i>
        </button>

        <div class="profile_div">
            <img class="profile_img" src="../images/artistimage/지온_셀카.png" style="border-radius: 50%;">
            <p class="profile_name">박지온</p>
        </div>
    </div>

    <div class="middle_div">

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