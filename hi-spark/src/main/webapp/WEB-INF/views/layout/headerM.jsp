<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MYPAGE</title>
    <script type="text/javascript" src="/js/base.js"></script>
    <style>
		@font-face {
			font-family: 'Pretendard';
			src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Medium.woff') format('woff');
			font-weight : 600;
			font-display: swap;
		}
        *{margin: 0; padding:0;
		}
        .wrap{
            padding-bottom : 15px;
            margin: 0 auto;
        }
        #header{
            width: 100vw;
            margin-left: calc(50% - 50vw);
            border-bottom : 0.5px solid rgb(232, 232, 232);
            height:150px;
            text-align: center;
            /*margin-bottom : 50px;*/
        }
        
		#header ul{list-style: none;}
		/* header */
		#header #snbBox{position:relative; width:100%; height:100%;}
		#header #snbBox h1{
            position: absolute; 
            left: 50%; 
            top: 60%;
            transform: translate(-50%, -50%);
            margin: 0;
            font-size: 40px;
			color : #035fe0;
            display: flex;
            align-items: center;
            gap: 10px;
        }
        #header #snbBox h1 img{
            width: 80px;
            height: 80px;
        }
		#header #snbBox #snb{position: absolute; top: 0; right: 0; margin:0 27px 0 0;}
		#header #snbBox #snb ul{width:100%; }
		#header #snbBox #snb ul li{float:left; height:25px; line-height:25px; padding:8px 2px 0 18px;}
		#header #snbBox #snb ul li a{text-decoration : none; color:#888; font-size:11px; font-weight:600;}

    </style>
</head>
<body>
    <div id="wrap">

	<div id="header">
		
		<div id="snbBox">
			<a href="/mypage/member"><h1><img src="/images/layout/logo_my.png" alt="(로고)" /></h1></a>
			<div id="snb">
				<ul>
					<c:if test="${not empty sessionScope.session_name}">
					    <li><a href="/mypage/member">${sessionScope.session_name}님</a></li>
					    <li><a href="/member/logout">로그아웃</a></li>
					</c:if>
					<c:if test="${empty sessionScope.session_name}">
					    <li><a href="/member/login?redirectTo=${pageContext.request.requestURI}">로그인</a></li>
					    <li><a href="/member/step01">회원가입</a></li>
					</c:if>
					<li><a href="/">메인으로</a></li>
				</ul>

			</div>
		</div>
	</div>
    
</body>
</html>