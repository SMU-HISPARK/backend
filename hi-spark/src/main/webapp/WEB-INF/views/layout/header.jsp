<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>LOGIN</title>
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
            width: 60px;
            height: 60px;
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
			<h1><img src="/images/hispark_crop.png" alt="(로고)" /></h1>
			<div id="snb">
				<ul>
					<li><a href="#">로그인</a></li>
					<li><a href="#">회원가입</a></li>
					<li><a href="/">메인으로</a></li>
				</ul>

			</div>
		</div>
	</div>
    
</body>
</html>