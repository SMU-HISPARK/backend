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
		<link rel="stylesheet" type="text/css" href="/css/shop_detail.css">
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
                                    <td id="product_text_01"><fmt:formatNumber value="${product.productPrice}" pattern="#,###" /> 원</td>
                                </tr>
                                <tr>
                                    <th>배송방법</th>
                                    <td>택배</td>
                                </tr>
                                <tr>
                                    <th>배송비</th>
                                    <td>
                                    <fmt:formatNumber value="${product.delfee}" pattern="#,###" /> 원
                                    </td>
                                </tr>
                                <tr>
                                    <th></th>
                                    <td>
                                    <div style="width: 200px;">(5만원이상 구매시 무료배송)</div>
                                    </td>
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
				
				<section class="product-recommendations">
				    <nav class="product-tabs">
				        <ul>
				            <li data-tab="content-together" class="is-active"><a><span class="tab_text">이 상품을 구매한 분들이 <span class="tap_product">함께 구매한 상품</span></span></a></li>
				            <li data-tab="content-frequent"><a><span class="tab_text">이 상품을 구매한 분들이 <span class="tap_product">많이 구매한 상품</span></a></li>
				        </ul>
				    </nav>
				
				    <div id="content-together" class="tab-content is-active">
				        <ul class="product-list">
				            <li class="product-item">
				                <a href="#"><img src="https://image.yes24.com/goods/152298374/L" alt="호의에 대하여"></a>
				                <div class="product-info">
				                    <p class="title"><a href="#">호의에 대하여</a></p>
				                    <p class="author">문형배 저</p>
				                    <p class="price">16,920원 <span class="discount">(10%)</span></p>
				                </div>
				            </li>
				            <li class="product-item">
				                <a href="#"><img src="https://image.yes24.com/goods/149739762/L" alt="렛뎀 이론"></a>
				                <div class="product-info">
				                    <p class="title"><a href="#">렛뎀 이론</a></p>
				                    <p class="author">멜 로빈스 저</p>
				                    <p class="price">18,000원 <span class="discount">(10%)</span></p>
				                </div>
				            </li>
				            <li class="product-item">
				                <a href="#"><img src="https://image.yes24.com/goods/90688995/L" alt="나는 왜 네 말이 힘들까"></a>
				                <div class="product-info">
				                    <p class="title"><a href="#">나는 왜 네 말이 힘들까</a></p>
				                    <p class="author">박재연 저</p>
				                    <p class="price">15,120원 <span class="discount">(10%)</span></p>
				                </div>
				            </li>
				            <li class="product-item">
				                <a href="#"><img src="https://image.yes24.com/goods/147178677/L" alt="단 한 줄만 내 마음에 새긴다고 해도"></a>
				                <div class="product-info">
				                    <p class="title"><a href="#">단 한 줄만 내 마음에 새긴다고 해도</a></p>
				                    <p class="author">나민애 저</p>
				                    <p class="price">21,420원 <span class="discount">(10%)</span></p>
				                </div>
				            </li>
				            <li class="product-item">
				                <a href="#"><img src="https://image.yes24.com/goods/136298166/L" alt="어른의 행복은 조용하다"></a>
				                <div class="product-info">
				                    <p class="title"><a href="#">어른의 행복은 조용하다</a></p>
				                    <p class="author">태수 저</p>
				                    <p class="price">16,020원 <span class="discount">(10%)</span></p>
				                </div>
				            </li>
				        </ul>
				    </div>
				
				    <div id="content-frequent" class="tab-content">
				        <ul class="product-list">
				            <li class="product-item">
				                <a href="#"><img src="https://image.yes24.com/goods/126590468/L" alt="초역 부처의 말"></a>
				                <div class="product-info">
				                    <p class="title"><a href="#">초역 부처의 말</a></p>
				                    <p class="author">코이케 류노스케 저</p>
				                    <p class="price">16,020원 <span class="discount">(10%)</span></p>
				                </div>
				            </li>
				            <li class="product-item">
				                <a href="#"><img src="https://image.yes24.com/goods/146344852/L" alt="우리의 낙원에서 만나자"></a>
				                <div class="product-info">
				                    <p class="title"><a href="#">우리의 낙원에서 만나자</a></p>
				                    <p class="author">하태완 저</p>
				                    <p class="price">17,550원 <span class="discount">(10%)</span></p>
				                </div>
				            </li>
				            <li class="product-item">
				                <a href="#"><img src="https://image.yes24.com/goods/144021119/L" alt="단 한 번의 삶"></a>
				                <div class="product-info">
				                    <p class="title"><a href="#">단 한 번의 삶</a></p>
				                    <p class="author">김영하 저</p>
				                    <p class="price">15,120원 <span class="discount">(10%)</span></p>
				                </div>
				            </li>
				            <li class="product-item">
				                <a href="#"><img src="https://image.yes24.com/goods/32526726/L" alt="슈독"></a>
				                <div class="product-info">
				                    <p class="title"><a href="#">슈독</a></p>
				                    <p class="author">필 나이트 저</p>
				                    <p class="price">19,800원 <span class="discount">(10%)</span></p>
				                </div>
				            </li>
				            <li class="product-item">
				                <a href="#"><img src="https://image.yes24.com/goods/8759796/L" alt="모순"></a>
				                <div class="product-info">
				                    <p class="title"><a href="#">모순</a></p>
				                    <p class="author">양귀자 저</p>
				                    <p class="price">11,700원 <span class="discount">(10%)</span></p>
				                </div>
				            </li>
				        </ul>
				    </div>
				</section>
				
				<br>
				<br>
				
                <div class="Information">
                    <p>상세정보</p>
					<br>
                    ${product.productContent}
                </div>
				
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
<script type="text/javascript" src="/js/shop.js"></script>
<%@ include file="../layout/footer.jsp" %>
