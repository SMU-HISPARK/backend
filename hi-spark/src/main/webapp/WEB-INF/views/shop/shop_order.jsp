<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>주문/결제</title>
    <link rel="stylesheet" href="../css/order.css" />
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>

</head>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript">
	function daumZipCode() {
		 new daum.Postcode({
	         oncomplete: function(data) {
	             // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
	
	             // 각 주소의 노출 규칙에 따라 주소를 조합한다.
	             // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
	             var addr = ''; // 주소 변수
	             var extraAddr = ''; // 참고항목 변수
	
	             //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
	             if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
	                 addr = data.roadAddress;
	             } else { // 사용자가 지번 주소를 선택했을 경우(J)
	                 addr = data.jibunAddress;
	             }
	
	             // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
	             if(data.userSelectedType === 'R'){
	                 // 법정동명이 있을 경우 추가한다. (법정리는 제외)
	                 // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
	                 if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
	                     extraAddr += data.bname;
	                 }
	                 // 건물명이 있고, 공동주택일 경우 추가한다.
	                 if(data.buildingName !== '' && data.apartment === 'Y'){
	                     extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
	                 }
	                 // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
	                 if(extraAddr !== ''){
	                     extraAddr = ' (' + extraAddr + ')';
	                 }
	                 // 조합된 참고항목을 해당 필드에 넣는다.
	                 document.getElementById("address1").value = extraAddr;
	             
	             } else {
	                 document.getElementById("address2").value = '';
	             }
	
	             // 우편번호와 주소 정보를 해당 필드에 넣는다.
	             document.getElementById('zipcode').value = data.zonecode;
	             document.getElementById("address1").value = addr;
	             // 커서를 상세주소 필드로 이동한다.
	             document.getElementById("address2").focus();
	         }
	     }).open();
	 }
</script>
<body>
    <div class="mainBox">
        <div class="title">
            
            <h1>SPARK SHOP</h1>
            <div class="hMenu hLeft">
                <a href="javascript:void(0);" id="backBtn"><img src="../images/shopping/left.png" style="width:28px;" /></a>
            </div>
            <div class="hMenu hRight">
                <a href="/shop/cart" class="cartWrapper">
                    <img src="../images/cart.png" style="width:29px;" />
                    <span class="cartBadge">3</span>
                </a>
                <a href="/mypage/shop"><img src="../images/user.png" style="width:28px;" /></a>
            </div>
        </div>

        <div class="ordertitle">
            주문 / 결제
        </div>

        <div class="contentWrap">
            <form action="/order/order_finish" class="purchaseFrm" method="post">  <!--post로바꾸기-->
                <div class="addressBox">
                    <details open>
                        <summary>배송지</summary>
                        <div>
        
                            <table>
                                <colgroup>
                                    <col width="102px">
                                    <col width="*">
                                </colgroup>
                                <tbody>
                                    <tr>
                                        <td class="label">받는사람 <span class="required">*</span></td>
                                        <td class="inputBox"><input type="text" id="receiver" name="acceptant"/></td>
                                    </tr>
                                    <tr>
                                        <td class="label">주소 <span class="required">*</span></td>
                                        <td class="inputBox">
                                            <ul>
                                                <li>
                                                    <div style="margin-bottom: 10px;">
                                                        <input type="text" placeholder="우편번호" style="width: 160px;" name="zipcode" id=zipcode readonly />
                                                        <button type="button" class="addressSearchBtn">주소검색</button>
                                                    </div>
                                                    
                                                    <script>
                                                    	$(document).on("click",".addressSearchBtn",function(){
                                                    		daumZipCode();
                                                    	})
                                                    </script>
                                                </li>
                                                <li>
                                                    <div style="margin-bottom: 10px;">
                                                        <input type="text" placeholder="기본주소"  id="address1" name="address1" />
                                                    </div>
                                                </li>
                                                <li>
                                                    <div>
                                                        <input type="text" placeholder="나머지 주소(선택 입력 가능)" id="address2" name="address2" />
                                                    </div>
                                                </li>
                                            </ul>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="label">휴대전화 <span class="required">*</span></td>
                                        <td class="inputBox">
                                            <div class="phone-group">
                                                <select name="phone1">
                                                    <option>010</option>
                                                    <option>011</option>
                                                    <option>016</option>
                                                    <option>017</option>
                                                    <option>018</option>
                                                    <option>019</option>
                                                </select>
                                                <span>-</span>
                                                <input type="text" class="phone2" />
                                                <span>-</span>
                                                <input type="text" class="phone3" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="label">이메일 <span class="required">*</span></td>
                                        <td class="inputBox">
                                            <div class="email-group">
                                                <input type="text" class="email-input" />
                                                <span>@</span>
                                                <select class="domain-select">
                                                    <option>직접입력</option>
                                                    <option>naver.com</option>
                                                    <option>gmail.com</option>
                                                    <option>daum.net</option>
                                                    <option>hotmail.com</option>
                                                    <option>yahoo.co.kr</option>
                                                </select>

                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="label">배송메세지</td>
                                        <td class="inputBox">
                                            <div class="messageBox">
                                                <select class="deliveryMessage">
                                                    <option>배송메모를 선택해주세요</option>
                                                    <option>배송 전 연락 바랍니다</option>
                                                    <option>부재 시 경비실에 맡겨주세요</option>
                                                    <option>부재 시 문 앞에 놓아주세요</option>
                                                    <option>파손의 위험이 있는 상품입니다. 배송 시 주의해 주세요</option>
                                                    <option value="selfText">직접입력</option>
                                                </select>
                                            </div>
                                            <input type="text" id="deliveryText" placeholder="배송메모 직접 입력" style="display:none;" />
                                        </td>
                                    </tr>
                                </tbody>
        
                            </table>
                        </div>
                    </details>
                    
                </div>
        
                <div class="orderProduct">
                    <details open>
                        <summary>주문상품 <span>(3)</span></summary>
                        <!-- 상품 하나 -->
                        <div class="orderProduct_one">
                            <img src="../images/productimage/photocard.png" alt="상품1" />
                            <div class="productInfo">
                                <p class="productName">HI-SRARK PHOTOCARD SET ver. 1 / 2</p>
                                <p class="productOption">옵션: ver.1</p>
                                <p class="productQty">수량: 1</p>
                                <p class="productPrice">₩6,600</p>
                            </div>
                        </div>

                        <!-- 반복될 부분 -->
                        <div class="orderProduct_one">
                            <img src="../images/productimage/acrylic_keyring_YUHYUN.png" alt="상품2" />
                            <div class="productInfo">
                                <p class="productName">YUHYUN ACRYLIC KEYRING</p>
                                <p class="productOption">옵션: YUHYUN</p>
                                <p class="productQty">수량: 2</p>
                                <p class="productPrice">₩24,000</p>
                            </div>
                        </div>
                        
                        <!-- 반복될 부분 -->
                        <div class="orderProduct_one">
                            <img src="../images/productimage/acrylic_keyring_JEONGHUN.png" alt="상품3" />
                            <div class="productInfo">
                                <p class="productName">JEONGHUN ACRYLIC KEYRING</p>
                                <p class="productOption">옵션: JEONGHUN</p>
                                <p class="productQty">수량: 1</p>
                                <p class="productPrice">₩12,000</p>
                            </div>
                        </div>
                    </details>
                </div>
        
                <!-- 결제 영역 -->
                <div class="paymentBox">
                    <details open>
                        <summary>결제정보</summary>
                        <div class="paymentContent">
                            <table>
                                <tbody>
                                    <tr>
                                        <td class="label">주문상품</td>
                                        <td class="value">₩42,600</td>
                                    </tr>
                                    <tr>
                                        <td class="label">배송비</td>
                                        <td class="value">₩3,000</td>
                                    </tr>
                                    <tr>
                                        <td class="label total">최종 결제 금액</td>
                                        <td class="value total">₩45,600</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </details>
                </div>

                <div class="paymentMethodBox">
                    <details open>
                        <summary>결제수단</summary>
                        <div class="paymethodradio">
                            <input type="radio" name="paymethod" id="creditcard" value="creditcard" checked>
                            <label for="creditcard" class="paymethod">신용카드</label>
                            
                            <input type="radio" name="paymethod" id="transfer" value="transfer">
                            <label for="transfer" class="paymethod">계좌이체</label>
                            
                            <input type="radio" name="paymethod" id="virtualAccount" value="virtualAccount">
                            <label for="virtualAccount" class="paymethod">가상계좌</label>
                            
                            <input type="radio" name="paymethod" id="paidCredit" value="paidCredit">
                            <label for="paidCredit" class="paymethod">적립금</label>
                            
                            <div class="paymethod-detail" id="creditcard-detail" style="display:none">- 10/1까지 적립금 외 다른 수단의 결제가 중단됩니다.</div>
                            <div class="paymethod-detail" id="transfer-detail" style="display:none">- 10/1까지 적립금 외 다른 수단의 결제가 중단됩니다.</div>
                            <div class="paymethod-detail" id="virtualAccount-detail" style="display:none">- 10/1까지 적립금 외 다른 수단의 결제가 중단됩니다.</div>
                            <div class="paymethod-detail" id="paidCredit-detail" style="display:none">
                                <div>
                                    적립금 : 1,000,000 P
                                </div>
                                <div class="creditInputDiv">
                                    결제 금액 : <input type="text" id="paidCreditValue" value="0" min="0" readonly/>
                                    <button type="button">적용</button>
                                    <button type="button" id="payAll">전액 사용</button>
                                </div>
                                <div>
                                    결제 후 잔액 : <span>954,400</span> P
                                </div>
                            </div>
                        </div>
                        
                    </details>
                </div>

                
                <div class="paymentTermsBox">
                    <div style="padding-bottom:20px;">결제 약관 동의</div>
                    <div class="paymentTerms-check">
                        <input type="checkbox" id="allconfirm"/>
                        <label for="allconfirm">모두 동의</label>
                    </div>
                    <div class="paymentTerms-check">
                        <input type="checkbox" id="shoppingterms"/>
                        <label id="shoppingterms-details">[필수] 쇼핑몰 이용약관 동의</label>
                    </div>
                    <div class="paymentTerms-check">
                        <input type="checkbox" id="personalinfo"/>
                        <label id="personalinfo-details">[필수] 개인정보 수집 및 이용 동의</label>
                    </div>
                    <div class="paymentTerms-check">
                        <input type="checkbox" id="thirdparty"/>
                        <label id="thirdparty-details">[필수] 개인정보의 제3자 제공(업무제휴를 위한 제휴사 제공)</label>
                    </div>
                </div>

            </form>
        </div>
        
        <div class="payButtonBox"> 
            <button type="button" id="payBtn">45,600원 결제하기</button>
            <!-- 결제 클릭했는데 적립금 부족시 적립금이 부족합니다 alert -->
        </div>
    </div>

    
</body>
<script type="text/javascript" src="js/order.js"></script>
</html>
