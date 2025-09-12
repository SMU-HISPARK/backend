$(document).ready(function(){
	
	updateCartBadge();
	
    //전체주문버튼
    function orderAllBtn(){
        orderFrm.submit();
    }

    // 품목 삭제 버튼
    $(document).on("click",".delete-btn",function(){
        
        //가장 가까운 product-container 위치 변수 선언
        var productContainer = $(this).closest('.product-container');
        
        productContainer.remove(); //해당 컨테이너 삭제
        checkCartEmpty();//장바구니가 비어있는지 확인
        updatePrice();// 가격 업데이트
		updateCartBadge();
        
    });//delete-btn
    
    
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
        var checkedBoxes = $('input[type="checkbox"]:checked');
        checkedBoxes.each(function(){
            $(this).closest('.product-container').remove();
        });
        
        checkCartEmpty();//장바구니가 비어있는지 확인
        updatePrice();// 가격 업데이트

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

	    // 오른쪽 가격 영역 업데이트
	    $(".productprice-sum div:last").text(total.toLocaleString() + "원");
	    $(".productprice-delivery div:last").text(shipping.toLocaleString() + "원");
	    $(".productprice-total div:last").text((total + shipping).toLocaleString() + "원");

	    // 상단 [기본배송] 영역도 업데이트
	    $("table.pricesum span:eq(0)").text(total.toLocaleString() + "원");
	    $("table.pricesum span:eq(1)").text(shipping.toLocaleString() + "원");
	    $("table.pricesum span:eq(2)").text((total + shipping).toLocaleString());
    }
	
	
	$('form[name="orderFrm"]').on('submit', function(){
	    $(this).find('input[type="checkbox"]').not(':checked').remove();
	});
	

	function updateCartBadge() {
	    var count = $(".product-container").length;

	    if (count > 0) {
	        $(".cartBadge").text(count).show();
	    } else {
	        $(".cartBadge").hide();
	    }

	}
	
	
	
	
	
});