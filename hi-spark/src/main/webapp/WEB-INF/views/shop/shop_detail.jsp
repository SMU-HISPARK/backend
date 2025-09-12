<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>


<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Hi-Spark Shop</title>
		<link rel="stylesheet" type="text/css" href="/css/shop_detail.css">
        <link rel="stylesheet" type="text/css" href="/css/base.css">

        <link rel="icon" href="/images/hispark_crop.png">
	</head>
	<body>
		<div class="wrap">
			<!-- header -->
			<div id="header">
				<div id="snbBox">
					<a href="shop_main.html"><h1><img src="/images/hispark_crop.png" alt="(로고)" /></h1></a>
					<div id="snb">
						<ul>
							<li><a href="#">로그인</a></li>
							<li><a href="#">회원가입</a></li>
							<li><a href="/">메인으로</a></li>
						</ul>

					</div>

					<!-- 우측 아이콘 -->
					<div class="hMenu hRight">
					<a href="/cart" class="cartWrapper">
						<img src="/images/cart.png" style="width:29px;" /> <!--여기부터 다음줄까지-->
						<span class="cartBadge">3</span> <!-- 스프링에서 c:if 활용 커뮤니티에선 display:none -->
					</a>
					<a href="/"><img src="/images/user.png" style="width:28px;" /></a>
				</div>
				</div>
			</div>

            <!-- container -->
			<div class="container">
				<div class="product_detail">
                    <div class="product_img">
                        <img src="${product.productImg}"/>
                    </div>
                    <div class="product_data">
                        <div class="product_name">
                            <p>${product.productName}</p>
                        </div>
                        <div class="product_text">
                            <table>
                                <tr>
                                    <th class="product_price">판매가</th>
                                    <td class="product_price">${product.productprice}</td>
                                </tr>
                                <tr>
                                    <th>배송방법</th>
                                    <td>택배</td>
                                </tr>
                                <tr>
                                    <th>배송비</th>
                                    <td>${product.productName}</td>
                                </tr>
                           <!--     <tr class="product_option">
                                    <th>옵션</th>
                                    <td>
                                        <select>
                                            <option value="value" selected>- [필수] 옵션을 선택하세요 -</option>
                                            <option value="**" disabled="" link_image="">-------------------</option>
                                            <option value="공유현">공유현</option>
                                            <option value="한정훈">한정훈</option>
                                            <option value="윤경">윤 경</option>
                                            <option value="이승민">이승민</option>
                                            <option value="박지온">박지온</option>
                                        </select>
                                    </td>
                                </tr>-->
                                 <tr>
                                    <td class="quantity-title">수량</td>
                                    <td class="countselect">
                                        <div class="quantity-control">
                                            <button type="button" class="quantity-btn">-</button>
                                            <input type="text" class="quantity-input" value="1" min="1" />
                                            <button type="button" class="quantity-btn">+</button>
                                        </div>
                                    </td>
                                </tr>
                                <tr class="product_total">
                                    <th>TOTAL</th>
                                   <td>
                                   	<fmt:formatNumber value="${product.productprice}" pattern="#,###" />
                                   </td>
                                </tr>
                                
                            </table>

                        </div>
	                        <div class="product_button">
	                           <button type="button"  class="basket">장바구니</button>
	                            <button type="button" class="buy">바로구매</button>
	                        </div>
                    	</div>
				</div>
                <div class="Information">
                    <p>상세정보</p>
					<br>
                    ${product.productContent}
                </div>
				
			</div>
                    <script>
						function formatNumber(num) {
						    return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
						}
						
						$(document).ready(function () {
						    let unitPrice = parseInt("${product.productprice}", 10); // 숫자 그대로 받음
						
						    function updateTotal(quantity) {
						        let total = unitPrice * quantity;
						        $(".product_total td").text(formatNumber(total) + " 원");
						    }
						
						    // + 버튼
						    $(".quantity-btn:last").click(function () {
						        let input = $(".quantity-input");
						        let quantity = parseInt(input.val()) || 1;
						        quantity++;
						        input.val(quantity);
						        updateTotal(quantity);
						    });
						
						    // - 버튼
						    $(".quantity-btn:first").click(function () {
						        let input = $(".quantity-input");
						        let quantity = parseInt(input.val()) || 1;
						        if (quantity > 1) quantity--;
						        input.val(quantity);
						        updateTotal(quantity);
						    });
						
						    // 직접 입력
						    $(".quantity-input").on("input", function () {
						        let quantity = parseInt($(this).val()) || 1;
						        if (quantity < 1) quantity = 1;
						        $(this).val(quantity);
						        updateTotal(quantity);
						    });
						
						    // 초기 TOTAL
						    updateTotal(1);
						    
						    
						    $(".basket").click(function(){
						        let productId = "${product.productId}";
						        let quantity = $(".quantity-input").val();
						        window.location.href = "/cart/add?productId=" + productId + "&quantity=" + quantity;
						    });

                    	
						});
						
						</script>

    <div id="footerWrap">
		<div id="footer">
			
			<div id="finfo">
				<div id="flogo"><img src="/images/hispark.png" alt="하이스파크 로고" /></div>
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
</html>