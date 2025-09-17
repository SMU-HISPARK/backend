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
					<a href="/shop"><h1><img src="/images/hispark_crop.png" alt="(로고)" /></h1></a>
					<div id="snb">
						<ul>
							<c:if test="${empty sessionScope.memberId}">
								<li><a href="/member/login">로그인</a></li>
								<li><a href="/member/step01">회원가입</a></li>
							</c:if>
							<c:if test="${not empty sessionScope.memberId}">
								<li><a href="/member/logout">로그아웃</a></li>
							</c:if>
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
                                    <th id="product_text_01">판매가</th>
                                    <td id="product_text_01"><fmt:formatNumber value="${product.productPrice}" pattern="#,###" /></td>
                                </tr>
                                <tr>
                                    <th>배송방법</th>
                                    <td>택배</td>
                                </tr>
                                <tr>
                                    <th>배송비</th>
                                    <td><fmt:formatNumber value="${product.delfee}" pattern="#,###" /></td>
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
									            <button type="button" class="quantity-btn-minus">-</button>
									            <!-- 수량 입력 input (폼에서 전송됨) -->
									            <input type="text" id="quantityInput" name="quantity"
									                   class="quantity-input" value="1"
									                   min="1" max="${product.productQuantity}" />
									            <button type="button" class="quantity-btn-plus">+</button>
									        </div>
									    </td>
									</tr>
                                <tr class="product_total">
                                    <th>TOTAL</th>
                                   <td>
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
                                   </td>
                                </tr>
                                
                            </table>
                     

                        </div>
	                        <div class="product_button">
	                        <c:if test="${product.productQuantity == 0}">
	                           <button type="button" disabled style="background: #ccc; cursor:not-allowed;" class="basket">장바구니</button>
	                        </c:if>
	                        <c:if test="${product.productQuantity > 0}">
	                        
	                        
	                        
	                        <c:choose>

							   <c:when test="${empty sessionScope.memberId}">
							       <button type="submit" class="basket" onclick="checkLogin()">장바구니</button>
							   </c:when>
							   <c:otherwise>
								   	<form action="/shop/cart/add" method="post" id="cartFrm">
									   	<input type="hidden" id="hiddenQuantity" name="quantity"
											                   class="quantity-input" value="1"
											                   min="1" max="${product.productQuantity}" />
								       <input type="hidden" name="productId" value="${product.productId}">
								       <button type="submit" class="basket">장바구니</button>
							       </form>
							   </c:otherwise>   
	                        </c:choose>
							</c:if>
	                          <!--    <button type="button" class="buy">바로구매</button>-->
	                        </div>
                    	</div>
				</div>
				
				
				<script>
					function checkLogin(){
						if(confirm("로그인이 필요한 서비스입니다. 로그인 하시겠습니까? ")){
							window.location.href="/member/login";
						}
					}
				
				</script>
                <div class="Information">
                    <p>상세정보</p>
					<br>
                    ${product.productContent}
                </div>
				
			</div>
            <script>
						$(document).ready(function () {
							//sold out일때 js update total 실행안하기
							
							let stock = parseInt("${product.productQuantity}", 10);
						    let unitPrice = parseInt("${product.productPrice}", 10); // 숫자 그대로 받음
							
						    // 수량 동기화 함수 
						    function syncQuantity() {
						    	let quantity = $("#quantityInput").val();
						    	$("#hiddenQuantity").val(quantity);
						    }
						    
						    
						    function updateTotal(quantity) {
						        let total = unitPrice * quantity;
						        $(".product_total td").text(formatNumber(total) + " 원");
						    }
						
						    // + 버튼
						    $(".quantity-btn-plus").click(function () {
						        let input = $("#quantityInput");
						        let quantity = parseInt(input.val()) || 1;

						        if (stock === 0) {
						            alert("품절입니다.");
						            input.val(1);  // ✅ 품절이면 강제로 1 고정
						            syncQuantity();
						           return;
						        }
						        if (quantity < stock) {
						            input.val(quantity + 1);
						            updateTotal(quantity + 1);
						            syncQuantity();
						        } else {
						            alert("재고를 초과할 수 없습니다.");
						            input.val(stock); // 재고 이상 못 올라가게 고정
						            syncQuantity();
						        }
						    });
						
						    // - 버튼
						    $(".quantity-btn-minus").click(function () {
						        let input = $("#quantityInput");
						        let quantity = parseInt(input.val()) || 1;
						        
						        if(quantity > 1){
							        input.val(quantity - 1);
							        updateTotal(quantity -1);
							        syncQuantity();
						        }else{
						        	alert("최소 수량은 1개입니다.");
						            input.val(1); // 최소 1로 고정
						            syncQuantity();
						        }
						    });
						

						    // 직접 입력
						    $(".quantity-input").on("input", function () {
						        let quantity = parseInt($(this).val()) || 1;

						        if (stock === 0) {
						            alert("품절입니다");
						            $(this).val(1);
						            syncQuantity();
						            return;
						        }
						        if (quantity < 1) {
						            alert("최소 수량은 1개입니다.");
						            quantity = 1;
						        }
						        if (quantity > stock) {
						            alert("재고를 초과할 수 없습니다");
						            quantity = stock;
						        }

						        $(this).val(quantity);
						        updateTotal(quantity);
						        syncQuantity();
						    });

						    // 초기 TOTAL
						    if (stock > 0) {
						        updateTotal(1);
						    } else {
						        $(".product_total .product_price").html("<span class='product_price'>SOLD OUT</span>");
						    }
						    
						    // 초기수량 동기화
						    syncQuantity();
						
												    
						    // 장바구니로 전송 
						    function formatNumber(num) {
							    return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
							}
						    
						    
						    // 수량이 바뀔 때 hidden input 값도 업데이트
							$(".quantity-input").on("input", function () {
							    $("#hiddenQuantity").val($(this).val());
							});
							$(".quantity-btn-plus, .quantity-btn-minus, .quantity-input").on("input click", function () {
							    syncQuantity();
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