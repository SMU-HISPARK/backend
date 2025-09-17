$(document).ready(function(){
	
	updateCartBadge();
	checkCartEmpty()
	
    //전체주문버튼
    function orderAllBtn(){
        orderFrm.submit();
    }

    // 품목 삭제 버튼
	$(document).on("click",".delete-btn",function(){
	    var productContainer = $(this).closest('.product-container');
	    var cartItemId = productContainer.find(".cart-checkbox").data("cartitemid");

	    $.ajax({
	        url: "/cart/delete",
	        type: "DELETE",
	        data: { cartItemId: cartItemId },
	        success: function(response) {
	            // DB 삭제 성공하면 화면에서도 삭제
	            productContainer.remove();
	            checkCartEmpty();
	            updatePrice();
	            updateCartBadge();
	        },
	        error: function() {
	            alert("삭제 중 오류가 발생했습니다.");
	        }
	    });
	});
    
    
    // 전체선택 버튼
    $(document).on("click", ".selectAll", function(){
        var checkBoxes = $('input[type="checkbox"]');     // 모든 체크박스 선택
        var allChecked = checkBoxes.filter(':checked').length == checkBoxes.length;  // 모두 체크되었나 확인
        
        checkBoxes.prop('checked', !allChecked); // 모두 체크되어있으면 체크 해제하기 
        if (allChecked) {
            $(this).text("전체선택");
        } else {
            $(this).text("선택해제");
        }
		
		updatePrice();// 가격 업데이트
		
    });//selectall
	
	$(document).on("change", 'input[type="checkbox"]', function() {
	    var allCheckBoxes = $('input[type="checkbox"]');
	    var selectAllBtn = $('.selectAll');

	    var total = allCheckBoxes.length;
	    var checked = allCheckBoxes.filter(':checked').length;

	    if (checked === total) {
	        selectAllBtn.text("선택해제"); // 모두 선택된 상태
	    } else {
	        selectAllBtn.text("전체선택"); // 일부 선택 혹은 모두 해제
	    }
	});
    
    //선택삭제
	$(document).on("click",".deleteSelected", function(){
		    if(confirm("선택한 상품을 삭제하시겠습니까?")) {
		        $('.basket input[type="checkbox"]:checked').each(function() {
		            var row = $(this).closest('.product-container');
		            var cartItemId = $(this).data('cartitemid');

		            $.ajax({
		                url: "/cart/delete",
		                type: "DELETE",
		                data: { cartItemId: cartItemId },
		                success: function(response) {
		                    row.remove();
		                    checkCartEmpty();
		                    updatePrice();
		                    updateCartBadge();
		                },
		                error: function() {
		                    alert("삭제 중 오류가 발생했습니다.");
		                }
		            });
		        });
		    }
		});//deleteSelected
	    
    
    
	
	// 1️ 페이지 로드 시 모든 체크박스 체크
    $(".basket input[type='checkbox']").prop("checked", true);

    // 2️ 전체 합계 표시
    updateTotal();
    updateSelectedTotal();

    // 3️ +- 버튼 클릭
    $(document).on("click", ".quantity-btn", function() {
        const parent = $(this).closest(".quantity-control");
        const input = parent.find(".quantity-input");
        let qty = parseInt(input.val());

        if ($(this).text() === "+") {
            qty++;
        } else if ($(this).text() === "-") {
            if (qty <= 1) {
                alert("수량은 1 이상이어야 합니다.");
                return;
            }
            qty--;
        }

        input.val(qty);

        // 단가 가져와서 테이블 금액 업데이트
        const table = $(this).closest("table");
        const unitPrice = parseInt(table.find(".unitPrice").val());
        table.find(".productprice").text((unitPrice * qty).toLocaleString() + "원");

        updateTotal();
        updateSelectedTotal();
    });

    // 4️ 수량 input 직접 입력
    $(document).on("input", ".quantity-input", function() {
        let val = parseInt($(this).val());
        if (isNaN(val) || val < 1) {
            alert("수량은 1 이상이어야 합니다.");
            $(this).val(1);
            val = 1;
        }

        const table = $(this).closest("table");
        const unitPrice = parseInt(table.find(".unitPrice").val());
        table.find(".productprice").text((unitPrice * val).toLocaleString() + "원");

        updateTotal();
        updateSelectedTotal();
    });

    // 5️ 체크박스 변경 시 선택 합계 업데이트
    $(document).on("change", '.basket input[type="checkbox"]', function() {
        updateSelectedTotal();
    });

    // 6️ 전체 합계 계산
    function updateTotal() {
        let total = 0;
        $(".product-container").each(function() {
            const priceText = $(this).find(".productprice").text().replace(/[^0-9]/g, "");
            total += parseInt(priceText);
        });
        $(".productprice-sum div:last-child").text(total.toLocaleString() + "원");
        $(".productprice-total div:last-child").text((total + 3000).toLocaleString() + "원");
    }

    // 7️ 선택 상품 합계 계산
	function updateSelectedTotal() {
	    let total = 0;
	    $(".basket input[type='checkbox']:checked").each(function() {
	        const table = $(this).closest("table");
	        const price = parseInt(table.find(".productprice").text().replace(/[^0-9]/g, ""));
	        total += price;
	    });
	
	    // 배송비: 체크된 상품이 있으면 3000원, 없으면 0원
	    const shipping = total >= 50000 ? 0 : (total > 0 ? 3000 : 0);
		var shippingtext = total >= 50000 ? "무료배송" : "3,000원";
		$(".deliveryFee").text(shippingtext);

	    // 기존 우측 영역 업데이트
	    $(".productprice-sum div:last-child").text(total.toLocaleString() + "원");
	    $(".productprice-delivery div:last-child").text(shipping.toLocaleString() + "원");
	    $(".productprice-total div:last-child").text((total + shipping).toLocaleString() + "원");

	    // 기본배송 <tr> 안의 span 업데이트
	    const basicDeliveryRow = $(".pricesum td");
	    basicDeliveryRow.find("span").eq(0).text(total.toLocaleString());       // 상품구매금액
	    basicDeliveryRow.find("span").eq(1).text(shipping.toLocaleString());    // 배송비
	    basicDeliveryRow.find("span").eq(2).text((total+shipping).toLocaleString());
		
		
		
	}
	
	
	

    //장바구니 비어있는지 확인하는 함수
    function checkCartEmpty(){
        //장바구니 내 상품 개수 확인
        var productCount = $(".product-container").length;

        //상품 0개일 때 상품이 없습니다 html 변경
        if(productCount==0){
            $(".cart-content").html('<div class="empty-cart">상품이 없습니다</div>');
        }
    }//checkCartEmpty


    
    function updatePrice() {
		let total = 0;
	    // 체크된 상품만 합산

	    $(".basket input[type='checkbox']:checked").each(function() {
	        const table = $(this).closest('table');
	        const qty = parseInt(table.find('.quantity-input').val());
	        const unitPrice = parseInt(table.find('.unitPrice').val());
	        total += qty * unitPrice;
	    });

	    const shipping = total >= 50000 ? 0 : (total > 0 ? 3000 : 0);
		var shippingtext = total >= 50000 ? "무료배송" : "3,000원";
		$(".deliveryFee").text(shippingtext);
		
	    // 오른쪽 가격 영역 업데이트
	    $(".productprice-sum div:last").text(total.toLocaleString() + "원");
	    $(".productprice-delivery div:last").text(shipping.toLocaleString() + "원");
	    $(".productprice-total div:last").text((total + shipping).toLocaleString() + "원");

	    // 상단 [기본배송] 영역도 업데이트
	    $("table.pricesum span:eq(0)").text(total.toLocaleString());
	    $("table.pricesum span:eq(1)").text(shipping.toLocaleString());
	    $("table.pricesum span:eq(2)").text((total + shipping).toLocaleString());
    }
	

	const orderFrm = $('form[name="orderFrm"]');

	// 선택 주문 버튼 수정
		$(document).on("click", "#selectOrderBtn", function(e){
		    e.preventDefault();

		    var $form = $("form[name='orderFrm']");
			$form.find("input[type='hidden'][name='cartItemIds']").remove();
			$form.find("input[type='hidden'][name='quantities']").remove();

		    var cartItemIds = [];
		    var quantities = [];

		    $(".product-container").each(function(){
		        var $row = $(this);
		        var $checkbox = $row.find(".cart-checkbox");
		        var $qtyInput = $row.find(".quantity-input");

		        if($checkbox.is(":checked")){
		            var cartItemId = $checkbox.val();
		            var quantity = $qtyInput.val() || 1;
		            
		            cartItemIds.push(cartItemId);
		            quantities.push(quantity);
		        }
		    });

		    if(cartItemIds.length === 0){
		        alert("선택한 상품이 없습니다.");
		        return;
		    }

		    // 배열을 하나씩 추가하여 순서 보장
		    for(var i = 0; i < cartItemIds.length; i++) {
		        $form.append($("<input>", {
		            type: "hidden",
		            name: "cartItemIds",
		            value: cartItemIds[i]
		        }));
		        $form.append($("<input>", {
		            type: "hidden",
		            name: "quantities", 
		            value: quantities[i]
		        }));
		    }

		    $form.submit();
		});

		// 전체 주문도 동일하게 수정
		$(document).on("click", "#allOrderBtn", function(e){
		    e.preventDefault();
		    var $form = $("form[name='orderFrm']");
		    
			$form.find("input[type='hidden'][name='cartItemIds']").remove();
			$form.find("input[type='hidden'][name='quantities']").remove();

		    var cartItemIds = [];
		    var quantities = [];

		    $(".product-container").each(function(){
		        var $row = $(this);
		        var $checkbox = $row.find(".cart-checkbox");
		        var $qtyInput = $row.find(".quantity-input");

		        var cartItemId = $checkbox.val();
		        var quantity = $qtyInput.val() || 1;
		        
		        cartItemIds.push(cartItemId);
		        quantities.push(quantity);
		    });

		    for(var i = 0; i < cartItemIds.length; i++) {
		        $form.append($("<input>", {
		            type: "hidden", 
		            name: "cartItemIds",
		            value: cartItemIds[i]
		        }));
		        $form.append($("<input>", {
		            type: "hidden",
		            name: "quantities",
		            value: quantities[i]
		        }));
		    }

		    $form.submit();
		});

	// 전체 주문 버튼
	$('#allOrderBtn').click(function() {
	    // 모든 체크박스 체크
	    orderFrm.find('input[type="checkbox"]').prop('checked', true);
	    orderFrm.submit();
	});
	
	
	
	

	function updateCartBadge() {
	    var count = $(".product-container").length;

	    if (count > 0) {
	        $(".cartBadge").text(count).show();
	    } else {
	        $(".cartBadge").hide();
	    }

	}// 카트 뱃지 업테이트
	
	
	
	
	
});