<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원정보</title>
    <style>
        *{
            margin:0;padding:0;
            box-sizing:border-box;
            font-family: 'Pretendard';
            color:#1a1a1a;
        }

		.address{ width : 652px;}

        .order-detail-actions {
            display: flex;
            justify-content: center;  /* 가운데 정렬 */
            gap: 10px;                /* 버튼 사이 간격 */
            margin : 20px 0;
        }

        /* 버튼 공통 */
        .update, .cancel {
            display: inline-block;  /* block 대신 inline-block */
            margin: 0;              /* margin auto 제거 */
            padding: 10px 15px;
            cursor: pointer;
        }

        .update {
            background: #1a1a1a;
            color: #fff;
            border: 1px solid #ccc;
        }

        .cancel {
            background: #fff;
            color: #333;
            border: 1px solid #ccc;
        }

        .addressSearchBtn{
            width:80px;
            height:42px;
            border:1px solid #ccc;
            background-color: white;
            margin-left: 4px;
        }

        ol,ul{list-style: none;}

        button, a, summary, input[type='checkbox']{cursor:pointer;}

        body{
            width:100%;height:100%;
        }
        .mainBox{
            min-width:500px;
            max-width:900px;
            min-height:100vh;
            display: flex;
            flex-direction: column;
            border:1px solid #ccc; margin:0 auto;
            background-color: #eee;
        }

        .contentWrap{
            flex: 1;
        }

        .ordertitle{
            width:100%;height:65px;
            display:flex;align-items:center;justify-content:center;
            color:#fafafa;background-color: #1a1a1a;
            font-size:20px; font-weight: 700;
        }
        .title{
            background-color: white;
            position:relative;
            padding:16px 0;
            width:100%;height:60px;
        }
        h1{
            margin: 0 95px;
            font-size: 18px;
            text-align: center;
        }

        .hMenu{
            position:absolute;
            top:50%;
            margin-top: -14px;
        }

        .hLeft{
            left: 0;
            margin-left:10px;
        }

        .hRight{
            width:72px;
            right:0;
            margin-right:20px;
            display: flex;
            justify-content:space-between;
        }

        /* box-닫히는 요소마다 변수명 추가 */
        .addressBox,.orderProduct,.paymentBox, .paymentMethodBox, .paymentTermsBox{
            width:100%;
            border-bottom:1px solid #ccc;
            background-color: white;
            padding: 0 20px;
        }

        .orderProduct, .paymentBox, .paymentMethodBox, .paymentTermsBox{
            margin-top:20px;
        }


        .addressBox .label{
            font-size: 14px;
            vertical-align: top;
            padding-top: 18px;
        }

        /* 배송지 필수정보, 주문갯수  */
        .label .required, .orderProduct span{
            color:#035fe0;
        }

        /* 화살표누르면 접히는거 */
        summary, .paymentTermsBox{
            width:100%;
            list-style: none;
            text-align: left;
            position: relative;
            font-weight: 700;
        }

        summary{
            height:67px;
            padding: 20px 0;
        }

        .paymentTermsBox{
            padding:20px;
        }

        summary::marker {
            display: none;
        }

        summary::after {
            content: "";
            background-size: contain;
            background-repeat: no-repeat;
            width: 28px;
            height: 28px;
            display: inline-block;
            position: absolute;
            right: 0;
            top: 50%;
            transform: translateY(-50%);
        }


        #acceptant, #address1, #address2{
            width: 100%;
        }

        input[type="text"], select{
            height: 42px;
            padding: 10px 11px 10px 10px;
            border: 1px solid #ccc;
            -webkit-box-sizing: border-box;
            box-sizing: border-box;
            color: #1a1a1a;
            font-weight: 400;
            line-height: 14px;
            
        }

        table{
            width:100%;
            margin-bottom:20px;
        }

        .phone-input{width:42px;}

        .inputBox{padding:5px 0;}

        .inputBox li{margin-bottom:10px 0;}

        .phone-group,.email-group {
            display: flex;
            justify-content: space-between;
        }

        .phone-group select, .phone-group input[type="text"]{width:32%;}

        .phone-group span, .email-group span{line-height: 40px;}


        .email-input,.domain-select{width:48%;}

        .deliveryMessage, #deliveryText{
            width:100%;
        }

        #deliveryText{
            margin-top:10px;
        }

    </style>
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
    <div class="ordertitle">
        배송지 변경
    </div>
    <div class="addressBox">
        <details open>
            <summary>배송지</summary>
            <div>
                <form id="updateForm" method = "POST" action="/mypage/shopupdate">
            		<%String orderCode = request.getParameter("orderCode");%>
                    <input type="hidden" name="orderCode" value="${order.orderCode}" />
                    <table>
                        <colgroup>
                            <col width="102px">
                            <col width="*">
                        </colgroup>
                        <tbody>
                            <tr>
                                <td class="label">받는사람 <span class="required">*</span></td>
                                <td class="inputBox"><input type="text" id="acceptant" name="receiver" value="${order.receiver}"/></td>
                            </tr>
                            <tr>
                                <td class="label">주소 <span class="required">*</span></td>
                                <td class="inputBox">
                                    <ul>
                                        <li>
                                            <div style="margin-bottom: 10px;">
                                                <input type="text" placeholder="우편번호" style="width: 160px;" id = "zipcode" name="zipcode" value="${order.zipcode}" readonly />
                                                <button type="button" class="addressSearchBtn">주소검색</button>
                                            </div>
                                        </li>
                                        <li>
                                            <div style="margin-bottom: 10px;">
                                                <input type="text" placeholder="기본주소" class = "address" id = "address1" name="addressMain" value="${order.addressMain}" readonly/>
                                            </div>
                                        </li>
                                        <li>
                                            <div>
                                                <input type="text" placeholder="나머지 주소(선택 입력 가능)" class = "address" id = "address2" name="addressDetail" value="${order.addressDetail}" />
                                            </div>
                                        </li>
                                    </ul>
                                </td>
                            </tr>
                            <tr>
                                <td class="label">휴대전화 <span class="required">*</span></td>
                                <td class="inputBox">
                                    <div class="phone-group">
                                        <input type="hidden" name="phone" id="phoneHidden" value="${order.phone}" />
                                        <select id="phone1">
                                            <option>010</option>
                                            <option>011</option>
                                            <option>016</option>
                                            <option>017</option>
                                            <option>018</option>
                                            <option>019</option>
                                        </select>
                                        <span>-</span>
                                        <input type="text" class="phone-input" id="phone2" />
                                        <span>-</span>
                                        <input type="text" class="phone-input" id="phone3" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="label">배송메세지</td>
                                <td class="inputBox">
                                    <div class="messageBox">
                                        <select class="deliveryMessage" name="deliveryMessage">
                                            <option value="">배송메모를 선택해주세요</option>
                                            <option value="배송 전 연락 바랍니다">배송 전 연락 바랍니다</option>
                                            <option value="부재 시 경비실에 맡겨주세요">부재 시 경비실에 맡겨주세요</option>
                                            <option value="부재 시 문 앞에 놓아주세요">부재 시 문 앞에 놓아주세요</option>
                                            <option value="파손의 위험이 있는 상품입니다. 배송 시 주의해 주세요">파손의 위험이 있는 상품입니다. 배송 시 주의해 주세요</option>
                                            <option value="selfText">직접입력</option>
                                        </select>
                                    </div>
                                    <input type="text" id="deliveryText" name="deliveryMessageCustom" placeholder="배송메모 직접 입력" style="display:none;" value="${order.deliveryMessage}" />
                                </td>
                            </tr>
                        </tbody>
                    </table>

                    <div class="order-detail-actions">
                        <button type="button" onclick="confirmAction()" class="btn update">수정</button>
                        <button type="button" onclick="cancelAction()" class="cancel">취소</button>
                    </div>
                </form>
            </div>
        </details>
        
        <script>
        
        $(document).on("click",".addressSearchBtn",function(){
    		daumZipCode();
    	})
        
            $(document).ready(function() {
                // 전화번호 분리해서 표시
                var phone = '${order.phone}';
                if (phone) {
                    var phoneParts = phone.split('-');
                    if (phoneParts.length === 3) {
                        $('#phone1').val(phoneParts[0]);
                        $('#phone2').val(phoneParts[1]);
                        $('#phone3').val(phoneParts[2]);
                    }
                }
                
                // 배송메시지 기본값 설정
                var deliveryMsg = '${order.deliveryMessage}';
                if (deliveryMsg) {
                    var found = false;
                    $('.deliveryMessage option').each(function() {
                        if ($(this).val() === deliveryMsg) {
                            $(this).prop('selected', true);
                            found = true;
                            return false;
                        }
                    });
                    
                    if (!found) {
                        $('.deliveryMessage').val('selfText');
                        $('#deliveryText').show().val(deliveryMsg);
                    }
                }
            });

            function confirmAction() {
                // 전화번호 합치기
                var phone = $('#phone1').val() + '-' + $('#phone2').val() + '-' + $('#phone3').val();
                $('#phoneHidden').val(phone);
                
                // 배송메시지 처리
                var deliveryMessage = '';
                if ($('.deliveryMessage').val() === 'selfText') {
                    deliveryMessage = $('#deliveryText').val();
                } else {
                    deliveryMessage = $('.deliveryMessage').val();
                }
                
                $.ajax({
                    url: '/mypage/shopupdate',
                    method: 'POST',
                    data: {
                        orderCode: $('input[name="orderCode"]').val(),
                        receiver: $('input[name="receiver"]').val(),
                        phone: phone,
                        zipcode: $('input[name="zipcode"]').val(),
                        addressMain: $('input[name="addressMain"]').val(),
                        addressDetail: $('input[name="addressDetail"]').val(),
                        deliveryMessage: deliveryMessage
                    },
                    success: function(result) {
                        if (result === 'SUCCESS') {
                            alert("배송지가 수정되었습니다.");
                            if (window.opener && window.opener.showOrdersList) {
                                window.opener.showOrdersList(); // 부모창 새로고침
                            }
                            window.close();
                        } else {
                            alert("수정에 실패했습니다.");
                        }
                    },
                    error: function() {
                        alert("수정 중 오류가 발생했습니다.");
                    }
                });
            }

            function cancelAction() {
                window.close();
            }
            
            // 배송메모 선택 처리
            $('.deliveryMessage').change(function() {
                if ($(this).val() == "selfText") {
                    $("#deliveryText").css("display", "block");
                } else {
                    $("#deliveryText").css("display", "none");
                }
            });
        </script>
    </div>
</body>
</html>