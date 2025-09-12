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
    <link rel="stylesheet" type="text/css" href="../css/Artist_Detail.css">
    <script src="../js/Artist_Detail.js"></script>
    <title>Artist_detail</title>
</head>
<script>
    document.addEventListener("DOMContentLoaded", function(){
        const modal = document.getElementById("imgModal");
        const modalImg = document.getElementById("modalImg");
        const closeBtn = document.querySelector(".close");

        document.querySelectorAll(".inform td.photo img").forEach(img => {
            img.addEventListener("click", function() {
                modal.style.display = "block";
                modalImg.src = this.src;
            });
        });

        closeBtn.addEventListener("click", function() {
            modal.style.display = "none";
        });

        modal.addEventListener("click", function(e) {
            if (e.target === modal) {
                modal.style.display = "none";
            }
        });
    });
    document.addEventListener("DOMContentLoaded", function(){
        const modal = document.getElementById("imgModal");
        const modalImg = document.getElementById("modalImg");
        const closeBtn = document.querySelector(".close");

        document.querySelectorAll(".profile_image img").forEach(img => {
            img.addEventListener("click", function() {
                modal.style.display = "block";
                modalImg.src = this.src;
            });
        });

        closeBtn.addEventListener("click", function() {
            modal.style.display = "none";
        });

        modal.addEventListener("click", function(e) {
            if (e.target === modal) {
                modal.style.display = "none";
            }
        });
    });

</script>
<body>
     <div class="background" >
        
        <div class="name">
            <h3>TEAM MEMBER</h2>
                <h1>${artist.engname}</h1>
            </div>
        <div class="content">
            <div class="total">

                <table class="profile01">
                    <tr>
                        <!-- 사진 -->
                        <td rowspan="9" class="profile_image">
                        <img src="${artist.profitimage}">
                        </td>
                    </tr>
                    <tr class="profile_info">
                        <th>이름</th>
                        <td>${artist.name}</td>
                    </tr>
                    <tr class="profile_info">
                        <th>생년월일</th>
                        <td>${artist.birth}.</td>
                    </tr>
                    <tr class="profile_info">
                        <th>MBTI</th>
                        <td>${artist.mbti}</td>
                    </tr>
                    <tr class="profile_info">
                        <th>키</th>
                        <td>${artist.height}cm</td>
                    </tr>
                    <tr class="profile_info">
                        <th>몸무게</th>
                        <td>${artist.weight}kg</td>
                    </tr>
                    <tr class="profile_info">
                        <th>동아리</th>
                        <td>${artist.club}</td>
                    </tr>
                    <tr class="profile_info">
                        <th>동아리 가입한 이유</th>
                        <td>${artist.clubReason}</td>
                    </tr>
                    <tr class="profile_info">
                        <th>좋아하는 것</th>
                        <td>${artist.favorite}</td>
                    </tr>
                </table>
                <table class="inform">
                    <tr>
                        <th class="phone">전화번호</th>
                        <th class="photo"><p style="text-align: center; width: 96%;">활동사진</p></th>
                    </tr>
                    <tr>
                        <td class="phone">
                            <button class="chatBtn" onclick="showPopup()">
                                <p>${artist.phone}<br>
                                
                            </p>
                            </button>
                            <p class="ment">연락처를 클릭하면<br>
                             멤버와 대화를 할 수 있어요!</p>
                        </td>
                        <td class="photo">
                        <img src="${artist.clubimage_01}">
                        <img src="${artist.clubimage_02}">
                        <img src="${artist.clubimage_03}">
                        </td>
                    </tr>
                </table>
                
                <table class="inform">
                    <tr>
                        <th class="phone">친구들에게 한마디</th>
                        <th class="photo"><p style="text-align: center; width: 96%;">TEAM MEMBER</p></th>
                    </tr>
                    <tr>
                        <td class="phone">
                            <div class="phone_sticker">
                               <p>${artist.acomment}
                                
                            </p>
                            </div>
                        </td>
                        <td class="photo_member">
                        <c:forEach var="other" items="${list}">
                        	<c:if test="${artist.ano ne other.ano}">
                        		<a href="/artist/detail?ano=${other.ano}"><img src="${other.profitimage}"></a>
							</c:if>
                        </c:forEach>

                        </td>
                    </tr>
                </table>
            </div>
        </div>
</div>
    <!-- 모달 구조 -->
    <div id="imgModal" class="modal">
    <span class="close">&times;</span>
    <img class="modal-content" id="modalImg">
    </div>

</body>
</html>