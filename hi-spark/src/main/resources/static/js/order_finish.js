$(document).ready(function(){
	
	updateCartBadge();
	function updateCartBadge() {
	    // main/detail에서는 DOM이 없으므로 세션 값으로 가져오기
	    var count = parseInt($("#cartCountHidden").val()) || 0; // hidden input 또는 data 속성 사용
	    if (count > 0) {
	        $(".cartBadge").text(count).show();
	    } else {
	        $(".cartBadge").hide();
	    }
	}
}