<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="ko">
	<head>
		<meta charset="UTF-8">
		<title>Hi-Spark Shop</title>
		<link rel="stylesheet" type="text/css" href="/css/shop.css">
		<link rel="stylesheet" type="text/css" href="/css/base.css">
		<link rel="icon" href="/images/hispark_crop.png">
		<script src="http://code.jquery.com/jquery-latest.min.js"></script>
	</head>
	<body>
		<div class="wrap">
			<!-- header -->
			<div id="header">
				
				<div id="snbBox">
					<a href="/shop"><h1><img src="/images/hispark_crop.png" alt="(로고)" /></h1></a>
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

					<!-- 우측 아이콘 -->
					<div class="hMenu hRight">
					<a href="/shop/cart" class="cartWrapper">
						<img src="/images/cart.png" style="width:29px;" /> <!--여기부터 다음줄까지-->
						<span class="cartBadge" style="display:none;" data-count="${sessionScope.cart_count != null ? sessionScope.cart_count : 0}">
					    	${sessionScope.cart_count != null ? sessionScope.cart_count : 0}
					    </span>
					</a>
					<a href="/mypage/shop"><img src="/images/user.png" style="width:28px;" /></a>
				</div>
				</div>
			</div>

			<!-- container -->
			<div class="container">
				<div class="banner">
					<img src="/images/banner.png" style="width:100%;"/>
				</div>
				<div class="list_text">
					<p>All Products</p>
				</div>
				<div class="product_list">
						<c:forEach var="product" items="${list}">
					<div class="product">
							<div class="product_img">
									<a href="/shop/detail?productId=${product.productId}">
	
										<img src="${product.productImg}"/>
									</a>
								</div>
								<div class="product_name">${product.productName}</div>
								
								
								<!-- 재고가 0일때 -->
								<c:choose>
									<c:when test="${product.productQuantity==0}">
										<div class="sold out">
											<p class="product_price">sold out</p>
										</div>
									</c:when>
									<c:otherwise>
										<div class="product_price">
											<fmt:formatNumber value="${product.productPrice}" pattern="#,###" />
										</div>
									</c:otherwise>									
								</c:choose>
							
							</div>
						</c:forEach>
					
					
			</div>
		</div>
	</div>

<script type="text/javascript" src="/js/shop.js"></script>
<%@ include file="../layout/footer.jsp" %>