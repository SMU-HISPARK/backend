<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>장바구니</title>
    <link rel="stylesheet" href="css/cart.css" />
    <link rel="stylesheet" href="css/base.css" />
    <link rel="icon" href="images/hispark_crop.png">
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
	<div id="header">
		
		<div id="snbBox">
			<a href="/shop"><h1><img src="images/hispark_crop.png" alt="(로고)" /></h1></a>
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
                <img src="images/cart.png" style="width:29px;" />
                <span class="cartBadge">3</span> <!-- 나중에 JS로 동적 숫자 -->
            </a>
            <a href="/"><img src="images/user.png" style="width:28px;" /></a>
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
                            <table class="product-container">
                                <tr>
                                    <td rowspan="5" class="checkbox-cell">
                                        <input type="checkbox" value="1번 상품 id">
                                    </td>
                                    <td class="pd_img">
                                        <a>
                                            <img src="images/productimage/photocard.png" width="100px"/>
                                        </a>
                                    </td>
                                    <td class="product">
                                        <div class="description">
                                            <strong>HI-SRARK PHOTOCARD SET ver. 1 / 2</strong>
                                            <br>
                                            6,600원
                                            <br>
                                            <br>
                                            <span>배송: 3,000원 / 기본배송</span>
                                        </div>
                                        <div class="delete-btn">
                                            <img src="images/shopping/closebtn.png" width="20px"/>
                                        </div>
                                    </td>
                                </tr>
                                <tr style="height:20px;"></tr>
                                <tr>
                                    <td colspan="2" class="optionselect">
                                        ㄴ 옵션 선택사항: ver.1
                                    </td>
                                </tr>
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
                                <tr>
                                    <td class="titleTd">주문금액</td>
                                    <td class="productprice">6,600원</td>
                                </tr>
                            </table>

                            <!-- 두 번째 상품 -->
                            <table class="product-container">
                                <tr>
                                    <td rowspan="5" class="checkbox-cell">
                                        <input type="checkbox" value="2번 상품 id">
                                    </td>
                                    <td class="pd_img">
                                        <a>
                                            <img src="images/productimage/acrylic_keyring_YUHYUN.png" width="100px"/>
                                        </a>
                                    </td>
                                    <td class="product">
                                        <div class="description">
                                            <strong>YUHYUN ACRYLIC KEYRING</strong>
                                            <br>
                                            24,000원
                                            <br>
                                            <br>
                                            <span>배송: 3,000원 / 기본배송</span>
                                        </div>
                                        <div class="delete-btn">
                                            <img src="images/shopping/closebtn.png" width="20px"/>
                                        </div>
                                    </td>
                                </tr>
                                <tr style="height:20px;"></tr>
                                <tr>
                                </tr>
                                <tr>
                                    <td class="quantity-title">수량</td>
                                    <td class="countselect">
                                        <div class="quantity-control">
                                            <button type="button" class="quantity-btn">-</button>
                                            <input type="text" class="quantity-input" value="2" min="1" />
                                            <button type="button" class="quantity-btn">+</button>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="titleTd">주문금액</td>
                                    <td class="productprice">24,000원</td>
                                </tr>
                            </table>
                            
                            <!-- 세 번째 상품 -->
                            <table class="product-container">
                                <tr>
                                    <td rowspan="5" class="checkbox-cell">
                                        <input type="checkbox" value="3번 장바구니상품 ID">
                                    </td>
                                    <td class="pd_img">
                                        <a>
                                            <img src="images/productimage/acrylic_keyring_JEONGHUN.png" width="100px"/>
                                        </a>
                                    </td>
                                    <td class="product">
                                        <div class="description">
                                            <strong>JEONGHUN ACRYLIC KEYRING</strong>
                                            <br>
                                            12,000원
                                            <br>
                                            <br>
                                            <span>배송: 3,000원 / 기본배송</span>
                                        </div>
                                        <div class="delete-btn">
                                            <img src="images/shopping/closebtn.png" width="20px"/>
                                        </div>
                                    </td>
                                </tr>
                                <tr style="height:20px;"></tr>
                                <tr>
                                </tr>
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
                                <tr>
                                    <td class="titleTd">주문금액</td>
                                    <td class="productprice">12,000원</td>
                                </tr>
                            </table>
                            
                            <!-- 전체 합계 (모든 상품 아래 하나만) -->
                            <table class="pricesum">
                                <tr>
                                    <td>
                                        [기본배송]<br>
                                        상품구매금액 42,600 + 배송비 3,000<br>
                                        합계 : 45,600원
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
                                <div>42,600원</div>
                            </div>
                            <div class="productprice-sum">
                                <div>배송비</div>
                                <div>3,000원</div>
                            </div>
                            <div class="productprice-total">
                                <div>합계</div>
                                <div>45,600원</div>
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
				<div id="flogo"><img src="images/hispark.png" alt="하이스파크 로고" /></div>
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
<script type="text/javascript" src="js/cart.js"></script>
</html>