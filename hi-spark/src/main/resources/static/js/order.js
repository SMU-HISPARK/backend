var popupWindows = []; //팝업창 연결(결제완료시 팝업창 제거용)
$(document).ready(function(){

    //좌측상단 이전페이지로 이동
    $(document).on("click", "#backBtn", function() {
        history.back();
    });// backBtn


    
    //배송메모
    $('.deliveryMessage').change(function() {
        if ($(this).val() == "selfText") {
            $("#deliveryText").css("display", "block");
        } else {
            $("#deliveryText").css("display", "none");
        }
    }); // 배송메모

    //결제수단
    $("input:radio[name=paymethod]").change(function(){
        if(this.value == '신용카드'){
            $(".paymethod-detail").css("display","none");
            $("#creditcard-detail").css("display","block");
        }else if(this.value == '가상계좌'){
            $(".paymethod-detail").css("display","none");
            $("#virtualAccount-detail").css("display","block");
        }else if(this.value == '카카오페이'){
            $(".paymethod-detail").css("display","none");
            $("#kakaoPay-detail").css("display","block");
        }else if(this.value == '적립금'){
            $(".paymethod-detail").css("display","none");
            $("#paidCredit-detail").css("display","block");
        }
    }); //결제수단

    // 모두 동의 체크박스 클릭 시
    $('#allconfirm').change(function() {
        var isChecked = $(this).is(':checked');
        $('#shoppingterms, #personalinfo, #thirdparty').prop('checked', isChecked);
    });
    
    // 개별 체크박스 클릭 시 모두 동의 해제
    $('#shoppingterms, #personalinfo, #thirdparty').change(function() {
        var totalCheckboxes = $('#shoppingterms, #personalinfo, #thirdparty').length;
        var checkedCheckboxes = $('#shoppingterms:checked, #personalinfo:checked, #thirdparty:checked').length;
        
        $('#allconfirm').prop('checked', totalCheckboxes === checkedCheckboxes);
    });//allconfirm

	
	//합계 금액
    let total = parseInt($("#totalAmount").data("total"));
	let paid = 0;
	
	//전액 사용 버튼 (input total금액으로 변경)
    $(document).on("click","#payAll",function(){
	    $("#paidCreditValue").val(total);
    }); 
	
	// 적용 버튼 (상황에 따른 alert)
	$(document).on("click", "#creditConfirm",function() {
		if (paid==1) {
		        alert("결제 예정 건입니다.");
		        return;
		}
		
	    let credit = parseInt($("#creditValue").text().replace(/,/g, ''));
	    let useAmount = parseInt($("#paidCreditValue").val());

	    if (isNaN(useAmount) || useAmount <= 0) {
	        alert("적용할 금액을 입력하세요.");
	        return;
	    }
	    if (useAmount > credit) {
	        alert("보유 적립금보다 많이 사용할 수 없습니다.");
			$("#paidCreditValue").val(0);
			if(confirm("충전하시겠습니까?")){
				location.href="/mypage/point";
			}
	        return;
	    }else if(useAmount >= total){
			if(confirm(total+" P를 차감하시겠습니까?")){
			    $("#paidCreditValue").val(total);
			    let remain = credit - total;
			    $("#creditValueAfter").text(remain.toLocaleString());
				paid = 1;
				
			}
		}
		
	});
	
	//카카오페이로 결제 버튼
	$('#kakaoPayBtn').click(function(e) {
		let isValid = true;

	    // 필수 입력값 체크
	    $('[required]').each(function() {
	        if ($(this).val().trim() === "") {
	            isValid = false;
	            return false; // each 중단
	        }
	    });
		
		if (!isValid) {
	        alert("필수 사항을 입력하셔야 결제가 가능합니다.");
			window.scrollTo({ top: 0, behavior: 'smooth' });
			e.preventDefault();
	        return;
	    }
		
		// 약관 체크
		if (!$("#allconfirm").prop('checked')) {
		    alert("필수 약관에 모두 동의하셔야 결제가 가능합니다.");
			e.preventDefault();
		    return;
		}
		
		let total = parseInt($("#totalAmount").data("total"))||0;
		let quantity = parseInt($(".countclass").data("count")) || 0;
		let cartItemIds = [];
		$('input[name="selectedItems"]').each(function() {
		    cartItemIds.push(parseInt($(this).val()));
		});
		
		let phone = $(".phone1").val()+"-"+$(".phone2").val()+"-"+$(".phone3").val()
		let deliveryMessage = $(".deliveryMessage").val(); // select에서 선택된 value
		let deliveryText = $("#deliveryText").val();       // 직접 입력한 텍스트

		// 만약 '직접입력'을 선택하면 deliveryText 사용
		if (deliveryMessage === "selfText") {
		    deliveryMessage = deliveryText;
		}
		
	    // 서버로 결제 준비 요청
		var requestData = {
		    name: "HISPARK SHOP",
		    quantity: quantity,
		    price: total,
			receiver: $("#receiver").val(),
			phone: phone,
			zipcode: $("#zipcode").val(),
			addressMain: $("#address1").val(),
			addressDetail: $("#address2").val()||"",
			cartItemIds: cartItemIds,
			deliveryMessage: deliveryMessage||"",
			shipping:$("input[name='shipping']").val()
		};
		
		console.log("보내는 데이터:", JSON.stringify(requestData));

		$.ajax({
		    url: "/kakao-pay/ready",
		    type: "POST",
			contentType:"application/json",  // 변경
			data: JSON.stringify(requestData),
		    success: function(response) {
		        window.location.href = response.next_redirect_pc_url;
		    },
		    error: function(err) {
		        alert("결제 준비 실패");
		        console.log(err);
		    }
		});

		
		paid=1;
	
		
	});
	
	
	
	

	$(document).on("click", "#payBtn", function(e) {
	    let isValid = true;

	    // 필수 입력값 체크
	    $('[required]').each(function() {
	        if ($(this).val().trim() === "") {
	            isValid = false;
	            return false; // each 중단
	        }
	    });

	    if (!isValid) {
	        alert("필수 사항을 입력하셔야 결제가 가능합니다.");
			window.scrollTo({ top: 0, behavior: 'smooth' });
			e.preventDefault();
	        return;
	    }

	    // 약관 체크
	    if (!$("#allconfirm").prop('checked')) {
	        alert("필수 약관에 모두 동의하셔야 결제가 가능합니다.");
			e.preventDefault();
	        return;
	    }
		
		if (paid == 0){
			alert("결제 수단을 선택해 주세요. (적립금의 경우 적용 버튼 클릭)");
			e.preventDefault();
			return;
		}

	});

	


});//jquery