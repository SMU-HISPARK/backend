<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/headerM.jsp" %>
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/css/mypage/mypage.css">
    <title>회원정보</title>
    <style>
        .profile-details {
            display: flex;
            flex-direction: column;
            align-items: center; 
            gap: 10px;
            margin: 0 auto;
            border : 1px solid #ccc;
        }

        .form-group {
            margin : 30px 0 20px 0;
            padding : 10px;
            background : #f6faff;
        }

        .form-group p {
            font-size : 17px;
            color : #035fe0;
            margin-top: 0;
            font-weight : 500;
        }

        .point{text-align: left; padding-left : 15px;}

        .mon{ padding : 10px 0 10px 100px; text-align : right;}

        /* 버튼 */
        .btn {
            border: 1px solid #ccc;
            background : #1a1a1a;
            color : #FFF;
            padding: 10px 15px;
            margin : 30px auto;
            cursor: pointer;
            font-size: 14px;
            font-weight: 400;
            display: block;
        }

        .btn:hover {
            background: #333;
        }

        .charge {
            width: 550px;
            border-collapse: collapse;
            margin: 0 auto; 
        }

        .charge tr {
            border-bottom: 1px solid #eee;
        }

        .charge td {
            padding: 12px 8px;
        }
        
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <div class = "title">마이페이지</div>
        </div>
        <ul class="menu">
            <li>
            <a href = "/mypage/member">회원정보</a>
            </li>
            <li>
                <a href = "/mypage/community">게시글 관리</a>
            </li>
            <li>
                <a href = "/mypage/shop">주문내역</a>                
            </li>
            <li  class = "selected">
                <a href = "/mypage/point">적립금</a>                
            </li>
        </ul>
        
        <div class="content">
            <div class="profile-details">
                <div class="form-group">
                    <p>보유 적립금 : <span id="currentPoint">${currentPoint}</span> P</p>
                </div>
                <form id="chargeFrm" action="/mypage/point" method="POST" onsubmit="return chargePoint()">
                <table class = "charge">
                    <tr>
                        <td><input type="radio" name="point" value="1000"></td>
                        <td class = "point">1,000P</td>
                        <td class = "mon">1,000원</td>
                    </tr>
                    <tr>
                        <td><input type="radio" name="point" value="3000"></td>
                        <td class = "point">3,000P</td>
                        <td class = "mon">3,000원</td>
                    </tr>
                    <tr>
                        <td><input type="radio" name="point" value="5000"></td>
                        <td class = "point">5,000P</td>
                        <td class = "mon">5,000원</td>
                    </tr>
                    <tr>
                        <td><input type="radio" name="point" value="10000"></td>
                        <td class = "point">10,000P</td>
                        <td class = "mon">10,000원</td>
                    </tr>
                    <tr>
                        <td><input type="radio" name="point" value="15000"></td>
                        <td class = "point">15,000P</td>
                        <td class = "mon">15,000원</td>
                    </tr>
                    <tr>
                        <td><input type="radio" name="point" value="20000"></td>
                        <td class = "point">20,000P</td>
                        <td class = "mon">20,000원</td>
                    </tr>
                    <tr>
                        <td><input type="radio" name="point" value="30000"></td>
                        <td class = "point">30,000P</td>
                        <td class = "mon">30,000원</td>
                    </tr>
                    <tr>
                        <td><input type="radio" name="point" value="50000"></td>
                        <td class = "point">50,000P</td>
                        <td class = "mon">50,000원</td>
                    </tr>
                    <tr>
                        <td><input type="radio" name="point" value="100000"></td>
                        <td class = "point">100,000P</td>
                        <td class = "mon">100,000원</td>
                    </tr>
                </table>
                <button type = "submit" class = "btn"> 충전하기 </button>
                </form>
            </div>
        </div>
    </div>

	<script>
		document.addEventListener('DOMContentLoaded', function() {
		    const currentPoint = ${currentPoint != null ? currentPoint : 0};
		    document.getElementById('currentPoint').textContent = currentPoint.toLocaleString();
		});
	
		function chargePoint() {
		    const selectedPoint = document.querySelector('input[name="point"]:checked');
		    if (!selectedPoint) {
		        alert("충전할 적립금을 선택해주세요.");
		        return false;
		    }
		    
		    const chargeAmount = parseInt(selectedPoint.value);
		    if (!confirm(chargeAmount.toLocaleString() + 'P를 충전하시겠습니까?')) {
		        return false;
		    }
	
		    return true; // 폼 제출
		}
	
    </script>
</body>
</html>
<%@ include file="../layout/footer.jsp" %>
