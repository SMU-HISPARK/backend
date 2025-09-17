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
    <title>주문완료</title>
    <link rel="stylesheet" href="/css/order.css" />
    <style>
        
    </style>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
    <div class="mainBox">
        <div class="title">
            
            <h1>SPARK SHOP</h1>
            <div class="hMenu hLeft">
                <a href="/shop/cart"><img src="/images/shopping/left.png" style="width:28px;" /></a>
            </div>
            <div class="hMenu hRight">
                <a href="/shop/cart" class="cartWrapper">
                    <img src="/images/cart.png" style="width:29px;" />
                    <span class="cartBadge" style="display:none;" data-count="${sessionScope.cart_count != null ? sessionScope.cart_count : 0}">
					    	${sessionScope.cart_count != null ? sessionScope.cart_count : 0}
					</span>
                </a>
                <a href="/mypage/shop"><img src="/images/user.png" style="width:28px;" /></a>
            </div>
        </div>
        <div class="ordertitle">
            주문 완료
        </div>

        <!-- 주문완료 안내 -->
        <div class="completeNoticeBox">
            <div class="completeTitle">주문이 완료되었습니다!</div>
            <p>주문해 주셔서 감사합니다.</p>
            
            <div class="orderInfo">
                <div>
                    <span>주문번호</span>
                    <span>${order.orderCode}</span>
                </div>
                <div>
                    <span>주문금액</span>
                    <span>₩<fmt:formatNumber value="${order.totalAmount}" pattern="#,###" /></span>
                </div>
            </div>
        </div>

        <div class="contentWrap">
            
            <!-- 결제수단 정보 -->
            <div class="paymentMethodBox">
                <details open>
                    <summary>결제수단</summary>
                    <div>
                        <table class="infoTable">
                            <tbody>
                                <tr>
                                    <td class="label">결제방법</td>
                                    <td class="value paymentMethod">${order.paymentMethod}</td>
                                </tr>
                                <tr>
                                    <td class="label">결제상태</td>
                                    <td class="value">결제완료</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </details>
            </div>

            <!-- 배송지 정보 -->
            <div class="completeInfoBox">
                <details open>
                    <summary>배송지</summary>
                    <div>
                        <table class="infoTable">
                            <tbody>
                                <tr>
                                    <td class="label">받는사람</td>
                                    <td class="value">${order.receiver}</td>
                                </tr>
                                <tr>
                                    <td class="label">주소</td>
                                    <td class="value">
                                        ${order.addressMain}<br>
                                        ${order.addressDetail}
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label">휴대전화</td>
                                    <td class="value">${order.phone}</td>
                                </tr>
                                <tr>
                                    <td class="label">배송메시지</td>
                                    <td class="value">${order.deliveryMessage}</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </details>
            </div>

            <!-- 주문상품 -->
            <div class="orderProduct">
                <details open>
                    <summary>주문상품 <span>(${selectedCount})</span></summary>
                    <!-- 상품 하나 -->
                    <c:forEach var="item" items="${order.orderitems}" >
	                    <div class="orderProduct_one">
	                        <img src="../${item.product.productImg}" alt="상품 사진" />
	                        <div class="productInfo">
	                            <p class="productName">${item.product.productName}</p>
	                            <p class="productQty">수량: ${item.quantity}</p>
	                            <p class="productPrice">₩<fmt:formatNumber value="${item.price}" pattern="#,###" /></p>
	                        </div>
	                    </div>
                    </c:forEach>
                </details>
            </div>

            <!-- 결제 정보 -->
            <div class="paymentBox">
                <details open>
                    <summary>결제정보</summary>
                    <div class="paymentContent">
                        <table>
                            <tbody>
                                <tr>
                                    <td class="label">주문상품</td>
                                    <td class="value">₩<fmt:formatNumber value="${order.totalAmount}" pattern="#,###" /></td>
                                </tr>
                                <tr>
                                    <td class="label">배송비</td>
                                    <td class="value">₩<fmt:formatNumber value="${order.deliverCost}" pattern="#,###" /></td>
                                </tr>
                                <tr>
                                    <td class="label total">최종 결제 금액</td>
                                    <td class="value total">₩<fmt:formatNumber value="${order.totalAmount+order.deliverCost}" pattern="#,###" /></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </details>
            </div>
        </div>

        <!-- 쇼핑몰 이동 버튼 -->
        <div class="buttonBox">
            <a href="/mypage/shop"><div class="orderDetailBtn">주문확인하기</div></a>
            <a href="/shop"><div class="MDShopBtn">쇼핑몰로 이동</div></a>
        </div>
    </div>

</body>
<script type="text/javascript" src="/js/shop.js"></script>
</html>