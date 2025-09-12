<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>장바구니</title>
    <link rel="stylesheet" href="../css/cart.css" />
    <link rel="stylesheet" href="../css/base.css" />
    <link rel="icon" href="../images/hispark_crop.png">
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
	<div id="header">
		
		<div id="snbBox">
			<a href="/shop"><h1><img src="../images/hispark_crop.png" alt="(로고)" /></h1></a>
			<div id="snb">
				<ul>
					<li><a href="#">로그인</a></li>
					<li><a href="#">회원가입</a></li>
					<li><a href="/">메인으로</a></li>
				</ul>

			</div>

            <!-- 우측 아이콘 -->
            <div class="hMenu hRight">
            <a href="/shop/cart" class="cartWrapper">
                <img src="../images/cart.png" style="width:29px;" />
                <span class="cartBadge">0</span> <!-- 나중에 JS로 동적 숫자 -->
            </a>
            <a href="/"><img src="../images/user.png" style="width:28px;" /></a>
        </div>
		</div>
	</div>
    


    <div class="mainBox">
        <div class="location">
            <ul>
                <li>홈</li>
                <li class="current">장바구니</li>
            </ul>
        </div>
        <div class="title">장바구니</div>
        <div class="orderStep">
            <ul>
                <li><span class="current">1. 장바구니</span></li>
                <li>2. 주문서작성</li>
                <li>3. 주문완료</li>
            </ul>
        </div>
        
        <div class="cart-content">
            <form action="/order/order_form" name="orderFrm" method="post">  <!--post로바꾸기-->

                <div class="pricebasketbox">
                    <div class="basketbox">
                        <div class="basket">
                            <!--상품당 table 하나-->
                            <!--품절아님(가격표시) / 품절(가격 대신 품절 표시)로 나눠서 -->
                        	<c:forEach var="item" items="${cart.items}">
                                <table class="product-container">
                                    <tr>
                                        <td rowspan="5" class="checkbox-cell">
                                            <input type="checkbox" name="cartItemIds" value="${item.cartitem_id}">
                                        </td>
                                        <td class="pd_img">
                                            <img src="../images/productimage/${item.product.productImg}" width="100px"/>
                                        </td>
                                        <td class="product">
                                            <div class="description">
                                                <strong>${item.product.productName}</strong>
                                                <br>
                                                <fmt:formatNumber value="${item.product.productPrice}" pattern="#,###" /> 원
                                                <input type="hidden" class="unitPrice" value="${item.product.productPrice}">
                                                <br><br>
                                                <span>배송: 3,000원</span>
                                            </div>
                                            <div class="delete-btn"> <img src="../images/shopping/closebtn.png" width="20px"/> </div>
                                        </td>
                                    </tr>
                                    <tr style="height:20px;"></tr>
                                    <tr>
                                        <td class="quantity-title">수량</td>
                                        <td class="countselect">
                                            <div class="quantity-control">
                                                <button type="button" class="quantity-btn">-</button>
                                                <input type="text" class="quantity-input" value="${item.quantity}" min="1"/>
                                                <button type="button" class="quantity-btn">+</button>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="titleTd">주문금액</td>
                                        <td class="productprice">
                                            <fmt:formatNumber value="${item.product.productPrice}" pattern="#,###" />원
                                        </td>
                                    </tr>
                                </table>
                            </c:forEach>

                            <!-- 전체 합계 (모든 상품 아래 하나만) -->
                            <table class="pricesum">
                                <tr>
                                    <td>
                                        [기본배송]<br>
                                        상품구매금액 <span>[여기 총 상품금액]</span>
                                         + 배송비 <span>[여기 배송비]</span>
                                        <br>
                                        합계 : <span>[여기 총 상품금액+배송비]</span>원
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <div class="action-buttons">
                            <button type="button" class="btn selectAll">전체선택</button>
                            <button type="button" class="btn deleteSelected">선택삭제</button>
                        </div>
                    </div>
                    
                    <div class="right-section">
                        <div class="pricecount">
                            <div class="productprice-sum">
                                <div>상품구매금액</div>
                                <div>0원</div>
                            </div>
                            <div class="productprice-delivery">
                                <div>배송비</div>
                                <div>0원</div>
                            </div>
                            <div class="productprice-total">
                                <div>합계</div>
                                <div>0원</div>
                            </div>
                        </div>
                        
                        <div class="order-buttons">
                            <button type="button" class="btn order-btn">선택 주문하기</button>
                            <button onclick="orderAllBtn()" class="btn order-btn orderAll">전체 주문하기</button>
                        </div>
                    </div>
                </div>
            </form>

            
        </div>
    </div>

    
 
    <div id="footerWrap">
		<div id="footer">
			
			<div id="finfo">
				<div id="flogo"><img src="../images/hispark.png" alt="하이스파크 로고" /></div>
				<address>
					<ul>
						<li>㈜스파크</li>
						<li>대표자 김슬비</li>
						<li class="tnone">주소 서울시 용산구 독서당로 111(한남더힐)</li>
						<li class="webnone">소비자상담실 02)123-4567</li>
						<li>사업자등록번호 012-345-6789</li>
						<li class="tnone">통신판매신고 제 강남 – 1160호</li>
						<li class="copy">COPYRIGHT © 2025 HI-SPARK <span>ALL RIGHTS RESERVED.</span></li>
					</ul>
				</address>

			</div>
            <div class="social-links">
                <a href="https://www.youtube.com/user/smtown/" class="youtube-button" target="_blank" rel="noopener noreferrer"></a>
                <a href="https://www.instagram.com/hispark__official/" class="instagram-button" target="_blank" rel="noopener noreferrer"></a>
                <a href="https://twitter.com/hispark5/" class="x-button" target="_blank" rel="noopener noreferrer"></a>
                <a href="https://www.facebook.com/smtown/" class="facebook-button" target="_blank" rel="noopener noreferrer"></a>
            </div>
		</div>
	</div>

</body>
<script type="text/javascript" src="../js/cart.js"></script>
</html>